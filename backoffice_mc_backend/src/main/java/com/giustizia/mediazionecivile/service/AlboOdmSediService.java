package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<AlboOdmSedi> findAllByRom(Long rom) {
		return alboOdmSediRepository.findByRom(rom);
	}
}
