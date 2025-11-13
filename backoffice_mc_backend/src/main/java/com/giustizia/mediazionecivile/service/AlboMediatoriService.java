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
import com.giustizia.mediazionecivile.model.AlboMediatori;
import com.giustizia.mediazionecivile.repository.AlboMediatoriRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;

@Service
public class AlboMediatoriService {
	
	@Autowired
	AlboMediatoriRepository alboMediatoriRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;

//	public AlboFormatori getFormatoreById(Long idAnagrafica){
//        return alboFormatoriRepository.findByIdProjection(idAnagrafica);        
//    }
	
	public List<AlboMediatori> getAllMediatori() {
		return alboMediatoriRepository.findAll();
	}

	
	public Page<AlboMediatori> getAllMediatoriPaged(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboMediatori> mediatoriElenco = alboMediatoriRepository.findAll(pageable);
        
        return mediatoriElenco;
    }

    public Page<AlboMediatori> getAllMediatoriPagedByNome(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboMediatori> mediatoriElenco = alboMediatoriRepository.getAllAlboMediatoriByNome(searchText, pageable);

        return mediatoriElenco;
    }
    
    public Page<AlboMediatori> getAllMediatoriPagedByCognome(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboMediatori> mediatoriElenco = alboMediatoriRepository.getAllAlboMediatoriByCognome(searchText, pageable);

        return mediatoriElenco;
    }
    
    public Page<AlboMediatori> getAllMediatoriPagedByCF(Pageable pageable, String searchText/*, String colonna*/) {
        Page<AlboMediatori> mediatoriElenco = alboMediatoriRepository.getAllAlboMediatoriByCF(searchText, pageable);

        return mediatoriElenco;
    }
    
    public Page<AlboMediatori> getAllMediatoriPagedByTipoForm(Pageable pageable, Long tipoForm) {
        Page<AlboMediatori> mediatoriElenco = alboMediatoriRepository.getAllAlboMediatoriByTipoMed(tipoForm, pageable);

        return mediatoriElenco;
    }
    
//    public List<Object[]> findByIdAlboFormatori(Long idAlboFormatori) {
//		return alboFormatoriRepository.findByIdAlboFormatori(idAlboFormatori);
//	}
    
    public HashMap<String, Object> findByIdAlboMediatori(Long idAlboMediatori) {
		HashMap<String, Object> response = new HashMap<>();
		List<Object[]> result = alboMediatoriRepository.findByIdAlboMediatori(idAlboMediatori);;

		List<FormatoreMediatoreDto> formMedDto = new ArrayList<FormatoreMediatoreDto>();
		for (Object[] obj : result) {
			FormatoreMediatoreDto formMedTemp = new FormatoreMediatoreDto();
			formMedTemp.setRom((Long) obj[0]);		
			formMedTemp.setDenominazioneOrganismo((String) obj[1]);
			formMedTemp.setSitoWeb((String) obj[2]);
			formMedTemp.setNaturaOrganismo((String) obj[3]);
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
