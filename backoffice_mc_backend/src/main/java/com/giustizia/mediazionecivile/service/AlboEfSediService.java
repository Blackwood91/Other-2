package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.AlboEfPdg;
import com.giustizia.mediazionecivile.model.AlboEfSedi;
import com.giustizia.mediazionecivile.repository.AlboEfSediRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboEfSediService {
	@Autowired
	AlboEfSediRepository alboEfSediRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	
	
	public List<AlboEfSedi> getAllEfSedi() {
		return alboEfSediRepository.findAll();
	}
	
	public List<AlboEfSedi> findAllByNumReg(Long numReg) {
		return alboEfSediRepository.findByNumReg(numReg);
	}
}
