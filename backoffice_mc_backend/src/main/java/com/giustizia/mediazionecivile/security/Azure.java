package com.giustizia.mediazionecivile.security;

import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class Azure {
	@Value("${leonardo.principal_uri:}")
	protected String PRINCIPAL_URI;
	protected final static String GRANT_TYPE = "authorization_code";
	@Value("${leonardo.redirect_uri:}")
	protected String REDIRECT_URI;
	@Value("${leonardo.api:}")
	protected String API;
	@Value("${leonardo.client_id:}")
	protected String CLIENT;
	@Value("${leonardo.code_verifier:}")
	protected String CODE_VERIFIER;

	
	protected UtenteLoggato convertJwtToken(String jwtToken) {
		UtenteLoggato utente = new UtenteLoggato();
		// Il payload nel token JWT è la seconda parte del token, separata da un punto
		String[] tokenParts = jwtToken.split("\\.");

		// Assicura che il token abbia tre parti
		if (tokenParts.length == 3) {
			// Decodifica il payload (Info utente IANG) come una stringa Base64
			String payloadBase64 = tokenParts[1];
			String payloadJson = new String(Base64.getDecoder().decode(payloadBase64));

			// Crea un oggetto JSON dal payload
			JSONObject payloadObject = new JSONObject();
			try {
				payloadObject = new JSONObject(payloadJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Accedi ai dati nel payload
			utente.setCodiceFiscale(payloadObject.optString("fiscalNumber"));
			utente.setNome(payloadObject.optString("given_name"));
			utente.setCognome(payloadObject.optString("family_name"));
			// Terrà conto se l'utente è adn o no
			utente.setIsAdn(payloadObject.optString("ADN_User").isEmpty() == true ? "false" : "true");
			return utente;
		} else {
			System.out.println("Il token JWT non è valido.");
			return utente;
		}
	}
}