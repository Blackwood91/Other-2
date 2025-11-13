package com.giustizia.mediazionecivile.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.SocietaUtenti;
import com.giustizia.mediazionecivile.repository.SocietaUtentiRepository;

@Service
public class SocietaUtenteService {
	
	@Autowired
	SocietaUtentiRepository societaUtentiRepository;

	
	void save(SocietaUtenti societaUtenti) {
		societaUtentiRepository.save(societaUtenti);
	}

	
//	Page<Object[]> getAllByUtente(long idUtente, Pageable pageable) {
//		return societaUtentiRepository.getAllbyUtente(idUtente, pageable);
//	}
	

}
