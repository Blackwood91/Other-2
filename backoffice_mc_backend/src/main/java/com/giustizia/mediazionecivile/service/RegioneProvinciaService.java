package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.projection.ProvinceProjection;
import com.giustizia.mediazionecivile.projection.RegioneProjection;
import com.giustizia.mediazionecivile.repository.RegioniProvinceRepository;

@Service
public class RegioneProvinciaService {
    @Autowired
    RegioniProvinceRepository regioniProvinceRepository; 

    public List<RegioneProjection> getAllRegioni(){
        return regioniProvinceRepository.findAllByRegioni();        
    }

    public List<ProvinceProjection> getAllProvince(int codiceRegione){
        return regioniProvinceRepository.findAllByRegioneForProvince(codiceRegione);        
    }
    
	
}

