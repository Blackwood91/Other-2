package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.SedeDetenzioneTitoloService;

@RestController
@RequestMapping("/sedeDetenzioneTitolo")
public class SedeDetenzioneTitoloController {
	@Autowired
	SedeDetenzioneTitoloService sedeDetenzioneTitoloService;
	
	@GetMapping("/getAllSedeDetezioneTitolo")
	public ResponseEntity<Object> getAllSedeDetezioneTitolo(){
		return new ResponseEntity<>(sedeDetenzioneTitoloService.getAllSedeDetezioneTitolo(), HttpStatus.OK);
	}
}
