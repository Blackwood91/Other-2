package com.giustizia.mediazionecivile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.Stato;
import com.giustizia.mediazionecivile.repository.StatoRepository;

@Service
public class StatoTipologicaService {

    @Autowired
    StatoRepository statoRepository;

    public Stato findStatoByNome(String descrizione) {
        return statoRepository.findStatoByDescrizione(descrizione);
    }
}
