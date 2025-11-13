package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.dto.RegistrazioneUtenteDto;
import com.giustizia.mediazionecivile.service.UserService;
import com.giustizia.mediazionecivile.utility.VerificaPIva;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private UserService userService;
	
/*	// DA ELIMINARE UNA VOLTA CHE IL DB ORACLE SARA ATTIVO
	@GetMapping("/inizializzaDatiTabTemporanea")
	public ResponseEntity<Object> inizializzaDatiTabTemporanea(Authentication authentication) {
		HashMap<String, Object> response = new HashMap<>();
		userService.temporaneo();
		return ResponseEntity.ok(response);
	}*/

	// Serve per avviare tutte le impostanzioni di sicurezza che veranno passate al
	// frontend
	@GetMapping("/startSecurity")
	public ResponseEntity<Object> startSecurity() {
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

	@PostMapping("/registrazioneUtente")
	public ResponseEntity<Object> registrazioneUtente(@RequestBody RegistrazioneUtenteDto registrazioneUtenteDto) {
		
		VerificaPIva verificaPIva = new VerificaPIva();
		try {
			// Se non c'è nessuno utente salvato dalla risposta dello spid, il metodo andrà
			// in errore
			cacheManager.getCache("cacheVE").get("utenteIAMG").get();
			if (verificaPIva.verificaPIva(registrazioneUtenteDto.getpIva()) == true) { 
			return ResponseEntity.ok(userService.registrazioneUtente(registrazioneUtenteDto));
			} else {
				return ResponseEntity.badRequest().body("Inserire una partita iva valida");
			}
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

}
