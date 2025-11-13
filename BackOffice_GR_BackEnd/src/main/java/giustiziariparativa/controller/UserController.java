package giustiziariparativa.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
//@CrossOrigin
public class UserController {
	@Autowired
	private CacheManager cacheManager;
	
	// Serve per avviare tutte le impostanzioni di sicurezza che veranno passate al frontend 
	@GetMapping("/startSecurity")
	public ResponseEntity<Object>startSecurity() {
        HashMap<String, String> response = new HashMap<>();
        response.put("esito", "Successo");
		return ResponseEntity.ok(response);
	}

	// Ritorna l'utente che ha fatto l'autenticazione ed è rimasto salvato
	@GetMapping("/utentLoggato")
	public ResponseEntity<Object> userAutenticato(Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("utente", cacheManager.getCache("cacheVE").get("utenteIAMG").get());
		return ResponseEntity.ok(response);
	}
}
