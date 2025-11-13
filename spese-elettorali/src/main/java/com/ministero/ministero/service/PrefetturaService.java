package com.ministero.ministero.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ministero.ministero.model.Prefettura;
import com.ministero.ministero.repository.PrefetturaRepository;

@Service
public class PrefetturaService {
    
    List<Prefettura> prefetture;

    @Autowired
    PrefetturaRepository prefetturaRepository;

    public List<Prefettura> getAll() { 
        return prefetturaRepository.findAll();
    }
    
    public Prefettura creaPrefettura (Prefettura prefettura){
        return prefetturaRepository.save(prefettura);
    }

    public Prefettura findPrefetturaById(Long id){
        return prefetturaRepository.findById(id).get();
    }

    public Prefettura updatePrefettura(Prefettura newPrefettura){
        Prefettura oldPref = findPrefetturaById(newPrefettura.getId());

        if(oldPref != null){
            oldPref.setCodiceUtg(newPrefettura.getCodiceUtg());
            oldPref.setCodiceRegione(newPrefettura.getCodiceRegione());
            oldPref.setDescrizioneUtg(newPrefettura.getDescrizioneUtg());
            oldPref.setSigla(newPrefettura.getSigla());

            return prefetturaRepository.save(oldPref);
        } else {
            return null;
        }
    }

    public void deletePrefetturaById(Long id){
        prefetturaRepository.deleteById(id);
    }
}
