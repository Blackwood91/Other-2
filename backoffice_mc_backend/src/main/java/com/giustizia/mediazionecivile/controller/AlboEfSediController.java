package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.AlboEfSediService;

@RestController
@RequestMapping("/alboEfSedi")
public class AlboEfSediController {
	@Autowired
	AlboEfSediService alboEfSediService;
	
	@GetMapping("/getAllEfSedi")
    public ResponseEntity<Object> getAllEfSedi(){
    	try {
    		return new ResponseEntity<>(alboEfSediService.getAllEfSedi(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping("/getEfSediByNumReg")
    public ResponseEntity<Object> getSediByNumReg(@RequestParam("numReg") Long numReg){
    	return new ResponseEntity<>(alboEfSediService.findAllByNumReg(numReg), HttpStatus.OK);
    }
}
