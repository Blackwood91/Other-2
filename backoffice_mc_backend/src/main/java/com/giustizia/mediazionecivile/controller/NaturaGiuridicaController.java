package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.NaturaGiuridicaService;

@RestController
@RequestMapping("/naturaGiuridica")
public class NaturaGiuridicaController {
	@Autowired
	NaturaGiuridicaService naturaGiuridicaService;
	
    @GetMapping("/getAllNaturaGiuridica")
    public ResponseEntity<Object> getAllNaturaGiuridica() {
    	try {
    		return new ResponseEntity<>(naturaGiuridicaService.getAllNaturaGiuridica(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }

}
