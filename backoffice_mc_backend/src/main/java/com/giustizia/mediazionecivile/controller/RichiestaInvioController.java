package com.giustizia.mediazionecivile.controller;

import java.util.AbstractMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.RichiestaInvioService;

@RestController
@RequestMapping("/richiestaInvio")
public class RichiestaInvioController {
	@Autowired
	RichiestaInvioService richiestaInvioService;
	
	@GetMapping("/activeInviaRic")
	public ResponseEntity<Object> activeInviaRic(@RequestParam("idRichiesta") Long idRichiesta) {
    	return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("active", richiestaInvioService.activeInviaRic(idRichiesta)), HttpStatus.OK);
	}
	
	@GetMapping("/downloadFileRI")
	public ResponseEntity<Object> downloadFileRI(@RequestParam("idRichiesta") Long idRichiesta) {
		return new ResponseEntity<>(richiestaInvioService.dwonloadFileRI(idRichiesta), HttpStatus.OK);
	}
	
    @GetMapping("/getRichiestaIntegrazione")
    public ResponseEntity<Object> getRichiestaIntegrazione(@RequestParam("idRichiesta") Long idRichiesta){
    	return new ResponseEntity<>(richiestaInvioService.getRichiestaIntegrazione(idRichiesta), HttpStatus.OK);
    }
	
    @PostMapping("/invioRichiestaIntegrazione")
    public ResponseEntity<Object> invioRichiestaIntegrazione(@RequestParam("idRichiesta") Long idRichiesta,
    											@RequestParam("motivazione") String motivazione) {
    	try {
    		richiestaInvioService.invioRichiestaIntegrazione(idRichiesta, motivazione);
            return new ResponseEntity<>(HttpStatus.OK);
    	} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
	    	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
    
	
}
