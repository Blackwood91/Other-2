package com.giustizia.mediazionecivile.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
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
import com.giustizia.mediazionecivile.dto.PdfDto;
import com.giustizia.mediazionecivile.dto.SedeOperativaTab;
import com.giustizia.mediazionecivile.dto.SelectAutocertReqOnoDto;
import com.giustizia.mediazionecivile.dto.SocietaDTO;
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
			AnagraficaOdmDto anagraficaOdmDto = new AnagraficaOdmDto();
			anagraficaOdmDto.istanziaElencoMediatori(obj);
			anagDto.add(anagraficaOdmDto);
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
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
	
	public HashMap<String, Object> getAllAnagraficaMediatoriB(Pageable pageable, Long idRichiesta) {

		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = anagraficaOdmRepository.getAllAnagraficaMediatoriB(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AnagraficaOdmDto> anagDto = new ArrayList<AnagraficaOdmDto>();
		
		for (Object[] obj : resultList) {
			AnagraficaOdmDto anagraficaOdmDto = new AnagraficaOdmDto();
			anagraficaOdmDto.istanziaElencoMediatori(obj);
			anagDto.add(anagraficaOdmDto);
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
			AnagraficaOdmDto anagraficaOdmDto = new AnagraficaOdmDto();
			anagraficaOdmDto.istanziaElencoMediatori(obj);
			anagDto.add(anagraficaOdmDto);
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


}
