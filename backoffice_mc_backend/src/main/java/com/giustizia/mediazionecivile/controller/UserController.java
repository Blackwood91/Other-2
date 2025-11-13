package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.configurations.ParametersConfigurations;
import com.giustizia.mediazionecivile.dto.RegistrazioneUtenteDto;
import com.giustizia.mediazionecivile.model.UserLogin;
import com.giustizia.mediazionecivile.service.UserService;
import com.giustizia.mediazionecivile.utility.VerificaPIva;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private UserService userService;
	@Autowired
	ParametersConfigurations parametersConfigurations;
	
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
	
	@GetMapping("/getAllRegistrazioniUtente")
	  public ResponseEntity<Object> getAllRegistrazioniUtente(
	            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
	            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {	
        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));

		return ResponseEntity.ok(userService.getAllRegistrazioniUtente(pageable, searchText));
	}
	
    @GetMapping("/getAllUtenti")
    public ResponseEntity<Object> getAllSocieta(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
        HashMap<String, Object> response = userService.getAllUtenti(pageable, searchText);

        return ResponseEntity.ok(response);
	}
	
	@PutMapping("/approvaUtente")
	public ResponseEntity<Object> approvaUtente(@RequestParam(name = "idUtente") long idUtente) {
		userService.approvaUtente(idUtente);
		
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/rifutaUtente")
	public ResponseEntity<Object> rifutaUtente(@RequestParam(name = "idUtente") long idUtente) {
		userService.rifutaUtente(idUtente);
		
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/abilitaUtente")
	public ResponseEntity<Object> abilitaUtente (@RequestParam (name = "idUtente") long idUtente) {
		userService.abilitaUtente(idUtente);
		
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/disabilitaUtente")
	public ResponseEntity<Object> disabilitaUtente (@RequestParam (name = "idUtente") long idUtente) {		
		userService.disabilitaUtente(idUtente);	
		
        return new ResponseEntity<>(HttpStatus.OK);
	}

}
