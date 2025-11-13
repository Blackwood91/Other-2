package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.giustizia.mediazionecivile.dto.FileAllegatoAppendiciDto;
import com.giustizia.mediazionecivile.dto.FileAllegatoDto;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.service.StatoModuloAllegatoService;

@RestController
@RequestMapping("/statoModuli")
public class StatoModuloAllegatoController {
	@Autowired
	StatoModuloAllegatoService statoModuloAllegatoService;
	@Autowired
	ParametersConfigurations parametersConfigurations;
	
	@GetMapping("/getAllAttoCostitutivoOdm")
	public ResponseEntity<Object> getAllAttoCostitutivoOdm(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllAttoCostOdm(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllAttoCostitutivoOdmNA")
	public ResponseEntity<Object> getAllAttoCostitutivoOdmNA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllAttoCostOdmNA(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllStatutoOrg")
	public ResponseEntity<Object> getAllStatutoOrg(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllStatutoOrg(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllAttoCostNonAutonomo")
	public ResponseEntity<Object> getAllAttoCostNonAutonomo(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllAttoCostNonAutonomo(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllStatutoOrgNonAutonomo")
	public ResponseEntity<Object> getAllStatutoOrgNonAutonomo(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllStatutoOrgNonAutonomo(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllStatutoOrgNA")
	public ResponseEntity<Object> getAllStatutoOrgNA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllStatutoOrgNA(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllSpeseMed")
	public ResponseEntity<Object> getAllSpeseMed(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllSpeseMed(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllRegProcedura")
	public ResponseEntity<Object> getAllRegProcedura(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllRegProcedura(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllCodiceEtico")
	public ResponseEntity<Object> getAllCodiceEtico(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllCodiceEtico(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}

	@GetMapping("/getAllBilancio")
	public ResponseEntity<Object> getAllBilancio(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllBilancio(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllPolizzaAssicurativa")
	public ResponseEntity<Object> getAllPolizzaAssicurativa(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllPolizzaAssicurativa(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllRichiestaInviata")
	public ResponseEntity<Object> getAllRichiestaInviata(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllRichiestaInviata(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}

	@PostMapping(value = "/saveFileAttoCostitutivoOdm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileAttoCostitutivoOdm(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertAttoCostOdm(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileStatutoOrg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileStatutoOrg(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertStatutoOrg(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileAttoCostitutivoOdmNA", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileAttoCostitutivoOdmNA(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertAttoCostOdmNA(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileStatutoOrgNA", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileStatutoOrgNA(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertStatutoOrgNA(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileRegProcedura", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileRegProcedura(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertRegProcedura(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileCodiceEtico", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileCodiceEtico(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertCodiceEtico(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileBilancio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileBilancio(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertBilancio(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileAttoCostNonAutonomo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileAttoCostNonAutonomo(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFileAttoCostNonAutonomo(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileStatutoOrgNonAutonomo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileStatutoOrgNonAutonomo(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFileStatutoOrgNonAutonomo(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileSpeseMed", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileSpeseMed(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			if(filePdf != null) {
				fileAllegatoDto.setFile(filePdf.getBytes());
			}
			return new ResponseEntity<>(statoModuloAllegatoService.saveSpeseMed(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFilePolizzaAssicurativa", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFilePolizzaAssicurativa(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFilePolizzaAssicurativa(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/updateFileSpeseMed", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updateFileSpeseMed(@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
													 @RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			if(filePdf != null) {
				fileAllegatoDto.setFile(filePdf.getBytes());
			}
			return new ResponseEntity<>(statoModuloAllegatoService.updateFileSpeseDiMediazione(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/updateFilePolizza", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updateFilePolizza(@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
												@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.updateFilePolizza(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	// METODI CENTRALIZZATI, ADATTABILI A TUTTI I MODULI
	@PostMapping(value = "/updateFileModulo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updateFileOdm(@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
												@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.updateFileModulo(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	// METODI CENTRALIZZATI, ADATTABILI A TUTTI I MODULI
	@PostMapping(value = "/deleteFileSpeseDiMediazione", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> deleteFileSpeseDiMediazione(@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto) {
		try {
			statoModuloAllegatoService.deleteFileSpeseDiMediazione(fileAllegatoDto);
	        Map<String, Object> response = new HashMap<>();
		    response.put("esito", "La cancellazione è andata a buon fine");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	/**********************************************************************************************************************/
	
	@GetMapping("/getAllDisponibilitaA")
	public ResponseEntity<Object> getAllDisponibilitaA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllDisponibilitaA(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllDisponibilitaB")
	public ResponseEntity<Object> getAllDisponibilitaB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllDisponibilitaB(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllDisponibilitaC")
	public ResponseEntity<Object> getAllDisponibilitaC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllDisponibilitaC(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllFormazioneInizialeA")
	public ResponseEntity<Object> getAllFormazioneInizialeA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllFormazioneInizialeA(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllFormazioneInizialeB")
	public ResponseEntity<Object> getAllFormazioneInizialeB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllFormazioneInizialeB(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}

	@GetMapping("/getAllFormazioneInizialeC")
	public ResponseEntity<Object> getAllFormazioneInizialeC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllFormazioneInizialeC(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllFormazioneSpecificaB")
	public ResponseEntity<Object> getAllFormazioneSpecificaB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllFormazioneSpecificaB(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllFormazioneSpecificaC")
	public ResponseEntity<Object> getAllFormazioneSpecificaC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllFormazioneSpecificaC(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllCertificazioneB")
	public ResponseEntity<Object> getAllCertificazioneB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllCertificazioneB(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto: " + e.toString());
		}
	}
	
	@GetMapping("/getAllCertificazioneC")
	public ResponseEntity<Object> getAllCertificazioneC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllCertificazioneC(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllUlterioriRequisitiA")
	public ResponseEntity<Object> getAllUlterioriRequisitiA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllUlterioriRequisitiA(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@GetMapping("/getAllUlterioriRequisitiB")
	public ResponseEntity<Object> getAllUlterioriRequisitiB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllUlterioriRequisitiB(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}

	@GetMapping("/getAllUlterioriRequisitiC")
	public ResponseEntity<Object> getAllUlterioriRequisitiC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAllUlterioriRequisitiC(pageable, idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verificato un errore non previsto");
		}
	}
	
	@PostMapping(value = "/saveFileDisponibilitaA", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileDisponibilitaA(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertDisponibilitaA(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileDisponibilitaB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileDisponibilitaB(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertDisponibilitaB(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileDisponibilitaC", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileDisponibilitaC(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertDisponibilitaC(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileFormazioneInizialeA", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileFormazineInizialeA(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFormazioneInizialeA(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileFormazioneInizialeB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileFormazineInizialeB(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFormazioneInizialeB(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileFormazioneInizialeC", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileFormazineInizialeC(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFormazioneInizialeC(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileFormazioneSpecificaB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileFormazioneSpecificaB(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFormazioneSpecificaB(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileFormazioneSpecificaC", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileFormazioneSpecificaC(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFormazioneSpecificaC(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileCertificazioneB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileCertificazioneB(@RequestPart("fileAllegatoAppendiciDto") FileAllegatoAppendiciDto fileAllegatoAppendiciDto,
														  @RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoAppendiciDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertCertificazioneB(fileAllegatoAppendiciDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileCertificazioneC", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileCertificazioneC(
			@RequestPart("fileAllegatoAppediciDto") FileAllegatoAppendiciDto fileAllegatoAppendiciDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoAppendiciDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertCertificazioneC(fileAllegatoAppendiciDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFormazioneSpecificaB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFormazioneSpecificaB(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFormazioneSpecificaB(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFormazioneSpecificaC", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFormazioneSpecificaC(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertFormazioneSpecificaC(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	@PostMapping(value = "/saveFileUlterioriRequisitiA", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileUlterioriRequisitiA(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertUlterioriRequisitiA(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileUlterioriRequisitiB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileUlterioriRequisitiB(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertUlterioriRequisitiB(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@PostMapping(value = "/saveFileUlterioriRequisitiC", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveFileUlterioriRequisitiC(
			@RequestPart("fileAllegatoDto") FileAllegatoDto fileAllegatoDto,
			@RequestPart("filePdf") MultipartFile filePdf) {
		try {
			fileAllegatoDto.setFile(filePdf.getBytes());
			return new ResponseEntity<>(statoModuloAllegatoService.insertUlterioriRequisitiC(fileAllegatoDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	// METODI CENTRALIZZATI, ADATTABILI A TUTTI I MODULI
	@PostMapping(value = "/updateFileAppendici", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updateFileAppendici(
			@RequestPart("fileAllegatoAppendiciDto") FileAllegatoAppendiciDto fileAllegatoAppendiciDto,
			@RequestPart(value = "filePdf", required = false) MultipartFile filePdf) {
		try {
			if(filePdf != null) {
				fileAllegatoAppendiciDto.setFile(filePdf.getBytes());
				//fileAllegatoAppendiciDto.setNomeFile(filePdf.getName());
			}
			
			return new ResponseEntity<>(statoModuloAllegatoService.updateFileAppendici(fileAllegatoAppendiciDto), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@GetMapping("/getElencoAutocerficatiRequisitiOnorabilitApp")
	public ResponseEntity<Object> getElencoAutocerficatiRequisitiOnorabilitApp(
			@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica,
			@RequestParam("idModulo") Long idModulo,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		
		try {
	        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
			return new ResponseEntity<>(statoModuloAllegatoService.getAutocertificazioneApp(pageable, idRichiesta, idAnagrafica, idModulo), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}	
		
}
