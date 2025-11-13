package giustiziariparativa.security;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import giustiziariparativa.backoffice.repository.UtenteAbilitatoRepository;
import io.jsonwebtoken.Claims;
import io.netty.handler.codec.http.HttpMethod;
import reactor.core.publisher.Mono;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private WebClient webClient;
    private PasswordEncoder passwordEncoder;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private UtenteAbilitatoRepository utenteAbilitatoRepository;
	@Autowired
	private Azure azure;

    // @Lazy annotazione necessaria per non avere problemi di ciclo con il bene e l'implementazione durante l'avvio di spring 
    public CustomUserDetailsService(WebClient.Builder webClientBuilder, @Lazy PasswordEncoder passwordEncoder, Azure azure) {
        this.webClient = webClientBuilder.baseUrl(azure.PRINCIPAL_URI).build();
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
        	// Split sul username che conterrà code per token e client della chiamata (frontoffice o backoffice)
        	String[] paramsLogin = username.split("-client");
        	String code = paramsLogin[0];
        	String client = paramsLogin[1];
        	
        	String api = "";
        	String clientId = "";
        	String codeVerifer = "";
       	
        	// Set dei dati a seconda se (frontoffice o backoffice)
        	if(client.equalsIgnoreCase("FE")) {
        		api = azure.API_FE;
        		clientId = azure.CLIENT_ID_FE;
        		codeVerifer = azure.CODE_VERIFIER_FE;
        	}
        	else if(client.equalsIgnoreCase("BE")) {
        		api = azure.API_BE;
        		clientId = azure.CLIENT_ID_BE;
        		codeVerifer = azure.CODE_VERIFIER_BE;
        	}
        	else {
        		throw new Exception("richiesta non valida");
        	}
        	
	        RestTemplate restTemplate = new RestTemplate();
	        // Crea un oggetto HttpHeaders con l'intestazione "Content-Type" impostata su "application/x-www-form-urlencoded"
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        // Specifica l'URL insieme all'api di destinazione
	        String url = azure.PRINCIPAL_URI + api;
	
	        MultiValueMap<String, String> body = new LinkedMultiValueMap();
	        body.add("grant_type", azure.GRANT_TYPE);
	        body.add("client_id", clientId);
	        body.add("code", code);
	        body.add("redirect_uri", azure.REDIRECT_URI);
	        body.add("code_verifier", codeVerifer);

	        ResponseEntity<String> response = restTemplate.postForEntity(url, body, String.class);
        		
	        // Check the response
	        if (response.getStatusCode().is2xxSuccessful()) {
	        	// Analizza la risposta JSON
	        	JSONObject jsonObject = new JSONObject(response.getBody());
	        	String jwt = jsonObject.getString("id_token");
	        	UtenteLoggato utente = azure.convertJwtToken(jwt);
	        	
	        	// Controllo utente autorizzato nel db
	        	Long ruolo = utenteAbilitatoRepository.utentiAbilitato(utente.getCodiceFiscale());
	        	if(ruolo == null) {
					return (UserDetails) new UsernameNotFoundException("Utente non abilitato a nessun ruolo");
	        	}
	        	else if(client.equalsIgnoreCase("BE") && ruolo == 3) {
					return (UserDetails) new UsernameNotFoundException("Utente non abilitato al portale di frontoffice");
	        	}
	        		
	        	utente.setRuolo(ruolo.toString());
	        	
	        	// Salva nella cache l'utente loggato
				cacheManager.getCache("cacheVE").put("utenteIAMG", utente);

	        	// Autorizzazione di accesso con ruolo specificato
	            return org.springframework.security.core.userdetails.User.builder()
	                    .username(username)
	                    .password(passwordEncoder.encode("utenteIAMG")) // Imposta la password dell'utente
	                    .roles(ruolo.toString()) // Assegna ruoli all'utente se necessario
	                    .build();
	        } 
	        else {
	    		System.out.println("Accesso negato dalla risposta AZURE:");
				return (UserDetails) new UsernameNotFoundException("Accesso negato");
	        }
        }
        catch(Exception e) {
  			System.out.println("Si è verificato un errore non previsto nell'autenticazione del token: " + e);
			return (UserDetails) new UsernameNotFoundException("Accesso negato");
        }
    }
}
