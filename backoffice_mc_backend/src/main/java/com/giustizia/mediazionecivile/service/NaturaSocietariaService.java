package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.NaturaSocietaria;
import com.giustizia.mediazionecivile.repository.NaturaSocietariaRepository;

@Service
public class NaturaSocietariaService {
	@Autowired
	NaturaSocietariaRepository naturaSocietariaRepository;
	
	public List<NaturaSocietaria> getAllNaturaSocietaria() {
		return naturaSocietariaRepository.findAll();
	}
	
}
