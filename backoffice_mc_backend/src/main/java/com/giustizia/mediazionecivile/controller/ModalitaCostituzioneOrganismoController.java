package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.ModalitaCostituzioneOrganismoService;

@RestController
@RequestMapping("/modalitaCostituzioneOrganismo")
public class ModalitaCostituzioneOrganismoController {
	@Autowired
	ModalitaCostituzioneOrganismoService modalitaCostituzioneOrganismoService;
	
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(){
    	try {
    		return new ResponseEntity<>(modalitaCostituzioneOrganismoService.getAll(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	
}
