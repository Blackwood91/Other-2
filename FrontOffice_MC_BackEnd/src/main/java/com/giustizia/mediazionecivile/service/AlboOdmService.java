package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.dto.AlboMediatoriElencoDto;
import com.giustizia.mediazionecivile.dto.OrganismoOdmDto;
import com.giustizia.mediazionecivile.model.AlboEntiFormatori;
import com.giustizia.mediazionecivile.model.AlboOdm;
import com.giustizia.mediazionecivile.repository.AlboOdmRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboOdmService {
	@Autowired
	AlboOdmRepository alboOdmRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	
	
	public List<AlboOdm> getAllOdm() {
		return alboOdmRepository.findAll();
	}

//	public Page<AlboOdm> getAllOdmPaged(Pageable pageable, String searchText/*, String colonna*/) {
//		Page<AlboOdm> odmElenco = alboOdmRepository.findAll(pageable);
//		
//        return odmElenco;
//    }
//
//    public Page<AlboOdm> getAllOdmPagedByDenominazione(Pageable pageable, String searchText/*, String colonna*/) {
//        Page<AlboOdm> odmElenco = alboOdmRepository.getAllOdmByDenominazione(searchText, pageable);
//
//        return odmElenco;
//    }
//    
//    public Page<AlboOdm> getAllOdmPagedByNumeroRegistro(Pageable pageable, String searchText/*, String colonna*/) {
//        Page<AlboOdm> odmElenco = alboOdmRepository.getAllOdmByNumeroRegistro(searchText, pageable);
//
//        return odmElenco;
//    }
	
	public HashMap<String, Object> getAllOdmIscritti(Pageable pageable) {
		HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboOdmRepository.getAllOdmIscritti(pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<OrganismoOdmDto> anagDto = new ArrayList<OrganismoOdmDto>();
		
		for (Object[] obj : resultList) {
			OrganismoOdmDto anagSing  = new OrganismoOdmDto();
			anagSing.setDenominazioneOdm((String) obj[0]);
			anagSing.setSitoWebSede((String) obj[1]);
			anagSing.setEmail((String) obj[2]);
			anagSing.setAutonomo((int) obj[3]);
			anagSing.setCodFiscSocieta((String) obj[4]);
			anagSing.setpIva((String) obj[5]);
			anagSing.setNumIscrAlbo((Long) obj[6]);
			anagSing.setIdRichiesta((Long) obj[7]);
			
			anagDto.add(anagSing);
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
    
    public HashMap<String, Object> getAllOdmPagedByDenominazione(Pageable pageable, String searchText) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboOdmRepository.getAllOdmPagedByDenominazione(searchText, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<OrganismoOdmDto> anagDto = new ArrayList<OrganismoOdmDto>();
		
		for (Object[] obj : resultList) {
			OrganismoOdmDto anagSing  = new OrganismoOdmDto();
			anagSing.setDenominazioneOdm((String) obj[0]);
			anagSing.setSitoWebSede((String) obj[1]);
			anagSing.setEmail((String) obj[2]);
			anagSing.setAutonomo((int) obj[3]);
			anagSing.setCodFiscSocieta((String) obj[4]);
			anagSing.setpIva((String) obj[5]);
			anagSing.setNumIscrAlbo((Long) obj[6]);
			anagSing.setIdRichiesta((Long) obj[7]);
			
			anagDto.add(anagSing);
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
    
    public HashMap<String, Object> getAllOdmPagedByNumeroRegistro(Pageable pageable, String searchText) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboOdmRepository.getAllOdmPagedByNumeroRegistro(searchText, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<OrganismoOdmDto> anagDto = new ArrayList<OrganismoOdmDto>();
		
		for (Object[] obj : resultList) {
			OrganismoOdmDto anagSing  = new OrganismoOdmDto();
			anagSing.setDenominazioneOdm((String) obj[0]);
			anagSing.setSitoWebSede((String) obj[1]);
			anagSing.setEmail((String) obj[2]);
			anagSing.setAutonomo((int) obj[3]);
			anagSing.setCodFiscSocieta((String) obj[4]);
			anagSing.setpIva((String) obj[5]);
			anagSing.setNumIscrAlbo((Long) obj[6]);
			anagSing.setIdRichiesta((Long) obj[7]);
			
			anagDto.add(anagSing);
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}

}
