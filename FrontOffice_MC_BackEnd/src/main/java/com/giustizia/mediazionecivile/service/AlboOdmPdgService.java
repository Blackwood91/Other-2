package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.dto.AlboOdmPdgDto;
import com.giustizia.mediazionecivile.dto.AlboOdmSediDto;
import com.giustizia.mediazionecivile.model.AlboEfPdg;
import com.giustizia.mediazionecivile.model.AlboOdmPdg;
import com.giustizia.mediazionecivile.repository.AlboOdmPdgRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboOdmPdgService {
	@Autowired
	AlboOdmPdgRepository alboOdmPdgRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	
	
	public List<AlboOdmPdg> getAllOdmPdg() {
		return alboOdmPdgRepository.findAll();
	}
	
//	public List<AlboOdmPdg> findAllByRom(Long rom) {
//		return alboOdmPdgRepository.findByRom(rom);
//	}
	
	public HashMap<String, Object> getAllOdmPdgPagedByNumeroRegistro(Pageable pageable, Long numIscrAlbo) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboOdmPdgRepository.findByNumIscrAlbo(numIscrAlbo, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AlboOdmPdgDto> anagDto = new ArrayList<AlboOdmPdgDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AlboOdmPdgDto(obj));
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
	
}
