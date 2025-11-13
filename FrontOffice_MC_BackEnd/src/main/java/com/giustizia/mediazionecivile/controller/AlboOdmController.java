package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.model.AlboEntiFormatori;
import com.giustizia.mediazionecivile.model.AlboOdm;
import com.giustizia.mediazionecivile.service.AlboEntiFormatoriService;
import com.giustizia.mediazionecivile.service.AlboOdmService;

@RestController
@RequestMapping("/alboOdm")
public class AlboOdmController {
	@Autowired
	AlboOdmService alboOdmService;
	
	@GetMapping("/getAllOdm")
    public ResponseEntity<Object> getAllEntiFormatori(){
    	try {
    		return new ResponseEntity<>(alboOdmService.getAllOdm(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping("/getAllOdmPaged")
    public ResponseEntity<Object> getAllEntiFormatoriPaged(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            @RequestParam(value = "tipoRicerca", required = false, defaultValue = "0") Long tipoRicerca,
            @RequestParam(value = "tipoMed", required = false, defaultValue = "") Long tipoMed) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), 10);
		HashMap<String, Object> odmElenco;
		// Gestione Pagine di riferimento Tabella

		if (searchText.isEmpty()) {
			//return new ResponseEntity<>(alboEntiFormatoriService.getAllEntiFormatoriPaged(pageable, searchText/*, colonna*/));
			odmElenco = alboOdmService.getAllOdmIscritti(pageable);
		} else {
			if(tipoRicerca == 0)
				odmElenco = alboOdmService.getAllOdmPagedByDenominazione(pageable, searchText/*, colonna*/);
			else if(tipoRicerca == 1)
				odmElenco = alboOdmService.getAllOdmPagedByNumeroRegistro(pageable, searchText/*, colonna*/);
			else 
				odmElenco = alboOdmService.getAllOdmIscritti(pageable);

		}

		HashMap<String, Object> response = odmElenco;
		return ResponseEntity.ok(response);
    }
	
}
