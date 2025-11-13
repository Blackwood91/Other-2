package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.giustizia.mediazionecivile.service.PdfService;

@RestController
@RequestMapping("/pdf")
public class PdfController {
	@Autowired
	PdfService pdfService;
	
	@GetMapping("/getFileModulo")
	public ResponseEntity<Object> getFileModulo(@RequestParam("id") Long idModFiglio) {
		try {
			return new ResponseEntity<>(pdfService.getFileModulo(idModFiglio), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getFileModuloDomanda")
	public ResponseEntity<Object> getFileModuloDomanda(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(pdfService.getFileModuloDomanda(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}

	@GetMapping("/getFileSedePlanimetria")
	public ResponseEntity<Object> getFileSedePlanimetria(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idSede") Long idSede) {
		try {
			return new ResponseEntity<>(pdfService.getFileSedePlanimetria(idRichiesta, idSede), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}

	@GetMapping("/getFileSedeCopiaContratto")
	public ResponseEntity<Object> getFileSedeCopiaContratto(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idSede") Long idSede) {
		try {
			return new ResponseEntity<>(pdfService.getFileSedeCopiaContratto(idRichiesta, idSede), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}

	@GetMapping("/getFileRappresentante")
	public ResponseEntity<Object> getFileRappresentante(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getFileRappresentante(idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getFileCompOrgAm")
	public ResponseEntity<Object> getFileCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta,
												   @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getFileCompOrgAm(idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getFilePrestatore")
	public ResponseEntity<Object> getFilePrestatore(@RequestParam("idRichiesta") Long idRichiesta,
													@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getFilePrestatore(idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getFileMediatore")
	public ResponseEntity<Object> getFileMediatore(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getFileMediatore(idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}

	@GetMapping("/getAnteprimaFileAttoRiepOdm")
	public ResponseEntity<Object> getAnteprimaFileAttoRiepOdm(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAttoRiepOdm(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileSchedeMediatori")
	public ResponseEntity<Object> getAnteprimaFileSchedeMediatori(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileSchedeMediatori(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAnteprimaFileSchedeMediatoriCompOrgAm")
	public ResponseEntity<Object> getAnteprimaFileSchedeMediatoriCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileSchedeCompOrgAm(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOno")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOno(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazione(idRichiesta, idAnagrafica, (long) 29), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOnoAppA")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOnoAppA(@RequestParam("idRichiesta") Long idRichiesta, 
																			   @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazione(idRichiesta, idAnagrafica, (long) 39), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOnoAppB")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOnoAppB(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazione(idRichiesta, idAnagrafica, (long) 44), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOnoAppC")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOnoAppC(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazione(idRichiesta, idAnagrafica, (long) 53), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOnoCompOrgAm")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOnoCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazioneReqOnoCompOrgAm(idRichiesta, idAnagrafica), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAnteprimaFileElencoMediatori")
	public ResponseEntity<Object> getAnteprimaFileElencoMediatori(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileElencoMediatori(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Si è verifcato un errore non previsto");
		}
	}
	
	@GetMapping("/getAnteprimaFilePolizzaAss")
	public ResponseEntity<Object> getAnteprimaFilePolizzaAss(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFilePolizzaAss(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFilePrestaSerOpe")
	public ResponseEntity<Object> getAnteprimaFilePrestaSerOpe(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFilePrestaSerOpe(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}

	@GetMapping("/getAnteprimaFileAppeA")
	public ResponseEntity<Object> getAnteprimaFileAppeA(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAppeA(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileAppeB")
	public ResponseEntity<Object> getAnteprimaFileAppeB(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAppeB(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileAppeC")
	public ResponseEntity<Object> getAnteprimaFileAppeC(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAppeC(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneAppeA")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneAppeA(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazioneAppe(idRichiesta, idAnagrafica, (long) 39), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneAppeB")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneAppeB(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazioneAppe(idRichiesta, idAnagrafica, (long) 44), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneAppeC")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneAppeC(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazioneAppe(idRichiesta, idAnagrafica, (long) 53), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileDisponibilitaAppeA")
	public ResponseEntity<Object> getAnteprimaFileDisponibilitaAppeA(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileDisponibilitaAppe(idRichiesta, idAnagrafica, (long) 40), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileDisponibilitaAppeB")
	public ResponseEntity<Object> getAnteprimaFileDisponibilitaAppeB(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileDisponibilitaAppe(idRichiesta, idAnagrafica, (long) 45), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileDisponibilitaAppeC")
	public ResponseEntity<Object> getAnteprimaFileDisponibilitaAppe(@RequestParam("idRichiesta") Long idRichiesta, 
																    @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileDisponibilitaAppe(idRichiesta, idAnagrafica, (long) 54), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileDichiaSostAppeA")
	public ResponseEntity<Object> getAnteprimaFileDichiaSostAppeA(@RequestParam("idRichiesta") Long idRichiesta, 
																  @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileDichiaSostAppe(idRichiesta, idAnagrafica, (long) 41), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileDichiaSostAppeB")
	public ResponseEntity<Object> getAnteprimaFileDichiaSostAppeB(@RequestParam("idRichiesta") Long idRichiesta, 
																  @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileDichiaSostAppe(idRichiesta, idAnagrafica, (long) 46), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	
	@GetMapping("/getAnteprimaFileDichiaSostAppeC")
	public ResponseEntity<Object> getAnteprimaFileDichiaSostAppeC(@RequestParam("idRichiesta") Long idRichiesta, 
																  @RequestParam("idAnagrafica") Long idAnagrafica) {
		try {
			return new ResponseEntity<>(pdfService.getAnteprimaFileDichiaSostAppe(idRichiesta, idAnagrafica, (long) 55), HttpStatus.OK);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage().replace("-ErrorInfo", "")
					: "Si è verificato un errore non previsto";

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
	}
	


	// metodo che inserisce i dati in tab mediatori
	@PostMapping(value = "/anteprimaSpesa", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> anteprimaSpese(
			@RequestPart(value = "allegatoPdf", required = false) MultipartFile allegatoPdf) {
		try {
			return null;// return new
			// ResponseEntity<>(pdfService.getAnteprimaFileSediLegali(allegatoPdf.getBytes(),
			// HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Si è verificato un errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}

}
