package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.dto.DomandaIscrizioneOdmDto;
import com.giustizia.mediazionecivile.dto.PolizzaAssicurativaDto;
import com.giustizia.mediazionecivile.dto.SezionePrimaDomOdmDto;
import com.giustizia.mediazionecivile.dto.SezioneQuartaDomOdmDto;
import com.giustizia.mediazionecivile.dto.SezioneSecondaDomOdmDto;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.projection.SezioneSecDomOdmProjection;
import com.giustizia.mediazionecivile.projection.PolizzaAssicurativaProjection;
import com.giustizia.mediazionecivile.projection.RichiestaDomIscProjection;
import com.giustizia.mediazionecivile.projection.RichiestaProjection;
import com.giustizia.mediazionecivile.projection.SezionePrimaDOMODMProjection;
import com.giustizia.mediazionecivile.projection.SezioneQuartaDomOdmProjection;
import com.giustizia.mediazionecivile.service.RichiesteService;

@RestController
@RequestMapping("/richiesta")
public class RichiesteController {
	@Autowired
	RichiesteService richiesteService;
	
		
    @GetMapping("/getSezionePrimaDOMODMP")
    public ResponseEntity<SezionePrimaDOMODMProjection> getSezionePrimaDOMODMP(@RequestParam("idRichiesta") Long idRichiesta){
    	return new ResponseEntity<>(richiesteService.getSezionePrimaDOMODMP(idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/getSezioneSecondaDOMODMP")
    public ResponseEntity<SezioneSecDomOdmProjection> getSezioneSecondaDOMODMP(@RequestParam("idRichiesta") Long idRichiesta){
    	return new ResponseEntity<>(richiesteService.getSezioneSecondaDOMODMP(idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/getSezioneQuartaDOMODMP")
    public ResponseEntity<SezioneQuartaDomOdmDto> getSezioneQuartaDOMODMP(@RequestParam("idRichiesta") Long idRichiesta){
    	return new ResponseEntity<>(richiesteService.getSezioneQuartaDOMODMP(idRichiesta), HttpStatus.OK);
    } 
    
    @GetMapping("/getPolizzaAssicurativa")
    public ResponseEntity<PolizzaAssicurativaProjection> getPolizzaAssicurativa(@RequestParam("idRichiesta") Long idRichiesta){
    	return new ResponseEntity<>(richiesteService.getPolizzaAssicurativa(idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/getRichiestaDomIscr")
    public ResponseEntity<RichiestaDomIscProjection> richiestaDomIscr(@RequestParam("idRichiesta") Long idRichiesta) {
        return new ResponseEntity<>(richiesteService.getRichiestaDomIscr(idRichiesta), HttpStatus.OK);
    }
	
    @GetMapping("/getRichiestaForSocieta")
    public ResponseEntity<RichiestaProjection> getRichiestaForSocieta(@RequestParam("idRichiesta") Long idRichiesta) {
        return new ResponseEntity<>(richiesteService.getRichiestaForSocieta(idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/richiestaNaturaIsAutonomo")
    public ResponseEntity<Object> richiestaNaturaIsAutonomo(@RequestParam("idRichiesta") Long idRichiesta){
        Map<String, Object> response = new HashMap<>();
	    response.put("autonomo", richiesteService.richiestaNaturaIsAutonomo(idRichiesta));
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getAllRichiestaPaged")
    public ResponseEntity<Object> getAllRichiestaPaged(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "idStatoRichiesta", required = false, defaultValue = "") Long idStatoRichiesta,
            @RequestParam(value = "idTipoRichiesta", required = false, defaultValue = "") Long idTipoRichiesta) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), 10);
		return ResponseEntity.ok(richiesteService.getAllRichiestePaged(pageable, idStatoRichiesta, idTipoRichiesta));
    }
    
}
