package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.giustizia.mediazionecivile.model.AlboMediatori;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.projection.SezioneSecDomOdmProjection;
import com.giustizia.mediazionecivile.projection.PolizzaAssicurativaProjection;
import com.giustizia.mediazionecivile.projection.RichiestaDomIscProjection;
import com.giustizia.mediazionecivile.projection.RichiestaProjection;
import com.giustizia.mediazionecivile.projection.SezionePrimaDOMODMProjection;
import com.giustizia.mediazionecivile.projection.SezioneQuartaDomOdmProjection;
import com.giustizia.mediazionecivile.service.CheckAccessService;
import com.giustizia.mediazionecivile.service.RichiesteService;

@RestController
@RequestMapping("/richiesta")
public class RichiesteController {
	@Autowired
	RichiesteService richiesteService;
	@Autowired
	CheckAccessService checkAccessService;
	
	@GetMapping("/getAllRichiesta")
    public ResponseEntity<Object> getAllRichiesta(@RequestParam("idRichiesta") Long idRichiesta){
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
    	return new ResponseEntity<>(richiesteService.getAllRichiesta(), HttpStatus.OK);
    }
		
    @GetMapping("/getSezionePrimaDOMODMP")
    public ResponseEntity<SezionePrimaDOMODMProjection> getSezionePrimaDOMODMP(@RequestParam("idRichiesta") Long idRichiesta){
		checkAccessService.societaIsAssociateUser(idRichiesta);

    	return new ResponseEntity<>(richiesteService.getSezionePrimaDOMODMP(idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/getSezioneSecondaDOMODMP")
    public ResponseEntity<SezioneSecDomOdmProjection> getSezioneSecondaDOMODMP(@RequestParam("idRichiesta") Long idRichiesta){
		checkAccessService.societaIsAssociateUser(idRichiesta);

    	return new ResponseEntity<>(richiesteService.getSezioneSecondaDOMODMP(idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/getSezioneQuartaDOMODMP")
    public ResponseEntity<SezioneQuartaDomOdmDto> getSezioneQuartaDOMODMP(@RequestParam("idRichiesta") Long idRichiesta){
		checkAccessService.societaIsAssociateUser(idRichiesta);

    	return new ResponseEntity<>(richiesteService.getSezioneQuartaDOMODMP(idRichiesta), HttpStatus.OK);
    } 
    
    @GetMapping("/getPolizzaAssicurativa")
    public ResponseEntity<PolizzaAssicurativaProjection> getPolizzaAssicurativa(@RequestParam("idRichiesta") Long idRichiesta){
		checkAccessService.societaIsAssociateUser(idRichiesta);

    	return new ResponseEntity<>(richiesteService.getPolizzaAssicurativa(idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/getRichiestaDomIscr")
    public ResponseEntity<RichiestaDomIscProjection> richiestaDomIscr(@RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

        return new ResponseEntity<>(richiesteService.getRichiestaDomIscr(idRichiesta), HttpStatus.OK);
    }
	
    @GetMapping("/getRichiestaForSocieta")
    public ResponseEntity<Object> getRichiestaForSocieta(@RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

        return new ResponseEntity<>(richiesteService.getRichiestaForSocieta(idRichiesta), HttpStatus.OK);
    }
    
    @GetMapping("/richiestaNaturaIsAutonomo")
    public ResponseEntity<Object> richiestaNaturaIsAutonomo(@RequestParam("idRichiesta") Long idRichiesta){
		checkAccessService.societaIsAssociateUser(idRichiesta);

        Map<String, Object> response = new HashMap<>();
	    response.put("autonomo", richiesteService.richiestaNaturaIsAutonomo(idRichiesta));
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/updateDomandaIscODM")
    public ResponseEntity<Object> updateDomandaIscODM(@RequestBody DomandaIscrizioneOdmDto domandaIscODM) throws Exception {
		checkAccessService.societaIsAssociateUser(domandaIscODM.getIdRichiesta());
    	
        return ResponseEntity.ok(richiesteService.updateDomandaIscODM(domandaIscODM));
    }

    @PostMapping("/saveSezionePrima")
    public ResponseEntity<Object> saveSezionePrima(@RequestBody SezionePrimaDomOdmDto sezionePrimaDto) throws Exception {
		checkAccessService.societaIsAssociateUser(sezionePrimaDto.getIdRichiesta());

        return ResponseEntity.ok(richiesteService.saveSezionePrima(sezionePrimaDto));
    }
    
    @PostMapping("/saveSezioneSeconda")
    public ResponseEntity<Object> saveSezioneSeconda(@RequestBody SezioneSecondaDomOdmDto sezioneSecondaDto) throws Exception {
		checkAccessService.societaIsAssociateUser(sezioneSecondaDto.getIdRichiesta());

        return ResponseEntity.ok(richiesteService.saveSezioneSeconda(sezioneSecondaDto));
    }
    
    @PostMapping("/saveSezioneQuarta")
    public ResponseEntity<Object> saveSezioneQuarta(@RequestBody SezioneQuartaDomOdmDto sezioneQuartaDomOdmDto) throws Exception {
		checkAccessService.societaIsAssociateUser(sezioneQuartaDomOdmDto.getIdRichiesta());

        return ResponseEntity.ok(richiesteService.saveSezioneQuarta(sezioneQuartaDomOdmDto));
    }
    
    @PostMapping("/saveDichiarazionePolizzaAss")
    public ResponseEntity<Object> savePolizzaAssicurativa(@RequestBody PolizzaAssicurativaDto polizzaAssicurativaDto) throws Exception {
		checkAccessService.societaIsAssociateUser(polizzaAssicurativaDto.getIdRichiesta());

        return ResponseEntity.ok(richiesteService.saveDichiarazionePolizzaAss(polizzaAssicurativaDto));
    }
    
}
