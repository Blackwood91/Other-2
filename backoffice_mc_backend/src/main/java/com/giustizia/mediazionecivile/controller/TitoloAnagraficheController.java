package com.giustizia.mediazionecivile.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.model.TitoloAnagrafiche;
import com.giustizia.mediazionecivile.service.TitoloAnagraficheService;

@RestController
@RequestMapping("/titoloAnagrafiche")
public class TitoloAnagraficheController {
	@Autowired
	TitoloAnagraficheService titoloAnagraficheService;
	
    @GetMapping("/getAll")
    public ResponseEntity<List<TitoloAnagrafiche>> getAll(){
    	return new ResponseEntity<>(titoloAnagraficheService.getAll(), HttpStatus.OK);
    }
	
    @GetMapping("/getIdTitolo")
    public ResponseEntity<Optional<TitoloAnagrafiche>> getIdTitolo(@RequestParam("idTitolo") Long idTitolo){
    	return new ResponseEntity<>(titoloAnagraficheService.getIdTitolo(idTitolo), HttpStatus.OK);
    }
}
