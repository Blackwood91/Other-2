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

import com.giustizia.mediazionecivile.model.OrdiniCollegi;
import com.giustizia.mediazionecivile.service.OrdiniCollegiService;

@RestController
@RequestMapping("/ordiniCollegi")
public class OrdiniCollegiController {
	@Autowired
	OrdiniCollegiService ordiniCollegiService;
	
    @GetMapping("/getAll")
    public ResponseEntity<List<OrdiniCollegi>> getAll(){
    	return new ResponseEntity<>(ordiniCollegiService.getAll(), HttpStatus.OK);
    }
	
    @GetMapping("/getIdOrdiniCollegi")
    public ResponseEntity<Optional<OrdiniCollegi>> getIdOrdiniCollegi(@RequestParam("idOrdiniCollegi") Long idOrdiniCollegi){
    	return new ResponseEntity<>(ordiniCollegiService.getIdOrdiniCollegi(idOrdiniCollegi), HttpStatus.OK);
    }

}
