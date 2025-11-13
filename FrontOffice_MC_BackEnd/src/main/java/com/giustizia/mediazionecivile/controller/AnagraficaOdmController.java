package com.giustizia.mediazionecivile.controller;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.giustizia.mediazionecivile.configurations.ParametersConfigurations;
import com.giustizia.mediazionecivile.dto.AnagraficaOdmDto;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.projection.AnagraficaOdmSezSecProjection;
import com.giustizia.mediazionecivile.projection.ElencoCompOrgAmProjection;
import com.giustizia.mediazionecivile.projection.ElencoRappresentantiProjection;
import com.giustizia.mediazionecivile.service.AnagraficaOdmService;
import com.giustizia.mediazionecivile.service.CheckAccessService;

@RestController
@RequestMapping("/anagrafica")
public class AnagraficaOdmController {
	@Autowired
	AnagraficaOdmService anagraficaOdmService;
	@Autowired
	ParametersConfigurations parametersConfigurations;
	@Autowired
	CheckAccessService checkAccessService;
	
	@GetMapping("/getAnagraficaById")
	public ResponseEntity<AnagraficaOdmSezSecProjection> getAnagraficaById(@RequestParam("idAnagrafica") Long idAnagrafica,
																		   @RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(anagraficaOdmService.getAnagraficaById(idAnagrafica), HttpStatus.OK);
	}
	
	@GetMapping("/getAnagraficaByIdDto")
	public ResponseEntity<Object> getAnagraficaByIdDto(@RequestParam("idAnagrafica") Long idAnagrafica,
													   @RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(anagraficaOdmService.getAnagraficaByIdDto(idAnagrafica), HttpStatus.OK);
	}
	
	@GetMapping("/getAnagraficaCloneRespOrg")
	public ResponseEntity<Object> getAnagraficaCloneRespOrg(@RequestParam("idRichiesta") Long idRichiesta, 
															@RequestParam("codiceFiscale") String codiceFiscale) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(anagraficaOdmService.getAnagraficaCloneRespOrg(idRichiesta, codiceFiscale), HttpStatus.OK);
	}
	
	@GetMapping("/getAllAnagraficaPrestatori")
	public ResponseEntity<Object> getAllAnagraficaPrestatori(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
		return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaPrestatori(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllAnagraficaMediatoriMedGen")
	public ResponseEntity<Object> getAllAnagraficaMediatoriMedGen(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));        
		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaMediatoriMedGen(pageable, idRichiesta));
	}
	
	@GetMapping("/getAllAnagraficaMediatoriMedInter")
	public ResponseEntity<Object> getAllAnagraficaMediatoriMedInter(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaMediatoriMedInter(pageable, idRichiesta));
	}
	
	@GetMapping("/getAllAnagraficaMediatoriMatCons")
	public ResponseEntity<Object> getAllAnagraficaMediatoriMatCons(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaMediatoriMatCons(pageable, idRichiesta));
	}
	
	@GetMapping("/getAllAnagraficaMediatoriA")
	public ResponseEntity<Object> getAllAnagraficaMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaMediatoriA(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllRappresentantiAutocertificazioni")
	public ResponseEntity<Object> getAllRappresentantiAutocertificazioni(@RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(anagraficaOdmService.getAllRappresentantiAutocertificazioni(idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllAutocertificazioneMediatoriA")
	public ResponseEntity<Object> getAllAutocertificazioneMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		
		checkAccessService.societaIsAssociateUser(idRichiesta);
		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllAutocertificazioneMediatoriA(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllAutocertificazioneMediatoriB")
	public ResponseEntity<Object> getAllAutocertificazioneMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllAutocertificazioneMediatoriB(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllAutocertificazioneMediatoriC")
	public ResponseEntity<Object> getAllAutocertificazioneMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllAutocertificazioneMediatoriC(pageable, idRichiesta), HttpStatus.OK);		
	}
	
	@GetMapping("/getAllDicDispMediatoriA")
	public ResponseEntity<Object> getAllDicDispMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														  @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														  @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllDicDispMediatoriA(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllDicDispMediatoriB")
	public ResponseEntity<Object> getAllDicDispMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														  @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														  @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllDicDispMediatoriB(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllDicDispMediatoriC")
	public ResponseEntity<Object> getAllDicDispMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														  @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														  @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllDicDispMediatoriC(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllAnagraficaMediatoriB")
	public ResponseEntity<Object> getAllAnagraficaMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaMediatoriB(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllAnagraficaMediatoriC")
	public ResponseEntity<Object> getAllAnagraficaMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaMediatoriC(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllFormazIniMediatoriA")
	public ResponseEntity<Object> getAllFormazIniMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllFormazIniMediatoriA(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllFormazIniMediatoriB")
	public ResponseEntity<Object> getAllFormazIniMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllFormazIniMediatoriB(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllFormazIniMediatoriC")
	public ResponseEntity<Object> getAllFormazIniMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllFormazIniMediatoriC(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllFormazSpeciMediatoriB")
	public ResponseEntity<Object> getAllFormazSpeciMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllFormazSpeciMediatoriB(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllFormazSpeciMediatoriC")
	public ResponseEntity<Object> getAllFormazSpeciMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllFormazSpeciMediatoriC(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllUlteReqMediatoriA")
	public ResponseEntity<Object> getAllUlteReqMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllUlteReqMediatoriA(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllUlteReqMediatoriB")
	public ResponseEntity<Object> getAllUlteReqMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllUlteReqMediatoriB(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllUlteReqMediatoriC")
	public ResponseEntity<Object> getAllUlteReqMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllUlteReqMediatoriC(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllCertificaMediatoriB")
	public ResponseEntity<Object> getAllCertificaMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllCertificaMediatoriB(pageable, idRichiesta), HttpStatus.OK);
	}
	
	@GetMapping("/getAllCertificaMediatoriC")
	public ResponseEntity<Object> getAllCertificaMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable;
		if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
		}
		else {
			pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		}
		
		return new ResponseEntity<>(anagraficaOdmService.getAllCertificaMediatoriC(pageable, idRichiesta), HttpStatus.OK);
	}

	@GetMapping("/getAllAnagraficaByIdRichiesta")
	public ResponseEntity<Object> getAllAnagraficaByIdRichiesta(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		Page<ElencoRappresentantiProjection> anagrafiche = anagraficaOdmService.getAllAnagraficaByIdRichiesta(pageable,
				idRichiesta);
		HashMap<String, Object> response = new HashMap<>();
		response.put("result", anagrafiche.getContent());
		response.put("totalResult", anagrafiche.getTotalElements());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getAllAnagraficaByIdRichiestaForRapLegAndRespOrg")
	public ResponseEntity<Object> getAllAnagraficaByIdRichiestaForRapLegAndRespOrg(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaByIdRichiestaForRapLegAndRespOrg(pageable, idRichiesta));
	}

	@GetMapping("/getAllAnagraficaByIdRichiestaForEleCompOrgAm")
	public ResponseEntity<Object> getAllAnagraficaByIdRichiestaForEleCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaByIdRichiestaForEleCompOrgAm(pageable, idRichiesta));		
	}
	
	@GetMapping("/getAllSelectAutocertReqOnoForCompOrgAm")
	public ResponseEntity<Object> getAllSelectAutocertReqOnoForCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		return ResponseEntity.ok(anagraficaOdmService.getAllSelectAutocertReqOnoForCompOrgAm(idRichiesta));
	}
	
	@GetMapping("/rapLegaleIsCompletato")
	public ResponseEntity<Object> rapLegaleIsCompletato(@RequestParam("idRichiesta") Long idRichiesta, @RequestParam("idAnagrafica") Long idAnagrafica) {
		checkAccessService.societaIsAssociateUser(idRichiesta);
		
		return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("completato", anagraficaOdmService.rapLegaleIsCompletato(idRichiesta)), HttpStatus.OK);
	}
	
	@PostMapping(value = "/saveAnagraficaPrestatore", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveAnagraficaPrestatore(@RequestPart("anagraficaDto") AnagraficaOdmDto anagraficaDto,
												 		   @RequestPart(value = "fileDocumento", required = false) MultipartFile fileDocumento) throws Exception {
		checkAccessService.societaIsAssociateUser(anagraficaDto.getIdRichiesta());

		anagraficaDto.setFile(fileDocumento != null ? fileDocumento.getBytes() : null);
		return new ResponseEntity<>(anagraficaOdmService.saveAnagraficaPrestatore(anagraficaDto), HttpStatus.OK);
	}
	
	@PostMapping(value = "/saveAnagraficaMedGen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveAnagraficaMediatoreGen(@RequestPart("anagraficaDto") AnagraficaOdmDto anagraficaOdmDto,
										                     @RequestPart(value = "fileDocumento", required = false) MultipartFile fileDocumento) throws Exception {
		checkAccessService.societaIsAssociateUser(anagraficaOdmDto.getIdRichiesta());

		anagraficaOdmDto.setFile(fileDocumento != null ? fileDocumento.getBytes() : null);
		return new ResponseEntity<>(anagraficaOdmService.saveAnagraficaMediatoreAppendici(anagraficaOdmDto), HttpStatus.OK);
	}

	@PostMapping(value = "/saveRapLegAndRespOrg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveRapLegAndRespOrg(@RequestPart("anagraficaDto") AnagraficaOdmDto anagraficaDto,
			@RequestPart(value = "fileDocumento", required = false) MultipartFile fileDocumento,
			@RequestPart(value = "fileQualificaMed", required = false) MultipartFile fileQualificaMed) throws Exception {
		checkAccessService.societaIsAssociateUser(anagraficaDto.getIdRichiesta());

		anagraficaDto.setFile(fileDocumento != null ? fileDocumento.getBytes() : null);
		anagraficaDto.setFile2(fileQualificaMed != null ? fileQualificaMed.getBytes() : null);
		return new ResponseEntity<>(anagraficaOdmService.saveRapLegAndRespOrg(anagraficaDto), HttpStatus.OK);
	}
	
	@PostMapping(value = "/saveCompOrgAmAndCompSoc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveCompOrgAmAndCompSoc(@RequestPart("anagraficaDto") AnagraficaOdmDto anagraficaDto,
			@RequestPart(value = "fileDocumento", required = false) MultipartFile fileDocumento) throws Exception {
		checkAccessService.societaIsAssociateUser(anagraficaDto.getIdRichiesta());

		anagraficaDto.setFile(fileDocumento != null ? fileDocumento.getBytes() : null);
		return new ResponseEntity<>(anagraficaOdmService.saveCompOrgAmAndCompSoc(anagraficaDto), HttpStatus.OK);
	}
	
	
	@PostMapping("/deleteRappresentante")
	public ResponseEntity<Object> deleteRappresentante(@RequestParam("idRichiesta") Long idRichiesta, @RequestParam("idAnagrafica") Long idAnagrafica) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		anagraficaOdmService.deleteRappresentante(idRichiesta, idAnagrafica);
		return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("esito", "La cancellazione è avvenuta con successo"), HttpStatus.OK);
	}
	
	@PostMapping("/deleteCompOrgAm")
	public ResponseEntity<Object> deleteCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta, @RequestParam("idAnagrafica") Long idAnagrafica) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		anagraficaOdmService.deleteCompOrgAm(idRichiesta, idAnagrafica);
		return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("esito", "La cancellazione è avvenuta con successo"), HttpStatus.OK);
	}
	
	@PostMapping("/deleteMediatore")
	public ResponseEntity<Object> deleteMediatore(@RequestParam("idRichiesta") Long idRichiesta, @RequestParam("idAnagrafica") Long idAnagrafica) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		anagraficaOdmService.deleteMediatore(idRichiesta, idAnagrafica);
		return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("esito", "La cancellazione è avvenuta con successo"), HttpStatus.OK);
	}
	
	@PostMapping("/deleteAnagraficaPrestatore")
	public ResponseEntity<Object> deleteAnagraficaPrestatore(@RequestParam("idRichiesta") Long idRichiesta, @RequestParam("idAnagrafica") Long idAnagrafica) {
		checkAccessService.societaIsAssociateUser(idRichiesta);

		anagraficaOdmService.deleteAnagraficaPrestatore(idRichiesta, idAnagrafica);
		return new ResponseEntity<>(new AbstractMap.SimpleEntry<>("esito", "La cancellazione è avvenuta con successo"), HttpStatus.OK);
	}

}
