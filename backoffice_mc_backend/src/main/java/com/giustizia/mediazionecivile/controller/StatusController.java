package com.giustizia.mediazionecivile.controller;

import java.util.AbstractMap;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.giustizia.mediazionecivile.service.StatusService;

@RestController
@RequestMapping("/status")
public class StatusController {
	@Autowired
	StatusService statusService;
	
	
	@GetMapping("/getModuloIsConvalidato")
	public ResponseEntity<Object> getModuloIsConvalidato(@RequestParam("idModulo") Long idModulo,
													     @RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", statusService.getModuloIsConvalidato(idModulo, idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/isConvalidatoAllModuli")
	public ResponseEntity<Object> isConvalidatoAllModuli(@RequestParam("idModulo") Long idModulo,
													     @RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", statusService.isConvalidatoAllModuli(idRichiesta, idModulo)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getModuloIsConvalidatoAdPersonam")
	public ResponseEntity<Object> getModuloIsConvalidatoAdPersonam(@RequestParam("idModulo") Long idModulo,
	 @RequestParam("idRichiesta") Long idRichiesta, @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
	    	return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", statusService.getModuloIsConvalidatoAdPersonam(idModulo, idRichiesta, idAnagrafica)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusSedi")
	public ResponseEntity<Object> getStatusSedi(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("status", statusService.getStatusSedi(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusAttoRODM")
	public ResponseEntity<Object> getStatusAttoRODM(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusAttoRODM(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusElencoMedGen")
	public ResponseEntity<Object> getStatusElencoMedGen(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusElencoMedGen(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusElencoMedInt")
	public ResponseEntity<Object> getStatusElencoMedInt(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusElencoMedInt(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusElencoMedCons")
	public ResponseEntity<Object> getStatusElencoMedCons(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusElencoMedCons(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusRapLegOrRespOrOrgAm")
	public ResponseEntity<Object> getStatusRapLegOrRespOrOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica,
															   @RequestParam("isCloneAnagraficaRapLegale") boolean isCloneAnagraficaRapLegale) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", statusService.getStatusRapLegOrRespOrOrgAm(idRichiesta, 
	    								 idAnagrafica, isCloneAnagraficaRapLegale)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusCompOrgAm")
	public ResponseEntity<Object> getStatusCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", statusService.getStatusCompOrgAm(idRichiesta, 
	    								 idAnagrafica)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusElencoRapLRespOrg")
	public ResponseEntity<Object> getStatusElencoRapLRespOrg(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", 
	    								 statusService.getStatusElencoRapLRespOrg(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusElenCompOrgAm")
	public ResponseEntity<Object> getStatusElenCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", 
	    								 statusService.getStatusElenCompOrgAm(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getAllModuloConvalidato")
	public ResponseEntity<Object> getAllModuloConvalidato(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>(statusService.getAllModuloConvalidato(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusReqOno")
	public ResponseEntity<Object> getStatusReqOno(@RequestParam("idRichiesta") Long idRichiesta, 
																		   @RequestParam("idAnagrafica") Long idAnagrafica,
																		   @RequestParam("convalidaAnagraficaRapLegale") boolean convalidaAnagraficaRapLegale) {      
		try {
		   	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", statusService.getStatusReqOno(idRichiesta, 
		   															  			idAnagrafica, convalidaAnagraficaRapLegale)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusReqOnoCompOrgAm")
	public ResponseEntity<Object> getStatusReqOnoCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
																		   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
		   	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", statusService.getStatusReqOnoCompOrgAm(idRichiesta, 
		   															  			idAnagrafica)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusElencoMediatori")
	public ResponseEntity<Object> getStatusElencoMediatori(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", statusService.getStatusElencoMediatori(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusAppendiceA")
	public ResponseEntity<Object> getStatusAppendiceA(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusAppendiceA(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusAppendiceB")
	public ResponseEntity<Object> getStatusAppendiceB(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusAppendiceB(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusAppendiceC")
	public ResponseEntity<Object> getStatusAppendiceC(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusAppendiceC(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusAppendiceD")
	public ResponseEntity<Object> getStatusAppendiceD(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusAppendiceD(idRichiesta)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@GetMapping("/getStatusPrestatoreServizio")
	public ResponseEntity<Object> getStatusPrestatoreServizio(@RequestParam("idRichiesta") Long idRichiesta, 
															  @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
	    	return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", statusService.getStatusPrestatoreServizio(idRichiesta, idAnagrafica)), HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }

	@GetMapping("/getAllModuloAnnulato")
	public ResponseEntity<Object> getAllModuloAnnulato(@RequestParam("idRichiesta") Long idRichiesta) {  
    	return new ResponseEntity<>(statusService.getAllModuloAnnulato(idRichiesta), HttpStatus.OK);
	}
	
	@PostMapping("/validaDomandaDiIscrizione")
	public ResponseEntity<Object> validaDomandaDiIscrizione(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneDomandaDiIscrizione(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaSediAttoRiepilogativo")
	public ResponseEntity<Object> validaSediAttoRiepilogativo(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneSediAttoRiepilogativo(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaSedeAttoRiepilogativo")
	public ResponseEntity<Object> validaSedeAttoRiepilogativo(@RequestParam("idRichiesta") Long idRichiesta, 
															  @RequestParam("idSede") Long idSede) {      
		try {
			statusService.validaSedeAttoRiepilogativo(idRichiesta, idSede);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaAttoRiepilogativoODM")
	public ResponseEntity<Object> validaAttoRiepilogativoODM(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneAttoRiepilogativoODM(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaAttoCostitutivoODM")
	public ResponseEntity<Object> validaAttoCostitutivoODM(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneAttoCostitutivoODM(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaStatutoOrganismo")
	public ResponseEntity<Object> validaStatutoOrganismo(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneStatutoOrganismo(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaAttoCostitutivoODMNA")
	public ResponseEntity<Object> validaAttoCostitutivoODMNA(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneAttoCostitutivoODMNA(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaStatutoOrganismoNA")
	public ResponseEntity<Object> validaStatutoOrganismoNA(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneStatutoOrganismoNA(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaRegolamentoProcedura")
	public ResponseEntity<Object> validaRegolamentoProcedura(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneRegolamentoProcedura(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaSpeseMediazione")
	public ResponseEntity<Object> validaSpeseMediazione(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneSpeseMediazione(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaCodiceEtico")
	public ResponseEntity<Object> validaCodiceEtico(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneCodiceEtico(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaBilancio")
	public ResponseEntity<Object> validaBilancio(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneBilancioCertificaizoneBancaria(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneRequisitiOnorabilita")
	public ResponseEntity<Object> validazioneRequisitiOnorabilita(@RequestParam("idRichiesta") Long idRichiesta, 
													   @RequestParam("idAnagrafica") Long idAnagrafica,
													   @RequestParam("isCloneAnagraficaRapLegale") boolean isCloneAnagraficaRapLegale) {      
		try {
			statusService.validazioneRequisitiOnorabilita(idRichiesta, idAnagrafica, isCloneAnagraficaRapLegale);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneRequisitiOnorabilitaAppendici")
	public ResponseEntity<Object> validazioneRequisitiOnorabilitaAppendici(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idModulo") Long idModulo, @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneOnorabilitaAppendici(idRichiesta, idModulo, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneRequisitiOnorabilitaCompOrgAm")
	public ResponseEntity<Object> validazioneRequisitiOnorabilitaCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
													   					      @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneRequisitiOnorabilitaCompOrgAm(idRichiesta, idAnagrafica, false);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneRapLegAndRespOrg")
	public ResponseEntity<Object> validazioneRapLegAndRespOrg(@RequestParam("idRichiesta") Long idRichiesta, 
															  @RequestParam("idAnagrafica") Long idAnagrafica,
															  @RequestParam("isCloneAnagraficaRapLegale") boolean isCloneAnagraficaRapLegale) {      
		try {
			statusService.validazioneRapLegAndRespOrg(idRichiesta, idAnagrafica, isCloneAnagraficaRapLegale);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneElencoRapLegAndRespOrg")
	public ResponseEntity<Object> validazioneElencoRapLegAndRespOrg(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneElencoRapLegAndRespOrg(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneElencoCompOrgAmAndCompSoc")
	public ResponseEntity<Object> validazioneElencoCompOrgAmAndCompSoc(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneElencoCompOrgAmAndCompSoc(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneCompOrgAmAndCompSoc")
	public ResponseEntity<Object> validazioneCompOrgAmAndCompSoc(@RequestParam("idRichiesta") Long idRichiesta, 
															        @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneCompOrgAmAndCompSoc(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneAttoCostNonAutonomo")
	public ResponseEntity<Object> validazioneAttoCostNonAutonomo(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneAttoCostNonAutonomo(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneStatutoOrgNonAutonomo")
	public ResponseEntity<Object> validazioneStatutoOrgNonAutonomo(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneStatutoOrgNonAutonomo(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneDichiarazionePolizzaAss")
	public ResponseEntity<Object> validazioneDichiarazionePolizzaAss(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneDichiaPolizzaAss(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazionePolizzaAssicurativa")
	public ResponseEntity<Object> validazionePolizzaAssicurativa(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazionePolizzaAssicurativa(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneMediatore")
	public ResponseEntity<Object> validazioneMediatore(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneMediatore(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneElencoMediatori")
	public ResponseEntity<Object> validazioneElencoMediatori(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneMediatore(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazionePrestatoreServizio")
	public ResponseEntity<Object> validazionePrestatoreServizio(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazionePrestatoreServizio(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validazioneElencoPrestServizio")
	public ResponseEntity<Object> validazioneElencoPrestServizio(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneElencoPrestServizio(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	/*******************************************************************************************************************/
	
	@PostMapping("/validaDisponibilitaA")
	public ResponseEntity<Object> validaDisponibilitaA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneDisponibilitaA(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaDisponibilitaB")
	public ResponseEntity<Object> validaDisponibilitaB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneDisponibilitaB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaDisponibilitaC")
	public ResponseEntity<Object> validaDisponibilitaC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneDisponibilitaC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaFormazioneInizialeA")
	public ResponseEntity<Object> validaFormazioneInizialeA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneFormazioneInizialeA(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaFormazioneInizialeB")
	public ResponseEntity<Object> validaFormazioneInizialeB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneFormazioneInizialeB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaFormazioneInizialeC")
	public ResponseEntity<Object> validaFormazioneInizialeC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneFormazioneInizialeC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaCertificazioneB")
	public ResponseEntity<Object> validaCertificazioneB(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneCertificazioneB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaCertificazioneC")
	public ResponseEntity<Object> validaCertificazioneC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneCertificazioneC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaFormazioneSpecificaB")
	public ResponseEntity<Object> validaFormazioneSpecificaB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneFormazioneSpecificaB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaFormazioneSpecificaC")
	public ResponseEntity<Object> validaFormazioneSpecificaC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneFormazioneSpecificaC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaUlterioriRequisitiA")
	public ResponseEntity<Object> validaUlterioriRequisitiA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneUlterioriRequisitiA(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaUlterioriRequisitiB")
	public ResponseEntity<Object> validaUlterioriRequisitiB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneUlterioriRequisitiB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaUlterioriRequisitiC")
	public ResponseEntity<Object> validaUlterioriRequisitiC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.validazioneUlterioriRequisitiC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaElencoMediatoreA")
	public ResponseEntity<Object> validaElencoMediatoreA(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneElencoMediatoreA(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaElencoMediatoreB")
	public ResponseEntity<Object> validaElencoMediatoreB(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneElencoMediatoreB(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaElencoMediatoreC")
	public ResponseEntity<Object> validaElencoMediatoreC(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validazioneElencoMediatoreC(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaDomandaDiIscrizione")
	public ResponseEntity<Object> annullaDomandaDiIscrizione(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaDomandaDiIscrizione(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaSediAttoRiepilogativo")
	public ResponseEntity<Object> annullaSediAttoRiepilogativo(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaSediAttoRiepilogativo(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaSedeAttoRiepilogativo")
	public ResponseEntity<Object> annullaSedeAttoRiepilogativo(@RequestParam("idRichiesta") Long idRichiesta,
															   @RequestParam("idSede") Long idSede) {      
		try {
			statusService.annullaSedeAttoRiepilogativo(idRichiesta, idSede);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaAttoRiepilogativoODM")
	public ResponseEntity<Object> annullaAttoRiepilogativoODM(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaAttoRiepilogativoODM(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaAttoCostitutivoODM")
	public ResponseEntity<Object> annullaAttoCostitutivoODM(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaAttoCostitutivoODM(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaStatutoOrganismo")
	public ResponseEntity<Object> annullaStatutoOrganismo(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaStatutoOrganismo(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaAttoCostitutivoODMNA")
	public ResponseEntity<Object> annullaAttoCostitutivoODMNA(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaAttoCostitutivoODMNA(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaStatutoOrganismoNA")
	public ResponseEntity<Object> annullaStatutoOrganismoNA(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaStatutoOrganismoNA(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaRegolamentoProcedura")
	public ResponseEntity<Object> annullaRegolamentoProcedura(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaRegolamentoProcedura(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaSpeseMediazione")
	public ResponseEntity<Object> annullaSpeseMediazione(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaSpeseMediazione(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaCodiceEtico")
	public ResponseEntity<Object> annullaCodiceEtico(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaCodiceEtico(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaBilancio")
	public ResponseEntity<Object> annullaBilancio(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaBilancioCertificaizoneBancaria(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaRequisitiOnorabilita")
	public ResponseEntity<Object> annullaRequisitiOnorabilita(@RequestParam("idRichiesta") Long idRichiesta, 
													   @RequestParam("idAnagrafica") Long idAnagrafica,
													   @RequestParam("isCloneAnagraficaRapLegale") boolean isCloneAnagraficaRapLegale) {      
		try {
			statusService.annullaRequisitiOnorabilita(idRichiesta, idAnagrafica, isCloneAnagraficaRapLegale);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaRequisitiOnorabilitaAppendici")
	public ResponseEntity<Object> annullaRequisitiOnorabilitaAppendici(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idModulo") Long idModulo, @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaOnorabilitaAppendici(idRichiesta, idModulo, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaRequisitiOnorabilitaCompOrgAm")
	public ResponseEntity<Object> annullaRequisitiOnorabilitaCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
													   					      @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaRequisitiOnorabilitaCompOrgAm(idRichiesta, idAnagrafica, false);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaRapLegAndRespOrg")
	public ResponseEntity<Object> annullaRapLegAndRespOrg(@RequestParam("idRichiesta") Long idRichiesta, 
															     @RequestParam("idAnagrafica") Long idAnagrafica,
															     @RequestParam("isCloneAnagraficaRapLegale") boolean isCloneAnagraficaRapLegale) {      
		try {
			statusService.annullaRapLegAndRespOrg(idRichiesta, idAnagrafica, isCloneAnagraficaRapLegale);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaElencoRapLegAndRespOrg")
	public ResponseEntity<Object> annullaElencoRapLegAndRespOrg(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaElencoRapLegAndRespOrg(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaElencoCompOrgAmAndCompSoc")
	public ResponseEntity<Object> annullaElencoCompOrgAmAndCompSoc(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaElencoCompOrgAmAndCompSoc(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaCompOrgAmAndCompSoc")
	public ResponseEntity<Object> annullaCompOrgAmAndCompSoc(@RequestParam("idRichiesta") Long idRichiesta, 
															 @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaCompOrgAmAndCompSoc(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaAttoCostNonAutonomo")
	public ResponseEntity<Object> annullaAttoCostNonAutonomo(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaAttoCostNonAutonomo(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaStatutoOrgNonAutonomo")
	public ResponseEntity<Object> annullaStatutoOrgNonAutonomo(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaStatutoOrgNonAutonomo(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaDichiarazionePolizzaAss")
	public ResponseEntity<Object> annullaDichiarazionePolizzaAss(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaDichiaPolizzaAss(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaPolizzaAssicurativa")
	public ResponseEntity<Object> annullaPolizzaAssicurativa(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaPolizzaAssicurativa(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaMediatore")
	public ResponseEntity<Object> annullaMediatore(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaMediatore(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
//	/*MANCA SERVICE GIUSTO?*/
//	@PostMapping("/annullaMediatore")
//	public ResponseEntity<Object> annullaMediatore(@RequestParam("idRichiesta") Long idRichiesta, 
//															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
//		try {
//			statusService.annullaMediatore(idRichiesta, idAnagrafica);
//	        return new ResponseEntity<>(HttpStatus.OK);
//		} catch (Exception e) {
//	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
//		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
//		}
//    }
	
	@PostMapping("/annullaPrestatoreServizio")
	public ResponseEntity<Object> annullaPrestatoreServizio(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaPrestatoreServizio(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaElencoPrestServizio")
	public ResponseEntity<Object> annullaElencoPrestServizio(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaElencoPrestServizio(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaElencoMediatoreA")
	public ResponseEntity<Object> annullaElencoMediatoreA(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaElencoMediatoreA(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaElencoMediatoreB")
	public ResponseEntity<Object> annullaElencoMediatoreB(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaElencoMediatoreB(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaElencoMediatoreC")
	public ResponseEntity<Object> annullaElencoMediatoreC(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaElencoMediatoreC(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	/*******************************************************************************************************************/
	
	@PostMapping("/annullaDisponibilitaA")
	public ResponseEntity<Object> annullaDisponibilitaA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaDisponibilitaA(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaDisponibilitaB")
	public ResponseEntity<Object> annullaDisponibilitaB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaDisponibilitaB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaDisponibilitaC")
	public ResponseEntity<Object> annullaDisponibilitaC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaDisponibilitaC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaFormazioneInizialeA")
	public ResponseEntity<Object> annullaFormazioneInizialeA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaFormazioneInizialeA(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaFormazioneInizialeB")
	public ResponseEntity<Object> annullaFormazioneInizialeB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaFormazioneInizialeB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaFormazioneInizialeC")
	public ResponseEntity<Object> annullaFormazioneInizialeC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaFormazioneInizialeC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaCertificazioneB")
	public ResponseEntity<Object> annullaCertificazioneB(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaCertificazioneB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaCertificazioneC")
	public ResponseEntity<Object> annullaCertificazioneC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaCertificazioneC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaFormazioneSpecificaB")
	public ResponseEntity<Object> annullaFormazioneSpecificaB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaFormazioneSpecificaB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaFormazioneSpecificaC")
	public ResponseEntity<Object> annullaFormazioneSpecificaC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaFormazioneSpecificaC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaUlterioriRequisitiA")
	public ResponseEntity<Object> annullaUlterioriRequisitiA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaUlterioriRequisitiA(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaUlterioriRequisitiB")
	public ResponseEntity<Object> annullaUlterioriRequisitiB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaUlterioriRequisitiB(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/annullaUlterioriRequisitiC")
	public ResponseEntity<Object> annullaUlterioriRequisitiC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		try {
			statusService.annullaUlterioriRequisitiC(idRichiesta, idAnagrafica);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }

	@PostMapping("/annullaElencoMediatori")
	public ResponseEntity<Object> annullaElencoMediatori(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.annullaElencoMediatori(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
	@PostMapping("/validaElencoMediatori")
	public ResponseEntity<Object> validaElencoMediatori(@RequestParam("idRichiesta") Long idRichiesta) {      
		try {
			statusService.validaElencoMediatori(idRichiesta);
	        return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
	    	String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? 
		    		e.getMessage().replace("-ErrorInfo", "") : "Si è verificato un errore non previsto";	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
    }
	
}
