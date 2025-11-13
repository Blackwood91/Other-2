package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.SedeDetenzioneTitolo;
import com.giustizia.mediazionecivile.repository.SedeDetenzioneTitoloRepository;

@Service
public class SedeDetenzioneTitoloService {
	@Autowired
	SedeDetenzioneTitoloRepository sedeDetenzioneTitoloRepository;

	public List<SedeDetenzioneTitolo> getAllSedeDetezioneTitolo() {
		return sedeDetenzioneTitoloRepository.findAll();
	}
	
}
