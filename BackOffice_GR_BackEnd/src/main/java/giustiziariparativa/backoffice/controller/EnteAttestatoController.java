package giustiziariparativa.backoffice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import giustiziariparativa.backoffice.model.EnteAttestato;
import giustiziariparativa.backoffice.service.EnteAttestatoService;
import giustiziariparativa.configurations.ParametersConfigurations;

@RestController
@RequestMapping("ente")
//@CrossOrigin
public class EnteAttestatoController {

	@Autowired
	ParametersConfigurations parametersConfigurations;
    @Autowired
    EnteAttestatoService enteAttestatoService;

    @GetMapping("/getAllEnti")
    public ResponseEntity<Object>getAllEnti(@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
											@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
											@RequestParam(value = "colonna", required = false, defaultValue = "") String colonna) {
	    
    	Map<String, Object> response = new HashMap<>();    	
	    Map<String, Object> ente = enteAttestatoService.getAllEnti();

	    response.put("result", ente.get("result"));
		return ResponseEntity.ok(response);
    }

    
    @GetMapping("/getEnteId")
    public ResponseEntity<EnteAttestato> findEnteAttestatoById(@RequestParam Long id) {
        return ResponseEntity.ok(enteAttestatoService.findEnteAttestatoById(id));
    }
    
    @GetMapping("/getAllEntiFormatore")
    public ResponseEntity<Object>getAllEntiFormatore(@RequestParam("enteFormatore") String enteFormatore) {
	    
    	Map<String, Object> response = new HashMap<>();    	
	    Map<String, Object> ente = enteAttestatoService.getAllEntiFormatore(enteFormatore);

	    response.put("result", ente.get("result"));
		return ResponseEntity.ok(response);
    }


    
    @PostMapping("/setInserisciEnteAttestato")
    public ResponseEntity<Object> setInserisciEnteAttestato(@RequestBody EnteAttestato enteAttestato) {
        try {
            Map<String, String> response = new HashMap<>();
            enteAttestatoService.inserisciEnteAttestato(enteAttestato);
            response.put("message", "INSERIMENTO RIUSCITO");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERRORE INSERIMENTO");
        }
    }

}
