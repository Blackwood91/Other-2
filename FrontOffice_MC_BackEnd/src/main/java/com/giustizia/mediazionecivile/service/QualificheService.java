package com.giustizia.mediazionecivile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.Qualifica;
import com.giustizia.mediazionecivile.repository.QualificaRepository;

@Service
public class QualificheService {
	@Autowired
	QualificaRepository qualificaRepository;
	
	public List<Qualifica> getAll() {
		return qualificaRepository.findAll();
	}
	
	public List<Qualifica> getAllCompOrgAmAndCompSoc() {
		return qualificaRepository.findAllExcludingIdQualificaFor2((long) 1, (long) 2);
	}
	
	public Optional<Qualifica> getIdTitolo(Long idTitolo) {
		return qualificaRepository.findById(idTitolo);
	}
	
}
