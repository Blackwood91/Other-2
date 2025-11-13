package com.giustizia.mediazionecivile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.AlboEntiFormatori;
import com.giustizia.mediazionecivile.model.AlboOdm;
import com.giustizia.mediazionecivile.repository.AlboOdmRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboOdmService {
	@Autowired
	AlboOdmRepository alboOdmRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	
	
	public List<AlboOdm> getAllOdm() {
		return alboOdmRepository.findAll();
	}

	public Page<AlboOdm> getAllOdmPaged(Pageable pageable, String searchText/*, String colonna*/) {
		Page<AlboOdm> odmElenco = alboOdmRepository.findAll(pageable);
		
        return odmElenco;
    }

    public Page<AlboOdm> getAllOdmPagedByDenominazione(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboOdm> odmElenco = alboOdmRepository.getAllOdmByDenominazione(searchText, pageable);

        return odmElenco;
    }
    
    public Page<AlboOdm> getAllOdmPagedByNumeroRegistro(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboOdm> odmElenco = alboOdmRepository.getAllOdmByNumeroRegistro(searchText, pageable);

        return odmElenco;
    }

}
