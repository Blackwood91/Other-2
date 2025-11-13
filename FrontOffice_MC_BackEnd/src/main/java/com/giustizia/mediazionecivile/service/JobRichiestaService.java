package com.giustizia.mediazionecivile.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.JobRichiesta;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.JobRichiestaRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;

@Service
public class JobRichiestaService {
	@Autowired
	private JobRichiestaRepository jobRichiestaRepository;
	@Autowired
	private StatoModuliRichiestaFigliRepository statoModuliFigliRepository;
	
	public boolean richiestaInviata(Long idRichiesta, String tipoRichiesta) {
		Optional<JobRichiesta> jobRichiesta =  jobRichiestaRepository.
				   findByIdRichiestaAndTipoRichiesta(idRichiesta, tipoRichiesta);
		
		Optional<StatoModuliRichiestaFigli> modulo =  statoModuliFigliRepository.
							   findByIdModuloAndIdRichiesta((long) 85, idRichiesta);
		
		if(modulo.isPresent() == false) {
			return false;
		}
		
		Object[] result = (Object[]) jobRichiestaRepository.getDataRichiestaIntegrazione(idRichiesta);
		if(result.length != 0) {
			Date dataRicIntBO = (Date) result[0];
			Date dataJobRic = jobRichiesta.get().getDataUltimoStato();
			
	        int comparisonResult = dataRicIntBO.compareTo(dataJobRic);
	
	        if(comparisonResult > 0) {
	        	return true;        	
	        } else {
	        	return false;
	        }
		}
		
		return false;
	}
	
	public String statusJobRichiesta(Long idRichiesta, String tipoRichiesta) {
		Optional<JobRichiesta> jobRichiesta =  jobRichiestaRepository.
				findFirstByIdRichiestaAndTipoRichiestaOrderByDataOraRichiestaDesc(idRichiesta, tipoRichiesta);
		
		// RICHIESTA NON ESISTENTE
		if(jobRichiesta.isPresent() == false) {
			return "N";
		}
		else {
			// RICHIESTA ESISTENTE MA IN ATTESA DI STATO
			if(jobRichiesta.get().getStatoJob() == null) {
				return "SN";
			}
			// RICHIESTA APERTA
			else if(jobRichiesta.get().getStatoJob().equalsIgnoreCase("aperta")) {
				return "A";
			}
			// RICHIESTA CHIUSA
			else if(jobRichiesta.get().getStatoJob().equalsIgnoreCase("chiusa")) {
				return "C";
			}
		}
		
		// CASISTICA NON POSSIBILE
		return "N";
		
	}
	

}
