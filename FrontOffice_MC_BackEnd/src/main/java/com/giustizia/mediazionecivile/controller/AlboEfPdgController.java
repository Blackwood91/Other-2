package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.model.AlboEfPdg;
import com.giustizia.mediazionecivile.service.AlboEfPdgService;

@RestController
@RequestMapping("/alboEfPdg")
public class AlboEfPdgController {
	@Autowired
	AlboEfPdgService alboEfPdgService;
	
	@GetMapping("/getAllEfPdg")
    public ResponseEntity<Object> getAllOdmSedi(){
    	try {
    		return new ResponseEntity<>(alboEfPdgService.getAllEfPdg(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping("/getEfPdgByNumReg")
    public ResponseEntity<Object> getPdgByNumReg(@RequestParam("numReg") Long numReg){
    	return new ResponseEntity<>(alboEfPdgService.findAllByNumReg(numReg), HttpStatus.OK);
    }
}
