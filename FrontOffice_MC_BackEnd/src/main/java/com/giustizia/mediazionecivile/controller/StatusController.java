package com.giustizia.mediazionecivile.controller;

import java.util.AbstractMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.service.CheckAccessService;
import com.giustizia.mediazionecivile.service.StatusService;

@RestController
@RequestMapping("/status")
public class StatusController {
	@Autowired
	StatusService convalidazioneService;
	@Autowired
	CheckAccessService checkAccessService;
	
	@GetMapping("/existModulo")
	public ResponseEntity<Object> existModulo(@RequestParam("idModulo") Long idModulo,
											  @RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("exist", convalidazioneService.existModulo(idModulo, idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getModuloIsConvalidato")
	public ResponseEntity<Object> getModuloIsConvalidato(@RequestParam("idModulo") Long idModulo,
													     @RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.getModuloIsConvalidato(idModulo, idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/isConvalidatoAllModuli")
	public ResponseEntity<Object> isConvalidatoAllModuli(@RequestParam("idModulo") Long idModulo,
													     @RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.isConvalidatoAllModuli(idRichiesta, idModulo)), HttpStatus.OK);
    }
	
	@GetMapping("/getModuloIsConvalidatoAdPersonam")
	public ResponseEntity<Object> getModuloIsConvalidatoAdPersonam(@RequestParam("idModulo") Long idModulo,
																   @RequestParam("idRichiesta") Long idRichiesta, 
																   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.getModuloIsConvalidatoAdPersonam(idModulo, idRichiesta, idAnagrafica)), HttpStatus.OK);
    }
	
	@GetMapping("/getAllModuliAreConvalidatoAdPersonam")
	public ResponseEntity<Object> getAllModuliAreConvalidatoAdPersonam(@RequestParam("idModulo") Long idModulo,
																	   @RequestParam("idRichiesta") Long idRichiesta, 
																	   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.getAllModuliAreConvalidatoAdPersonam(idModulo, idRichiesta, idAnagrafica)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusSedi")
	public ResponseEntity<Object> getStatusSedi(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusSedi(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusAttoRODM")
	public ResponseEntity<Object> getStatusAttoRODM(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusAttoRODM(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusRapLegOrRespOrOrgAm")
	public ResponseEntity<Object> getStatusRapLegOrRespOrOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica,
															   @RequestParam("convalidaAnagraficaRapLegale") boolean convalidaAnagraficaRapLegale) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.getStatusRapLegOrRespOrOrgAm(idRichiesta, 
	    								 idAnagrafica, convalidaAnagraficaRapLegale)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusCompOrgAm")
	public ResponseEntity<Object> getStatusCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
													 @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.getStatusCompOrgAm(idRichiesta, 
	    								 idAnagrafica)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusElenRapLegAndRespOrg")
	public ResponseEntity<Object> getStatusElenRapLegAndRespOrg(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", 
	    								 convalidazioneService.getStatusElenRapLegAndRespOrg(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getAllModuloConvalidato")
	public ResponseEntity<Object> getAllModuloConvalidato(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(convalidazioneService.getAllModuloConvalidato(idRichiesta), HttpStatus.OK);
    }
	
	@GetMapping("/getAllRichieste")
	public ResponseEntity<Object> getAllRichieste(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>(convalidazioneService.getAllRichieste(idRichiesta), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusReqOno")
	public ResponseEntity<Object> getStatusReqOno(@RequestParam("idRichiesta") Long idRichiesta, 
												  @RequestParam("idAnagrafica") Long idAnagrafica,
												  @RequestParam("convalidaAnagraficaRapLegale") boolean convalidaAnagraficaRapLegale) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.getStatusReqOno(idRichiesta, 
		   															  			idAnagrafica, convalidaAnagraficaRapLegale)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusReqOnoCompOrgAm")
	public ResponseEntity<Object> getStatusReqOnoCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
														   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.getStatusReqOnoCompOrgAm(idRichiesta, 
		   															  			idAnagrafica)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusElencoMediatori")
	public ResponseEntity<Object> getStatusElencoMediatori(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("isConvalidato", convalidazioneService.getStatusElencoMediatori(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusElencoRapLRespOrg")
	public ResponseEntity<Object> getStatusElencoRapLRespOrg(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", 
	    			convalidazioneService.getStatusElencoRapLRespOrg(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusElenCompOrgAm")
	public ResponseEntity<Object> getStatusElenCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", 
	    			convalidazioneService.getStatusElenCompOrgAm(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusElencoMedGen")
	public ResponseEntity<Object> getStatusElencoMedGen(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusElencoMedGen(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusElencoMedInt")
	public ResponseEntity<Object> getStatusElencoMedInt(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusElencoMedInt(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusElencoMedCons")
	public ResponseEntity<Object> getStatusElencoMedCons(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusElencoMedCons(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusAppendiceA")
	public ResponseEntity<Object> getStatusAppendiceA(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusAppendiceA(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusAppendiceB")
	public ResponseEntity<Object> getStatusAppendiceB(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusAppendiceB(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusAppendiceC")
	public ResponseEntity<Object> getStatusAppendiceC(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusAppendiceC(idRichiesta)), HttpStatus.OK);
    }
	
	@GetMapping("/getStatusPrestatoreServizio")
	public ResponseEntity<Object> getStatusPrestatoreServizio(@RequestParam("idRichiesta") Long idRichiesta, 
															  @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

	    return new ResponseEntity<>( new AbstractMap.SimpleEntry<>("status", convalidazioneService.getStatusPrestatoreServizio(idRichiesta, idAnagrafica)), HttpStatus.OK);
    }
	
	@PostMapping("/domandaDiIscrizione")
	public ResponseEntity<Object> domandaDiIscrizione(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneDomandaDiIscrizione(idRichiesta);
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/sediAttoRiepilogativo")
	public ResponseEntity<Object> sediAttoRiepilogativo(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneSediAttoRiepilogativo(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/attoRiepilogativoODM")
	public ResponseEntity<Object> attoRiepilogativoODM(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneAttoRiepilogativoODM(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/attoCostitutivoODM")
	public ResponseEntity<Object> attoCostitutivoODM(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneAttoCostitutivoODM(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/statutoOrganismo")
	public ResponseEntity<Object> statutoOrganismo(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneStatutoOrganismo(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/attoCostitutivoODMNA")
	public ResponseEntity<Object> attoCostitutivoODMNA(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneAttoCostitutivoODMNA(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/statutoOrganismoNA")
	public ResponseEntity<Object> statutoOrganismoNA(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneStatutoOrganismoNA(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/regolamentoProcedura")
	public ResponseEntity<Object> regolamentoProcedura(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneRegolamentoProcedura(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/speseMediazione")
	public ResponseEntity<Object> speseMediazione(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneSpeseMediazione(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/codiceEtico")
	public ResponseEntity<Object> codiceEtico(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneCodiceEtico(idRichiesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/bilancio")
	public ResponseEntity<Object> bilancio(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneBilancioCertificaizoneBancaria(idRichiesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneRequisitiOnorabilita")
	public ResponseEntity<Object> convalidazioneRequisitiOnorabilita(@RequestParam("idRichiesta") Long idRichiesta, 
													   @RequestParam("idAnagrafica") Long idAnagrafica,
													   @RequestParam("convalidaAnagraficaRapLegale") boolean convalidaAnagraficaRapLegale) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneRequisitiOnorabilita(idRichiesta, idAnagrafica, convalidaAnagraficaRapLegale);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneRequisitiOnorabilitaAppendici")
	public ResponseEntity<Object> convalidazioneRequisitiOnorabilitaAppendici(@RequestParam("idRichiesta") Long idRichiesta, 
																			  @RequestParam("idModulo") Long idModulo, 
																			  @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneOnorabilitaAppendici(idRichiesta, idModulo, idAnagrafica);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneRequisitiOnorabilitaCompOrgAm")
	public ResponseEntity<Object> convalidazioneRequisitiOnorabilitaCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, 
													   					      @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneRequisitiOnorabilitaCompOrgAm(idRichiesta, idAnagrafica, false);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneRapLegAndRespOrg")
	public ResponseEntity<Object> convalidazioneRapLegAndRespOrg(@RequestParam("idRichiesta") Long idRichiesta, 
															     @RequestParam("idAnagrafica") Long idAnagrafica,
															     @RequestParam("convalidaAnagraficaRapLegale") boolean convalidaAnagraficaRapLegale) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneRapLegAndRespOrg(idRichiesta, idAnagrafica, convalidaAnagraficaRapLegale);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneElencoRapLegAndRespOrg")
	public ResponseEntity<Object> convalidazioneElencoRapLegAndRespOrg(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneElencoRapLegAndRespOrg(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneElencoMediatori")
	public ResponseEntity<Object> convalidazioneElencoMediatori(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneElencoMediatori(idRichiesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneElencoCompOrgAmAndCompSoc")
	public ResponseEntity<Object> convalidazioneElencoCompOrgAmAndCompSoc(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneElencoCompOrgAmAndCompSoc(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneCompOrgAmAndCompSoc")
	public ResponseEntity<Object> convalidazioneCompOrgAmAndCompSoc(@RequestParam("idRichiesta") Long idRichiesta, 
															        @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneCompOrgAmAndCompSoc(idRichiesta, idAnagrafica);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneAttoCostNonAutonomo")
	public ResponseEntity<Object> convalidazioneAttoCostNonAutonomo(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneAttoCostNonAutonomo(idRichiesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneStatutoOrgNonAutonomo")
	public ResponseEntity<Object> convalidazioneStatutoOrgNonAutonomo(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneStatutoOrgNonAutonomo(idRichiesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneDichiarazionePolizzaAss")
	public ResponseEntity<Object> convalidazioneDichiarazionePolizzaAss(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneDichiaPolizzaAss(idRichiesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazionePolizzaAssicurativa")
	public ResponseEntity<Object> convalidazionePolizzaAssicurativa(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazionePolizzaAssicurativa(idRichiesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneMediatore")
	public ResponseEntity<Object> convalidazioneMediatore(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneMediatore(idRichiesta, idAnagrafica);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazionePrestatoreServizio")
	public ResponseEntity<Object> convalidazionePrestatoreServizio(@RequestParam("idRichiesta") Long idRichiesta, 
															   @RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazionePrestatoreServizio(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/convalidazioneElencoPrestServizio")
	public ResponseEntity<Object> convalidazioneElencoPrestServizio(@RequestParam("idRichiesta") Long idRichiesta) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneElencoPrestServizio(idRichiesta);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	/*******************************************************************************************************************/
	
	@PostMapping("/disponibilitaA")
	public ResponseEntity<Object> disponibilitaA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneDisponibilitaA(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/disponibilitaB")
	public ResponseEntity<Object> disponibilitaB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneDisponibilitaB(idRichiesta, idAnagrafica);
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/disponibilitaC")
	public ResponseEntity<Object> disponibilitaC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneDisponibilitaC(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/formazioneInizialeA")
	public ResponseEntity<Object> formazioneInizialeA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneFormazioneInizialeA(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/formazioneInizialeB")
	public ResponseEntity<Object> formazioneInizialeB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneFormazioneInizialeB(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/formazioneInizialeC")
	public ResponseEntity<Object> formazioneInizialeC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneFormazioneInizialeC(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/certificazioneB")
	public ResponseEntity<Object> certificazioneB(@RequestParam("idRichiesta") Long idRichiesta, 
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneCertificazioneB(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/certificazioneC")
	public ResponseEntity<Object> certificazioneC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneCertificazioneC(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/formazioneSpecificaB")
	public ResponseEntity<Object> formazioneSpecificaB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneFormazioneSpecificaB(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/formazioneSpecificaC")
	public ResponseEntity<Object> formazioneSpecificaC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneFormazioneSpecificaC(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/ulterioriRequisitiA")
	public ResponseEntity<Object> ulterioriRequisitiA(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneUlterioriRequisitiA(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/ulterioriRequisitiB")
	public ResponseEntity<Object> ulterioriRequisitiB(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneUlterioriRequisitiB(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/ulterioriRequisitiC")
	public ResponseEntity<Object> ulterioriRequisitiC(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam("idAnagrafica") Long idAnagrafica) {      
		checkAccessService.societaIsAssociateUser(idRichiesta);

		convalidazioneService.convalidazioneUlterioriRequisitiC(idRichiesta, idAnagrafica);
	    return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping("/checkFinalizza")
	public ResponseEntity<Object> checkFinalizza(@RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		boolean convalidazione = convalidazioneService.checkFinalizza(idRichiesta);
		return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("isConvalidato", convalidazione) , HttpStatus.OK);
	}
		
}
