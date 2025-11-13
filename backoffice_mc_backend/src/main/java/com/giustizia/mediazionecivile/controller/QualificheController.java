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

import com.giustizia.mediazionecivile.model.Qualifica;
import com.giustizia.mediazionecivile.service.QualificheService;

@RestController
@RequestMapping("/qualifica")
public class QualificheController {
	@Autowired
	QualificheService qualificheService;
	
    @GetMapping("/getAll")
    public ResponseEntity<List<Qualifica>> getAll(){
    	return new ResponseEntity<>(qualificheService.getAll(), HttpStatus.OK);
    }
    
    @GetMapping("/getAllCompOrgAmAndCompSoc")
    public ResponseEntity<List<Qualifica>> getAllCompOrgAmAndCompSoc(){
    	return new ResponseEntity<>(qualificheService.getAllCompOrgAmAndCompSoc(), HttpStatus.OK);
    }
	
    @GetMapping("/getIdQualifica")
    public ResponseEntity<Optional<Qualifica>> getIdTitolo(@RequestParam("idTitolo") Long idTitolo){
    	return new ResponseEntity<>(qualificheService.getIdTitolo(idTitolo), HttpStatus.OK);
    }
}
