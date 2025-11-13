package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.projection.EfTipoRichiedenteProjection;
import com.giustizia.mediazionecivile.projection.TipoRichiedenteProjection;
import com.giustizia.mediazionecivile.repository.EfTipoRichiedenteRepository;
import com.giustizia.mediazionecivile.repository.TipoRichiedenteRepository;

@Service
public class TipoRichiedenteService {
    @Autowired
    TipoRichiedenteRepository tipoRichiedenteRepository;
    @Autowired
    EfTipoRichiedenteRepository efTipoRichiedenteRepository; 

    public List<TipoRichiedenteProjection> getAllTipoRichiedente(){
        return tipoRichiedenteRepository.findAllBy();        
    }

    public List<EfTipoRichiedenteProjection> getAllTipoRichiedenteEf(){
        return efTipoRichiedenteRepository.findAllBy();        
    }
    
}
