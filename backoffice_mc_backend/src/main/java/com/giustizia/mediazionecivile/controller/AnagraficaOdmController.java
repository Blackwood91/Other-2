package com.giustizia.mediazionecivile.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.configurations.ParametersConfigurations;
import com.giustizia.mediazionecivile.projection.AnagraficaOdmSezSecProjection;
import com.giustizia.mediazionecivile.projection.ElencoRappresentantiProjection;
import com.giustizia.mediazionecivile.service.AnagraficaOdmService;

@RestController
@RequestMapping("/anagrafica")
public class AnagraficaOdmController {
	@Autowired
	AnagraficaOdmService anagraficaOdmService;
	@Autowired
	ParametersConfigurations parametersConfigurations;
	
	@GetMapping("/getAnagraficaById")
	public ResponseEntity<AnagraficaOdmSezSecProjection> getAnagraficaById(@RequestParam("idAnagrafica") Long idAnagrafica) {
		return new ResponseEntity<>(anagraficaOdmService.getAnagraficaById(idAnagrafica), HttpStatus.OK);
	}
	
	@GetMapping("/getAnagraficaByIdDto")
	public ResponseEntity<Object> getAnagraficaByIdDto(@RequestParam("idAnagrafica") Long idAnagrafica) {
		return new ResponseEntity<>(anagraficaOdmService.getAnagraficaByIdDto(idAnagrafica), HttpStatus.OK);
	}

	@GetMapping("/getAllAnagrafica")
	public ResponseEntity<Object> getAllNaturaSocietaria() {
		try {
			return new ResponseEntity<>(anagraficaOdmService.getAllAnagrafica(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAnagraficaCloneRespOrg")
	public ResponseEntity<Object> getAnagraficaCloneRespOrg(@RequestParam("idRichiesta") Long idRichiesta, 
															@RequestParam("codiceFiscale") String codiceFiscale) {
		return new ResponseEntity<>(anagraficaOdmService.getAnagraficaCloneRespOrg(idRichiesta, codiceFiscale), HttpStatus.OK);
	}
	
	@GetMapping("/getAllAnagraficaPrestatori")
	public ResponseEntity<Object> getAllAnagraficaPrestatori(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			
			return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaPrestatori(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllAnagraficaMediatori")
	public ResponseEntity<Object> getAllAnagraficaMediatori() {
		try {
			return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaMediatori(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllAnagraficaMediatoriMedGen")
	public ResponseEntity<Object> getAllAnagraficaMediatoriMedGen(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
        
		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaMediatoriMedGen(pageable, idRichiesta));
	}
	
	@GetMapping("/getAllAnagraficaMediatoriMedInter")
	public ResponseEntity<Object> getAllAnagraficaMediatoriMedInter(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));

		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaMediatoriMedInter(pageable, idRichiesta));
	}
	
	@GetMapping("/getAllAnagraficaMediatoriMatCons")
	public ResponseEntity<Object> getAllAnagraficaMediatoriMatCons(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));

		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaMediatoriMatCons(pageable, idRichiesta));
	}
	
	@GetMapping("/getAllAnagraficaMediatoriA")
	public ResponseEntity<Object> getAllAnagraficaMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			
			return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaMediatoriA(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllAutocertificazioneMediatoriA")
	public ResponseEntity<Object> getAllAutocertificazioneMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllAutocertificazioneMediatoriA(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllAutocertificazioneMediatoriB")
	public ResponseEntity<Object> getAllAutocertificazioneMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllAutocertificazioneMediatoriB(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllAutocertificazioneMediatoriC")
	public ResponseEntity<Object> getAllAutocertificazioneMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllAutocertificazioneMediatoriC(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllDicDispMediatoriA")
	public ResponseEntity<Object> getAllDicDispMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														  @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														  @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllDicDispMediatoriA(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllDicDispMediatoriB")
	public ResponseEntity<Object> getAllDicDispMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														  @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														  @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllDicDispMediatoriB(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllDicDispMediatoriC")
	public ResponseEntity<Object> getAllDicDispMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														  @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														  @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllDicDispMediatoriC(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllAnagraficaMediatoriB")
	public ResponseEntity<Object> getAllAnagraficaMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaMediatoriB(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllAnagraficaMediatoriC")
	public ResponseEntity<Object> getAllAnagraficaMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllAnagraficaMediatoriC(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllFormazIniMediatoriA")
	public ResponseEntity<Object> getAllFormazIniMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllFormazIniMediatoriA(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllFormazIniMediatoriB")
	public ResponseEntity<Object> getAllFormazIniMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllFormazIniMediatoriB(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllFormazIniMediatoriC")
	public ResponseEntity<Object> getAllFormazIniMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllFormazIniMediatoriC(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllFormazSpeciMediatoriB")
	public ResponseEntity<Object> getAllFormazSpeciMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllFormazSpeciMediatoriB(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllFormazSpeciMediatoriC")
	public ResponseEntity<Object> getAllFormazSpeciMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllFormazSpeciMediatoriC(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllUlteReqMediatoriA")
	public ResponseEntity<Object> getAllUlteReqMediatoriA(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllUlteReqMediatoriA(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllUlteReqMediatoriB")
	public ResponseEntity<Object> getAllUlteReqMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllUlteReqMediatoriB(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllUlteReqMediatoriC")
	public ResponseEntity<Object> getAllUlteReqMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllUlteReqMediatoriC(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllCertificaMediatoriB")
	public ResponseEntity<Object> getAllCertificaMediatoriB(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllCertificaMediatoriB(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllCertificaMediatoriC")
	public ResponseEntity<Object> getAllCertificaMediatoriC(@RequestParam("idRichiesta") Long idRichiesta,
														     @RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
														     @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		try {
			Pageable pageable;
			if(indexPage.equalsIgnoreCase("MAX_RESULT")) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE);
			}
			else {
				pageable = PageRequest.of(Integer.parseInt(indexPage),
					Integer.parseInt(parametersConfigurations.getRowsTable()));
			}
			
			return new ResponseEntity<>(anagraficaOdmService.getAllCertificaMediatoriC(pageable, idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getAllRappresentantiAutocertificazioni")
	public ResponseEntity<Object> getAllRappresentantiAutocertificazioni(@RequestParam("idRichiesta") Long idRichiesta) {
		try {
			return new ResponseEntity<>(anagraficaOdmService.getAllRappresentantiAutocertificazioni(idRichiesta), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("errore non previsto", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getAllAnagraficaByIdRichiesta")
	public ResponseEntity<Object> getAllAnagraficaByIdRichiesta(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
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
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaByIdRichiestaForRapLegAndRespOrg(pageable, idRichiesta));
	}

	@GetMapping("/getAllAnagraficaByIdRichiestaForEleCompOrgAm")
	public ResponseEntity<Object> getAllAnagraficaByIdRichiestaForEleCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta,
			@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
			@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
		Pageable pageable = PageRequest.of(Integer.parseInt(indexPage),
				Integer.parseInt(parametersConfigurations.getRowsTable()));
		return ResponseEntity.ok(anagraficaOdmService.getAllAnagraficaByIdRichiestaForEleCompOrgAm(pageable, idRichiesta));		
	}
	
	@GetMapping("/getAllSelectAutocertReqOnoForCompOrgAm")
	public ResponseEntity<Object> getAllSelectAutocertReqOnoForCompOrgAm(@RequestParam("idRichiesta") Long idRichiesta) {
		return ResponseEntity.ok(anagraficaOdmService.getAllSelectAutocertReqOnoForCompOrgAm(idRichiesta));
	}

}
