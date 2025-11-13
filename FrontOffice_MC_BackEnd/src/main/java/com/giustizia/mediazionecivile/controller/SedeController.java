package com.giustizia.mediazionecivile.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.giustizia.mediazionecivile.configurations.ParametersConfigurations;
import com.giustizia.mediazionecivile.dto.SedeDto;
import com.giustizia.mediazionecivile.service.CheckAccessService;
import com.giustizia.mediazionecivile.service.SedeService;


@RestController
@RequestMapping("/sede")
public class SedeController {
	@Autowired
	SedeService sedeService;
	@Autowired
	ParametersConfigurations parametersConfigurations;
	@Autowired
	CheckAccessService checkAccessService;
    
    @GetMapping("/getAllSediOperativeByIdRichiesta")
    public ResponseEntity<Object> getAllSediOperativeByIdRichiesta(@RequestParam("idRichiesta") Long idRichiesta,
										            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
										            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

        // Gestione Pagine di riferimento Tabella
        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
        return new ResponseEntity<Object>(sedeService.getAllSediByIdRichiesta(pageable, idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/getSedeOperativa")
    public ResponseEntity<Object> getSedeOperativa(@RequestParam("idSede") Long idSede, 
    											   @RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
    	
		return new ResponseEntity<Object>(sedeService.getSedeById(idSede), HttpStatus.OK);
    }
    
    @GetMapping("/existSedeLegale")
    public ResponseEntity<Object> getExistSedeLegale(@RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

        Map<String, Object> response = new HashMap<>();
        response.put("esito",sedeService.existSedeLegale(idRichiesta) );
		return ResponseEntity.ok(response);
    }
    
	
	@PostMapping(value = "/insertSede", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> insertSede(@RequestPart("sedeLegale") SedeDto sede,
		      							   @RequestPart(value = "allegatoCopContratto", required = false)  MultipartFile allegatoCopContratto,
		      							   @RequestPart(value = "allegatoPlanimetria", required = false)  MultipartFile allegatoPlanimetria) throws IOException {      
		checkAccessService.societaIsAssociateUser(sede.getIdRichiesta());

		sede.setAllegatoCopContratto(allegatoCopContratto.getBytes());
		sede.setAllegatoPlanimetria(allegatoPlanimetria.getBytes());
        return new ResponseEntity<>(sedeService.insertSede(sede), HttpStatus.OK);
    }
	
	@PostMapping(value = "/updateSede", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updateSede(@RequestPart("sedeLegale") SedeDto sede,
		      							     @RequestPart(value = "allegatoCopContratto", required = false)  MultipartFile allegatoCopContratto,
		      							     @RequestPart(value = "allegatoPlanimetria", required = false)  MultipartFile allegatoPlanimetria) throws IOException {      
		checkAccessService.societaIsAssociateUser(sede.getIdRichiesta());

		// In caso di update non saranno obbligatori i file, perchè nella logica dell'inserimento sono obbligatori e quindi vuol dire che già sono presenti
		sede.setAllegatoCopContratto(allegatoCopContratto != null ?  allegatoCopContratto.getBytes() : null);
		sede.setAllegatoPlanimetria(allegatoPlanimetria != null ? allegatoPlanimetria.getBytes() : null);
        
		return new ResponseEntity<>(sedeService.updateSede(sede), HttpStatus.OK);
    }
	
	@PostMapping(value = "/deleteSedeOperativa")
	public ResponseEntity<Object> deleteSedeOperativa(@RequestParam Long idSede, @RequestParam Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

        Map<String, Object> response = new HashMap<>();
		sedeService.deleteSede(idSede);
        response.put("esito", "La cancellazione è andata a buon fine");
		return ResponseEntity.ok(response);
    }
	
}
