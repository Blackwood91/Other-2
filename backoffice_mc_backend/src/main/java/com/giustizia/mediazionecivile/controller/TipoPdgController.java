package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.TipoPdgService;

@RestController
@RequestMapping("/tipoPdg")
public class TipoPdgController {
	@Autowired
	TipoPdgService tipoPdgService;
	
	@GetMapping("/getAll")
    public ResponseEntity<Object> getAllEfSedi(){
    	return new ResponseEntity<>(tipoPdgService.getAll(), HttpStatus.OK);
    }
	
}
