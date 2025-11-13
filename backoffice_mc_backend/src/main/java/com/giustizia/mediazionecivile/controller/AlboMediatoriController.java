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

import com.giustizia.mediazionecivile.model.AlboMediatori;
import com.giustizia.mediazionecivile.service.AlboMediatoriService;

@RestController
@RequestMapping("/alboMediatori")
public class AlboMediatoriController {
	
	
	@Autowired
	AlboMediatoriService alboMediatoriService;
	
	
	@GetMapping("/getAllMediatori")
    public ResponseEntity<Object> getAllMediatori(){
    	try {
    		return new ResponseEntity<>(alboMediatoriService.getAllMediatori(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping("/getAllMediatoriPaged")
    public ResponseEntity<Object> getAllAlboMediatoriPaged(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            @RequestParam(value = "tipoRicerca", required = false, defaultValue = "0") Long tipoRicerca,
            @RequestParam(value = "tipoMed", required = false, defaultValue = "") Long tipoMed) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), 13);
		Page<AlboMediatori> mediatoriElenco;
		// Gestione Pagine di riferimento Tabella

		if (searchText.isEmpty()) {
			mediatoriElenco = alboMediatoriService.getAllMediatoriPaged(pageable, searchText/*, colonna*/);
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
				mediatoriElenco = alboMediatoriService.getAllMediatoriPagedByTipoForm(pageable, tipoMed);
			else 
				mediatoriElenco = alboMediatoriService.getAllMediatoriPaged(pageable, searchText/*, colonna*/);

		}

		HashMap<String, Object> response = new HashMap<>();
		response.put("result", mediatoriElenco.getContent());
		response.put("totalResult", mediatoriElenco.getTotalElements());
		return ResponseEntity.ok(response);

    }
	
	@GetMapping("/getMediatoriByIdAlbo")
    public ResponseEntity<Object> getSediByNumReg(@RequestParam("idAlboMediatori") Long idAlboMediatori){
    	return new ResponseEntity<Object>(alboMediatoriService.findByIdAlboMediatori(idAlboMediatori), HttpStatus.OK);
    }

}
