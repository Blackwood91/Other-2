package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.dto.AlboMediatoriElencoDto;
import com.giustizia.mediazionecivile.dto.AnagraficaOdmDto;
import com.giustizia.mediazionecivile.dto.FormatoreMediatoreDto;
import com.giustizia.mediazionecivile.dto.OrganismoOdmDto;
import com.giustizia.mediazionecivile.model.AlboMediatori;
import com.giustizia.mediazionecivile.repository.AlboMediatoriRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboMediatoriService {
	
	@Autowired
	AlboMediatoriRepository alboMediatoriRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	
    
    public HashMap<String, Object> findByIdAlboMediatori(Long idAlboMediatori) {
		HashMap<String, Object> response = new HashMap<>();
		List<Object[]> result = alboMediatoriRepository.findByIdAlboMediatori(idAlboMediatori);;

		List<FormatoreMediatoreDto> formMedDto = new ArrayList<FormatoreMediatoreDto>();
		for (Object[] obj : result) {
			FormatoreMediatoreDto formMedTemp = new FormatoreMediatoreDto();
			formMedTemp.setRom((Long) obj[0]);		
			formMedTemp.setDenominazioneOrganismo((String) obj[1]);
			formMedTemp.setSitoWeb((String) obj[2]);
			formMedTemp.setNaturaOrganismo((String) obj[3]);
			formMedTemp.setCancellato((Integer) obj[4]);
			formMedTemp.setDataCancellato((Date) obj[5]);
			formMedTemp.setNome((String) obj [6]);
			formMedTemp.setCognome((String)obj [7]);
			formMedTemp.setCodiceFiscale((String) obj[8]);
			formMedTemp.setEmail((String) obj[9]);
			formMedTemp.setPartitaIva((String) obj[10]);		


			formMedDto.add(formMedTemp);
		}

		response.put("result", formMedDto);
		return response;
	}
    
    
    public HashMap<String, Object> getAllMediatoriIscritti(Pageable pageable) {
		HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboMediatoriRepository.getAllMediatoriIscritti(pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AlboMediatoriElencoDto> anagDto = new ArrayList<AlboMediatoriElencoDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AlboMediatoriElencoDto(obj));
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
    
    public HashMap<String, Object> getAllMediatoriPagedByNome(Pageable pageable, String searchText) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboMediatoriRepository.getAllAlboMediatoriByNome(searchText, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AlboMediatoriElencoDto> anagDto = new ArrayList<AlboMediatoriElencoDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AlboMediatoriElencoDto(obj));
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
    
    public HashMap<String, Object> getAllMediatoriPagedByCognome(Pageable pageable, String searchText) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboMediatoriRepository.getAllAlboMediatoriByCognome(searchText, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AlboMediatoriElencoDto> anagDto = new ArrayList<AlboMediatoriElencoDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AlboMediatoriElencoDto(obj));
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
    
    public HashMap<String, Object> getAllMediatoriPagedByCF(Pageable pageable, String searchText) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboMediatoriRepository.getAllAlboMediatoriByCF(searchText, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AlboMediatoriElencoDto> anagDto = new ArrayList<AlboMediatoriElencoDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AlboMediatoriElencoDto(obj));
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
    
    public HashMap<String, Object> getAllMediatoriPagedByTipoMed(Pageable pageable, Long tipoMed) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboMediatoriRepository.getAllAlboMediatoriByTipoMed(tipoMed, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AlboMediatoriElencoDto> anagDto = new ArrayList<AlboMediatoriElencoDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AlboMediatoriElencoDto(obj));
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
    
    
    public HashMap<String, Object> getAllOdmPagedByIdAnagrafica(Pageable pageable, Long idAnagrafica) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboMediatoriRepository.getAllOdmPagedByIdAnagrafica(idAnagrafica, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<OrganismoOdmDto> anagDto = new ArrayList<OrganismoOdmDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new OrganismoOdmDto(obj));
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}

}
