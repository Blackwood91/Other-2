package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.dto.AlboOdmSediDto;
import com.giustizia.mediazionecivile.dto.OrganismoOdmDto;
import com.giustizia.mediazionecivile.model.AlboEfSedi;
import com.giustizia.mediazionecivile.model.AlboOdmSedi;
import com.giustizia.mediazionecivile.repository.AlboOdmSediRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboOdmSediService {
	@Autowired
	AlboOdmSediRepository alboOdmSediRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	
	
	public List<AlboOdmSedi> getAllOdmSedi() {
		return alboOdmSediRepository.findAll();
	}
	
//	public List<AlboOdmSedi> findAllByRom(Long rom) {
//		return alboOdmSediRepository.findByRom(rom);
//	}
	
	public HashMap<String, Object> getAllOdmPagedByNumeroRegistro(Pageable pageable, Long numIscrAlbo) {
    	HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = alboOdmSediRepository.findByNumIscrAlbo(numIscrAlbo, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<AlboOdmSediDto> anagDto = new ArrayList<AlboOdmSediDto>();
		
		for (Object[] obj : resultList) {
			anagDto.add(new AlboOdmSediDto(obj));
		}
		
		response.put("totalResult", resultPage.getTotalElements());
		response.put("result", anagDto);
		return response;
	}
}
