package giustiziariparativa.backoffice.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import giustiziariparativa.backoffice.DTO.CreateUtenteRequest;
import giustiziariparativa.backoffice.model.MediatoreGiustiziaRiparativa;
import giustiziariparativa.backoffice.model.RegistroOperazioneUtente;
import giustiziariparativa.backoffice.model.UtenteAbilitato;
import giustiziariparativa.backoffice.service.UtenteAbilitatoService;
import giustiziariparativa.configurations.ParametersConfigurations;
import giustiziariparativa.util.TipoOperazione;

@RestController
@RequestMapping("utente-abilitato")
//@CrossOrigin
public class UtenteAbilitatoRestController {
	@Autowired
	ParametersConfigurations parametersConfigurations;

	@Autowired
	UtenteAbilitatoService utenteAbilitatoService;

	/*
	 * @GetMapping("/getAllUtenti")
	 * public ResponseEntity<List<UtenteAbilitato>> getAllUtenti() {
	 * return new ResponseEntity<>(utenteAbilitatoService.getAllUtentiAbilitati(),
	 * HttpStatus.OK);
	 * }
	 */

	// metodo che prende tutti gli utenti
	@GetMapping("/getAllUtenti")
	public ResponseEntity<Object> getAllUtenti(
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
			@RequestParam(value = "colonna", required = false, defaultValue = "") String colonna) {

		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		Page<UtenteAbilitato> utentiElenco;
		// Gestione Pagine di riferimento Tabella
		/*
		 * if (colonna.isEmpty() == false) { Sort sort =
		 * Sort.by(Sort.Order.asc(colonna)); // Specifica l'ordinamento qui pageable =
		 * PageRequest.of(Integer.parseInt(indexPage),
		 * Integer.parseInt(parametersConfigurations.getRowsTable()), sort); } else {
		 * pageable = PageRequest.of(Integer.parseInt(indexPage),
		 * Integer.parseInt(parametersConfigurations.getRowsTable())); }
		 */

		if (searchText.isEmpty()) {
			utentiElenco = utenteAbilitatoService.getAllUtentiAbilitati(pageable, searchText, colonna);
		} else {
			utentiElenco = utenteAbilitatoService.getAllUtentiAbilitatiByText(pageable, searchText, colonna);
		}

		HashMap<String, Object> response = new HashMap<>();
		response.put("result", utentiElenco.getContent());
		response.put("totalResult", utentiElenco.getTotalElements());
		return ResponseEntity.ok(response);

		// return ResponseEntity<Object>(mediatoriElencoPubblico, HttpStatus.OK);
	}

	// metodo per inserire gli utenti
	@PostMapping("/getAllutentiPost")
	public ResponseEntity<Object> inserisciUtenza(@RequestBody UtenteAbilitato utente) {
		try {
			Map<String, String> response = new HashMap<>();
			utenteAbilitatoService.inserisciUtenza(utente);
			response.put("message", "INSERIMENTO RIUSCITO");
			// elezioniService.inserisciElezione(elezione);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("ERRORE NELLA MODIFICA");
		}
	}

	// metodo che aggiorna utenti
	@PostMapping("/UpdateUtenti")
	public ResponseEntity<Object> Utenti(@RequestBody UtenteAbilitato utente) {

		try {
			Map<String, String> response = new HashMap<>();
			utenteAbilitatoService.UpdateUtenti(utente);
			response.put("message", "MODIFICA RIUSCITA");
			// utenteAbilitatoService.UpdateUtenti(utente);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("ERRORE NELLA MODIFICA");
		}

	}

	@GetMapping("/getUtenteByCodiceFiscale")
	public ResponseEntity<UtenteAbilitato> getUtenteByCodiceFiscale(@RequestParam String codiceFiscaleUtente) {
		// UtenteAbilitato utente =
		// utenteAbilitatoService.findUtenteByCodiceFiscale(codiceFiscaleUtente);
		return null;
	}
	// if (utente != null) {
	// return new ResponseEntity<>(utente, HttpStatus.OK);
	// } else {
	// return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Imposta uno stato HTTP
	// 404 (Not Found)
	// }
	// }

	@PostMapping("/createUtente")
	public ResponseEntity<UtenteAbilitato> createUtente(@RequestBody CreateUtenteRequest request) {
		UtenteAbilitato utenteCreato = utenteAbilitatoService.createUtente(
				request.getNomeUtente(),
				request.getCognomeUtente(),
				request.getCodiceFiscaleUtente(),
				request.getEnteAppartenenza(),
				request.getIdRuoloUtente(),
				request.getIsAbilitato());

		if (utenteCreato != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(utenteCreato);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// la createUtente
	// l'assegnaRuolo
	@PostMapping("/setCreateUtente")
	public ResponseEntity<Object> setCreateUtente(@RequestBody UtenteAbilitato utente) {
		try {
			Map<String, String> response = new HashMap<>();
			utenteAbilitatoService.createUtente(utente.getNomeUtente(), utente.getCognomeUtente(),
					utente.getCodiceFiscaleUtente(), utente.getEnteAppartenenza(), utente.getIdRuoloUtente(),
					utente.getIsAbilitato());
			response.put("message", "INSERIMENTO RIUSCITA");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("ERRORE INSERIMENTO");
		}
	}

	@GetMapping("/verificaCodiceFiscaleUtenti")
	public ResponseEntity<Object> verificaCodiceFiscaleUtenti(@RequestParam String codiceFiscale) {
		try {
			Map<String, String> response = new HashMap<>();

			// Esegui la verifica del codice fiscale nel tuo database
			boolean codiceFiscaleEsiste = verificaCodiceFiscaleNelDatabase(codiceFiscale);

			if (codiceFiscaleEsiste) {
				response.put("message", "Il codice fiscale è già esistente.");
			} else {
				response.put("message", "");
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("ERRORE NELLA VERIFICA DEL CODICE FISCALE");
		}
	}

	private boolean verificaCodiceFiscaleNelDatabase(String codiceFiscale) {

		String codiceFiscaleEsiste = utenteAbilitatoService.findCodiceFiscale(codiceFiscale);
		if (codiceFiscaleEsiste == "" || codiceFiscaleEsiste == null || codiceFiscaleEsiste.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	@GetMapping("/getUltimaModificaUtente")
	public ResponseEntity<Object> getUltimaModificaUtente() {
		try {
			Map<String, String> response = new HashMap<>();

			String ultimaModifica = utenteAbilitatoService.getDataUltimaModificaUtenteMax();
			response.put("dataUltimaModifica", ultimaModifica);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping("/getUltimoDisabilitaUtente")
	public ResponseEntity<Object> getUltimoDisabilitaUtente() {
		try {
			Map<String, String> response = new HashMap<>();

			String ultimaModifica = utenteAbilitatoService.getDataUltimoDisabilitaUtenteMax();
			response.put("dataUltimaModifica", ultimaModifica);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
