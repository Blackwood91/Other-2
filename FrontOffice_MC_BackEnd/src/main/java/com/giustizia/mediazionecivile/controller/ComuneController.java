package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.ComuneService;

@RestController
@RequestMapping("/comune")
public class ComuneController {
	@Autowired
	ComuneService comuneService;
	
    @GetMapping("/getComune")
    public ResponseEntity<Object> getComuneById(@RequestParam("idComune") Long idComune) {
    	try {
    		return new ResponseEntity<>(comuneService.getComuneById(idComune), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
    @GetMapping("/getAllComuneByNome")
    public ResponseEntity<Object> getAllComuneByNome(@RequestParam("nomeComune") String nomeComune) {
    	try {
			Pageable pageable = PageRequest.of(0, 50);
    		return new ResponseEntity<>(comuneService.getAllComuneByNome(nomeComune, pageable), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
    
    @GetMapping("/getAllComune")
    public ResponseEntity<Object> getAllComune() {
    	try {
    		return new ResponseEntity<>(comuneService.getAllComune(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }

}
