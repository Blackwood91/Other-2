package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.AlboEfPdg;
import com.giustizia.mediazionecivile.repository.AlboEfPdgRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboEfPdgService {
	@Autowired
	AlboEfPdgRepository alboEfPdgRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	
	
	public List<AlboEfPdg> getAllEfPdg() {
		return alboEfPdgRepository.findAll();
	}
	
	public List<AlboEfPdg> findAllByNumReg(Long numReg) {
		return alboEfPdgRepository.findByNumReg(numReg);
	}
}
