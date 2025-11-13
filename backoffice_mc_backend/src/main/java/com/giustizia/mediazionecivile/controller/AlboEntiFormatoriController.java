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
import com.giustizia.mediazionecivile.service.AlboEntiFormatoriService;

@RestController
@RequestMapping("/alboEntiFormatori")
public class AlboEntiFormatoriController {
	@Autowired
	AlboEntiFormatoriService alboEntiFormatoriService;
	
//	@GetMapping("/getFormatoreById")
//    public ResponseEntity<AlboFormatori> getAnagraficaById(@RequestParam("idAnagrafica") Long idAnagrafica){
//    	return new ResponseEntity<>(alboFormatoriService.getFormatoreById(idAnagrafica), HttpStatus.OK);
//    }
	
	@GetMapping("/getAllEntiFormatori")
    public ResponseEntity<Object> getAllEntiFormatori(){
    	try {
    		return new ResponseEntity<>(alboEntiFormatoriService.getAllEntiFormatori(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping("/getAllEntiFormatoriPaged")
    public ResponseEntity<Object> getAllEntiFormatoriPaged(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            @RequestParam(value = "tipoRicerca", required = false, defaultValue = "0") Long tipoRicerca,
            @RequestParam(value = "tipoMed", required = false, defaultValue = "") Long tipoMed) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), 13);
		Page<AlboEntiFormatori> entiFormatoriElenco;
		// Gestione Pagine di riferimento Tabella

		if (searchText.isEmpty()) {
			//return new ResponseEntity<>(alboEntiFormatoriService.getAllEntiFormatoriPaged(pageable, searchText/*, colonna*/));
			entiFormatoriElenco = alboEntiFormatoriService.getAllEntiFormatoriPaged(pageable, searchText/*, colonna*/);
		} else {
			if(tipoRicerca == 0)
				entiFormatoriElenco = alboEntiFormatoriService.getAllEntiFormatoriPagedByDenominazione(pageable, searchText/*, colonna*/);
			else if(tipoRicerca == 1)
				entiFormatoriElenco = alboEntiFormatoriService.getAllEntiFormatoriPagedByNumeroRegistro(pageable, searchText/*, colonna*/);
			else 
				entiFormatoriElenco = alboEntiFormatoriService.getAllEntiFormatoriPaged(pageable, searchText/*, colonna*/);

		}

		HashMap<String, Object> response = new HashMap<>();
		response.put("result", entiFormatoriElenco.getContent());
		response.put("totalResult", entiFormatoriElenco.getTotalElements());
		return ResponseEntity.ok(response);
    }
	
}
