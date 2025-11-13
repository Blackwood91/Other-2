package com.giustizia.mediazionecivile.controller;

import java.util.AbstractMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.JobRichiestaService;

@RestController
@RequestMapping("/jobRichiesta")
public class JobRichiestatController {
	@Autowired
	JobRichiestaService jobRichiestaService;
	
	@GetMapping("/statusJobRichiesta")
	public ResponseEntity<Object> statusJobRichiesta(@RequestParam("idRichiesta") Long idRichiesta, 
											  @RequestParam("tipoRichiesta") String tipoRichiesta) {      
		try {
			return new ResponseEntity<>(new AbstractMap.SimpleEntry<String, Object>("status", jobRichiestaService.statusJobRichiesta(idRichiesta, tipoRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
}
