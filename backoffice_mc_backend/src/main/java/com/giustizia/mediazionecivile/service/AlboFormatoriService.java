package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.dto.FormatoreMediatoreDto;
import com.giustizia.mediazionecivile.model.AlboEfSedi;
import com.giustizia.mediazionecivile.model.AlboFormatori;
import com.giustizia.mediazionecivile.repository.AlboFormatoriRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboFormatoriService {
	
	@Autowired
	AlboFormatoriRepository alboFormatoriRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;

//	public AlboFormatori getFormatoreById(Long idAnagrafica){
//        return alboFormatoriRepository.findByIdProjection(idAnagrafica);        
//    }
	
	public List<AlboFormatori> getAllFormatori() {
		return alboFormatoriRepository.findAll();
	}

	
	public Page<AlboFormatori> getAllFormatoriPaged(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboFormatori> formatoriElenco = alboFormatoriRepository.findAll(pageable);
        
        return formatoriElenco;
    }

    public Page<AlboFormatori> getAllFormatoriPagedByNome(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboFormatori> formatoriElenco = alboFormatoriRepository.getAllFormatoriByNome(searchText, pageable);

        return formatoriElenco;
    }
    
    public Page<AlboFormatori> getAllFormatoriPagedByCognome(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboFormatori> formatoriElenco = alboFormatoriRepository.getAllFormatoriByCognome(searchText, pageable);

        return formatoriElenco;
    }
    
    public Page<AlboFormatori> getAllFormatoriPagedByCF(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboFormatori> formatoriElenco = alboFormatoriRepository.getAllFormatoriByCF(searchText, pageable);

        return formatoriElenco;
    }
    
    public Page<AlboFormatori> getAllFormatoriPagedByTipoForm(Pageable pageable, Long tipoForm) {
        Page<AlboFormatori> formatoriElenco = alboFormatoriRepository.getAllFormatoriByTipoForm(tipoForm, pageable);

        return formatoriElenco;
    }
    
//    public List<Object[]> findByIdAlboFormatori(Long idAlboFormatori) {
//		return alboFormatoriRepository.findByIdAlboFormatori(idAlboFormatori);
//	}
    
    public HashMap<String, Object> findByIdAlboFormatori(Long idAlboFormatori) {
		HashMap<String, Object> response = new HashMap<>();
		List<Object[]> result = alboFormatoriRepository.findByIdAlboFormatori(idAlboFormatori);;

		List<FormatoreMediatoreDto> formMedDto = new ArrayList<FormatoreMediatoreDto>();
		for (Object[] obj : result) {
			FormatoreMediatoreDto formMedTemp = new FormatoreMediatoreDto();
			formMedTemp.setNumReg((Long) obj[0]);		
			formMedTemp.setDenominazione((String) obj[1]);
			formMedTemp.setSitoWeb((String) obj[2]);
			formMedTemp.setNatura((String) obj[3]);
			formMedTemp.setCancellato((Integer) obj[4]);
			formMedTemp.setDataCancellato((Date) obj[5]);
			formMedTemp.setNome((String) obj [6]);
			formMedTemp.setCognome((String)obj [7]);
			formMedTemp.setCodiceFiscale((String) obj[8]);
			formMedTemp.setEmail((String) obj[9]);
			formMedTemp.setPartitaIva((String) obj[10]);		

			formMedDto.add(formMedTemp);
		}

		response.put("result", formMedDto);
		return response;
	}
	
}
