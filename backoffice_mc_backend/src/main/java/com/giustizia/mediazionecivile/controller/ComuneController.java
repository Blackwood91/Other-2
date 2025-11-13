package com.giustizia.mediazionecivile.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.giustizia.mediazionecivile.model.Comune;
import com.giustizia.mediazionecivile.model.RegioneProvince;
import com.giustizia.mediazionecivile.service.ComuneService;
import com.giustizia.mediazionecivile.utility.EstraiCsv;
import com.itextpdf.io.exceptions.IOException;

@RestController
@RequestMapping("/comune")
public class ComuneController {
	@Autowired
	ComuneService comuneService;
	
    @GetMapping("/getComune")
    public ResponseEntity<Object> getComuneById(@RequestParam("idComune") Long idComune) {
    	try {
    		return new ResponseEntity<>(comuneService.getComuneById(idComune), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
	
    @GetMapping("/getAllComuneByNome")
    public ResponseEntity<Object> getAllComuneByNome(@RequestParam("nomeComune") String nomeComune) {
    	try {
			Pageable pageable = PageRequest.of(0, 50);
    		return new ResponseEntity<>(comuneService.getAllComuneByNome(nomeComune, pageable), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
    
    @GetMapping("/getAllComune")
    public ResponseEntity<Object> getAllComune() {
    	try {
    		return new ResponseEntity<>(comuneService.getAllComune(), HttpStatus.OK);
    	}
    	catch (Exception e) {
        	return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
    }
    
	@PostMapping(value = "/setCaricaComuniCsv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> setCaricaComuniCsv(@RequestPart("file") MultipartFile file) throws IOException {

		try {
			Map<String, Object> response = new HashMap<>();
			String paramsNotValid = "";

			paramsNotValid = comuneService.insertCSVComuni(file);

			response.put("paramsNotValid", paramsNotValid);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			if (e.getMessage().contains("-ErrorController")) {
				return ResponseEntity.badRequest().body(e.getMessage().replace("-ErrorController", ""));
			}
			return ResponseEntity.badRequest().body("Si è verificato un errore non previsto nel caricmaneto dei Seggi");
		}
	}
	
}
