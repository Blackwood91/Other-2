package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.NaturaSocietariaService;

@RestController
@RequestMapping("/naturaSocietaria")
public class NaturaSocietariaController {
	@Autowired
	NaturaSocietariaService naturaSocietariaService;
	
    @GetMapping("/getAllNaturaSocietaria")
    public ResponseEntity<Object> getAllNaturaSocietaria(){
    	try {
    		return new ResponseEntity<>(naturaSocietariaService.getAllNaturaSocietaria(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
	
}
