package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.giustizia.mediazionecivile.configurations.ParametersConfigurations;
import com.giustizia.mediazionecivile.dto.EmissionePdgOdmDto;
import com.giustizia.mediazionecivile.dto.FileAllegatoDto;
import com.giustizia.mediazionecivile.service.EmissionePdgOdmService;

@RestController
@RequestMapping("/emissionePdgOdm")
public class EmissionePdgOdmController {
	@Autowired
	EmissionePdgOdmService emissionePdgService;
	@Autowired
	ParametersConfigurations parametersConfigurations;
	
	@GetMapping("/accessEmettiPdg")
	public ResponseEntity<Boolean> accessEmettiPdg(@RequestParam("idRichiesta") Long idRichiesta) {
		return new ResponseEntity<>(emissionePdgService.accessEmettiPdg(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllEmissionePdgOdmForTable")
	public ResponseEntity<Object> getAllEmissionePdgOdmForTable(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
			Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			
			return new ResponseEntity<>(emissionePdgService.getAllEmissionePdg(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getFilePdg")
	public ResponseEntity<Object> getFilePdg(@RequestParam("idEmissionePdg") Long idEmissionePdg) {		
		try {
			return new ResponseEntity<>(emissionePdgService.getFilePdg(idEmissionePdg), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/emissionePdg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> emissionePdg(@RequestPart("emissionePdgOdmDto") EmissionePdgOdmDto emissionePdgOdmDto,
											   @RequestPart("filePdf") MultipartFile filePdf) {
		try {
			emissionePdgOdmDto.setFile(filePdf.getBytes());
			emissionePdgOdmDto.setNomeFile(filePdf.getOriginalFilename());
			emissionePdgService.emissionePdg(emissionePdgOdmDto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
}
