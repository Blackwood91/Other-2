package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.NaturaGiuridica;
import com.giustizia.mediazionecivile.repository.NaturaGiuridicaRepository;

@Service
public class NaturaGiuridicaService {
	@Autowired
	NaturaGiuridicaRepository naturaGiuridicaRepository;
	
	
	public List<NaturaGiuridica> getAllNaturaGiuridica() {
		return naturaGiuridicaRepository.findAll();
	}


}
