package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.dto.AlboMediatoriElencoDto;
import com.giustizia.mediazionecivile.model.AlboMediatori;
import com.giustizia.mediazionecivile.service.AlboMediatoriService;

@RestController
@RequestMapping("/alboMediatori")
public class AlboMediatoriController {
	
	
	@Autowired
	AlboMediatoriService alboMediatoriService;
	
	
	@GetMapping("/getMediatoriByIdAlbo")
    public ResponseEntity<Object> getSediByNumReg(@RequestParam("idAlboMediatori") Long idAlboMediatori){
    	return new ResponseEntity<Object>(alboMediatoriService.findByIdAlboMediatori(idAlboMediatori), HttpStatus.OK);
    }
	
	@GetMapping("/getAllMediatoriIscrittiPaged")
    public ResponseEntity<Object> getAllAlboMediatoriIscrittiPaged(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            @RequestParam(value = "tipoRicerca", required = false, defaultValue = "0") Long tipoRicerca,
            @RequestParam(value = "tipoMed", required = false, defaultValue = "") Long tipoMed) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), 10);
		HashMap<String, Object> mediatoriElenco;
		// Gestione Pagine di riferimento Tabella

		if (searchText.isEmpty()) {
			mediatoriElenco = alboMediatoriService.getAllMediatoriIscritti(pageable);
		} else {
			if(tipoRicerca == 0)
				mediatoriElenco = alboMediatoriService.getAllMediatoriPagedByNome(pageable, searchText/*, colonna*/);
			else if(tipoRicerca == 1)
				mediatoriElenco = alboMediatoriService.getAllMediatoriPagedByCognome(pageable, searchText/*, colonna*/);
			else if(tipoRicerca == 2)
				mediatoriElenco = alboMediatoriService.getAllMediatoriPagedByCF(pageable, searchText/*, colonna*/);
//			else if(tipoRicerca == 3)
//				anagraficaElenco = anagraficaOdmService.getAllAnagraficaPagedByNumeroRegistro(pageable, searchText/*, colonna*/);
			else if(tipoRicerca == 4)
				mediatoriElenco = alboMediatoriService.getAllMediatoriPagedByTipoMed(pageable, tipoMed);
			else 
				mediatoriElenco = alboMediatoriService.getAllMediatoriIscritti(pageable/*, colonna*/);

		}

		HashMap<String, Object> response = mediatoriElenco;
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/getAllOdmByIdAnagraficaPaged")
    public ResponseEntity<Object> getAllEntiFormatoriPaged(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "idAnagrafica", required = false, defaultValue = "") Long idAnagrafica) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), 10);
		HashMap<String, Object> odmElenco;
		// Gestione Pagine di riferimento Tabella

		odmElenco = alboMediatoriService.getAllOdmPagedByIdAnagrafica(pageable, idAnagrafica);

		HashMap<String, Object> response = odmElenco;
		return ResponseEntity.ok(response);
    }

}
