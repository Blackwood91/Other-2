package com.giustizia.mediazionecivile.controller;

import java.util.AbstractMap;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.configurations.ParametersConfigurations;
import com.giustizia.mediazionecivile.dto.RichiestaIscrizioneEfDto;
import com.giustizia.mediazionecivile.dto.RichiestaIscrizioneOdmDto;
import com.giustizia.mediazionecivile.dto.SocietaDTO;
import com.giustizia.mediazionecivile.model.EfRichiesta;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.projection.SocietaDomIscProjection;
import com.giustizia.mediazionecivile.service.SocietaService;
import com.giustizia.mediazionecivile.utility.StatusCheck;


@RestController
@RequestMapping("/societa")
public class SocietaController {
    @Autowired
    SocietaService societaService;
	@Autowired
	ParametersConfigurations parametersConfigurations;

    @GetMapping("/getSocietaById")
    public ResponseEntity<Object> getSocietaById(@RequestParam Long idSocieta) {
        return new ResponseEntity<Object>(societaService.findSocietaById(idSocieta), HttpStatus.OK);
    }
    
    @GetMapping("/getSocietaByIdRichiesta")
    public ResponseEntity<Object> getSocietaByIdRichiesta(@RequestParam Long idSocieta) {
        return new ResponseEntity<Object>(societaService.findSocietaByIdRichiesta(idSocieta), HttpStatus.OK);
    }
	
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllSocieta(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
        // Gestione Pagine di riferimento Tabella
        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
        HashMap<String, Object> response = societaService.getAllSocieta(pageable, searchText);

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getSocietaDomIscr")
    public ResponseEntity<SocietaDomIscProjection> getSocietaDomIscr(@RequestParam Long idSocieta) {
        return new ResponseEntity<>(societaService.getSocietaDomIscr(idSocieta), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> createSocieta(@RequestBody SocietaDTO societa) {
        StatusCheck statusCheck = societaService.insertSocieta(societa);

        if (!statusCheck.getEsito()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusCheck.getDescrizioneEsito());
        }

    	return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("statusCheck", statusCheck.getDescrizioneEsito()), HttpStatus.OK);
    }
    
    @PostMapping("/richiestaIscrizioneODM")
    public ResponseEntity<Richiesta> richiestaIscrizioneODM(@RequestBody RichiestaIscrizioneOdmDto richiestaIscrizioneORDDto) {
        return new ResponseEntity<>(societaService.richiestaIscrizioneODM(richiestaIscrizioneORDDto), HttpStatus.OK);
    }
    
    @PostMapping("/richiestaIscrizioneEF")
    public ResponseEntity<EfRichiesta> richiestaIscrizioneEF(@RequestBody RichiestaIscrizioneEfDto richiestaIscrizioneEfDto) {
        return new ResponseEntity<>(societaService.richiestaIscrizioneEF(richiestaIscrizioneEfDto), HttpStatus.OK);
    }
    
    @PostMapping("/update")
    public ResponseEntity<Object> updateSocieta(@RequestBody SocietaDTO societa) {
    	try {
        return new ResponseEntity<>(societaService.updateSocieta(societa), HttpStatus.OK);
    	}
        catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
	    	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
    
}
