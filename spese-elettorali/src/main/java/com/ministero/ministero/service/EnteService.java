package com.ministero.ministero.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ministero.ministero.model.Ente;
import com.ministero.ministero.repository.EnteRepository;

@Service
public class EnteService {

    List<Ente> enti;

    @Autowired
    EnteRepository enteRepository;

    public EnteService() {
        enti = new ArrayList<>();

    }

    public List<Ente> getAll() {
        return enteRepository.findAll();
    }

    public Ente creaEnte(Ente ente) {
        return enteRepository.save(ente);
    }

    public Ente findEnteById(Long id) {
        return enteRepository.findById(id).get();
    }

    public Ente updateEnte(Ente newEnte) {

        Ente vecchioEnte = findEnteById(newEnte.getId());

        if (vecchioEnte != null) {
            vecchioEnte.setCodiceEnte(newEnte.getCodiceEnte());
            vecchioEnte.setCodiceSut(newEnte.getCodiceSut());
            vecchioEnte.setDescrizioneEnte(newEnte.getDescrizioneEnte());
            vecchioEnte.setCodiceUtg(newEnte.getCodiceUtg());

            return enteRepository.save(vecchioEnte);
        } else {
            return null;
        }
    }

    public void deleteEnteById(Long id) {
        enteRepository.deleteById(id);
    }

}
