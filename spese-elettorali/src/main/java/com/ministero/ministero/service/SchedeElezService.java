package com.ministero.ministero.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ministero.ministero.model.SchedeElez;
import com.ministero.ministero.repository.SchedeElezRepository;

@Service
public class SchedeElezService {

    @Autowired
    SchedeElezRepository schedaElezRepository;

    public SchedeElez createSchedeElez(SchedeElez schedeElez) {

        switch (schedeElez.getTipoScheda()) {

            case ("PC"):
                schedeElez.setTipoSchedeNomeCompleto("POLITICHE CAMERA");
                break;
            case ("PS"):
                schedeElez.setTipoSchedeNomeCompleto("POLITICHE SENATO");
                break;
            case ("EU"):
                schedeElez.setTipoSchedeNomeCompleto("EUROPEE");
                break;
            case ("AR"):
                schedeElez.setTipoSchedeNomeCompleto("REGIONALI");
                break;
            case ("AC"):
                schedeElez.setTipoSchedeNomeCompleto("COMUNALI");
                break;
            case ("RS"):
                schedeElez.setTipoSchedeNomeCompleto("REFERENDUM STATALE");
                break;
            case ("RL"):
                schedeElez.setTipoSchedeNomeCompleto("REFERENDUM LOCALE");
                break;
        }

        return schedaElezRepository.save(schedeElez);
    }

    public List<SchedeElez> getAll() {
        return schedaElezRepository.findAll();
    }

    public SchedeElez findSchedeElezById(Long id) {
        if (!schedaElezRepository.findById(id).isPresent()) {
            return null;
        } else
            return schedaElezRepository.findById(id).get();
    }

}
