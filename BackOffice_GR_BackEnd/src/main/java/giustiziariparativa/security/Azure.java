package giustiziariparativa.security;

import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class Azure {

	@Value("${leonardo.principal_uri:}")
	protected String PRINCIPAL_URI; 
	protected final static String GRANT_TYPE = "authorization_code";
	@Value("${leonardo.redirect_uri:}")
	protected String REDIRECT_URI; // PRODUZIONE
	@Value("${leonardo.api_fe:}")
	protected  String API_FE; 
	@Value("${leonardo.client_id_fe:}")
	protected  String CLIENT_ID_FE;
	@Value("${leonardo.code_verifier_fe:}")
	protected  String CODE_VERIFIER_FE;
	@Value("${leonardo.api_be:}")
	protected  String API_BE; 
	@Value("${leonardo.client_id_be:}")
	protected  String CLIENT_ID_BE;
	@Value("${leonardo.code_verifier_be:}")
	protected  String CODE_VERIFIER_BE;
	
	
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
			return utente;
        } else {
            System.out.println("Il token JWT non è valido.");
            return utente;
        }
	}
}
