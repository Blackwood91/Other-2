package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/getOdmSediByRom")
    public ResponseEntity<Object> getSediByRom(@RequestParam("rom") Long rom){
    	return new ResponseEntity<>(alboOdmSediService.findAllByRom(rom), HttpStatus.OK);
    }
	
}
