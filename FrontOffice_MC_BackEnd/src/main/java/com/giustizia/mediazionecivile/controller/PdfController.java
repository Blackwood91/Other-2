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

import com.giustizia.mediazionecivile.service.CheckAccessService;
import com.giustizia.mediazionecivile.service.PdfService;

@RestController
@RequestMapping("/pdf")
public class PdfController {
	@Autowired
	PdfService pdfService;
	@Autowired
	CheckAccessService checkAccessService;
	
	@GetMapping("/getFileModulo")
	public ResponseEntity<Object> getFileModulo(@RequestParam("idRichiesta") Long idRichiesta, 
												@RequestParam("id") Long idModFiglio) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
	
		return new ResponseEntity<>(pdfService.getFileModulo(idModFiglio), HttpStatus.OK);
	}
	
	@GetMapping("/getFileModuloDomanda")
	public ResponseEntity<Object> getFileModuloDomanda(@RequestParam("idRichiesta") Long idRichiesta) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getFileModuloDomanda(idRichiesta), HttpStatus.OK);
	}

	@GetMapping("/getFileSedePlanimetria")
	public ResponseEntity<Object> getFileSedePlanimetria(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idSede") Long idSede) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
	
		return new ResponseEntity<>(pdfService.getFileSedePlanimetria(idRichiesta, idSede), HttpStatus.OK);
	}

	@GetMapping("/getFileSedeCopiaContratto")
	public ResponseEntity<Object> getFileSedeCopiaContratto(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idSede") Long idSede) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
	
		return new ResponseEntity<>(pdfService.getFileSedeCopiaContratto(idRichiesta, idSede), HttpStatus.OK);
	}

	@GetMapping("/getFileRappresentante")
	public ResponseEntity<Object> getFileRappresentante(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getFileRappresentante(idRichiesta, idAnagrafica), HttpStatus.OK);
	}
	
	@GetMapping("/getFileCompOrgAm")
	public ResponseEntity<Object> getFileCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta,
												   @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getFileCompOrgAm(idRichiesta, idAnagrafica), HttpStatus.OK);
	}
	
	@GetMapping("/getFilePrestatore")
	public ResponseEntity<Object> getFilePrestatore(@RequestParam("idRichiesta") Long idRichiesta,
													@RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getFilePrestatore(idRichiesta, idAnagrafica), HttpStatus.OK);
	}
	
	@GetMapping("/getFileMediatore")
	public ResponseEntity<Object> getFileMediatore(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getFileMediatore(idRichiesta, idAnagrafica), HttpStatus.OK);
	}

	@GetMapping("/getAnteprimaFileAttoRiepOdm")
	public ResponseEntity<Object> getAnteprimaFileAttoRiepOdm(@RequestParam("idRichiesta") Long idRichiesta) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAttoRiepOdm(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileSchedeRapLegRespOrg")
	public ResponseEntity<Object> getAnteprimaFileSchedeRapLegRespOrg(@RequestParam("idRichiesta") Long idRichiesta, 
																      @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileSchedeRapLegRespOrg(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileSchedeMediatoriCompOrgAm")
	public ResponseEntity<Object> getAnteprimaFileSchedeMediatoriCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileSchedeCompOrgAm(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazione")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazione(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazione(idRichiesta, idAnagrafica, (long) 29), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOnoCompOrgAm")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOnoCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazioneReqOnoCompOrgAm(idRichiesta, idAnagrafica), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOnoAppA")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOnoAppA(@RequestParam("idRichiesta") Long idRichiesta, 
																			   @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazione(idRichiesta, idAnagrafica, (long) 39), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOnoAppB")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOnoAppB(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazione(idRichiesta, idAnagrafica, (long) 44), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneReqOnoAppC")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneReqOnoAppC(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazione(idRichiesta, idAnagrafica, (long) 53), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileElencoMediatori")
	public ResponseEntity<Object> getAnteprimaFileElencoMediatori(@RequestParam("idRichiesta") Long idRichiesta) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileElencoMediatori(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFilePolizzaAss")
	public ResponseEntity<Object> getAnteprimaFilePolizzaAss(@RequestParam("idRichiesta") Long idRichiesta) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFilePolizzaAss(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFilePrestaSerOpe")
	public ResponseEntity<Object> getAnteprimaFilePrestaSerOpe(@RequestParam("idRichiesta") Long idRichiesta) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFilePrestaSerOpe(idRichiesta), HttpStatus.OK);
	}

	@GetMapping("/getAnteprimaFileAppeA")
	public ResponseEntity<Object> getAnteprimaFileAppeA(@RequestParam("idRichiesta") Long idRichiesta) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAppeA(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAppeB")
	public ResponseEntity<Object> getAnteprimaFileAppeB(@RequestParam("idRichiesta") Long idRichiesta) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAppeB(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAppeC")
	public ResponseEntity<Object> getAnteprimaFileAppeC(@RequestParam("idRichiesta") Long idRichiesta) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAppeC(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneAppeA")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneAppeA(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazioneAppe(idRichiesta, idAnagrafica, (long) 39), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneAppeB")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneAppeB(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazioneAppe(idRichiesta, idAnagrafica, (long) 44), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileAutocertificazioneAppeC")
	public ResponseEntity<Object> getAnteprimaFileAutocertificazioneAppeC(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
	
		return new ResponseEntity<>(pdfService.getAnteprimaFileAutocertificazioneAppe(idRichiesta, idAnagrafica, (long) 53), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileDisponibilitaAppeA")
	public ResponseEntity<Object> getAnteprimaFileDisponibilitaAppeA(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileDisponibilitaAppe(idRichiesta, idAnagrafica, (long) 40), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileDisponibilitaAppeB")
	public ResponseEntity<Object> getAnteprimaFileDisponibilitaAppeB(@RequestParam("idRichiesta") Long idRichiesta, 
																		  @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileDisponibilitaAppe(idRichiesta, idAnagrafica, (long) 45), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileDisponibilitaAppeC")
	public ResponseEntity<Object> getAnteprimaFileDisponibilitaAppe(@RequestParam("idRichiesta") Long idRichiesta, 
																    @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileDisponibilitaAppe(idRichiesta, idAnagrafica, (long) 54), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileDichiaSostAppeA")
	public ResponseEntity<Object> getAnteprimaFileDichiaSostAppeA(@RequestParam("idRichiesta") Long idRichiesta, 
																  @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileDichiaSostAppe(idRichiesta, idAnagrafica, (long) 41), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileDichiaSostAppeB")
	public ResponseEntity<Object> getAnteprimaFileDichiaSostAppeB(@RequestParam("idRichiesta") Long idRichiesta, 
																  @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileDichiaSostAppe(idRichiesta, idAnagrafica, (long) 46), HttpStatus.OK);
	}
	
	@GetMapping("/getAnteprimaFileDichiaSostAppeC")
	public ResponseEntity<Object> getAnteprimaFileDichiaSostAppeC(@RequestParam("idRichiesta") Long idRichiesta, 
																  @RequestParam("idAnagrafica") Long idAnagrafica) throws Exception {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(pdfService.getAnteprimaFileDichiaSostAppe(idRichiesta, idAnagrafica, (long) 55), HttpStatus.OK);
	}

}
