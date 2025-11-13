package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.TipoPdg;
import com.giustizia.mediazionecivile.repository.TipoPdgRepository;

@Service
public class TipoPdgService {
	@Autowired
	TipoPdgRepository tipoPdgRepository;
	
	public List<TipoPdg> getAll() {
		return tipoPdgRepository.findAll();
	}
	
}
