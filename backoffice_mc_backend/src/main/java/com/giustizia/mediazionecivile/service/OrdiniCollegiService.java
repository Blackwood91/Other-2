package com.giustizia.mediazionecivile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.OrdiniCollegi;
import com.giustizia.mediazionecivile.repository.OrdiniCollegiRepository;

@Service
public class OrdiniCollegiService {
	@Autowired
	OrdiniCollegiRepository ordiniCollegiRepository;
	
	public List<OrdiniCollegi> getAll() {
		return ordiniCollegiRepository.findAll();
	}
	
	public Optional<OrdiniCollegi> getIdOrdiniCollegi(Long idOrdiniCollegi) {
		return ordiniCollegiRepository.findById(idOrdiniCollegi);
	}
	
}
