package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.ModalitaCostituzioneOrganismo;
import com.giustizia.mediazionecivile.repository.ModalitaCostituzioneOrganismoRepository;

@Service
public class ModalitaCostituzioneOrganismoService {
	@Autowired
	ModalitaCostituzioneOrganismoRepository modalitaCostituzioneOrganismoRepository;
	
	public List<ModalitaCostituzioneOrganismo> getAll() {
		return modalitaCostituzioneOrganismoRepository.findAll();
	}
}
