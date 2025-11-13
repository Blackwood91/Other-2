package com.giustizia.mediazionecivile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.TitoloAnagrafiche;
import com.giustizia.mediazionecivile.repository.TitoloAnagraficheRepository;

@Service
public class TitoloAnagraficheService {
	@Autowired
	TitoloAnagraficheRepository titoloAnagraficheRepository;
	
	public List<TitoloAnagrafiche> getAll() {
		return titoloAnagraficheRepository.findAll();
	}
	
	public Optional<TitoloAnagrafiche> getIdTitolo(Long idTitolo) {
		return titoloAnagraficheRepository.findById(idTitolo);
	}
}
