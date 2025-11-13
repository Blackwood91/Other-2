package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.AlboOdmPdgService;

@RestController
@RequestMapping("/alboOdmPdg")
public class AlboOdmPdgController {
	@Autowired
	AlboOdmPdgService alboOdmPdgService;
	
	@GetMapping("/getAllOdmPdg")
    public ResponseEntity<Object> getAllOdmSedi(){
    	try {
    		return new ResponseEntity<>(alboOdmPdgService.getAllOdmPdg(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping("/getOdmPdgByRom")
    public ResponseEntity<Object> getPdgByNumReg(@RequestParam("rom") Long rom){
    	return new ResponseEntity<>(alboOdmPdgService.findAllByRom(rom), HttpStatus.OK);
    }
}
