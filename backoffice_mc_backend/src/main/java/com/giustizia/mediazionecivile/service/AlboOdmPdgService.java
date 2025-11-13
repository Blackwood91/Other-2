package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<AlboOdmPdg> findAllByRom(Long rom) {
		return alboOdmPdgRepository.findByRom(rom);
	}
}
