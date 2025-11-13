package com.giustizia.mediazionecivile.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.AlboEntiFormatori;
import com.giustizia.mediazionecivile.model.AlboFormatori;
import com.giustizia.mediazionecivile.repository.AlboEntiFormatoriRepository;
import com.giustizia.mediazionecivile.repository.AlboFormatoriRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboEntiFormatoriService {
	@Autowired
	AlboEntiFormatoriRepository alboEntiFormatoriRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;

//	public AlboFormatori getFormatoreById(Long idAnagrafica){
//        return alboFormatoriRepository.findByIdProjection(idAnagrafica);        
//    }
	
	public List<AlboEntiFormatori> getAllEntiFormatori() {
		return alboEntiFormatoriRepository.findAll();
	}

	public Page<AlboEntiFormatori> getAllEntiFormatoriPaged(Pageable pageable, String searchText/*, String colonna*/) {
		Page<AlboEntiFormatori> entiFormatoriElenco = alboEntiFormatoriRepository.findAll(pageable);
		
        return entiFormatoriElenco;
    }

    public Page<AlboEntiFormatori> getAllEntiFormatoriPagedByDenominazione(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboEntiFormatori> entiFormatoriElenco = alboEntiFormatoriRepository.getAllEntiFormatoriByDenominazione(searchText, pageable);

        return entiFormatoriElenco;
    }
    
    public Page<AlboEntiFormatori> getAllEntiFormatoriPagedByNumeroRegistro(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboEntiFormatori> entiFormatoriElenco = alboEntiFormatoriRepository.getAllEntiFormatoriByNumeroRegistro(searchText, pageable);

        return entiFormatoriElenco;
    }
}
