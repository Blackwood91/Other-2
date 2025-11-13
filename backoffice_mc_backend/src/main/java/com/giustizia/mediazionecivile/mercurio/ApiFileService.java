package com.giustizia.mediazionecivile.mercurio;
import java.io.ByteArrayInputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiFileService {
	@Autowired
	Mercurio mercurio;
	@Autowired
	MercurioSessionParameters sessionParameters;

	/**
	 * Con il metodo loginMercurio Accederà con le credenziali preimpostate al documentale di mercurio,
	 * cio è necessario per aver accesso alle api 
	 * @param jsonObject.getString("token") Sarebbe il jwt che torna dalla risposta, esso servirà alla valorizzazione
	 * del BearerAuth conuto nel header delle richieste api
	 */
	public void loginMercurio() {	
		RestTemplate restTemplate = new RestTemplate();
		// DICHIARAZIONE DEL TIPO DEL FORMATO NELLA HEADER DELLA RICHIESTA
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// SET DEL CORPO DELLA RICHIESTA
		JSONObject body = new JSONObject();
		body.put("application", mercurio.getApplication());
		body.put("company", "");
		body.put("username", mercurio.getUser());
		body.put("password", mercurio.getPassword());
		String uri = mercurio.getPrincipalUri() + "mercurio-auth/api/v1/login";
		HttpEntity<String> requestEntity = new HttpEntity<>(body.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		// Check the response
		if (response.getStatusCode().is2xxSuccessful()) {
			// Formattazione della risposta del JSON
			JSONObject jsonObject = new JSONObject(response.getBody());
			sessionParameters.setBarerToken(jsonObject.getString("token"));	
		}
		else {
			throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
		}
	}
	
	/**
	 * Con il metodo insertFile tornera una istanza valorizzata della classe MercurioFile,
	 * che conterrà il documentIdClient e il contentId che serviranno per il recupero del file
	 * inserito nel documentale
	 * @param path Percorso dove verrà salvato il documento nel documentale
	 * @param name Nome del file senza specificare l'estensione
	 * @param file Il file di ritorno, non e necessaria la valorizzazione al fine della logica costruita
	 */
	public MercurioFile insertFile(String path, String name, byte[] file) {
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		String boundary = "MyBoundary"; // Imposta il limite personalizzato
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("boundary", boundary);
		headers.setBearerAuth(sessionParameters.getBarerToken());

		// Crea il corpo della richiesta multipart/form-data
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("payload", "{\"path\":\"" + path + "\",\"title\":\"" + name + "\"}");
		body.add("attachment", file);
		// Crea l'entità HttpEntity con intestazioni e corpo
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		String uri = mercurio.getPrincipalUri() + "mercurio-contents/api/v1/documents/" + mercurio.getDomainCode() + "/" + mercurio.getApplicationCode() + "/" 
				 + mercurio.getContext() +  "/create/stream" ;
		
		// Esegui la richiesta usando RestTemplate
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
        // CHECK PER VEDERE SE LA RISPOSTA NON CONTENGA ERRORI
		if (response.getStatusCode().is2xxSuccessful()) {
			JSONObject jsonObject = new JSONObject(response.getBody());
		     // RECUPERO PARZIALE DEI PARAMETRI INFO DEL DOCUMENTO INSERITO
            String documentIdClient = jsonObject.getJSONObject("payload").getString("documentIdClient");
            
		     // RECUPERO COMPLETO DEI PARAMETRI INFO DEL DOCUMENTO INSERITO
            HttpHeaders headersAllInf = new HttpHeaders();
            headersAllInf.setBearerAuth(sessionParameters.getBarerToken());
            
    		String uriAllInfoDocumento = mercurio.getPrincipalUri() + "mercurio-contents/api/v1/documents/" + mercurio.getDomainCode() + "/" + 
    									 mercurio.getApplicationCode() + "/"  + documentIdClient;
    		ResponseEntity<String> responseAllInfoDocumento = restTemplate.exchange(uriAllInfoDocumento, HttpMethod.GET, new HttpEntity<>(headersAllInf), String.class);
    		if (responseAllInfoDocumento.getStatusCode().is2xxSuccessful()) {
    			// Parsa la stringa JSON
    			JSONObject jsonObjectAllInf = new JSONObject(responseAllInfoDocumento.getBody());
				int contentId = jsonObjectAllInf
						        .getJSONObject("payload")
						        .getJSONObject("document")
						        .getJSONObject("currentVersion")
						        .getJSONArray("contents")
						        .getJSONObject(0)
						        .getJSONObject("documentContent")
						        .getJSONObject("key")
						        .getInt("contentId");
    		    
    			return new MercurioFile(documentIdClient, contentId, null);			
    		}
    		else {
    			throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
    		}	
		}
		else {
			throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
		}	
	}
	
	/**
	 * Con il metodo getFile tornerà dirrettamente un array di byte,
	 * esso è pensato per la lettura dei file che veranno passati nella parte front end
	 * @param documentIdClient Parametro fondamentale per la presa del file nel documentale
	 * @param contentId Secondo parametro fondamentale per la presa del file nel documentale
	 */
	public byte[] getFile(String documentIdClient, Integer contentId) {
		try {
			RestTemplate restTemplate = new RestTemplate();
	        // Set del barerToken per l'autorizzazione nell'header Authorization
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(sessionParameters.getBarerToken());
	
			String uri = mercurio.getPrincipalUri() + "mercurio-contents/api/v2/documents/" + mercurio.getDomainCode() + "/" + mercurio.getApplicationCode() + "/" 
						 + documentIdClient + "/content/" + contentId + "/session/download/start" ;
	        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
			// Check the response
			if (response.getStatusCode().is2xxSuccessful()) {
				JSONObject jsonObject = new JSONObject(response.getBody());
				String uriFile = jsonObject.getJSONObject("payload").getString("urlDownload");	
	
		        ResponseEntity<byte[]> responseFile = restTemplate.exchange(uriFile, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
				// Check the response
		        if (responseFile.getStatusCode().is2xxSuccessful()) {
		            byte[] fileContent = responseFile.getBody();
		            return fileContent;
		        } else {
					throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
		        }
				
			}
			else {
				throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
			}
		}
		catch(Exception e) {
			if(e.getClass().equals(Unauthorized.class) || e.getClass().equals(ResourceAccessException.class)) {
				loginMercurio();
				return getFile(documentIdClient, contentId);
			}
			throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
		}
	}

	/**
	 * Con il metodo getFilePdf tornerà dirrettamente un tipo InputStreamResource,
	 * esso è pensato per la costruzione dei file pdf che servirà per la formattazione
	 * e precompilazione del modulo ufficiale
	 * @param documentIdClient Parametro fondamentale per la presa del file nel documentale
	 * @param contentId Secondo parametro fondamentale per la presa del file nel documentale
	 */
	public InputStreamResource getFilePdf(String documentIdClient, Integer contentId) {
		try {
			RestTemplate restTemplate = new RestTemplate();
	        // Set del barerToken per l'autorizzazione nell'header Authorization
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(sessionParameters.getBarerToken());
			MultiValueMap<String, String> body = new LinkedMultiValueMap();
			body.add("generatepresignedlink", "false");
	
			String uri = mercurio.getPrincipalUri() + "mercurio-contents/api/v2/documents/" + mercurio.getDomainCode() + "/" + mercurio.getApplicationCode() + "/" 
						 + documentIdClient + "/content/" + contentId + "/session/download/start" ;
	        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
			// Check the response
			if (response.getStatusCode().is2xxSuccessful()) {
				JSONObject jsonObject = new JSONObject(response.getBody());
				String uriFile = jsonObject.getJSONObject("payload").getString("urlDownload");	
	
		        ResponseEntity<byte[]> responseFile = restTemplate.exchange(uriFile, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
				// Check the response
		        if (responseFile.getStatusCode().is2xxSuccessful()) {
		            byte[] fileContent = responseFile.getBody();
		            // Crea un InputStreamResource utilizzando il contenuto del file della risposta
		            return new InputStreamResource(new ByteArrayInputStream(fileContent));
		        } else {
					throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
		        }
				
			}
			else {
				throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
			}	
		}
		catch(Exception e) {
			if(e.getClass().equals(Unauthorized.class) || e.getClass().equals(ResourceAccessException.class)) {
				loginMercurio();
				return getFilePdf(documentIdClient, contentId);
			}
			throw new RuntimeException("-ErrorInfo Si è verificato un errore con la comunicazione con il documentale");
		}
	}
	
	/**
	 * Con il existFile getFile tornerà dirrettamente un boolean in caso di esistenza o no del file 
	 * associato
	 * @param documentIdClient Parametro fondamentale per la presa del file nel documentale
	 * @param contentId Secondo parametro fondamentale per la presa del file nel documentale
	 */
	public boolean existFile(String documentIdClient, Integer contentId) {
		try {
			RestTemplate restTemplate = new RestTemplate();
	        // Set del barerToken per l'autorizzazione nell'header Authorization
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(sessionParameters.getBarerToken());
			MultiValueMap<String, String> body = new LinkedMultiValueMap();
			body.add("generatepresignedlink", "false");
	
			String uri = mercurio.getPrincipalUri() + "mercurio-contents/api/v2/documents/" + mercurio.getDomainCode() + "/" + mercurio.getApplicationCode() + "/" 
						 + documentIdClient + "/content/" + contentId + "/session/download/start" ;
	        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
			// Check the response
			if (response.getStatusCode().is2xxSuccessful()) {
				JSONObject jsonObject = new JSONObject(response.getBody());
				String errorCode = jsonObject.getJSONObject("summary").getString("code");
				// SE LE CONDIZIONE E' POSITIVA SIGNIFICA CHE NON E' STATO TROVA NESSUN FILE ASSOCIATO
				if(errorCode.equalsIgnoreCase("true")) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return false;
			}	
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Con il metodo deleteFile verra cancellato il documento specificato nel documentIdClient,
	 * e tornerà un boolean caso di esito positivo o negativo
	 * necessita solo di questo per la cancellazione
	 * @param documentIdClient Parametro fondamentale per la specifica del file nel documentale
	 */
	public boolean deleteFile(String documentIdClient) {
		try {
			RestTemplate restTemplate = new RestTemplate();
	        // Set del barerToken per l'autorizzazione nell'header Authorization
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(sessionParameters.getBarerToken());
	
			//https://middlewarecoll-documentale.giustizia.it/mercurio-contents/api/v1/documents/AMMINISTRATIVO/MEDCIV/MEDCONTEXT/MINGG-20240325-4-24-1-1-000415612T/remove 
			String uri = mercurio.getPrincipalUri() + "mercurio-contents/api/v1/documents/" + mercurio.getDomainCode() + "/" + mercurio.getApplicationCode() + "/" 
						 + mercurio.getContext() + "/" + documentIdClient + "/remove" ;
	        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
			// Check the response
			if (response.getStatusCode().is2xxSuccessful()) {
				return true;
			}	
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}


}
