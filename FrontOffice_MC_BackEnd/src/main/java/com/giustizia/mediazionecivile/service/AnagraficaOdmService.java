package com.giustizia.mediazionecivile.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.AnagraficaOdmDto;
import com.giustizia.mediazionecivile.dto.AutocertificazioneAppD;
import com.giustizia.mediazionecivile.dto.ElencoCompOrgAmDto;
import com.giustizia.mediazionecivile.dto.ElencoRappresentantiDto;

import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.mercurio.MercurioFile;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.NaturaSocietaria;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.Societa;
import com.giustizia.mediazionecivile.model.SoggettoRichiesta;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.projection.SezioneSecDomOdmProjection;
import com.giustizia.mediazionecivile.projection.AnagraficaOdmSezSecProjection;
import com.giustizia.mediazionecivile.projection.ElencoCompOrgAmProjection;
import com.giustizia.mediazionecivile.projection.ElencoRappresentantiProjection;
import com.giustizia.mediazionecivile.projection.MediatoreProjection;
import com.giustizia.mediazionecivile.projection.SelectAutocertReqOnoProjection;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;

@Service
public class AnagraficaOdmService {
	@Autowired
	AnagraficaOdmRepository anagraficaOdmRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	@Autowired
	StatoModuliRichiestaFigliRepository statoModuliRichiestaFigliRepository;
	@Autowired
	RichiesteRepository richiesteRepository;
	@Autowired 
	ApiFileService apiFileService;
	@Autowired
	PdfService pdfService;
	@Autowired
	StatusService convalidazioneService; 
	@Autowired
	StatoModuloAllegatoService statoModuloAllegatoService;

	public AnagraficaOdmSezSecProjection getAnagraficaById(Long idAnagrafica) {
		return anagraficaOdmRepository.findByidAnagraficaProj(idAnagrafica);
	}
	
	public Optional<AnagraficaOdm> getAnagraficaByIdDto(Long idAnagrafica) {
		return anagraficaOdmRepository.findById(idAnagrafica);
	}

	public Optional<AnagraficaOdm> getRappresentateLegale(Long idRichiesta) {
		return anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
	}
	
	public HashMap<String, Object> getAllRappresentantiAutocertificazioni(Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		
		List<Object[]> resultSelectRespOrg = anagraficaOdmRepository.findAllForSelectAutocertReqOno(idRichiesta, (long) 2);
		List<AutocertificazioneAppD> anagraficheRespOrg = new ArrayList<AutocertificazioneAppD>();
		for(Object[] obj : resultSelectRespOrg) {
			AutocertificazioneAppD anagrafica = new AutocertificazioneAppD();
			anagrafica.setIdAnagrafica((Long) obj[0]);
			anagrafica.setIdQualifica((Long) obj[1]);
			anagrafica.setNome((String) obj[2]);
			anagrafica.setCognome((String) obj[3]);
			anagrafica.setCodiceFiscale((String) obj[4]);
			anagrafica.setCompletato((Integer) obj[5]);
			anagrafica.setValidato((Integer) obj[6]);
			anagrafica.setAnnullato((Integer) obj[7]);
			
			anagraficheRespOrg.add(anagrafica);
		}
		
		Optional<Object[]> resultAnagraficaRapLeg = anagraficaOdmRepository.findForSelectAutocertReqOno(idRichiesta, (long) 1);
		AutocertificazioneAppD anagraficaRapLeg = new AutocertificazioneAppD();
		Object[] objRapL = (Object[]) resultAnagraficaRapLeg.get()[0];
		anagraficaRapLeg.setIdAnagrafica((Long) objRapL[0]);
		anagraficaRapLeg.setIdQualifica((Long) objRapL[1]);
		anagraficaRapLeg.setNome((String) objRapL[2]);
		anagraficaRapLeg.setCognome((String) objRapL[3]);
		anagraficaRapLeg.setCodiceFiscale((String) objRapL[4]);
		anagraficaRapLeg.setCompletato((Integer) objRapL[5]);
		anagraficaRapLeg.setValidato((Integer) objRapL[6]);
		anagraficaRapLeg.setAnnullato((Integer) objRapL[7]);		
		
		List<AutocertificazioneAppD> anagrafiche = anagraficheRespOrg;
		Optional<AutocertificazioneAppD> anagraficaClone = Optional.empty();	
		boolean existClone = false;
	
		// CICLO PER LA GESTIONE DELLA LISTA A SECONDA DELL'ESISTENZA O NO DEL CLONE 
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AutocertificazioneAppD anagrafica = anagrafiche.get(a);
			// CONDIZIONE SOLO PER IL RESPONSABILE DELL'ORGANISMO
			if(anagrafica.getIdQualifica() == 2) {
				// CONDIZIONE PER TROVARE LA SCHEDA CLONATA SE ESISTENTE 
				if(anagraficaRapLeg.getCodiceFiscale().equalsIgnoreCase(anagrafica.getCodiceFiscale())) {
					anagraficaClone = Optional.of(anagrafica);	
					existClone = true;
					
					// SERVIRA' COME PARAMETRO DI RIFERIMENTO PER LA DOPPIA CONVALIDAZIONE IN CASO DI CLONE CON RAP.LEGALE
					response.put("anagraficaRapLegClone", anagraficaRapLeg);
				}
			}
		}
		// SE NON ESISTE IL CLONE VERRA INSERITO IL RAP.LEGALE DENTRO LA LISTA DELLE ANAGRAFICHE
		if(existClone == false) {
			// VERRA' INSERITA LA SCHEDA DEL RAP LEGALE COME PRIMO RISULTATRO, 
			// PER AVERE ANCHE UNA IMPAGINAZIONE CORRETTA DELLA MODULISTICA 
			anagrafiche.add(0, anagraficaRapLeg);
			response.put("idAnagraficaRapLegClone", null);
		}
		
		response.put("list", anagrafiche);
		return response;	
	}
	
	public HashMap<String, Object> getAllAutocertificazioneMediatoriA(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllAutocertificazioneMediatoriA(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllAutocertificazioneMediatoriB(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllAutocertificazioneMediatoriB(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllAutocertificazioneMediatoriC(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllAutocertificazioneMediatoriC(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public boolean rapLegaleIsCompletato(Long idRichiesta) {
		Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta,
						anagraficaLeg.get().getIdAnagrafica());
		
		// SE IL DOCUEMNTO DEL RAP LEGALE E' STATO INSERTIO VERRA CONSIDERATO COMPLETATO L'ANAGRAFICA
		if(moduloDocumento.isPresent() == false) {
			return false;
		}
		else {
			return true;
		}		
	}
	
	public List<AutocertificazioneAppD> getAllSelectAutocertReqOnoForCompOrgAm(Long idRichiesta) {	
		List<Object[]> resultSelectAnagrafiche = anagraficaOdmRepository.findAllForSelectAutocertReqOnoForCompOrgAm(idRichiesta);
		List<AutocertificazioneAppD> anagrafiche = new ArrayList<AutocertificazioneAppD>();
		for(Object[] obj : resultSelectAnagrafiche) {
			AutocertificazioneAppD anagrafica = new AutocertificazioneAppD();
			anagrafica.setIdAnagrafica((Long) obj[0]);
			anagrafica.setIdQualifica((Long) obj[1]);
			anagrafica.setNome((String) obj[2]);
			anagrafica.setCognome((String) obj[3]);
			anagrafica.setCodiceFiscale((String) obj[4]);
			anagrafica.setCompletato((Integer) obj[5]);
			anagrafica.setValidato((Integer) obj[6]);
			anagrafica.setAnnullato((Integer) obj[7]);
			
			anagrafiche.add(anagrafica);
		}
		
		return anagrafiche;
	}

	public List<AnagraficaOdm> getAllAnagrafica() {
		return anagraficaOdmRepository.findAll();
	}


//	public HashMap<String, Object> getAllAnagraficaPrestatori(Long idRichiesta) {
//		HashMap<String, Object> response = new HashMap<>();
//		List<Object[]> result = anagraficaOdmRepository.getAllAnagraficaPrestatori(idRichiesta);
//
//		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
//		for (Object[] obj : result) {
//			AnagraficaOdmDto formMedTemp = new AnagraficaOdmDto();
//			formMedTemp.setIdAnagrafica((Long) obj[2]);
//			formMedTemp.setNome((String) obj[6].toString());		
//			formMedTemp.setCognome((String) obj[7].toString());
//			formMedTemp.setCodiceFiscale((String) obj[5].toString());
//			formMedTemp.setSesso((String) obj[8]);
//			formMedTemp.setDataNascita((Date) obj[43]);
//			formMedTemp.setIdComuneNascita((Long) obj[47]);
//			formMedTemp.setComuneNascitaEstero((String) obj[18]);
//			formMedTemp.setIdTipoAnagrafica((Long) obj[54]);
//			formMedTemp.setMedCellulare((String) obj[29]);
//			formMedTemp.setMedTelefono((String) obj[28]);
//			formMedTemp.setMedFax((String) obj[30]);
//			formMedTemp.setIdComuneResidenza((Long) obj[12]);
//
//			anagDto.add(formMedTemp);
//		}
//
//		response.put("result", anagDto);
//		return response;
//	}
	
	public HashMap<String, Object> getAllAnagraficaMediatori() {
		HashMap<String, Object> response = new HashMap<>();
		List<Object[]> result = anagraficaOdmRepository.getAllAnagraficaMediatori();

		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		for (Object[] obj : result) {
			AnagraficaOdmDto formMedTemp = new AnagraficaOdmDto();
			formMedTemp.setIdAnagrafica((Long) obj[0]);
			formMedTemp.setNome((String) obj[3].toString());		
			formMedTemp.setCognome((String) obj[2].toString());
			formMedTemp.setCodiceFiscale((String) obj[1].toString());
			formMedTemp.setSesso((String) obj[4]);
			formMedTemp.setDataNascita((Date) obj[6]);
			formMedTemp.setIdComuneNascita((Long) obj[8]);
			formMedTemp.setComuneNascitaEstero((String) obj[9]);
			formMedTemp.setIdTipoAnagrafica((Long) obj[50]);
			formMedTemp.setMedCellulare((String) obj[29]);
			formMedTemp.setMedTelefono((String) obj[28]);
			formMedTemp.setMedFax((String) obj[30]);
			formMedTemp.setIdComuneResidenza((Long) obj[14]);


			anagDto.add(formMedTemp);
		}

		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllAnagraficaPrestatori(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllAnagraficaPrestatori(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllAnagraficaMediatoriA(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllAnagraficaMediatoriA(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			AnagraficaOdmDto anagrafica = new AnagraficaOdmDto(obj);
			// PARAMETRI AGGIUNTIVI AL COSTRUTTORE
			anagrafica.setMedNumeroOrganismiDisp((Long) obj[16]);
			
			anagDto.add(anagrafica);
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllAnagraficaMediatoriB(Pageable pageable, Long idRichiesta) {

		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllAnagraficaMediatoriB(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			AnagraficaOdmDto anagrafica = new AnagraficaOdmDto(obj);
			// PARAMETRI AGGIUNTIVI AL COSTRUTTORE
			anagrafica.setMedNumeroOrganismiDisp((Long) obj[16]);
			
			anagDto.add(anagrafica);
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllAnagraficaMediatoriC(Pageable pageable, Long idRichiesta) {

		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllAnagraficaMediatoriC(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			AnagraficaOdmDto anagrafica = new AnagraficaOdmDto(obj);
			// PARAMETRI AGGIUNTIVI AL COSTRUTTORE
			anagrafica.setMedNumeroOrganismiDisp((Long) obj[16]);
			
			anagDto.add(anagrafica);
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllDicDispMediatoriA(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllDicDispMediatoriA(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllDicDispMediatoriB(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllDicDispMediatoriB(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllDicDispMediatoriC(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllDicDispMediatoriC(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllFormazIniMediatoriA(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllFormazIniMediatoriA(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllFormazIniMediatoriB(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllFormazIniMediatoriB(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllFormazIniMediatoriC(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllFormazIniMediatoriC(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllFormazSpeciMediatoriB(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllFormazSpeciMediatoriB(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllFormazSpeciMediatoriC(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllFormazSpeciMediatoriC(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllUlteReqMediatoriA(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllUlteReqMediatoriA(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllUlteReqMediatoriB(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllUlteReqMediatoriB(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllUlteReqMediatoriC(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllUlteReqMediatoriC(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllCertificaMediatoriB(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllCertificaMediatoriB(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
	public HashMap<String, Object> getAllCertificaMediatoriC(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllCertificaMediatoriC(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AnagraficaOdmDto(obj));
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}

	public Page<AnagraficaOdm> getAllAnagraficaPaged(Pageable pageable, String searchText/* , String colonna */) {
		Page<AnagraficaOdm> anagraficaElenco = anagraficaOdmRepository.findAll(pageable);

		return anagraficaElenco;
	}

	public Page<ElencoRappresentantiProjection> getAllAnagraficaByIdRichiesta(Pageable pageable, Long idRichiesta) {
		return anagraficaOdmRepository.getAllAnagraficaByIdRichiesta(idRichiesta, pageable);
	}
	
	public HashMap<String, Object> getAllAnagraficaByIdRichiestaForRapLegAndRespOrg(Pageable pageable, Long idRichiesta) {
        HashMap<String, Object> response = new HashMap<>();
        Page<Object[]> resultPage;
        
        resultPage = anagraficaOdmRepository.getAllAnagraficaByIdRichiestaForRapLegAndRespOrg(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
        List<ElencoRappresentantiDto> elencoDTO = new ArrayList<>();
        for (Object[] obj : resultList) {
        	ElencoRappresentantiDto row = new ElencoRappresentantiDto(obj);
            elencoDTO.add(row);
        }
        response.put("result", elencoDTO);
        response.put("totalResult", resultPage.getTotalElements());

        return response;		
	}
	
	public HashMap<String, Object> getAllAnagraficaByIdRichiestaForEleCompOrgAm(Pageable pageable, Long idRichiesta) {
        HashMap<String, Object> response = new HashMap<>();
        Page<Object[]> resultPage;
        
        resultPage = anagraficaOdmRepository.getAllAnagraficaByIdRichiestaForEleCompOrgAm(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
        List<ElencoCompOrgAmDto> elencoDTO = new ArrayList<>();
        for (Object[] obj : resultList) {
        	ElencoCompOrgAmDto row = new ElencoCompOrgAmDto(obj);
            elencoDTO.add(row);
        }
        response.put("result", elencoDTO);
        response.put("totalResult", resultPage.getTotalElements());

        return response;
	}
	
	public ElencoRappresentantiProjection getAnagraficaCloneRespOrg(Long idRichiesta, String codiceFiscale) {
		return anagraficaOdmRepository.getAnagraficaCloneRespOrg(idRichiesta, codiceFiscale);
	}
	
    public HashMap<String, Object> getAllAnagraficaMediatoriMedGen(Pageable pageable, Long idRichiesta) {
        HashMap<String, Object> response = new HashMap<>();
        Page<MediatoreProjection> resultPage = anagraficaOdmRepository.getAllMediatoriForIdTipoAnagrafica(idRichiesta, (long) 4, pageable);

        response.put("result", resultPage.getContent());
        response.put("totalResult", resultPage.getTotalElements());

        return response;
    }
    
    public HashMap<String, Object> getAllAnagraficaMediatoriMedInter(Pageable pageable, Long idRichiesta) {
        HashMap<String, Object> response = new HashMap<>();
        Page<MediatoreProjection> resultPage = anagraficaOdmRepository.getAllMediatoriForIdTipoAnagrafica(idRichiesta, (long) 5, pageable);

        response.put("result", resultPage.getContent());
        response.put("totalResult", resultPage.getTotalElements());

        return response;
    }
    
    public HashMap<String, Object> getAllAnagraficaMediatoriMatCons(Pageable pageable, Long idRichiesta) {
        HashMap<String, Object> response = new HashMap<>();
        Page<MediatoreProjection> resultPage = anagraficaOdmRepository.getAllMediatoriForIdTipoAnagrafica(idRichiesta, (long) 6, pageable);

        response.put("result", resultPage.getContent());
        response.put("totalResult", resultPage.getTotalElements());

        return response;
    }


	@Transactional(rollbackFor = Exception.class)
	public AnagraficaOdm insertSchedaAnaRespOrg(AnagraficaOdmDto anagraficaOdmDto) {
		AnagraficaOdm anagraficaOdm = new AnagraficaOdm();
		StatoModuliRichiestaFigli moduloCostOdm = new StatoModuliRichiestaFigli();

		anagraficaOdm.setIdTitoloAnagrafica(anagraficaOdmDto.getIdTitoloAnagrafica());
		anagraficaOdm.setCognome(anagraficaOdmDto.getCognome());
		anagraficaOdm.setNome(anagraficaOdmDto.getNome());
		anagraficaOdm.setSesso(anagraficaOdmDto.getSesso());
		anagraficaOdm.setDataNascita(anagraficaOdmDto.getDataNascita());
		anagraficaOdm.setPoDataAssunzione(anagraficaOdmDto.getPoDataAssunzione());
		anagraficaOdm.setStatoNascita(anagraficaOdmDto.getStatoNascita());
		anagraficaOdm.setIdComuneNascita(anagraficaOdmDto.getIdComuneNascita());
		anagraficaOdm.setCodiceFiscale(anagraficaOdmDto.getCodiceFiscale());
		anagraficaOdm.setCittadinanza(anagraficaOdmDto.getCittadinanza());
		anagraficaOdm.setPoTipoRappOdm(anagraficaOdmDto.getPoTipoRappOdm());
		anagraficaOdm.setComuneNascitaEstero(anagraficaOdmDto.getComuneNascitaEstero());

		moduloCostOdm.setIdModulo((long) 5);
		moduloCostOdm.setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
		moduloCostOdm.setNomeAllegato(anagraficaOdmDto.getNomeFile());
		moduloCostOdm.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModuloCostOdm = statoModuliRichiestaFigliRepository.save(moduloCostOdm);

		try {
			pdfService.checkValidLoadDocument(anagraficaOdmDto.getFile());
			
			String pathCostOdm = "/" + anagraficaOdmDto.getIdRichiesta() + "/odm/" + saveModuloCostOdm.getIdModulo();
			MercurioFile infoFile = apiFileService.insertFile(pathCostOdm, Long.toString(saveModuloCostOdm.getId()), anagraficaOdmDto.getFile());
			saveModuloCostOdm.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModuloCostOdm.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModuloCostOdm);
			
			return anagraficaOdmRepository.save(anagraficaOdm);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}

	}

	
	public AnagraficaOdm saveAnagraficaPrestatore(AnagraficaOdmDto anagraficaOdmDto) throws Exception {
		// ID_TIPO_ANAGRAFICA = 1 di default
		Optional<AnagraficaOdm> anagraficaOdm = anagraficaOdmRepository.findById(anagraficaOdmDto.getIdAnagrafica());
		Optional<StatoModuliRichiestaFigli> moduloScheda = Optional.empty();
		Optional<StatoModuliRichiestaFigli> moduloDocumento = Optional.empty();
		
		if(anagraficaOdm.isPresent() == false) {
			anagraficaOdm = Optional.of(new AnagraficaOdm());
		}

		anagraficaOdm.get().setIdTitoloAnagrafica(anagraficaOdmDto.getIdTitoloAnagrafica());
		anagraficaOdm.get().setCognome(anagraficaOdmDto.getCognome());
		anagraficaOdm.get().setNome(anagraficaOdmDto.getNome());
		anagraficaOdm.get().setSesso(anagraficaOdmDto.getSesso());
		anagraficaOdm.get().setDataNascita(anagraficaOdmDto.getDataNascita());
		anagraficaOdm.get().setPoDataAssunzione(anagraficaOdmDto.getPoDataAssunzione());
		anagraficaOdm.get().setStatoNascita(anagraficaOdmDto.getStatoNascita());
		anagraficaOdm.get().setIdComuneNascita(anagraficaOdmDto.getIdComuneNascita());
		anagraficaOdm.get().setCodiceFiscale(anagraficaOdmDto.getCodiceFiscale());
		anagraficaOdm.get().setCittadinanza(anagraficaOdmDto.getCittadinanza());
		anagraficaOdm.get().setPoTipoRappOdm(anagraficaOdmDto.getPoTipoRappOdm());
		anagraficaOdm.get().setComuneNascitaEstero(anagraficaOdmDto.getComuneNascitaEstero());
		anagraficaOdm.get().setIdTipoAnagrafica((long) 3);
		
		AnagraficaOdm saveAnagrafica = anagraficaOdmRepository.save(anagraficaOdm.get()); 
		
		if(anagraficaOdmDto.getIdAnagrafica() == 0) {
			SoggettoRichiesta sogRichiesta = new SoggettoRichiesta();
			sogRichiesta.setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
			sogRichiesta.setIdAnagrafica(saveAnagrafica.getIdAnagrafica());
			sogRichiesta.setIdTipoAnagrafica(3);
			soggettoRichiestaRepository.save(sogRichiesta);
		}

		//SCHEDA
		moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica(
						(long) 36, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
		
		if(moduloScheda.isPresent() == false) { //inserimento se non è già presente

			moduloScheda = Optional.of(new StatoModuliRichiestaFigli());
			moduloScheda.get().setIdModulo((long) 36);
			moduloScheda.get().setIdAnagrafica(saveAnagrafica.getIdAnagrafica());
			moduloScheda.get().setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
			moduloScheda.get().setNomeAllegato(anagraficaOdmDto.getNomeFile());
			moduloScheda.get().setDataInserimento(new Date());
			//convalida, perchè siamo in inserimento
			moduloScheda.get().setCompletato((Integer) 1);
		}
		else { //deconvalida in update
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
		}
		
		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		
		//DOCUMENTO
		if(anagraficaOdmDto.getFile() != null) {
			long idModuloDocumento = 0;
			
			moduloDocumento = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, 
						      anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
			idModuloDocumento = 37;
					
			if(moduloDocumento.isPresent() == false) { //inserimento se non è già presente
				moduloDocumento = Optional.of(new StatoModuliRichiestaFigli());
				moduloDocumento.get().setIdModulo(idModuloDocumento);
				moduloDocumento.get().setIdAnagrafica(saveAnagrafica.getIdAnagrafica());
				moduloDocumento.get().setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
				moduloDocumento.get().setNomeAllegato(anagraficaOdmDto.getNomeFile());
				moduloDocumento.get().setDataInserimento(new Date());
				//convalida, perchè siamo in inserimento
				moduloDocumento.get().setCompletato(1);
			}
			else { //deconvalida in update
				moduloScheda.get().setValidato(0);
				moduloScheda.get().setCompletato(0);
			}
			
			StatoModuliRichiestaFigli saveModuloDocumento = statoModuliRichiestaFigliRepository.save(moduloDocumento.get());			
			//Mercurio
			try {
				pdfService.checkValidLoadDocument(anagraficaOdmDto.getFile());
				
				if(saveModuloDocumento.getDocumentIdClient() != null && saveModuloDocumento.getDocumentIdClient().isEmpty() == false) {
					apiFileService.deleteFile(saveModuloDocumento.getDocumentIdClient());
				}
				
				String pathCostOdm = "/" + anagraficaOdmDto.getIdRichiesta() + "/odm/" + saveModuloDocumento.getIdModulo();
				MercurioFile infoFile = apiFileService.insertFile(pathCostOdm, Long.toString(saveModuloDocumento.getId()), anagraficaOdmDto.getFile());
				saveModuloDocumento.setDocumentIdClient(infoFile.getDocumentIdClient());
				saveModuloDocumento.setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(saveModuloDocumento);				
			} catch (Exception e) {
				String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
						: "Si è verificato un errore con il caricamento del file nel documentale";
				throw new RuntimeException(message);
			}
		}
		
		// ANNULLA CONVALIDAZIONE DELL'ELENCO DEI PRESTATORI DI SERVIZIO
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long) 35, 
			      anagraficaOdmDto.getIdRichiesta());
		if(moduloElenco.isPresent() && moduloElenco.get().getCompletato() == (Integer) 1) {
			moduloElenco.get().setCompletato(0);
			moduloScheda.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
			
		return saveAnagrafica;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public AnagraficaOdm saveAnagraficaMediatoreAppendici(AnagraficaOdmDto anagraficaOdmDto) throws Exception {
		// ID_TIPO_ANAGRAFICA = 1 di default
		Optional<AnagraficaOdm> anagraficaOdm = anagraficaOdmRepository.findById(anagraficaOdmDto.getIdAnagrafica());
		Optional<StatoModuliRichiestaFigli> moduloDocumento = Optional.empty();
		Optional<StatoModuliRichiestaFigli> moduloScheda = Optional.empty();
		Optional<Richiesta> richiesta = richiesteRepository.findById(anagraficaOdmDto.getIdRichiesta());
		
		// SOLO IN CASO DI INSERIMENTO VERRANNO EFFETUATI I SEGUENTI CONTROLLI
		if(anagraficaOdmDto.getIdAnagrafica() == null || anagraficaOdmDto.getIdAnagrafica() == 0) {
			if(anagraficaOdmDto.getIdTipoAnagrafica() == 4) {
				if(anagraficaOdm.isEmpty()) {
				List<AnagraficaOdm> mediatoriGenerici = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(anagraficaOdmDto.getIdRichiesta(), (long) 4);
			
					if(mediatoriGenerici.size() >= richiesta.get().getNumMediatori()) {
						throw new RuntimeException("-ErrorInfo Il numero esatto dei mediatori generici che si possono inserire deve essere uguale a " + richiesta.get().getNumMediatori());				
					}
			}
			}
			if(anagraficaOdmDto.getIdTipoAnagrafica() == 5) {
				if(anagraficaOdm.isEmpty()) {
				List<AnagraficaOdm> mediatoriInternazionali = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(anagraficaOdmDto.getIdRichiesta(), (long) 5);
			
					if(mediatoriInternazionali.size() >= richiesta.get().getNumMediatoriInter()) {
						throw new RuntimeException("-ErrorInfo Il numero esatto dei mediatori esperti di materia internazionale che si possono inserire deve essere uguale a " + richiesta.get().getNumMediatoriInter());				
					}
			}
			}
			if(anagraficaOdmDto.getIdTipoAnagrafica() == 6) {
				if(anagraficaOdm.isEmpty()) {
				List<AnagraficaOdm> mediatoriConsumo = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(anagraficaOdmDto.getIdRichiesta(), (long) 6);
			
					if(mediatoriConsumo.size() >= richiesta.get().getNumMediatoriCons()) {
						throw new RuntimeException("-ErrorInfo Il numero esatto dei mediatori esperti di materia di consumo che si possono inserire deve essere uguale a " + richiesta.get().getNumMediatoriCons());				
				}
				}
			}
		}
		
		if(anagraficaOdm.isPresent() == false) {
			anagraficaOdm = Optional.of(new AnagraficaOdm());
		}
		
		anagraficaOdm.get().setIdTipoAnagrafica(anagraficaOdmDto.getIdTipoAnagrafica());

		anagraficaOdm.get().setIdTitoloAnagrafica(anagraficaOdmDto.getIdTitoloAnagrafica());
		anagraficaOdm.get().setCognome(anagraficaOdmDto.getCognome());
		anagraficaOdm.get().setNome(anagraficaOdmDto.getNome());
		anagraficaOdm.get().setSesso(anagraficaOdmDto.getSesso());
		anagraficaOdm.get().setDataNascita(anagraficaOdmDto.getDataNascita());
		anagraficaOdm.get().setStatoNascita(anagraficaOdmDto.getStatoNascita());
		anagraficaOdm.get().setIdComuneNascita(anagraficaOdmDto.getIdComuneNascita());
		anagraficaOdm.get().setCodiceFiscale(anagraficaOdmDto.getCodiceFiscale());
		anagraficaOdm.get().setCittadinanza(anagraficaOdmDto.getCittadinanza());
		anagraficaOdm.get().setComuneNascitaEstero(anagraficaOdmDto.getComuneNascitaEstero());
		anagraficaOdm.get().setStatoResidenza(anagraficaOdmDto.getStatoResidenza());
		anagraficaOdm.get().setIdComuneResidenza(anagraficaOdmDto.getIdComuneResidenza());
		anagraficaOdm.get().setComuneResidenzaEstero(anagraficaOdmDto.getComuneResidenzaEstero());
		anagraficaOdm.get().setIndirizzo(anagraficaOdmDto.getIndirizzo());
		anagraficaOdm.get().setNumeroCivico(anagraficaOdmDto.getNumeroCivico());
		anagraficaOdm.get().setCap(anagraficaOdmDto.getCap());
		anagraficaOdm.get().setStatoDomicilio(anagraficaOdmDto.getStatoDomicilio());
		anagraficaOdm.get().setIdComuneDomicilio(anagraficaOdmDto.getIdComuneDomicilio());
		anagraficaOdm.get().setComuneDomicilioEstero(anagraficaOdmDto.getComuneDomicilioEstero());
		anagraficaOdm.get().setIndirizzoDomicilio(anagraficaOdmDto.getIndirizzoDomicilio());
		anagraficaOdm.get().setNumeroCivicoDomicilio(anagraficaOdmDto.getNumeroCivicoDomicilio());
		anagraficaOdm.get().setCapDomicilio(anagraficaOdmDto.getCapDomicilio());
		anagraficaOdm.get().setIndirizzoEmail(anagraficaOdmDto.getIndirizzoEmail());
		anagraficaOdm.get().setIndirizzoPec(anagraficaOdmDto.getIndirizzoPec());
		anagraficaOdm.get().setMedPiva(anagraficaOdmDto.getMedPiva());
		anagraficaOdm.get().setMedNumeroOrganismiDisp(anagraficaOdmDto.getMedNumeroOrganismiDisp());
		anagraficaOdm.get().setIdOrdiniCollegi(anagraficaOdmDto.getIdOrdiniCollegi());
		anagraficaOdm.get().setMedDataOrdineCollegioProfess(anagraficaOdmDto.getMedDataOrdineCollegioProfess());
		anagraficaOdm.get().setMedRappGiuridicoEconomico(anagraficaOdmDto.getMedRappGiuridicoEconomico());
		anagraficaOdm.get().setMedTitoloDiStudio(anagraficaOdmDto.getMedTitoloDiStudio());
		anagraficaOdm.get().setLingueStraniere(anagraficaOdmDto.getLingueStraniere());
		anagraficaOdm.get().setMedUniversita(anagraficaOdmDto.getMedUniversita());
		
		AnagraficaOdm saveAnagrafica = anagraficaOdmRepository.save(anagraficaOdm.get());
		
		//Soggetto Richiesta
		if(anagraficaOdmDto.getIdAnagrafica() == 0) {
			SoggettoRichiesta soggRich = new SoggettoRichiesta();
			
			soggRich.setIdAnagrafica(saveAnagrafica.getIdAnagrafica());
			soggRich.setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
			soggRich.setIdTipoAnagrafica(saveAnagrafica.getIdTipoAnagrafica().intValue());
			
			soggettoRichiestaRepository.save(soggRich);
		}
		
		long idModuloScheda = 0;
		
		//Scheda
		switch(anagraficaOdmDto.getIdTipoAnagrafica().intValue()) {
		case 4: 
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica(
							(long) 38, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
			idModuloScheda = 38;
			break;
		case 5: 
			moduloScheda = statoModuliRichiestaFigliRepository
			.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 43, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica()); 
			idModuloScheda = 43;
			break;
		case 6: 
			moduloScheda = statoModuliRichiestaFigliRepository
			.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 52, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica()); 
			idModuloScheda = 52;
			break;
		}
		
		if(moduloScheda.isPresent() == false) { //inserimento se non è già presente

			moduloScheda = Optional.of(new StatoModuliRichiestaFigli());
			moduloScheda.get().setIdModulo(idModuloScheda);
			moduloScheda.get().setIdAnagrafica(saveAnagrafica.getIdAnagrafica());
			moduloScheda.get().setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
			moduloScheda.get().setNomeAllegato(anagraficaOdmDto.getNomeFile());
			moduloScheda.get().setDataInserimento(new Date());
			//convalida, perchè siamo in inserimento
			moduloScheda.get().setCompletato(1);
		}
		else { //deconvalida in update
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
		}
		
		statoModuliRichiestaFigliRepository.save(moduloScheda.get());

		long idModuloDocumento = 0;
		//Documento
		switch(anagraficaOdmDto.getIdTipoAnagrafica().intValue()) {
		case 4: 
			moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 42, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
			idModuloDocumento = 42;
			break;
		case 5: 
			moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 47, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
			idModuloDocumento = 47;
			break;
		case 6: 
			moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 56, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
			idModuloDocumento = 56;
			break;
		}
		
		if(moduloDocumento.isPresent() == false) { //inserimento se non è già presente
			moduloDocumento = Optional.of(new StatoModuliRichiestaFigli());
			moduloDocumento.get().setIdModulo(idModuloDocumento);
			moduloDocumento.get().setIdAnagrafica(saveAnagrafica.getIdAnagrafica());
			moduloDocumento.get().setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
			moduloDocumento.get().setNomeAllegato(anagraficaOdmDto.getNomeFile());
			moduloDocumento.get().setDataInserimento(new Date());
			//convalida, perchè siamo in inserimento
			moduloDocumento.get().setCompletato(1);
		}
		else { //deconvalida in update
			moduloScheda.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
		}
		StatoModuliRichiestaFigli saveModuloDocumento = statoModuliRichiestaFigliRepository.save(moduloDocumento.get());		
			
		//Mercurio
		if(anagraficaOdmDto.getFile() != null) {
			try {
				pdfService.checkValidLoadDocument(anagraficaOdmDto.getFile());
				
				String pathCostOdm = "/" + anagraficaOdmDto.getIdRichiesta() + "/odm/" + saveModuloDocumento.getIdModulo();
				MercurioFile infoFile = apiFileService.insertFile(pathCostOdm, Long.toString(saveModuloDocumento.getId()), anagraficaOdmDto.getFile());
				saveModuloDocumento.setDocumentIdClient(infoFile.getDocumentIdClient());
				saveModuloDocumento.setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(saveModuloDocumento);
				
				//return anagraficaOdmRepository.save(anagraficaOdm.get());
			} catch (Exception e) {
				String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
						: "Si è verificato un errore con il caricamento del file nel documentale";
				throw new RuntimeException(message);
			}
		}
		
		// INSERIMENTO AUTOCERTIFICAZIONE
		Optional<StatoModuliRichiestaFigli> moduloAutocertificazione = Optional.empty();;
		
		long idModuloAutocerticazione = 0;
		//Documento
		switch(anagraficaOdmDto.getIdTipoAnagrafica().intValue()) {
		case 4: 
			moduloAutocertificazione = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 39, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
			idModuloAutocerticazione = 39;
			break;
		case 5: 
			moduloAutocertificazione = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 44, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
			idModuloAutocerticazione = 44;
			break;
		case 6: 
			moduloAutocertificazione = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 53, anagraficaOdmDto.getIdRichiesta(), saveAnagrafica.getIdAnagrafica());
			idModuloAutocerticazione = 53;
			break;
		}
		
		if(moduloAutocertificazione.isPresent() == false) { //inserimento se non è già presente
			moduloAutocertificazione = Optional.of(new StatoModuliRichiestaFigli());
			moduloAutocertificazione.get().setIdModulo(idModuloAutocerticazione);
			moduloAutocertificazione.get().setIdAnagrafica(saveAnagrafica.getIdAnagrafica());
			moduloAutocertificazione.get().setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
			moduloAutocertificazione.get().setNomeAllegato(anagraficaOdmDto.getNomeFile());
			moduloAutocertificazione.get().setDataInserimento(new Date());
		}
		else { //deconvalida in update
			moduloAutocertificazione.get().setValidato(0);
			moduloAutocertificazione.get().setCompletato(0);
		}
		
		statoModuliRichiestaFigliRepository.save(moduloAutocertificazione.get());
		
		// ANNULAMENTO CONVALIDAZIONE ELENCO E AUTOCERTIFICAZIONE
		if(anagraficaOdmDto.getIdAnagrafica() != 0) {
			Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiesta((long) 26, anagraficaOdmDto.getIdRichiesta());
			
			if(moduloElenco.isPresent()) {
				moduloElenco.get().setValidato(0);
				moduloElenco.get().setCompletato(0);
				statoModuliRichiestaFigliRepository.save(moduloElenco.get());
			}
		}
		
		
		return saveAnagrafica;
	}
	
//	@Transactional(rollbackFor = Exception.class)
//	public AnagraficaOdm saveAnagraficaMediatoreGen(AnagraficaOdmDto anagraficaOdmDto) throws Exception {
//		// ID_TIPO_ANAGRAFICA = 1 di default
//		Optional<AnagraficaOdm> anagraficaOdm = anagraficaOdmRepository.findById(anagraficaOdmDto.getIdAnagrafica());
//		Optional<StatoModuliRichiestaFigli> moduloDocumento = Optional.empty();
//		Optional<StatoModuliRichiestaFigli> moduloScheda = Optional.empty();
//		Optional<Richiesta> richiesta = richiesteRepository.findById(anagraficaOdmDto.getIdRichiesta());
//		
//		if(anagraficaOdmDto.getIdTipoAnagrafica() == 4) {
//			List<AnagraficaOdm> mediatoriGenerici = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica((long) anagraficaOdmDto.getIdRichiesta(), (long) 4);
//
//			if(mediatoriGenerici.size() != richiesta.get().getNumMediatori())
//				throw new RuntimeException("-ErrorInfo Il numero esatto dei mediatori generici che si possono inserire deve essere uguale a " + richiesta.get().getNumMediatori());				
//		}
//		if(anagraficaOdmDto.getIdTipoAnagrafica() == 5) {
//			List<AnagraficaOdm> mediatoriInternazionali = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica((long) anagraficaOdmDto.getIdRichiesta(), (long) 5);
//
//			if(mediatoriInternazionali.size() != richiesta.get().getNumMediatori())
//				throw new RuntimeException("-ErrorInfo Il numero esatto dei mediatori internazionali che si possono inserire deve essere uguale a " + richiesta.get().getNumMediatori());				
//		}
//		if(anagraficaOdmDto.getIdTipoAnagrafica() == 6) {
//			List<AnagraficaOdm> mediatoriConsumo = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica((long) anagraficaOdmDto.getIdRichiesta(), (long) 6);
//
//			if(mediatoriConsumo.size() != richiesta.get().getNumMediatori())
//				throw new RuntimeException("-ErrorInfo Il numero esatto dei mediatori di consumo che si possono inserire deve essere uguale a " + richiesta.get().getNumMediatori());				
//		}
//		
//		
//		if(anagraficaOdm.isPresent() == false) {
//			anagraficaOdm = Optional.of(new AnagraficaOdm());
//		}
//		
//		anagraficaOdm.get().setIdTipoAnagrafica(anagraficaOdmDto.getIdTipoAnagrafica());
//
//		anagraficaOdm.get().setIdTitoloAnagrafica(anagraficaOdmDto.getIdTitoloAnagrafica());
//		anagraficaOdm.get().setCognome(anagraficaOdmDto.getCognome());
//		anagraficaOdm.get().setNome(anagraficaOdmDto.getNome());
//		anagraficaOdm.get().setSesso(anagraficaOdmDto.getSesso());
//		anagraficaOdm.get().setDataNascita(anagraficaOdmDto.getDataNascita());
//		anagraficaOdm.get().setStatoNascita(anagraficaOdmDto.getStatoNascita());
//		anagraficaOdm.get().setIdComuneNascita(anagraficaOdmDto.getIdComuneNascita());
//		anagraficaOdm.get().setCodiceFiscale(anagraficaOdmDto.getCodiceFiscale());
//		anagraficaOdm.get().setCittadinanza(anagraficaOdmDto.getCittadinanza());
//		anagraficaOdm.get().setComuneNascitaEstero(anagraficaOdmDto.getComuneNascitaEstero());
//		anagraficaOdm.get().setStatoResidenza(anagraficaOdmDto.getStatoResidenza());
//		anagraficaOdm.get().setIdComuneResidenza(anagraficaOdmDto.getIdComuneResidenza());
//		anagraficaOdm.get().setComuneResidenzaEstero(anagraficaOdmDto.getComuneResidenzaEstero());
//		anagraficaOdm.get().setIndirizzo(anagraficaOdmDto.getIndirizzo());
//		anagraficaOdm.get().setNumeroCivico(anagraficaOdmDto.getNumeroCivico());
//		anagraficaOdm.get().setCap(anagraficaOdmDto.getCap());
//		anagraficaOdm.get().setStatoDomicilio(anagraficaOdmDto.getStatoDomicilio());
//		anagraficaOdm.get().setIdComuneDomicilio(anagraficaOdmDto.getIdComuneDomicilio());
//		anagraficaOdm.get().setComuneDomicilioEstero(anagraficaOdmDto.getComuneDomicilioEstero());
//		anagraficaOdm.get().setIndirizzoDomicilio(anagraficaOdmDto.getIndirizzoDomicilio());
//		anagraficaOdm.get().setNumeroCivicoDomicilio(anagraficaOdmDto.getNumeroCivicoDomicilio());
//		anagraficaOdm.get().setCapDomicilio(anagraficaOdmDto.getCapDomicilio());
//		anagraficaOdm.get().setIndirizzoEmail(anagraficaOdmDto.getIndirizzoEmail());
//		anagraficaOdm.get().setIndirizzoPec(anagraficaOdmDto.getIndirizzoPec());
//		anagraficaOdm.get().setMedPiva(anagraficaOdmDto.getMedPiva());
//		anagraficaOdm.get().setMedNumeroOrganismiDisp(anagraficaOdmDto.getMedNumeroOrganismiDisp());
//		anagraficaOdm.get().setIdOrdiniCollegi(anagraficaOdmDto.getIdOrdiniCollegi());
//		anagraficaOdm.get().setMedRappGiuridicoEconomico(anagraficaOdmDto.getMedRappGiuridicoEconomico());
//		anagraficaOdm.get().setMedTitoloDiStudio(anagraficaOdmDto.getMedTitoloDiStudio());
//		anagraficaOdm.get().setLingueStraniere(anagraficaOdmDto.getLingueStraniere());
//		anagraficaOdm.get().setMedUniversita(anagraficaOdmDto.getMedUniversita());
//		
//		AnagraficaOdm saveAnagrafica = anagraficaOdmRepository.save(anagraficaOdm.get());
//		
//		statoModuloAllegatoService.insertSchedaMediatore(null, anagraficaOdmDto.getIdTipoAnagrafica());
//		
//		convalidazioneService.convalidazioneMediatore(anagraficaOdmDto.getIdRichiesta(), anagraficaOdmDto.getIdAnagrafica());
//		
//
//
//		return anagraficaOdmRepository.save(anagraficaOdm.get());
//	}

	public AnagraficaOdm updateAnagrafica(AnagraficaOdmDto anagraficaDto) {
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository
				.findByidAnagrafica(anagraficaDto.getIdAnagrafica());

		if (anagrafica.isPresent()) {
			anagrafica.get().setIdAnagrafica(anagraficaDto.getIdAnagrafica());
			anagrafica.get().setIdTitoloAnagrafica(anagraficaDto.getIdTitoloAnagrafica());
			anagrafica.get().setCognome(anagraficaDto.getCognome());
			anagrafica.get().setNome(anagraficaDto.getNome());
			anagrafica.get().setSesso(anagraficaDto.getSesso());
			anagrafica.get().setDataNascita(anagraficaDto.getDataNascita());
			anagrafica.get().setPoDataAssunzione(anagraficaDto.getPoDataAssunzione());
			anagrafica.get().setStatoNascita(anagraficaDto.getStatoNascita());
			anagrafica.get().setIdComuneNascita(anagraficaDto.getIdComuneNascita());
			anagrafica.get().setCodiceFiscale(anagraficaDto.getCodiceFiscale());
			anagrafica.get().setCittadinanza(anagraficaDto.getCittadinanza());
			anagrafica.get().setPoTipoRappOdm(anagraficaDto.getPoTipoRappOdm());
			anagrafica.get().setComuneNascitaEstero(anagraficaDto.getComuneNascitaEstero());
			anagrafica.get().setIndirizzoPec(anagraficaDto.getIndirizzoPec());
			anagrafica.get().setIndirizzoEmail(anagraficaDto.getIndirizzoEmail());
			
			return anagraficaOdmRepository.save(anagrafica.get());
		} else {
			return null;
		}
	}
	
	/*
	 * @Transactional(rollbackFor = Exception.class) public AnagraficaOdm
	 * saveRappresentanteLegale(AnagraficaOdmDto anagraficaDto) { try {
	 * Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository
	 * .findByidAnagrafica(anagraficaDto.getIdAnagrafica()); boolean inserimento =
	 * false;
	 * 
	 * // In caso di inserimento verra richiamata l'istanzia e reso true
	 * l'inserimento if (anagrafica.isPresent() == false) { anagrafica =
	 * Optional.of(new AnagraficaOdm()); inserimento = true; }
	 * anagrafica.get().setIdTitoloAnagrafica(anagraficaDto.getIdTitoloAnagrafica())
	 * ; anagrafica.get().setCognome(anagraficaDto.getCognome());
	 * anagrafica.get().setNome(anagraficaDto.getNome()); // NASCITA
	 * anagrafica.get().setSesso(anagraficaDto.getSesso());
	 * anagrafica.get().setDataNascita(anagraficaDto.getDataNascita());
	 * anagrafica.get().setStatoNascita(anagraficaDto.getStatoNascita());
	 * anagrafica.get().setIdComuneNascita(anagraficaDto.getIdComuneNascita());
	 * anagrafica.get().setCodiceFiscale(anagraficaDto.getCodiceFiscale());
	 * anagrafica.get().setCittadinanza(anagraficaDto.getCittadinanza());
	 * anagrafica.get().setComuneNascitaEstero(anagraficaDto.getComuneNascitaEstero(
	 * )); // RESIDENZA
	 * anagrafica.get().setStatoResidenza(anagraficaDto.getStatoResidenza());
	 * anagrafica.get().setIdComuneResidenza(anagraficaDto.getIdComuneResidenza());
	 * anagrafica.get().setIndirizzo(anagraficaDto.getIndirizzo());
	 * anagrafica.get().setNumeroCivico(anagraficaDto.getNumeroCivico());
	 * anagrafica.get().setCap(anagraficaDto.getCap());
	 * anagrafica.get().setComuneResidenzaEstero(anagraficaDto.
	 * getComuneResidenzaEstero()); // DOMICILIO
	 * anagrafica.get().setStatoDomicilio(anagraficaDto.getStatoDomicilio());
	 * anagrafica.get().setIdComuneDomicilio(anagraficaDto.getIdComuneDomicilio());
	 * anagrafica.get().setIndirizzoDomicilio(anagraficaDto.getIndirizzoDomicilio())
	 * ; anagrafica.get().setNumeroCivicoDomicilio(anagraficaDto.
	 * getNumeroCivicoDomicilio());
	 * anagrafica.get().setCapDomicilio(anagraficaDto.getCapDomicilio());
	 * anagrafica.get().setComuneDomicilioEstero(anagraficaDto.
	 * getComuneDomicilioEstero()); // CONTATTI
	 * anagrafica.get().setIndirizzoPec(anagraficaDto.getIndirizzoPec());
	 * anagrafica.get().setIndirizzoEmail(anagraficaDto.getIndirizzoEmail());
	 * 
	 * anagrafica.get().setIdQualifica((long) 1); AnagraficaOdm anagraficaUpdate =
	 * anagraficaOdmRepository.save(anagrafica.get());
	 * 
	 * if(inserimento) { SoggettoRichiesta sogRichiesta = new SoggettoRichiesta();
	 * sogRichiesta.setIdRichiesta(anagraficaDto.getIdRichiesta());
	 * sogRichiesta.setIdAnagrafica(anagraficaUpdate.getIdAnagrafica());
	 * soggettoRichiestaRepository.save(sogRichiesta); }
	 * 
	 * // INSERIMENTO MODULO DOCUMENTO CON FILE ANNESSO
	 * Optional<StatoModuliRichiestaFigli> moduloAttoDocumento =
	 * statoModuliRichiestaFigliRepository
	 * .findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30,
	 * anagraficaDto.getIdRichiesta(), anagraficaDto.getIdAnagrafica());
	 * 
	 * // Evita l'inserimento del file in caso di null, casistica possibile in caso
	 * di if (anagraficaDto.getFile() != null) { try {
	 * pdfService.checkValidLoadDocument(anagraficaDto.getFile());
	 * 
	 * apiFileService.deleteFile(moduloAttoDocumento.get().getDocumentIdClient());
	 * String pathDocumento = "/" + moduloAttoDocumento.get().getIdRichiesta() +
	 * "/odm/" + moduloAttoDocumento.get().getIdModulo(); MercurioFile infoFile =
	 * apiFileService.insertFile(pathDocumento,
	 * Long.toString(moduloAttoDocumento.get().getId()), anagraficaDto.getFile());
	 * moduloAttoDocumento.get().setDocumentIdClient(infoFile.getDocumentIdClient())
	 * ; moduloAttoDocumento.get().setContentId(infoFile.getContentId());
	 * statoModuliRichiestaFigliRepository.save(moduloAttoDocumento.get()); } catch
	 * (Exception e) { String message = (e.getMessage() != null &&
	 * e.getMessage().contains("-ErrorInfo")) ? e.getMessage() :
	 * "Si è verificato un errore con il caricamento del file nel documentale";
	 * throw new RuntimeException(message); } }
	 * 
	 * 
	 * if(inserimento == false) { // SE SI ENTRA QUI VUOL DIRE CHE E' STATO FATTO
	 * UN'AGGIORNAMENTE E QUINDI BISOGNERA' ANNUALARE LA CONVALIDA
	 * convalidazioneService.annullaConvalidazioneRapOrRespOrOrgAm(anagraficaDto.
	 * getIdRichiesta(), anagraficaUpdate.getIdAnagrafica(),
	 * anagraficaDto.getRapLegaleIsRespOrg()); }
	 * 
	 * return anagraficaUpdate;
	 * 
	 * } catch (Exception e) { String message = (e.getMessage() != null &&
	 * e.getMessage().contains("-ErrorInfo")) ? e.getMessage() :
	 * "Si è verificato un non previsto"; throw new RuntimeException(message); } }
	 */

	@Transactional(rollbackFor = Exception.class)
	public AnagraficaOdm saveRapLegAndRespOrg(AnagraficaOdmDto anagraficaDto) {
		try {			
			Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository
					.findByidAnagrafica(anagraficaDto.getIdAnagrafica());
			
			boolean inserimento = false;
			// IN CASO DI NUOVO INSERIMENTO VERRA CREATA UNA ISTANZA VUOTO CON LA VAR INSERIMENTO CHE SARA' SETTATA A TRUE
			if (anagrafica.isPresent() == false) {
				// PRIMA DI UN NUOVO INSERIMENTO COME RESPONSABILE DELL'ORGANISMO, VERRA VERIFICATO CHE NON ESISTA GIA' UNO
				if(anagraficaDto.getIdQualifica() == 2) {
					List<AnagraficaOdm> anagraficaRespOrg = anagraficaOdmRepository
							.findAllByIdRichiestaAndIdQualifica(anagraficaDto.getIdRichiesta(), (long) 2);
					if(anagraficaRespOrg != null && anagraficaRespOrg.isEmpty() == false) {
						throw new RuntimeException("-ErrorInfo Impossibile proseguire con l'inserimneto perchè già "
													+ "è presente un responsabile dell'organismo in banca dati");
					}
				}
				
				anagrafica = Optional.of(new AnagraficaOdm());
				inserimento = true;
			}
			anagrafica.get().setIdTitoloAnagrafica(anagraficaDto.getIdTitoloAnagrafica());
			anagrafica.get().setCognome(anagraficaDto.getCognome());
			anagrafica.get().setNome(anagraficaDto.getNome());
			// NASCITA
			anagrafica.get().setSesso(anagraficaDto.getSesso());
			anagrafica.get().setDataNascita(anagraficaDto.getDataNascita());
			anagrafica.get().setStatoNascita(anagraficaDto.getStatoNascita());
			anagrafica.get().setIdComuneNascita(anagraficaDto.getIdComuneNascita());
			anagrafica.get().setCodiceFiscale(anagraficaDto.getCodiceFiscale());
			anagrafica.get().setCittadinanza(anagraficaDto.getCittadinanza());
			anagrafica.get().setComuneNascitaEstero(anagraficaDto.getComuneNascitaEstero());
			// RESIDENZA
			anagrafica.get().setStatoResidenza(anagraficaDto.getStatoResidenza());
			anagrafica.get().setIdComuneResidenza(anagraficaDto.getIdComuneResidenza());
			anagrafica.get().setIndirizzo(anagraficaDto.getIndirizzo());
			anagrafica.get().setNumeroCivico(anagraficaDto.getNumeroCivico());
			anagrafica.get().setCap(anagraficaDto.getCap());
			anagrafica.get().setComuneResidenzaEstero(anagraficaDto.getComuneResidenzaEstero());
			// DOMICILIO
			anagrafica.get().setStatoDomicilio(anagraficaDto.getStatoDomicilio());
			anagrafica.get().setIdComuneDomicilio(anagraficaDto.getIdComuneDomicilio());
			anagrafica.get().setIndirizzoDomicilio(anagraficaDto.getIndirizzoDomicilio());
			anagrafica.get().setNumeroCivicoDomicilio(anagraficaDto.getNumeroCivicoDomicilio());
			anagrafica.get().setCapDomicilio(anagraficaDto.getCapDomicilio());
			anagrafica.get().setComuneDomicilioEstero(anagraficaDto.getComuneDomicilioEstero());
			// CONTATTI
			anagrafica.get().setIndirizzoPec(anagraficaDto.getIndirizzoPec());
			anagrafica.get().setIndirizzoEmail(anagraficaDto.getIndirizzoEmail());

			anagrafica.get().setIdQualifica(anagraficaDto.getIdQualifica());
			AnagraficaOdm anagraficaUpdate = anagraficaOdmRepository.save(anagrafica.get());

			if(inserimento) {
				SoggettoRichiesta sogRichiesta = new SoggettoRichiesta();
				sogRichiesta.setIdRichiesta(anagraficaDto.getIdRichiesta());
				sogRichiesta.setIdAnagrafica(anagraficaUpdate.getIdAnagrafica());
				soggettoRichiestaRepository.save(sogRichiesta);
			}

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, anagraficaDto.getIdRichiesta(),
							anagraficaDto.getIdAnagrafica());

			if (moduloScheda.isPresent() == false) {
				StatoModuliRichiestaFigli newModuloScheda = new StatoModuliRichiestaFigli();
				newModuloScheda.setIdModulo((long) 28);
				newModuloScheda.setIdAnagrafica(anagrafica.get().getIdAnagrafica());
				newModuloScheda.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloScheda.setDataInserimento(new Date());
				statoModuliRichiestaFigliRepository.save(newModuloScheda);
			}
			

			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, anagraficaDto.getIdRichiesta(),
							anagraficaDto.getIdAnagrafica());
			if (moduloAttoReqOnora.isPresent() == false) {
				StatoModuliRichiestaFigli newModuloAttoReqOnora = new StatoModuliRichiestaFigli();
				newModuloAttoReqOnora.setIdModulo((long) 29);
				newModuloAttoReqOnora.setIdAnagrafica(anagrafica.get().getIdAnagrafica());
				newModuloAttoReqOnora.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloAttoReqOnora.setDataInserimento(new Date());
				statoModuliRichiestaFigliRepository.save(newModuloAttoReqOnora);
			}

			// INSERIMENTO MODULO DOCUMENTO CON FILE ANNESSO
			Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, anagraficaDto.getIdRichiesta(),
							anagraficaDto.getIdAnagrafica());

			if (moduloAttoDocumento.isPresent() == false) {
				StatoModuliRichiestaFigli newModuloDocumento = new StatoModuliRichiestaFigli();
				newModuloDocumento.setIdModulo((long) 30);
				newModuloDocumento.setIdAnagrafica(anagrafica.get().getIdAnagrafica());
				newModuloDocumento.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloDocumento.setNomeAllegato("documento");
				newModuloDocumento.setDataInserimento(new Date());
				// Se nuovo verrà anche assegnato il nuovo oggetto valorizzato alla varibile di
				// riferimento
				moduloAttoDocumento = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloDocumento));
			}

			// Evita l'inserimento del file in caso di null, casistica possibile in caso di
			// aggiornamento
			if (anagraficaDto.getFile() != null) {	
				try {
					pdfService.checkValidLoadDocument(anagraficaDto.getFile());
				
					apiFileService.deleteFile(moduloAttoDocumento.get().getDocumentIdClient());
					String pathDocumento = "/" + moduloAttoDocumento.get().getIdRichiesta() + "/odm/"
							+ moduloAttoDocumento.get().getIdModulo();
					MercurioFile infoFile = apiFileService.insertFile(pathDocumento, Long.toString(moduloAttoDocumento.get().getId()), anagraficaDto.getFile());
					moduloAttoDocumento.get().setDocumentIdClient(infoFile.getDocumentIdClient());
					moduloAttoDocumento.get().setContentId(infoFile.getContentId());
					statoModuliRichiestaFigliRepository.save(moduloAttoDocumento.get());
				} catch (Exception e) {
					String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
							: "Si è verificato un errore con il caricamento del file nel documentale";
					throw new RuntimeException(message);
				}
			}

			// SOLO SE E' RESPONSABILE DELL'ORGANISMO VERRA INSERITO IL FILE CON IL MODULO
			if (anagraficaUpdate.getIdQualifica() == 2) {
				// INSERIMENTO MODULO QUALIFICA MEDIATORE CON FILE ANNESSO
				Optional<StatoModuliRichiestaFigli> moduloAttoQuaificaMed = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, anagraficaDto.getIdRichiesta(),
								anagraficaDto.getIdAnagrafica());

				if (moduloAttoQuaificaMed.isPresent() == false) {
					StatoModuliRichiestaFigli newModuloQualificaMed = new StatoModuliRichiestaFigli();
					newModuloQualificaMed.setIdModulo((long) 84);
					newModuloQualificaMed.setIdAnagrafica(anagrafica.get().getIdAnagrafica());
					newModuloQualificaMed.setIdRichiesta(anagraficaDto.getIdRichiesta());
					newModuloQualificaMed.setNomeAllegato("qualifica_mediatore");
					newModuloQualificaMed.setDataInserimento(new Date());
					// Se nuovo verrà anche assegnato il nuovo oggetto valorizzato alla varibile di
					// riferimento
					moduloAttoQuaificaMed = Optional
							.of(statoModuliRichiestaFigliRepository.save(newModuloQualificaMed));
				}

				// Evita l'inserimento del file in caso di null, casistica possibile in caso di
				// aggiornamento
				if (anagraficaDto.getFile2() != null) {
					try {
						pdfService.checkValidLoadDocument(anagraficaDto.getFile2());
						
						apiFileService.deleteFile(moduloAttoQuaificaMed.get().getDocumentIdClient());
						String pathQuaificaMed = "/" + moduloAttoQuaificaMed.get().getIdRichiesta() + "/odm/"
								+ moduloAttoQuaificaMed.get().getIdModulo();
						MercurioFile infoFile = apiFileService.insertFile(pathQuaificaMed, Long.toString(moduloAttoQuaificaMed.get().getId()), anagraficaDto.getFile());
						moduloAttoQuaificaMed.get().setDocumentIdClient(infoFile.getDocumentIdClient());
						moduloAttoQuaificaMed.get().setContentId(infoFile.getContentId());
						statoModuliRichiestaFigliRepository.save(moduloAttoQuaificaMed.get());						
					} catch (Exception e) {
						String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
								? e.getMessage()
										: "Si è verificato un errore con il caricamento del file nel documentale";
						throw new RuntimeException(message);
					}
				}
			}

			// SOLO QUESTA O LA SEGUENTE CONDIZIONE POTRA' ESSERE VERA, NON POSSIBILE IL DOPPIO TRUE NELLE CONDIZIONI
			// CONDIZIONE SOLO IN CASO DI AGGIORNARE DI RAP. LEGALE GIA' ESISTENTE
			if(anagraficaDto.getUpdateAnagraficaRapLegale()) {
				Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(anagraficaDto.getIdRichiesta());
				anagraficaLeg.get().setIdTitoloAnagrafica(anagraficaDto.getIdTitoloAnagrafica());
				anagraficaLeg.get().setCognome(anagraficaDto.getCognome());
				anagraficaLeg.get().setNome(anagraficaDto.getNome());
				// NASCITA
				anagraficaLeg.get().setSesso(anagraficaDto.getSesso());
				anagraficaLeg.get().setDataNascita(anagraficaDto.getDataNascita());
				anagraficaLeg.get().setStatoNascita(anagraficaDto.getStatoNascita());
				anagraficaLeg.get().setIdComuneNascita(anagraficaDto.getIdComuneNascita());
				anagraficaLeg.get().setCodiceFiscale(anagraficaDto.getCodiceFiscale());
				anagraficaLeg.get().setCittadinanza(anagraficaDto.getCittadinanza());
				anagraficaLeg.get().setComuneNascitaEstero(anagraficaDto.getComuneNascitaEstero());
				// RESIDENZA
				anagraficaLeg.get().setStatoResidenza(anagraficaDto.getStatoResidenza());
				anagraficaLeg.get().setIdComuneResidenza(anagraficaDto.getIdComuneResidenza());
				anagraficaLeg.get().setIndirizzo(anagraficaDto.getIndirizzo());
				anagraficaLeg.get().setNumeroCivico(anagraficaDto.getNumeroCivico());
				anagraficaLeg.get().setCap(anagraficaDto.getCap());
				anagraficaLeg.get().setComuneResidenzaEstero(anagraficaDto.getComuneResidenzaEstero());
				// DOMICILIO
				anagraficaLeg.get().setStatoDomicilio(anagraficaDto.getStatoDomicilio());
				anagraficaLeg.get().setIdComuneDomicilio(anagraficaDto.getIdComuneDomicilio());
				anagraficaLeg.get().setIndirizzoDomicilio(anagraficaDto.getIndirizzoDomicilio());
				anagraficaLeg.get().setNumeroCivicoDomicilio(anagraficaDto.getNumeroCivicoDomicilio());
				anagraficaLeg.get().setCapDomicilio(anagraficaDto.getCapDomicilio());
				anagraficaLeg.get().setComuneDomicilioEstero(anagraficaDto.getComuneDomicilioEstero());
				// CONTATTI
				anagraficaLeg.get().setIndirizzoPec(anagraficaDto.getIndirizzoPec());
				anagraficaLeg.get().setIndirizzoEmail(anagraficaDto.getIndirizzoEmail());

				anagraficaOdmRepository.save(anagraficaLeg.get());

				// INSERIMENTO MODULO DOCUMENTO CON FILE ANNESSO
				Optional<StatoModuliRichiestaFigli> moduloAttoDocumentoSL = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, anagraficaDto.getIdRichiesta(),
								anagraficaDto.getIdAnagrafica());
				
				// Evita l'inserimento del file in caso di null, casistica possibile in caso di
				if (anagraficaDto.getFile() != null) {
					try {
						pdfService.checkValidLoadDocument(anagraficaDto.getFile());

						apiFileService.deleteFile(moduloAttoDocumentoSL.get().getDocumentIdClient());
						String pathDocumento = "/" + moduloAttoDocumentoSL.get().getIdRichiesta() + "/odm/"
								+ moduloAttoDocumentoSL.get().getIdModulo();
						MercurioFile infoFile = apiFileService.insertFile(pathDocumento, Long.toString(moduloAttoDocumentoSL.get().getId()), anagraficaDto.getFile());
						moduloAttoDocumentoSL.get().setDocumentIdClient(infoFile.getDocumentIdClient());
						moduloAttoDocumentoSL.get().setContentId(infoFile.getContentId());
						statoModuliRichiestaFigliRepository.save(moduloAttoDocumentoSL.get());							
					} catch (Exception e) {
						String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
								: "Si è verificato un errore con il caricamento del file nel documentale";
						throw new RuntimeException(message);
					}
				}		
			}
			// CASISTICA IN CASO DI INSERIMENTO DEL RESPONSABILE DELL'ORGANISMO CLONE DEL RAP LEGALE
			else if (anagraficaDto.getRapLegaleIsRespOrg() == true) {
				Optional<AnagraficaOdm> anagraficaOrg = Optional.of(new AnagraficaOdm());
				anagraficaOrg.get().setIdTitoloAnagrafica(anagraficaDto.getIdTitoloAnagrafica());
				anagraficaOrg.get().setCognome(anagraficaDto.getCognome());
				anagraficaOrg.get().setNome(anagraficaDto.getNome());
				// NASCITA
				anagraficaOrg.get().setSesso(anagraficaDto.getSesso());
				anagraficaOrg.get().setDataNascita(anagraficaDto.getDataNascita());
				anagraficaOrg.get().setStatoNascita(anagraficaDto.getStatoNascita());
				anagraficaOrg.get().setIdComuneNascita(anagraficaDto.getIdComuneNascita());
				anagraficaOrg.get().setCodiceFiscale(anagraficaDto.getCodiceFiscale());
				anagraficaOrg.get().setCittadinanza(anagraficaDto.getCittadinanza());
				anagraficaOrg.get().setComuneNascitaEstero(anagraficaDto.getComuneNascitaEstero());
				// RESIDENZA
				anagraficaOrg.get().setStatoResidenza(anagraficaDto.getStatoResidenza());
				anagraficaOrg.get().setIdComuneResidenza(anagraficaDto.getIdComuneResidenza());
				anagraficaOrg.get().setIndirizzo(anagraficaDto.getIndirizzo());
				anagraficaOrg.get().setNumeroCivico(anagraficaDto.getNumeroCivico());
				anagraficaOrg.get().setCap(anagraficaDto.getCap());
				anagraficaOrg.get().setComuneResidenzaEstero(anagraficaDto.getComuneResidenzaEstero());
				// DOMICILIO
				anagraficaOrg.get().setStatoDomicilio(anagraficaDto.getStatoDomicilio());
				anagraficaOrg.get().setIdComuneDomicilio(anagraficaDto.getIdComuneDomicilio());
				anagraficaOrg.get().setIndirizzoDomicilio(anagraficaDto.getIndirizzoDomicilio());
				anagraficaOrg.get().setNumeroCivicoDomicilio(anagraficaDto.getNumeroCivicoDomicilio());
				anagraficaOrg.get().setCapDomicilio(anagraficaDto.getCapDomicilio());
				anagraficaOrg.get().setComuneDomicilioEstero(anagraficaDto.getComuneDomicilioEstero());
				// CONTATTI
				anagraficaOrg.get().setIndirizzoPec(anagraficaDto.getIndirizzoPec());
				anagraficaOrg.get().setIndirizzoEmail(anagraficaDto.getIndirizzoEmail());

				anagraficaOrg.get().setIdQualifica((long) 2);

				AnagraficaOdm anagraficaOrgUpdate = anagraficaOdmRepository.save(anagraficaOrg.get());

				SoggettoRichiesta sogRichiesta = new SoggettoRichiesta();
				sogRichiesta.setIdRichiesta(anagraficaDto.getIdRichiesta());
				sogRichiesta.setIdAnagrafica(anagraficaOrgUpdate.getIdAnagrafica());
				soggettoRichiestaRepository.save(sogRichiesta);
				
				// INSERIMENTO MODULO NUOVA SCHEDA
				StatoModuliRichiestaFigli newModuloScheda = new StatoModuliRichiestaFigli();
				newModuloScheda.setIdModulo((long) 28);
				newModuloScheda.setIdAnagrafica(anagraficaOrg.get().getIdAnagrafica());
				newModuloScheda.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloScheda.setDataInserimento(new Date());
				statoModuliRichiestaFigliRepository.save(newModuloScheda);

				// IN CASO DI NUOVO INSERIMENTE VERRA CREATO ANCHE UN NUOVO MODULO REQUISITO ONORARIO
				StatoModuliRichiestaFigli newModuloAttoReqOnora = new StatoModuliRichiestaFigli();
				newModuloAttoReqOnora.setIdModulo((long) 29);
				newModuloAttoReqOnora.setIdAnagrafica(anagraficaOrg.get().getIdAnagrafica());
				newModuloAttoReqOnora.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloAttoReqOnora.setDataInserimento(new Date());
				statoModuliRichiestaFigliRepository.save(newModuloAttoReqOnora);

				// INSERIMENTO MODULO DOCUMENTO CON FILE ANNESSO
				StatoModuliRichiestaFigli newModuloDocumento = new StatoModuliRichiestaFigli();
				newModuloDocumento.setIdModulo((long) 30);
				newModuloDocumento.setIdAnagrafica(anagraficaOrg.get().getIdAnagrafica());
				newModuloDocumento.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloDocumento.setNomeAllegato("documento");
				newModuloDocumento.setDataInserimento(new Date());
				Optional<StatoModuliRichiestaFigli> moduloAttoDocumentoClo = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloDocumento));
				
				// INSERIMENTO MODULO QUALIFICA CON FILE ANNESSO
				StatoModuliRichiestaFigli newModuloQualificaMed = new StatoModuliRichiestaFigli();
				newModuloQualificaMed.setIdModulo((long) 84);
				newModuloQualificaMed.setIdAnagrafica(anagraficaOrg.get().getIdAnagrafica());
				newModuloQualificaMed.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloQualificaMed.setNomeAllegato("qualifica_mediatore");
				newModuloQualificaMed.setDataInserimento(new Date());
				Optional<StatoModuliRichiestaFigli> moduloAttoQuaificaClo = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloQualificaMed));

				try {	
					pdfService.checkValidLoadDocument(anagraficaDto.getFile());
					pdfService.checkValidLoadDocument(anagraficaDto.getFile2());

					String pathDocumentoOrg = "/" + moduloAttoDocumentoClo.get().getIdRichiesta() + "/odm/"
							+ moduloAttoDocumentoClo.get().getIdModulo();
					MercurioFile infoFile = apiFileService.insertFile(pathDocumentoOrg, Long.toString(moduloAttoDocumentoClo.get().getId()), anagraficaDto.getFile());
					moduloAttoDocumentoClo.get().setDocumentIdClient(infoFile.getDocumentIdClient());
					moduloAttoDocumentoClo.get().setContentId(infoFile.getContentId());
					
					String pathQuaificaMed = "/" + moduloAttoQuaificaClo.get().getIdRichiesta() + "/odm/" + moduloAttoQuaificaClo.get().getIdModulo();
					MercurioFile infoFile2 = apiFileService.insertFile(pathQuaificaMed, Long.toString(moduloAttoQuaificaClo.get().getId()), anagraficaDto.getFile2());
					moduloAttoQuaificaClo.get().setDocumentIdClient(infoFile2.getDocumentIdClient());
					moduloAttoQuaificaClo.get().setContentId(infoFile2.getContentId());
					
					statoModuliRichiestaFigliRepository.save(moduloAttoDocumentoClo.get());	
					statoModuliRichiestaFigliRepository.save(moduloAttoQuaificaClo.get());
				} catch (Exception e) {
					String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
							: "Si è verificato un errore con il caricamento del file nel documentale";
					throw new RuntimeException(message);
				}

			}

			// CONDIZIONE SOLO PER L'INSERIMENTO SU SCHEDA ANAGRAFICA, LA CONDIZIONE SARA'SEMPRE TRUE
			if(anagraficaDto.getConvalidazioeRapOrRespOrOrgAm()) {
				// IL RAP.LEGALE CLONE NON VERRA' MAI CONVALIDATO INSIEME AL RESP. DELL'ORGANISMO NELLA CASISTICA DI NUOVO INSERIMENTO 
				convalidazioneService.convalidazioneRapLegAndRespOrg(anagraficaDto.getIdRichiesta(), anagraficaUpdate.getIdAnagrafica(), false);
			}
			else {
				// SE SI ENTRA QUI VUOL DIRE CHE E' STATO FATTO UN'AGGIORNAMENTE E QUINDI BISOGNERA' ANNUALARE LA CONVALIDA
				convalidazioneService.annullaConvalidazioneRapAndRespOrg(anagraficaDto.getIdRichiesta(), anagraficaUpdate.getIdAnagrafica(),
																		 anagraficaDto.getUpdateAnagraficaRapLegale());
			}
			
			return anagraficaUpdate;

		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un non previsto";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public AnagraficaOdm saveCompOrgAmAndCompSoc(AnagraficaOdmDto anagraficaDto) {
		try {
			Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(anagraficaDto.getIdAnagrafica());
			boolean inserimento = false;

			// In caso di inserimento verra richiamata l'istanzia e reso true l'inserimento
			if(anagrafica.isPresent() == false) {
				anagrafica = Optional.of(new AnagraficaOdm());
				inserimento = true;
			}
			anagrafica.get().setIdTitoloAnagrafica(anagraficaDto.getIdTitoloAnagrafica());
			anagrafica.get().setCognome(anagraficaDto.getCognome());
			anagrafica.get().setNome(anagraficaDto.getNome());
			// NASCITA
			anagrafica.get().setSesso(anagraficaDto.getSesso());
			anagrafica.get().setDataNascita(anagraficaDto.getDataNascita());
			anagrafica.get().setStatoNascita(anagraficaDto.getStatoNascita());
			anagrafica.get().setIdComuneNascita(anagraficaDto.getIdComuneNascita());
			anagrafica.get().setCodiceFiscale(anagraficaDto.getCodiceFiscale());
			anagrafica.get().setCittadinanza(anagraficaDto.getCittadinanza());
			anagrafica.get().setComuneNascitaEstero(anagraficaDto.getComuneNascitaEstero());
			// RESIDENZA
			anagrafica.get().setStatoResidenza(anagraficaDto.getStatoResidenza());
			anagrafica.get().setIdComuneResidenza(anagraficaDto.getIdComuneResidenza());
			anagrafica.get().setIndirizzo(anagraficaDto.getIndirizzo());
			anagrafica.get().setNumeroCivico(anagraficaDto.getNumeroCivico());
			anagrafica.get().setCap(anagraficaDto.getCap());
			anagrafica.get().setComuneResidenzaEstero(anagraficaDto.getComuneResidenzaEstero());
			// DOMICILIO
			anagrafica.get().setStatoDomicilio(anagraficaDto.getStatoDomicilio());
			anagrafica.get().setIdComuneDomicilio(anagraficaDto.getIdComuneDomicilio());
			anagrafica.get().setIndirizzoDomicilio(anagraficaDto.getIndirizzoDomicilio());
			anagrafica.get().setNumeroCivicoDomicilio(anagraficaDto.getNumeroCivicoDomicilio());
			anagrafica.get().setCapDomicilio(anagraficaDto.getCapDomicilio());
			anagrafica.get().setComuneDomicilioEstero(anagraficaDto.getComuneDomicilioEstero());
			// CONTATTI
			anagrafica.get().setIndirizzoPec(anagraficaDto.getIndirizzoPec());
			anagrafica.get().setIndirizzoEmail(anagraficaDto.getIndirizzoEmail());

			if(anagraficaDto.getIdQualifica() == 6) {
				// SOLO IN CASO DI QUALIFICA ALTRO
				anagrafica.get().setDescQualifica(anagraficaDto.getDescQualifica());
			}
			else {
				anagrafica.get().setDescQualifica(null);
			}

			anagrafica.get().setIdQualifica(anagraficaDto.getIdQualifica());
			anagrafica = Optional.of(anagraficaOdmRepository.save(anagrafica.get()));

			if(inserimento) {
				SoggettoRichiesta sogRichiesta = new SoggettoRichiesta();
				sogRichiesta.setIdRichiesta(anagraficaDto.getIdRichiesta());
				sogRichiesta.setIdAnagrafica(anagrafica.get().getIdAnagrafica());
				soggettoRichiestaRepository.save(sogRichiesta);
			}
			
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, anagraficaDto.getIdRichiesta(),
							anagrafica.get().getIdAnagrafica());
			if(moduloScheda.isPresent() == false) {
				StatoModuliRichiestaFigli newModuloScheda = new StatoModuliRichiestaFigli();
				newModuloScheda.setIdModulo((long) 31);
				newModuloScheda.setIdAnagrafica(anagrafica.get().getIdAnagrafica());
				newModuloScheda.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloScheda.setDataInserimento(new Date());
				// Se nuovo verrà anche assegnato il nuovo oggetto valorizzato alla varibile di riferimento
				statoModuliRichiestaFigliRepository.save(newModuloScheda);
			}
			
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, anagraficaDto.getIdRichiesta(),
							anagrafica.get().getIdAnagrafica());
			if (moduloAttoReqOnora.isPresent() == false) {
				StatoModuliRichiestaFigli newModuloAttoReqOnora = new StatoModuliRichiestaFigli();
				newModuloAttoReqOnora.setIdModulo((long) 32);
				newModuloAttoReqOnora.setIdAnagrafica(anagrafica.get().getIdAnagrafica());
				newModuloAttoReqOnora.setIdRichiesta(anagraficaDto.getIdRichiesta());
				newModuloAttoReqOnora.setDataInserimento(new Date());
				statoModuliRichiestaFigliRepository.save(newModuloAttoReqOnora);
			}

			// Evita l'inserimento del file in caso di null, casistica possibile in caso di aggiornamento
			if(anagraficaDto.getFile() != null) {	
				// INSERIMENTO MODULO DOCUMENTO CON FILE ANNESSO
				Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, anagraficaDto.getIdRichiesta(),
								anagrafica.get().getIdAnagrafica());
	
				if(moduloAttoDocumento.isPresent() == false) {
					StatoModuliRichiestaFigli newModuloDocumento = new StatoModuliRichiestaFigli();
					newModuloDocumento.setIdModulo((long) 33);
					newModuloDocumento.setIdAnagrafica(anagrafica.get().getIdAnagrafica());
					newModuloDocumento.setIdRichiesta(anagraficaDto.getIdRichiesta());
					newModuloDocumento.setNomeAllegato("documento");
					newModuloDocumento.setDataInserimento(new Date());
					// Se nuovo verrà anche assegnato il nuovo oggetto valorizzato alla varibile di riferimento
					moduloAttoDocumento = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloDocumento));
				}
	
				try {
					pdfService.checkValidLoadDocument(anagraficaDto.getFile());
				
					apiFileService.deleteFile(moduloAttoDocumento.get().getDocumentIdClient());
					String pathDocumento = "/" + moduloAttoDocumento.get().getIdRichiesta() + "/odm/"
							+ moduloAttoDocumento.get().getIdModulo();
					MercurioFile infoFile = apiFileService.insertFile(pathDocumento, Long.toString(moduloAttoDocumento.get().getId()), anagraficaDto.getFile());
					moduloAttoDocumento.get().setDocumentIdClient(infoFile.getDocumentIdClient());
					moduloAttoDocumento.get().setContentId(infoFile.getContentId());
					statoModuliRichiestaFigliRepository.save(moduloAttoDocumento.get());
				} catch (Exception e) {
					String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
							: "Si è verificato un errore con il caricamento del file nel documentale";
					throw new RuntimeException(message);
				}
			}

			// CONDIZIONE SOLO PER L'INSERIMENTO DA SCHEDA ANAGRAFICA, LA CONDIZIONE SARA'SEMPRE TRUE
			if(anagraficaDto.getConvalidazioeRapOrRespOrOrgAm()) {
				// IL RAP.LEGALE CLONE NON VERRA' MAI CONVALIDATO INSIEME AL RESP. DELL'ORGANISMO NELLA CASISTICA DI NUOVO INSERIMENTO 
				convalidazioneService.convalidazioneCompOrgAmAndCompSoc(anagraficaDto.getIdRichiesta(), anagrafica.get().getIdAnagrafica());
			}
			else {
				// SE SI ENTRA QUI VUOL DIRE CHE E' STATO FATTO UN'AGGIORNAMENTE E QUINDI BISOGNERA' ANNUALARE LA CONVALIDA
				convalidazioneService.annullaConvalidazioneCompOrgAmAndCompSoc(anagraficaDto.getIdRichiesta(), anagrafica.get().getIdAnagrafica());
			}
			
			return anagrafica.get();

		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un non previsto";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteRappresentante(Long idRichiesta, Long idAnagrafica) {
		Optional<SoggettoRichiesta> sogRichiesta = soggettoRichiestaRepository.findByIdRichiestaAndIdAnagrafica(idRichiesta, idAnagrafica);
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);

		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, idAnagrafica);

		Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);

		Optional<StatoModuliRichiestaFigli> moduloAttoQuaifica = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);

		// IN CASO NON ESISTE UN MODULO O L'ANAGRAFICA VERRA' IMPEDITA LA CANCELLAZIONE
		if (anagrafica.isPresent() == false || moduloAttoReqOnora.isPresent() == false 
				&& moduloAttoDocumento.isPresent() == false || moduloAttoQuaifica.isPresent() == false) {
			throw new RuntimeException("si è verificato un errore non previsto");
		}

		soggettoRichiestaRepository.delete(sogRichiesta.get());
		anagraficaOdmRepository.delete(anagrafica.get());
		statoModuliRichiestaFigliRepository.delete(moduloAttoReqOnora.get());
		statoModuliRichiestaFigliRepository.delete(moduloAttoDocumento.get());
		statoModuliRichiestaFigliRepository.delete(moduloAttoQuaifica.get());

		apiFileService.deleteFile(moduloAttoDocumento.get().getDocumentIdClient());
		apiFileService.deleteFile(moduloAttoQuaifica.get().getDocumentIdClient());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteCompOrgAm(Long idRichiesta, Long idAnagrafica) {
		Optional<SoggettoRichiesta> sogRichiesta = soggettoRichiestaRepository.findByIdRichiestaAndIdAnagrafica(idRichiesta, idAnagrafica);
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);

		Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);

		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta,
						anagrafica.get().getIdAnagrafica());

		apiFileService.deleteFile(moduloAttoDocumento.get().getDocumentIdClient());

		soggettoRichiestaRepository.delete(sogRichiesta.get());
		anagraficaOdmRepository.delete(anagrafica.get());
		statoModuliRichiestaFigliRepository.delete(moduloScheda.get());
		statoModuliRichiestaFigliRepository.delete(moduloAttoDocumento.get());

	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteAnagraficaPrestatore(Long idRichiesta, Long idAnagrafica) {
		Optional<SoggettoRichiesta> sogRichiesta = soggettoRichiestaRepository.findByIdRichiestaAndIdAnagrafica(idRichiesta, idAnagrafica);
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);

		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, idAnagrafica);

		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);
		
		apiFileService.deleteFile(moduloDocumento.get().getDocumentIdClient());
		
		soggettoRichiestaRepository.delete(sogRichiesta.get());
		anagraficaOdmRepository.delete(anagrafica.get());
		statoModuliRichiestaFigliRepository.delete(moduloScheda.get());
		statoModuliRichiestaFigliRepository.delete(moduloDocumento.get());
		
		// ANNULLA CONVALIDAZIONE DELL'ELENCO DEI PRESTATORI DI SERVIZIO
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long) 35, 
			      idRichiesta);
		if(moduloElenco.isPresent() && moduloElenco.get().getCompletato() == (Integer) 1) {
			moduloElenco.get().setValidato(0);
			moduloElenco.get().setCompletato(0);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteMediatore(Long idRichiesta, Long idAnagrafica) {
		Optional<SoggettoRichiesta> sogRichiesta = soggettoRichiestaRepository.findByIdRichiestaAndIdAnagrafica(idRichiesta, idAnagrafica);
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		
		if(anagrafica.get().getIdTipoAnagrafica() == 4) {
			ArrayList<Long> moduliA = 
					new ArrayList<Long>(
							Arrays.asList(
									(long) 38, (long) 39, (long) 40, (long) 41, (long) 42, (long) 75));
			
			for(Long modulo : moduliA) {
				Optional<StatoModuliRichiestaFigli> moduloEliminando = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica(modulo, idRichiesta, idAnagrafica);
				if(moduloEliminando.isPresent())
					statoModuliRichiestaFigliRepository.delete(moduloEliminando.get());
			}
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 5) {
			ArrayList<Long> moduliB = 
					new ArrayList<Long>(
							Arrays.asList(
									(long) 43, (long) 44, (long) 45, (long) 46, (long) 47, (long) 77, (long) 78, (long) 50));
			
			for(Long modulo : moduliB) {
				Optional<StatoModuliRichiestaFigli> moduloEliminando = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica(modulo, idRichiesta, idAnagrafica);
				if(modulo ==(long) 50 && moduloEliminando.isPresent()) {
					if(anagrafica.get().getLingueStraniere() != null && !anagrafica.get().getLingueStraniere().isEmpty()) {
						statoModuliRichiestaFigliRepository.delete(moduloEliminando.get());

					}
				}
				if(modulo != 50) {
					statoModuliRichiestaFigliRepository.delete(moduloEliminando.get());

				}
				
			}
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 6) {
			ArrayList<Long> moduliC = 
					new ArrayList<Long>(
							Arrays.asList(
									(long) 52, (long) 53, (long) 54, (long) 55, (long) 56, (long) 80, (long) 81, (long) 82));
			
			for(Long modulo : moduliC) {
				Optional<StatoModuliRichiestaFigli> moduloEliminando = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica(modulo, idRichiesta, idAnagrafica);
				if(modulo == (long) 82 && moduloEliminando.isPresent()) {
					if(anagrafica.get().getLingueStraniere() != null && !anagrafica.get().getLingueStraniere().isEmpty()) {
						statoModuliRichiestaFigliRepository.delete(moduloEliminando.get());

					}
				}
				if(modulo != 82) {
					statoModuliRichiestaFigliRepository.delete(moduloEliminando.get());

				}

			}
		}
		
		if(sogRichiesta.isPresent())
			soggettoRichiestaRepository.delete(sogRichiesta.get());
		if(anagrafica.isPresent())
			anagraficaOdmRepository.delete(anagrafica.get());

//		Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = statoModuliRichiestaFigliRepository
//				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);
//
//		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
//				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta,
//						anagrafica.get().getIdAnagrafica());
//
//		//parte che richiede Mercurio
//		if(moduloAttoDocumento.isPresent())
//			apiFileService.deleteFile(moduloAttoDocumento.get().getDocumentIdClient());
//
//		if(sogRichiesta.isPresent())
//			soggettoRichiestaRepository.delete(sogRichiesta.get());
//		if(anagrafica.isPresent())
//			anagraficaOdmRepository.delete(anagrafica.get());
//		if(moduloScheda.isPresent())
//			statoModuliRichiestaFigliRepository.delete(moduloScheda.get());
//		if(moduloAttoDocumento.isPresent())
//			statoModuliRichiestaFigliRepository.delete(moduloAttoDocumento.get());

	}

}
