package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.model.AlboEntiFormatori;
import com.giustizia.mediazionecivile.model.AlboFormatori;
import com.giustizia.mediazionecivile.projection.FormatoreMediatoreProjection;
import com.giustizia.mediazionecivile.service.AlboEntiFormatoriService;
import com.giustizia.mediazionecivile.service.AlboFormatoriService;

@RestController
@RequestMapping("/alboFormatori")
public class AlboFormatoriController {
	@Autowired
	AlboEntiFormatoriService alboEntiFormatoriService;
	@Autowired
	AlboFormatoriService alboFormatoriService;
	
//	@GetMapping("/getFormatoreById")
//    public ResponseEntity<AlboFormatori> getAnagraficaById(@RequestParam("idAnagrafica") Long idAnagrafica){
//    	return new ResponseEntity<>(alboFormatoriService.getFormatoreById(idAnagrafica), HttpStatus.OK);
//    }
	
	@GetMapping("/getAllFormatori")
    public ResponseEntity<Object> getAllFormatori(){
    	try {
    		return new ResponseEntity<>(alboFormatoriService.getAllFormatori(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
	@GetMapping("/getAllFormatoriPaged")
    public ResponseEntity<Object> getAllAnagraficaPaged(
            @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            @RequestParam(value = "tipoRicerca", required = false, defaultValue = "0") Long tipoRicerca,
            @RequestParam(value = "tipoForm", required = false, defaultValue = "") Long tipoForm) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), 13);
		Page<AlboFormatori> formatoriElenco;
		// Gestione Pagine di riferimento Tabella

		if (searchText.isEmpty()) {
			formatoriElenco = alboFormatoriService.getAllFormatoriPaged(pageable, searchText/*, colonna*/);
		} else {
			if(tipoRicerca == 0)
				formatoriElenco = alboFormatoriService.getAllFormatoriPagedByNome(pageable, searchText/*, colonna*/);
			else if(tipoRicerca == 1)
				formatoriElenco = alboFormatoriService.getAllFormatoriPagedByCognome(pageable, searchText/*, colonna*/);
			else if(tipoRicerca == 2)
				formatoriElenco = alboFormatoriService.getAllFormatoriPagedByCF(pageable, searchText/*, colonna*/);
//			else if(tipoRicerca == 3)
//				anagraficaElenco = anagraficaOdmService.getAllAnagraficaPagedByNumeroRegistro(pageable, searchText/*, colonna*/);
			else if(tipoRicerca == 4)
				formatoriElenco = alboFormatoriService.getAllFormatoriPagedByTipoForm(pageable, tipoForm);
			else 
				formatoriElenco = alboFormatoriService.getAllFormatoriPaged(pageable, searchText/*, colonna*/);

		}

		HashMap<String, Object> response = new HashMap<>();
		response.put("result", formatoriElenco.getContent());
		response.put("totalResult", formatoriElenco.getTotalElements());
		return ResponseEntity.ok(response);

    }
	
	@GetMapping("/getFormatoriByIdAlbo")
    public ResponseEntity<Object> getSediByNumReg(@RequestParam("idAlboFormatori") Long idAlboFormatori){
    	return new ResponseEntity<Object>(alboFormatoriService.findByIdAlboFormatori(idAlboFormatori), HttpStatus.OK);
    }

}
