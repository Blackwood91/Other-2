package com.ministero.ministero.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ministero.ministero.model.TipoSchede;
import com.ministero.ministero.repository.TipoSchedeRepository;

public class TipoSchedeService {

    @Autowired
    TipoSchedeRepository tipoSchedeRepository;

    public List<TipoSchede> getAll(){
        return tipoSchedeRepository.findAll();
    }

    
    
}
