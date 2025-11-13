package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.SoggettoRichiedente;
import com.giustizia.mediazionecivile.repository.SoggettoRichiedenteRepository;

@Service
public class SoggettoRichiedenteService {

    List<SoggettoRichiedente> soggettoRichiedente;

    @Autowired
    SoggettoRichiedenteRepository soggettoRichiedenteRepository;

    public List<SoggettoRichiedente> getAll() {
        return soggettoRichiedenteRepository.findAll();
    }

    public SoggettoRichiedente findSoggettoRichiedenteById(Long id) {
        return soggettoRichiedenteRepository.findById(id).get();
    }

    public SoggettoRichiedente findSoggettoRichiedenteBySoggettoRichiedente(String soggettoRichiedente) {
        return soggettoRichiedenteRepository.findSoggettoRichiedenteBySoggettoRichiedente(soggettoRichiedente);
    }
}
