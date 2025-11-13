package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.giustizia.mediazionecivile.dto.FileAllegatoDto;
import com.giustizia.mediazionecivile.service.FinalizzaService;

@RestController
@RequestMapping("/finalizza")
public class FinalizzaController {
	@Autowired
	FinalizzaService finalizzaService;
	
	@GetMapping("/downloadRichiestaODM")
	public ResponseEntity<Object> downloadRichiestaODM(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(finalizzaService.downloadRichiestaODM(idRichiesta), HttpStatus.OK);			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@PostMapping("/finalizzazioneRichiestaODM")
	public ResponseEntity<Object> finalizzazioneRichiestaODM(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			finalizzaService.finalizzazioneRichiestaODM(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/inviaRichiestaODM", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> inviaRichiestaODM(@RequestPart("idRichiesta") Long idRichiesta,
													@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			finalizzaService.inviaRichiestaODM(idRichiesta, filePdf.getBytes(), filePdf.getOriginalFilename());
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	
	
}
