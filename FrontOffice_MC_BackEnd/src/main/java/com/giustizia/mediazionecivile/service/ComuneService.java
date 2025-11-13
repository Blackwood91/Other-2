package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.Comune;
import com.giustizia.mediazionecivile.projection.ComuneProjection;
import com.giustizia.mediazionecivile.repository.ComuneRepository;

@Service
public class ComuneService {
	@Autowired
	ComuneRepository comuneRepository; 
	
	public List<ComuneProjection> getAllComuneByNome(String nomeComune, Pageable pageable) {
		return comuneRepository.findByNomeComuneIgnoreCaseStartingWithAndStatoOrderByNomeComuneAsc(nomeComune, "A", pageable);
	}
	
	public ComuneProjection getComuneById(Long idComune) {	
		return comuneRepository.findByIdCodComune(idComune);
	}
	
	public List<Comune> getAllComune() {	
		return comuneRepository.findAll();
	}
}
