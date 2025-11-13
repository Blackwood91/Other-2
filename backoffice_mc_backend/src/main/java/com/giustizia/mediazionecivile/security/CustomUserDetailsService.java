package com.giustizia.mediazionecivile.security;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
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

import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.model.UserLogin;
import com.giustizia.mediazionecivile.repository.UserLoginRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private Azure azure;
	@Autowired
	private UserLoginRepository userLoginRepository;
	@Autowired
    private ApiFileService apiFileService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			// PARAMETRI PER LA RICHIESTA DI AUTORIZZAZIONE DELL'AZURE		
			String code = username;
			String api = azure.API;
			String clientId = azure.CLIENT;
			String codeVerifer = azure.CODE_VERIFIER;

			RestTemplate restTemplate = new RestTemplate();
			// URL DI RICHISTA
			String url = azure.PRINCIPAL_URI + api;

			MultiValueMap<String, String> body = new LinkedMultiValueMap();
			body.add("grant_type", azure.GRANT_TYPE);
			body.add("client_id", clientId);
			body.add("code", code);
			body.add("redirect_uri", azure.REDIRECT_URI);
			body.add("code_verifier", codeVerifer);

			ResponseEntity<String> response = restTemplate.postForEntity(url, body, String.class);

			// CHECK PER VEDERE SE LA RISPOSTA NON CONTENGA ERRORI
			if (response.getStatusCode().is2xxSuccessful()) {
				// RISPOSTA IN JSON
				JSONObject jsonObject = new JSONObject(response.getBody());
				String jwt = jsonObject.getString("id_token");
				UtenteLoggato utente = azure.convertJwtToken(jwt);

				// CONTROLLO UTENTE AUTORIZZATO
				UserLogin utenteLogin = userLoginRepository.findByCodiceFiscale(utente.getCodiceFiscale());
				
				if(utenteLogin != null) {
					if(utenteLogin.getIsAbilitato() == 2) {
						throw new RuntimeException("-ErrorAccountDisabled");
					}
					else if(utenteLogin.getIsAbilitato() == 3) {
						throw new RuntimeException("-ErrorAccountInApprovazione");
					}
					else if (utenteLogin.getIdRuolo() != 1) {
						throw new RuntimeException("-ErrorAccountRuolo");
					}
				}

				// SALVATAGGIO DELL'UTENTE NELLA CACHE
				cacheManager.getCache("cacheVE").put("utenteIAMG", utente);
				
				if (utenteLogin != null && utenteLogin.getIsAbilitato() == 1) {
					// LOGIN AL DOCUMENTALE DI MERCURIO
					try {
						apiFileService.loginMercurio();
					} catch (Exception e) {
						throw new RuntimeException("-ErrorMercurio");
					}

					// SET DEL RUOLO NEL ISTANZA DELL'UTENTE
					utente.setRuolo(String.valueOf(utenteLogin.getIdRuolo()));
					return org.springframework.security.core.userdetails.User.builder().username(username)
							.password("utenteIAMG").roles(utente.toString()) // SET DEL RUOLO
																										// NEI PERMESSI
																										// DEL RUOLO
							.build();
				} else {
					// IN QUESTA CASISTICA VERRA VALORIZZATA ANCHE LA CACHE PER LA PRECOMPILAZIONE DEI CAMPI DI REGISTRAZIONE
					throw new RuntimeException("-ErrorAccountNotRegistered");

				}

			} else {
				throw new RuntimeException("-ErrorMercurio");
			}
		} catch (Exception e) {
			if (e.getMessage().contains("-ErrorMercurio")) {
				System.out.println("errore generato: " + e.toString());
				throw new UsernameNotFoundException("Si è verificato un errore di autenticazione con il documentale di mercurio");
			}
			else if (e.getMessage().contains("-ErrorAccountDisabled")) {
				throw new UsernameNotFoundException("L'account è stato disabilitato quindi non è possibile proseguire con l'accesso");
			}
			else if (e.getMessage().contains("-ErrorAccountInApprovazione")) {
				throw new UsernameNotFoundException("L'account è in attesa di approvazione quindi non è possibile proseguire con l'accesso");
			}
			else if (e.getMessage().contains("-ErrorAccountNotRegistered")) {
				throw new UsernameNotFoundException("L'account non è registrato");
			}
			else if (e.getMessage().contains("-ErrorAccountRuolo")) {
				throw new UsernameNotFoundException("Il ruolo dell'account non è abilitato");
			}
			else {
				throw new UsernameNotFoundException("Si è verificato un errore non previsto nell'autenticazione: " + e);
			}
		}
	}
				  
}
