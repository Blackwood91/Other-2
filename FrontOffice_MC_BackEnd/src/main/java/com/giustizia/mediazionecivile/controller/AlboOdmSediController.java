package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.AlboOdmSediService;


@RestController
@RequestMapping("/alboOdmSedi")
public class AlboOdmSediController {
	@Autowired
	AlboOdmSediService alboOdmSediService;
	
	@GetMapping("/getAllOdmSedi")
    public ResponseEntity<Object> getAllOdmSedi(){
    	try {
    		return new ResponseEntity<>(alboOdmSediService.getAllOdmSedi(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
//	@GetMapping("/getOdmSediByRom")
//    public ResponseEntity<Object> getSediByRom(@RequestParam("rom") Long rom){
//    	return new ResponseEntity<>(alboOdmSediService.findAllByRom(rom), HttpStatus.OK);
//    }
	
	@GetMapping("/getAllOdmSediPaged")
    public ResponseEntity<Object> getAllEntiFormatoriPaged(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "numIscrAlbo", required = false, defaultValue = "") Long numIscrAlbo) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), 10);
		HashMap<String, Object> odmElenco;
		// Gestione Pagine di riferimento Tabella

		odmElenco = alboOdmSediService.getAllOdmPagedByNumeroRegistro(pageable, numIscrAlbo);

		HashMap<String, Object> response = odmElenco;
		return ResponseEntity.ok(response);
    }
	
}
