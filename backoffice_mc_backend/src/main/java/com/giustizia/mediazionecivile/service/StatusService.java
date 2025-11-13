package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.ElencoCompOrgAmDto;
import com.giustizia.mediazionecivile.dto.ModuloAnnullatoRicInteDto;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.Sede;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.SedeRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;
import com.itextpdf.text.Document;

@Service
public class StatusService {
	@Autowired
	StatoModuliRichiestaFigliRepository statoModuliRichiestaFigliRepository;
	@Autowired
	AnagraficaOdmRepository anagraficaOdmRepository;
	@Autowired
	SedeRepository sedeRepository;
	@Autowired
	RichiesteRepository richiesteRepository;
	
	

	
	public boolean getModuloIsConvalidato(Long idModulo, Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiestaAndCompletato(idModulo, idRichiesta, 1);
	}
	
	public boolean getModuloIsValidato(Long idModulo, Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiestaAndValidato(idModulo, idRichiesta, 1);
	}
	
	public boolean getModuloIsAnnullato(Long idModulo, Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiestaAndAnnullato(idModulo, idRichiesta, 1);
	}
	
	public boolean getModuloIsConvalidatoAdPersonam(Long idModulo, Long idRichiesta, Long idAnagrafica) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiestaAndIdAnagraficaAndCompletato(idModulo, idRichiesta, idAnagrafica, 1);
	}
	
	public boolean getModuloIsConvalidatoAllRows(Long idRichiesta, Long idModulo) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta(idModulo, idRichiesta);
		
		for(StatoModuliRichiestaFigli modulo : statoModuliAllegati) {
			if(modulo.getCompletato() == null || modulo.getCompletato() == 0) 
				return false;
		}
		return true;
	}
	
	public boolean getModuloIsInteractedAllRows(Long idRichiesta, Long idModulo) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta(idModulo, idRichiesta);
		
		for(StatoModuliRichiestaFigli modulo : statoModuliAllegati) {
			if(modulo.getValidato() == null && modulo.getAnnullato() == null) 
				return false;
		}
		return true;
	}
	
	public boolean getModuloIsValidatoAllRows(Long idRichiesta, Long idModulo) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta(idModulo, idRichiesta);
		
		for(StatoModuliRichiestaFigli modulo : statoModuliAllegati) {
			if(modulo.getValidato() == null || modulo.getValidato() == 0) 
				return false;
		}
		return true;
	}
	
	public boolean getModuloIsValidatoAllRowsCertB(Long idRichiesta, Long idModulo) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriB(idRichiesta);
		
		for(AnagraficaOdm anag : anagrafiche) {
			List<StatoModuliRichiestaFigli> listaCertificati = statoModuliRichiestaFigliRepository
					.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, anag.getIdAnagrafica());
			for(StatoModuliRichiestaFigli modulo : listaCertificati) {
				if(modulo.getValidato() == null || modulo.getValidato() == 0) 
					return false;
			}

				List<StatoModuliRichiestaFigli> statoModuli = statoModuliRichiestaFigliRepository
						.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, anag.getIdAnagrafica());
				
				for(StatoModuliRichiestaFigli modulo : statoModuli) {
					if(modulo.getValidato() == null || modulo.getValidato() == 0) 
						return false;
				}
				return true;
			}

		return true;
	}
	
	public boolean getModuloIsValidatoAllRowsCertC(Long idRichiesta, Long idModulo) {
	List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriC(idRichiesta);
		
		for(AnagraficaOdm anag : anagrafiche) {
			List<StatoModuliRichiestaFigli> listaCertificati = statoModuliRichiestaFigliRepository
					.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, anag.getIdAnagrafica());
			for(StatoModuliRichiestaFigli modulo : listaCertificati) {
				if(modulo.getValidato() == null || modulo.getValidato() == 0) 
					return false;
			}

				List<StatoModuliRichiestaFigli> statoModuli = statoModuliRichiestaFigliRepository
						.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, anag.getIdAnagrafica());
				
				for(StatoModuliRichiestaFigli modulo : statoModuli) {
					if(modulo.getValidato() == null || modulo.getValidato() == 0) 
						return false;
				}
				return true;
			}

		return true;
	}
	
	
	public boolean isConvalidatoAllModuli(Long idRichiesta, Long idModulo) {
		int moduliTotConvalidati = statoModuliRichiestaFigliRepository.findAllByIdModuloAndIdRichiestaAndCompletato(idModulo, idRichiesta).size();
		int moduliTot = statoModuliRichiestaFigliRepository.findAllByIdModuloAndIdRichiesta(idModulo, idRichiesta).size();

		if(moduliTotConvalidati == 0 || moduliTotConvalidati < moduliTot)
			return false;
		else
			return true;
	}

	public String getStatusSedi(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long)68, (long) 69, (long) 70, idRichiesta);
		List<Sede> sedi = sedeRepository.findByIdRichiesta(idRichiesta);
		// Verifica se le sedi presenti nello StatoModuliRichiestaFigli, sono tutte convalidate
		if (sedi.size() >= 2 && statoModuliAllegati.size() >= (sedi.size() * 3)) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				if(moduloAlleg.getAnnullato() == (Integer) 1)
					return "a";
				else if(moduloAlleg.getValidato() == null || moduloAlleg.getValidato() == 0)
					return "";
			}
		} else {
			return "";
		}

		return "v";
	}

	public String getStatusAttoRODM(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long)68, (long) 69, (long) 70, idRichiesta);
		List<Sede> sedi = sedeRepository.findByIdRichiesta(idRichiesta);

		// Controlli per procedere alla convalidazione
		if (sedi.size() >= 2 && statoModuliAllegati.size() >= (sedi.size() * 3)) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				if(moduloAlleg.getAnnullato() == (Integer) 1)
					return "a";
				else if(moduloAlleg.getValidato() != (Integer) 1)
					return "";
			}
		} else {
			return "";
		}

		// check parametri idRichiesta?
		if (statoModulo.isPresent()) {
			if (statoModulo.get().getValidato() != (Integer) 1) {
				return "";
			} else {
				return "v";
			}
		} else {
			return "";
		}
	}
	
	public boolean getStatusElencoMediatori(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
											.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);
	
		if(statoModulo.isPresent() == false || statoModulo.get().getCompletato() != (Integer) 1) {
			return false;
		}
		else {
			return true;
		}

	}
	
	public boolean getStatusElencoMediatoriFinalizzaBO(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
											.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);
	
		if(statoModulo.isPresent() == false || (statoModulo.get().getValidato() == null && statoModulo.get().getAnnullato() == null)) {
			return false;
		}
		else {
			return true;
		}

	}
	
	public boolean getStatusElencoMediatoriValidatoPerPdgBO(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
											.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);
	
		if(statoModulo.isPresent() == false || (statoModulo.get().getValidato() != (Integer) 1)) {
			return false;
		}
		else {
			return true;
		}

	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean getStatusRapLegOrRespOrOrgAm(Long idRichiesta, Long idAnagrafica, boolean isCloneAnagraficaRapLegale) {
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);
		

		if(moduloScheda.isPresent() == false || moduloScheda.get().getCompletato() != (Integer) 1) {
			return false;
		}			
		
		// CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(isCloneAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloDocumentoRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
	
			if(moduloDocumentoRapL.isPresent() == false || moduloDocumentoRapL.get().getCompletato() != (Integer) 1 || 
				moduloSchedaRapL.isPresent() == false || moduloSchedaRapL.get().getCompletato() != (Integer) 1) {
				return false;
			}	
		}
		
		if(moduloDocumento.isPresent() == false || moduloDocumento.get().getCompletato() != (Integer) 1|| 
			moduloQualificaMed.isPresent() == false || moduloQualificaMed.get().getCompletato() != (Integer) 1) {
			return false;
		}	
		
		return true;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean getStatusCompOrgAm(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);
		
		if(moduloScheda.isPresent() == false || moduloScheda.get().getCompletato() != (Integer) 1|| 
		   moduloDocumento.isPresent() == false || moduloDocumento.get().getCompletato() != (Integer) 1) {
			return false;
		}	
		
		return true;
	}
	
	public boolean getStatusReqOno(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, idAnagrafica);
		
		if(moduloAttoReqOnora.isPresent() == false || moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
			return false;
		}
		
		// CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(convalidaAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnoraRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagraficaLeg.get().getIdAnagrafica());

			if(moduloAttoReqOnoraRapL.isPresent() == false || moduloAttoReqOnoraRapL.get().getCompletato() != (Integer) 1) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean getStatusReqOnoCompOrgAm(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, idAnagrafica);
		
		if(moduloAttoReqOnora.isPresent() == false || moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
			return false;
		}
		
		return true;
	}
	
	
	public String getStatusElencoRapLRespOrg(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForRapLAndOrg(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		String stato = "v";

		
		if(moduloElenco.get().getAnnullato() == (Integer) 1) {
			return "a";
		}
		else if(moduloElenco.get().getValidato() != (Integer) 1) {
			stato = "";
		}
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloSchedaRap = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository 
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloSchedaRap.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloSchedaRap.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			
			if(anagrafica.getIdQualifica() != 1) {
				if(moduloQualificaMed.get().getAnnullato() == (Integer) 1) {
					return "a";
				}
				else if(moduloQualificaMed.get().getValidato() != (Integer) 1) {
					stato = "";
				}
			}
			
		}

		// SE ARRIVERA ALLA FINE VUOL DIRE CHE L'ESITO E' VALIDATO O SENZA NESSUN ESITO
		if(stato.isEmpty()) {
			return "";
		}
		else {
			return "v";
		}
	}
	
	public String getStatusElenCompOrgAm(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForCompOrgAm(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 83, idRichiesta);
		String stato = "v";
		
		if(moduloElenco.get().getAnnullato() == (Integer) 1) {
			return "a";
		}
		else if(moduloElenco.get().getValidato() != (Integer) 1) {
			stato = "";
		}
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloScheda.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1) {
				stato = "";
			}	
		}
		
		// SE ARRIVERA ALLA FINE VUOL DIRE CHE L'ESITO E' VALIDATO O SENZA NESSUN ESITO
		if(stato.isEmpty()) {
			return "";
		}
		else {
			return "v";
		}
	}
	
	public String getStatusPrestatoreServizio(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, idAnagrafica);		
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);		
		
		if(moduloScheda.get().getAnnullato() == (Integer) 1 || moduloDocumento.get().getAnnullato() == (Integer) 1)
			return "a";
		else if(moduloScheda.get().getValidato() == null || moduloScheda.get().getValidato() == 0 ||
				moduloDocumento.get().getValidato() == null || moduloDocumento.get().getValidato() == 0)
			return "";
		
		return "v";
	}
	
	public String getStatusElencoMedGen(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriA(idRichiesta);	
		String stato = "v";
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 39, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloScheda.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1) {
				stato = "";
			}
		}

		// SE ARRIVERA ALLA FINE VUOL DIRE CHE L'ESITO E' VALIDATO O SENZA NESSUN ESITO
		if(stato.isEmpty()) {
			return "";
		}
		else {
			return "v";
		}
	}
	
	public String getStatusElencoMedInt(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriB(idRichiesta);	
		String stato = "v";
	
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 44, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloScheda.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1) {
				stato = "";
			}
		}

		// SE ARRIVERA ALLA FINE VUOL DIRE CHE L'ESITO E' VALIDATO O SENZA NESSUN ESITO
		if(stato.isEmpty()) {
			return "";
		}
		else {
			return "v";
		}
	}
	
	public String getStatusElencoMedCons(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriC(idRichiesta);	
		String stato = "v";
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 53, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1) {
				return "a";
			}
			else if(moduloScheda.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1) {
				stato = "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1) {
				stato = "";
			}
		}

		// SE ARRIVERA ALLA FINE VUOL DIRE CHE L'ESITO E' VALIDATO O SENZA NESSUN ESITO
		if(stato.isEmpty()) {
			return "";
		}
		else {
			return "v";
		}
	}

	public List<StatoModuliRichiestaFigli> getAllModuloConvalidato(Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.findAllDistinctByIdModuloAndIdRichiesta(idRichiesta);
	}
	
	public List<ModuloAnnullatoRicInteDto> getAllModuloAnnulato(Long idRichiesta) {
		List<Object[]> result = statoModuliRichiestaFigliRepository.findAllForRichiestaIntegrazione(idRichiesta);
        List<ModuloAnnullatoRicInteDto> moduli = new ArrayList<>();
        for (Object[] obj : result) {
        	ModuloAnnullatoRicInteDto moduloAnnullato = new ModuloAnnullatoRicInteDto();
        	moduloAnnullato.setProgressivoModulo((Long) obj[0]);
        	moduloAnnullato.setIdModulo((Long) obj[1]);
        	moduloAnnullato.setCompletato((Integer) obj[2]);
        	moduloAnnullato.setValidato((Integer) obj[3]);
        	moduloAnnullato.setAnnullato((Integer) obj[4]);
        	moduloAnnullato.setDescrizioneModulo((String) obj[5]);
        	moduloAnnullato.setNome((String) obj[6]);
        	moduloAnnullato.setCognome((String) obj[7]);
        	moduloAnnullato.setIndirizzo((String) obj[8]);
        	moduloAnnullato.setNumeroCivico((String) obj[9]);
        	moduloAnnullato.setNomeComune((String) obj[10]);
        	moduloAnnullato.setSiglaProvincia((String) obj[11]);
        	moduloAnnullato.setNomeFile((String) obj[12]);
        	moduli.add(moduloAnnullato);
        }
		
		return moduli;
	}
		
	@Transactional(rollbackFor = Exception.class)
	public String isModuliCompletatoElencoMediatoriErrore(Long idRichiesta) {
		String message = "Ci sono dati non sono convalidati in: ";
		
		// SCHEDE ANAGRAFICA A, 1-by-1
//		List<StatoModuliRichiestaFigli> statoModuliSchedeA = statoModuliRichiestaFigliRepository
//															.findAllByIdModuloAndIdRichiesta((long) 38, idRichiesta);
//		
//		for(StatoModuliRichiestaFigli statoModulo : statoModuliSchedeA) {
//			if(getModuloIsConvalidatoAdPersonam((long) 38, idRichiesta, statoModulo.getIdAnagrafica()) == false)
//				message += "schede anagrafiche";
//		}
		
		// MEDIATORI GENERICI
		if(isConvalidatoAllModuli(idRichiesta, (long) 38))
			message += "<br>schede anagrafiche di Mediatori Generici";
		if(isConvalidatoAllModuli(idRichiesta, (long) 39))
			message += "<br>autocertificazione dei requisiti di onorabilità di Mediatori Generici";
		if(isConvalidatoAllModuli(idRichiesta, (long) 40))
			message += "<br>disponibilità di Mediatori Generici";
		if(isConvalidatoAllModuli(idRichiesta, (long) 41))
			message += "<br>dichiarazione sostitutiva di Mediatori Generici";
		if(isConvalidatoAllModuli(idRichiesta, (long) 42))
			message += "<br>documenti di identità di Mediatori Generici";
		if(isConvalidatoAllModuli(idRichiesta, (long) 75))
			message += "<br>ulteriori requisiti di Mediatori Generici";
		
		//MEDIATORI INTERNAZIONALI
		if(isConvalidatoAllModuli(idRichiesta, (long) 43))
			message += "<br>schede anagrafiche di Mediatori Internazioni";
		if(isConvalidatoAllModuli(idRichiesta, (long) 44))
			message += "<br>autocertificazione dei requisiti di onorabilità di Mediatori Internazioni";
		if(isConvalidatoAllModuli(idRichiesta, (long) 45))
			message += "<br>disponibilità di Mediatori Internazioni";
		if(isConvalidatoAllModuli(idRichiesta, (long) 46))
			message += "<br>dichiarazione sostitutiva di Mediatori Internazioni";
		if(isConvalidatoAllModuli(idRichiesta, (long) 47))
			message += "<br>documenti di identità di Mediatori Internazioni";	
		if(isConvalidatoAllModuli(idRichiesta, (long) 77))
			message += "<br>certificazione di formazione iniziale di Mediatori Internazioni";
		if(isConvalidatoAllModuli(idRichiesta, (long) 78))
			message += "<br>certificazione sigli ulteriori requisiti di Mediatori Internazioni";
		if(isConvalidatoAllModuli(idRichiesta, (long) 50))
			message += "<br>certificazione linguistica di Mediatori Internazioni";
		
		//MEDIATORI DI CONSUMO
		if(isConvalidatoAllModuli(idRichiesta, (long) 52))
			message += "<br>schede anagrafiche di Mediatori di Consumo";
		if(isConvalidatoAllModuli(idRichiesta, (long) 53))
			message += "<br>autocertificazione dei requisiti di onorabilità di Mediatori di Consumo";
		if(isConvalidatoAllModuli(idRichiesta, (long) 54))
			message += "<br>disponibilità di Mediatori di Consumo";
		if(isConvalidatoAllModuli(idRichiesta, (long) 55))
			message += "<br>dichiarazione sostitutiva di Mediatori di Consumo";
		if(isConvalidatoAllModuli(idRichiesta, (long) 56))
			message += "<br>documenti di identità di Mediatori di Consumo";
		if(isConvalidatoAllModuli(idRichiesta, (long) 80))
			message += "<br>certificazione di formazione iniziale di Mediatori di Consumo";
		if(isConvalidatoAllModuli(idRichiesta, (long) 81))
			message += "<br>certificazione sigli ulteriori requisiti di Mediatori di Consumo";
		if(isConvalidatoAllModuli(idRichiesta, (long) 82))
			message += "<br>certificazione linguistica di Mediatori di Consumo";
		
		return message;

	}
	
	@Transactional(rollbackFor = Exception.class)
	private boolean isModuliCompletatoElencoPrestatoriEsito(Long idRichiesta) {
		
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 35) == false)
			return false;
		else if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 36) == false)
			return false;
		else if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 37) == false)
			return false;
		else
			return true;
	}
	
	@Transactional(rollbackFor = Exception.class)
	private boolean isModuliInteractedElencoPrestatoriEsito(Long idRichiesta) {
		
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 35) == false)
			return false;
		else if(getModuloIsInteractedAllRows(idRichiesta, (long) 36) == false)
			return false;
		else if(getModuloIsInteractedAllRows(idRichiesta, (long) 37) == false)
			return false;
		else
			return true;
	}
	
	@Transactional(rollbackFor = Exception.class)
	private boolean isModuliValidatoElencoPrestatoriEsito(Long idRichiesta) {
		
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 35) == false)
			return false;
		else if(getModuloIsValidatoAllRows(idRichiesta, (long) 36) == false)
			return false;
		else if(getModuloIsValidatoAllRows(idRichiesta, (long) 37) == false)
			return false;
		else
			return true;
	}
	
	@Transactional(rollbackFor = Exception.class)
	private boolean isModuliCompletatoElencoMediatoriEsito(Long idRichiesta) {
		
		// MEDIATORI GENERICI
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 38) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 39) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 40) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 41) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 42) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 75) == false)
			return false;
		
		//MEDIATORI INTERNAZIONALI
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 43) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 44) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 45) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 46) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 47) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 77) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 78) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 50) == false)
			return false;
			
		//MEDIATORI DI CONSUMO
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 52) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 53) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 54) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 55) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 56) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 80) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 81) == false)
			return false;
		if(getModuloIsConvalidatoAllRows(idRichiesta, (long) 82) == false)
			return false;
		
		return true;

	}
	
	
	@Transactional(rollbackFor = Exception.class)
	private boolean isModuliInteractedElencoMediatoriEsito(Long idRichiesta) {
		
		// MEDIATORI GENERICI
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 38) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 39) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 40) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 41) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 42) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 75) == false)
			return false;
		
		//MEDIATORI INTERNAZIONALI
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 43) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 44) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 45) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 46) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 47) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 77) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 78) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 50) == false)
			return false;
			
		//MEDIATORI DI CONSUMO
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 52) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 53) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 54) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 55) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 56) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 80) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 81) == false)
			return false;
		if(getModuloIsInteractedAllRows(idRichiesta, (long) 82) == false)
			return false;
		
		return true;

	}
	
	
	@Transactional(rollbackFor = Exception.class)
	private boolean isModuliValidatoElencoMediatoriEsito(Long idRichiesta) {
		
		// MEDIATORI GENERICI
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 38) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 39) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 40) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 41) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 42) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 75) == false)
			return false;
		
		//MEDIATORI INTERNAZIONALI
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 43) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 44) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 45) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 46) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 47) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 77) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 78) == false)
			return false;
		if(getModuloIsValidatoAllRowsCertB(idRichiesta, (long) 50) == false)
			return false;
			
		//MEDIATORI DI CONSUMO
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 52) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 53) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 54) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 55) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 56) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 80) == false)
			return false;
		if(getModuloIsValidatoAllRows(idRichiesta, (long) 81) == false)
			return false;
		if(getModuloIsValidatoAllRowsCertC(idRichiesta, (long) 82) == false)
			return false;
		
		return true;

	}
	
	
	public String getStatusModuloAppendici(Long idRichiesta, Long idModulo) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta(idModulo, idRichiesta);
		
		
		if((idModulo == (long)50 || (long) idModulo == 82) && statoModuliAllegati.size() == 0) {
			return "v";
		}
		if(statoModuliAllegati.size() != 0) {
			for(StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				if(moduloAlleg.getAnnullato() == (Integer) 1)
					return "a";
				else if(moduloAlleg.getValidato() != (Integer) 1)
					return "";
			}
			
			// SE IL METODO NON VERRA BLOCCATO VUOL DIRE CHE I MODULI SONO TUTTI CONVALIDATI
			return "v";
		}
		else {
			return "";
		}
	}
	
	public String getStatusAppendiceA(Long idRichiesta) {		
		ArrayList<Long> moduliA = 
				new ArrayList<Long>(
						Arrays.asList(
								(long) 38, (long) 39, (long) 40, (long) 41, (long) 42, (long) 75));
		String stato = "v";
		
		for(Long modulo : moduliA) {
			String statusModulo = getStatusModuloAppendici(idRichiesta, modulo);
			if(statusModulo.equalsIgnoreCase("a"))
				return "a";
			else if(statusModulo.equalsIgnoreCase("v") == false)
				stato = "";
		}
		
		// SE IL METODO NON VERRA BLOCCATO VUOL DIRE CHE NESSUNO MODULO E' STATO ANNULLATO, PERO' POTREBBE NON ESSERE VALIDATO
		if(stato.isEmpty())
			return "";
		else
			return "v";
	}
	
	public String getStatusAppendiceB(Long idRichiesta) {		
		ArrayList<Long> moduliB = 
				new ArrayList<Long>(
						Arrays.asList(
								(long) 43, (long) 44, (long) 45, (long) 46, (long) 47, (long) 77, (long) 78, (long) 50));
		String stato = "v";
		

		for(Long modulo : moduliB) {
			String statusModulo = getStatusModuloAppendici(idRichiesta, modulo);
			if(statusModulo.equalsIgnoreCase("a"))
				return "a";
			else if(statusModulo.equalsIgnoreCase("v") == false)
				stato = "";
		}
		

		// SE E' C O V LO STATUS DELL'INSIEME DEI MODULI SARA' COMPLETATO
	
		if(stato.isEmpty())
			return "";
		else
			return "v";
	}

	

	
	public String getStatusAppendiceC(Long idRichiesta) {		
		ArrayList<Long> moduliC = 
				new ArrayList<Long>(
						Arrays.asList(
								(long) 52, (long) 53, (long) 54, (long) 55, (long) 56, (long) 80, (long) 81, (long) 82));
		String stato = "v";		

		for(Long modulo : moduliC) {
			String statusModulo = getStatusModuloAppendici(idRichiesta, modulo);
			if(statusModulo.equalsIgnoreCase("a"))
				return "a";
			else if(statusModulo.equalsIgnoreCase("v") == false)
				stato = "";
		}
		
		if(stato.isEmpty())
			return "";
		else
			return "v";
	}
	
	public String getStatusAppendiceD(Long idRichiesta) {		
		ArrayList<Long> moduliD = 
				new ArrayList<Long>(
						Arrays.asList(
								(long) 27, (long) 28, (long) 29, (long) 30, (long) 31, (long) 32, (long) 33, (long) 83));
		
		boolean isValidato = true;
		for(Long modulo : moduliD) {
			String statusModulo = getStatusModuloAppendici(idRichiesta, modulo);
			if(statusModulo.equalsIgnoreCase("a"))
				return "a";
			else if(statusModulo.equalsIgnoreCase("v") == false)
				isValidato = false;
		}

		// SE IL METODO NON VERRA BLOCCATO VUOL DIRE CHE NESSUNO MODULO E' STATO ANNULLATO, PERO' POTREBBE NON ESSERE VALIDATO
		if(isValidato)
			return "v";
		else
			return "";
	}
	
	public boolean finalizza(Long idRichiesta) {
		boolean esito = true;
		
		//MODULO DOMANDA
		if(getModuloIsConvalidato((long) 1, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 3, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 4, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 5, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 16, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 73, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 17, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 23, idRichiesta))
			esito = false;
		if(getStatusElencoMediatori(idRichiesta))
			esito = false;
		
		//APPENDICE D
		if(getModuloIsConvalidato((long) 27, idRichiesta)) 
			esito = false;
		if(getModuloIsConvalidato((long) 83,idRichiesta))
			esito = false;
		
		//PRESTATORI
		if(isModuliCompletatoElencoPrestatoriEsito(idRichiesta))
			esito = false;
		
		//MODULO POLIZZA
		if(getModuloIsConvalidato((long) 58, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 59, idRichiesta))
			esito = false;
		
		//MODULI NON AUTOMOMI
		if(getModuloIsConvalidato((long) 71, idRichiesta))
			esito = false;
		if(getModuloIsConvalidato((long) 72, idRichiesta))
			esito = false;
				
		//APPENDICI
		if(isModuliCompletatoElencoMediatoriEsito(idRichiesta))
			esito = false;
		
		return esito;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneDomandaDiIscrizione(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 1, idRichiesta);
		
		statoModulo.get().setAnnullato(0);
		statoModulo.get().setValidato(1);
		statoModuliRichiestaFigliRepository.save(statoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void validazioneSediAttoRiepilogativo(Long idRichiesta) {//Convalidare anche sedi
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long) 68, (long) 69, (long) 70, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		} 
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaSedeAttoRiepilogativo(Long idRichiesta, Long idSede) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiestaAndIdRiferimento((long) 68, (long) 69, (long) 70, idRichiesta, idSede);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneOnorabilitaAppendici(Long idRichiesta, Long idModulo, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> moduli = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : moduli) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void validazioneAttoRiepilogativoODM(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		
		/* List<StatoModuliRichiestaFigli> sediModuli = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long) 68, (long) 69, (long) 70, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (sediModuli != null && sediModuli.size() >= 2) {
			for (StatoModuliRichiestaFigli moduloS : sediModuli) {
				if (moduloS.getValidato() != (Integer) 1) {
					throw new RuntimeException(
							"-ErrorInfo Bisogna prima convalidare tutte le sedi inserite per convalidare l'atto");
				}
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo Bisogna inserire almeno una sede legale e una operativa per convalidare l'atto");
		}*/

		// check parametri idRichiesta?
		if (statoModulo.isPresent()) {
			statoModulo.get().setAnnullato(0);
			statoModulo.get().setValidato(1);
			statoModuliRichiestaFigliRepository.save(statoModulo.get());
			
			// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 3);
			}
		} else {
			throw new RuntimeException("-ErrorInfo Non è stata trovato nessun modulo associato");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void validazioneAttoCostitutivoODM(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 4, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void validazioneStatutoOrganismo(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 5, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneAttoCostitutivoODMNA(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 71, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void validazioneStatutoOrganismoNA(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 72, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void validazioneRegolamentoProcedura(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 16, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void validazioneSpeseMediazione(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 73, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void validazioneCodiceEtico(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 17, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneBilancioCertificaizoneBancaria(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 23, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneRequisitiOnorabilita(Long idRichiesta, Long idAnagrafica, boolean isCloneAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, idAnagrafica);
		
		// CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(isCloneAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnoraRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagraficaLeg.get().getIdAnagrafica());

			moduloAttoReqOnoraRapL.get().setAnnullato(0);			
			moduloAttoReqOnoraRapL.get().setValidato(1);			
			statoModuliRichiestaFigliRepository.save(moduloAttoReqOnoraRapL.get());
		}
		
		moduloAttoReqOnora.get().setAnnullato(0);			
		moduloAttoReqOnora.get().setValidato(1);
		statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneRequisitiOnorabilitaCompOrgAm(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, idAnagrafica);

		moduloAttoReqOnora.get().setAnnullato(0);
		moduloAttoReqOnora.get().setValidato(1);
		statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneAttoCostNonAutonomo(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 71, idRichiesta);

		modulo.get().setAnnullato(0);
		modulo.get().setValidato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneStatutoOrgNonAutonomo(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 72, idRichiesta);

		modulo.get().setAnnullato(0);
		modulo.get().setValidato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneDichiaPolizzaAss(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 58, idRichiesta);

		modulo.get().setAnnullato(0);
		modulo.get().setValidato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazionePolizzaAssicurativa(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 59, idRichiesta);

		modulo.get().setAnnullato(0);
		modulo.get().setValidato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneRapLegAndRespOrg(Long idRichiesta, Long idAnagrafica, boolean isCloneAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, idAnagrafica); 
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);
	
		// CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(isCloneAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumentoRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagraficaLeg.get().getIdAnagrafica());

			moduloSchedaRapL.get().setAnnullato(0);
			moduloDocumentoRapL.get().setAnnullato(0);
			moduloDocumentoRapL.get().setValidato(1);
			moduloSchedaRapL.get().setValidato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloDocumentoRapL.get());
			statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
		}
		
		// CASISTICA POSSIBILE IN CASO DI RAP LEGALE NON CORRISPONDENTE A REASPONSABILE DELL'ORGANISMO
		if(moduloQualificaMed.isPresent()) {
			moduloQualificaMed.get().setAnnullato(0);
			moduloQualificaMed.get().setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
		}
		
		moduloDocumento.get().setAnnullato(0);
		moduloScheda.get().setAnnullato(0);
		moduloDocumento.get().setValidato(1);
		moduloScheda.get().setValidato(1);
		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneMediatore(Long idRichiesta, Long idAnagrafica) {
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloScheda = Optional.empty();
		Optional<StatoModuliRichiestaFigli> moduloDocumento = Optional.empty();
		
		if(anagrafica.get().getIdTipoAnagrafica() == 4) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, idAnagrafica);

			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 5) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, idAnagrafica);
			
			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 6) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, idAnagrafica);

			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneElencoRapLegAndRespOrg(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForRapLAndOrg(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElencoRLAndRO = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloSchedaRap = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagrafica.getIdAnagrafica());
		//	Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
		//			.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository 
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloSchedaRap.isPresent()) {
				moduloSchedaRap.get().setAnnullato(0);
				moduloSchedaRap.get().setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloSchedaRap.get());
			}
			
		/*	if(moduloAttoReqOnora.isPresent()) {
				moduloAttoReqOnora.get().setAnnullato(0);
				moduloAttoReqOnora.get().setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
			} */
			
			if(moduloDocumento.isPresent()) {
				moduloDocumento.get().setAnnullato(0);
				moduloDocumento.get().setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
			}	
			
			if(anagrafica.getIdQualifica() == 2) {
				if(moduloQualificaMed.isPresent()) {
					moduloQualificaMed.get().setAnnullato(0);
					moduloQualificaMed.get().setValidato(1);
					statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
				}
			}
			
		}
		
		if(moduloElencoRLAndRO.isPresent()) {
			moduloElencoRLAndRO.get().setAnnullato(0);
			moduloElencoRLAndRO.get().setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloElencoRLAndRO.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneElencoCompOrgAmAndCompSoc(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForCompOrgAm(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 83, idRichiesta);
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, anagrafica.getIdAnagrafica());
			//Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				//	.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, anagrafica.getIdAnagrafica());
			
			/*if(moduloAttoReqOnora.isPresent()) {
				moduloAttoReqOnora.get().setAnnullato(0);
				moduloAttoReqOnora.get().setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
			}*/
			
			if(moduloDocumento.isPresent()) {
				moduloDocumento.get().setAnnullato(0);
				moduloDocumento.get().setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
			}	
			
			if(moduloScheda.isPresent()) {
				moduloScheda.get().setAnnullato(0);
				moduloScheda.get().setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			}
			
		}
		
		if(moduloElenco.isPresent()) {
			moduloElenco.get().setAnnullato(0);
			moduloElenco.get().setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());	
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneCompOrgAmAndCompSoc(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);
		
		moduloScheda.get().setAnnullato(0);
		moduloDocumento.get().setAnnullato(0);
		moduloScheda.get().setValidato(1);
		moduloDocumento.get().setValidato(1);

		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneElencoPrestServizio(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 3);
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 35, idRichiesta);	
		
		for(AnagraficaOdm anagrafica : anagrafiche) {
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, anagrafica.getIdAnagrafica());		
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, anagrafica.getIdAnagrafica());		
				
			if(moduloScheda.isPresent()) {
				moduloScheda.get().setAnnullato(0);
				moduloScheda.get().setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			}

			if(moduloDocumento.isPresent()) {
				moduloDocumento.get().setAnnullato(0);
				moduloDocumento.get().setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
			}

		}

		if(moduloElenco.isPresent()) {
			moduloElenco.get().setAnnullato(0);
			moduloElenco.get().setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazionePrestatoreServizio(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 35, idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, idAnagrafica);		
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);		
		
		moduloScheda.get().setAnnullato(0);;
		moduloScheda.get().setValidato(1);
		moduloDocumento.get().setAnnullato(0);;
		moduloDocumento.get().setValidato(1);

		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		if(moduloElenco.isPresent()) {
			moduloElenco.get().setAnnullato(0);
			moduloElenco.get().setCompletato(1);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	public void annullaDomandaDiIscrizione(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 1, idRichiesta);
		
		if (statoModulo.isPresent()) {
			statoModulo.get().setValidato(0);
			statoModulo.get().setCompletato(0);
			statoModulo.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(statoModulo.get());
		}
		
		if(richiesta.get().getIdStato() != 3) {
			richiesta.get().setIdStato((long) 3);
			richiesteRepository.save(richiesta.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaValidazioneRapAndRespOrg(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		
		Optional<StatoModuliRichiestaFigli> moduloElencoRLAndRO = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);
		
		if(anagrafica.get().getIdQualifica() == 1) {
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, idAnagrafica);
			moduloSchedaRapL.get().setValidato(0);
			moduloSchedaRapL.get().setCompletato(0);
			moduloSchedaRapL.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
		}
		
		if(moduloElencoRLAndRO.isPresent() && moduloElencoRLAndRO.get().getValidato() == (Integer) 1) {
			moduloElencoRLAndRO.get().setValidato(0);
			moduloElencoRLAndRO.get().setCompletato(0);
			moduloElencoRLAndRO.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloElencoRLAndRO.get());
		}
		
		if(moduloDocumento.isPresent() && moduloDocumento.get().getValidato() == (Integer) 1) {
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		if(moduloQualificaMed.isPresent() && moduloQualificaMed.get().getValidato() == (Integer) 1) {
			moduloQualificaMed.get().setValidato(0);
			moduloQualificaMed.get().setCompletato(0);
			moduloQualificaMed.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
		}
		
		// ANNULLA CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(convalidaAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloDocumentoRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			
			if(moduloDocumentoRapL.get().getValidato() == (Integer) 1) {
				moduloDocumentoRapL.get().setValidato(0);
				moduloDocumentoRapL.get().setCompletato(0);
				moduloDocumentoRapL.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloDocumentoRapL.get());
			}
			
			if(moduloSchedaRapL.get().getValidato() == (Integer) 1) {
				moduloSchedaRapL.get().setValidato(0);
				moduloSchedaRapL.get().setCompletato(0);
				moduloSchedaRapL.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
			}
			
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaValidazioneAttoRiepilogativoODM(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);

		if (statoModulo.isPresent() && statoModulo.get().getValidato() == (Integer) 1) {
			statoModulo.get().setValidato(0);
			statoModulo.get().setCompletato(0);
			statoModulo.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(statoModulo.get());
		} 
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaValidazioneCompOrgAmAndCompSoc(Long idRichiesta, Long idAnagrafica) {		
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 83, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);

		if(moduloElenco.isPresent() && moduloElenco.get().getValidato() == (Integer) 1) {
			moduloElenco.get().setValidato(0);
			moduloElenco.get().setCompletato(0);
			moduloElenco.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
		
		if(moduloScheda.isPresent() && moduloScheda.get().getValidato() == (Integer) 1) {
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		}
		
		if(moduloAttoReqOnora.isPresent() && moduloAttoReqOnora.get().getValidato() == (Integer) 1) {
			moduloAttoReqOnora.get().setValidato(0);
			moduloAttoReqOnora.get().setCompletato(0);
			moduloAttoReqOnora.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		}
		
		if(moduloDocumento.isPresent() && moduloDocumento.get().getValidato() == (Integer) 1) {
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	/*********************************************************************************************************************/
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneDisponibilitaA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 40, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneDisponibilitaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 45, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneDisponibilitaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 54, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneFormazioneInizialeA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 41, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneFormazioneInizialeB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 46, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneFormazioneInizialeC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 55, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneCertificazioneB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 50, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneCertificazioneC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 82, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneFormazioneSpecificaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 77, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setAnnullato(0);
				moduloAlleg.setValidato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo Bisogna inserire almeno un file nell'atto costitutivo per proseguire");
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneFormazioneSpecificaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 80, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneUlterioriRequisitiA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 75, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneUlterioriRequisitiB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 78, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneUlterioriRequisitiC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 81, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setAnnullato(0);
			moduloAlleg.setValidato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneElencoMediatoreA(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);	
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, anagrafica.getIdAnagrafica());
			
			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneElencoMediatoreB(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);	
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, anagrafica.getIdAnagrafica());
			
			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validazioneElencoMediatoreC(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);	
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, anagrafica.getIdAnagrafica());
			
			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void annullaSediAttoRiepilogativo(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> moduloAttoRiep = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long) 68, (long) 69, (long) 70, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0); 
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// ANNULAZIONE ANCHE DELL'ATTO RIEPILOGATIVO ESSENDO DIPEDENTE ANCHE DA ESSO
		if(moduloAttoRiep.isPresent()) {
			moduloAttoRiep.get().setValidato(0); 
			moduloAttoRiep.get().setCompletato(0);
			moduloAttoRiep.get().setAnnullato(1);
		}
		
		statoModuliRichiestaFigliRepository.save(moduloAttoRiep.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaSedeAttoRiepilogativo(Long idRichiesta, Long idSede) {
		Optional<StatoModuliRichiestaFigli> moduloAttoRiep = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiestaAndIdRiferimento((long) 68, (long) 69, (long) 70, idRichiesta, idSede);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0); 
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// ANNULAZIONE ANCHE DELL'ATTO RIEPILOGATIVO ESSENDO DIPEDENTE ANCHE DA ESSO
		if(moduloAttoRiep.isPresent()) {
			moduloAttoRiep.get().setValidato(0); 
			moduloAttoRiep.get().setCompletato(0);
			moduloAttoRiep.get().setAnnullato(1);
		}
		
		statoModuliRichiestaFigliRepository.save(moduloAttoRiep.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaOnorabilitaAppendici(Long idRichiesta, Long idModulo, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> moduli = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
			for (StatoModuliRichiestaFigli moduloAlleg : moduli) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

	    if(elencoMediatori.isPresent()) {
	    	elencoMediatori.get().setValidato(0);
	    	elencoMediatori.get().setCompletato(0);
	    	elencoMediatori.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
	    }
	    
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void annullaAttoRiepilogativoODM(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		
		statoModulo.get().setValidato(0);
		statoModulo.get().setCompletato(0);
		statoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(statoModulo.get());

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	
		/*List<StatoModuliRichiestaFigli> sediModuli = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long) 68, (long) 69, (long) 70, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (sediModuli != null && sediModuli.size() >= 2) {
			for (StatoModuliRichiestaFigli moduloS : sediModuli) {
				if (moduloS.getValidato() != (Integer)0) {
					throw new RuntimeException(
							"-ErrorInfo Bisogna prima convalidare tutte le sedi inserite per convalidare l'atto");
				}
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo Bisogna inserire almeno una sede legale e una operativa per convalidare l'atto");
		}*/

		// check parametri idRichiesta?
	}

	@Transactional(rollbackFor = Exception.class)
	public void annullaAttoCostitutivoODM(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 4, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void annullaStatutoOrganismo(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 5, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAttoCostitutivoODMNA(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 71, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void annullaStatutoOrganismoNA(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 72, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void annullaRegolamentoProcedura(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 16, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void annullaSpeseMediazione(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 73, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void annullaCodiceEtico(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 17, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaBilancioCertificaizoneBancaria(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 23, idRichiesta);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaRequisitiOnorabilita(Long idRichiesta, Long idAnagrafica, boolean isCloneAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, idAnagrafica);
		
		// CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(isCloneAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnoraRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagraficaLeg.get().getIdAnagrafica());

			moduloAttoReqOnoraRapL.get().setValidato(0);
			moduloAttoReqOnoraRapL.get().setCompletato(0);
			moduloAttoReqOnoraRapL.get().setAnnullato(1);			
			statoModuliRichiestaFigliRepository.save(moduloAttoReqOnoraRapL.get());
		}
		
		moduloAttoReqOnora.get().setValidato(0);
		moduloAttoReqOnora.get().setCompletato(0);
		moduloAttoReqOnora.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaRequisitiOnorabilitaCompOrgAm(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, idAnagrafica);
		
		moduloAttoReqOnora.get().setValidato(0);
		moduloAttoReqOnora.get().setCompletato(0);
		moduloAttoReqOnora.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAttoCostNonAutonomo(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 71, idRichiesta);

		modulo.get().setValidato(0);
		modulo.get().setCompletato(0);
		modulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaStatutoOrgNonAutonomo(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 72, idRichiesta);

		modulo.get().setValidato(0);
		modulo.get().setCompletato(0);
		modulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaDichiaPolizzaAss(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> moduloAttoRiep = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 58, idRichiesta);

		modulo.get().setValidato(0); 
		modulo.get().setCompletato(0);
		modulo.get().setAnnullato(1);
		
		// ANNULAZIONE ANCHE DELL'ATTO RIEPILOGATIVO ESSENDO DIPEDENTE ANCHE DA ESSO
		if(moduloAttoRiep.isPresent()) {
			moduloAttoRiep.get().setValidato(0); 
			moduloAttoRiep.get().setCompletato(0);
			moduloAttoRiep.get().setAnnullato(1);
		}
		
		statoModuliRichiestaFigliRepository.save(moduloAttoRiep.get());
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaPolizzaAssicurativa(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> moduloAttoRiep = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 59, idRichiesta);

		modulo.get().setValidato(0);
		modulo.get().setCompletato(0);
		modulo.get().setAnnullato(1);
		
		// ANNULAZIONE ANCHE DELL'ATTO RIEPILOGATIVO ESSENDO DIPEDENTE ANCHE DA ESSO
		if(moduloAttoRiep.isPresent()) {
			moduloAttoRiep.get().setValidato(0); 
			moduloAttoRiep.get().setCompletato(0);
			moduloAttoRiep.get().setAnnullato(1);
		}
		
		statoModuliRichiestaFigliRepository.save(moduloAttoRiep.get());
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaRapLegAndRespOrg(Long idRichiesta, Long idAnagrafica, boolean isCloneAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloElencoRLAndRO = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, idAnagrafica); 
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);
	
		// CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(isCloneAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumentoRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			
			moduloSchedaRapL.get().setValidato(0);
			moduloSchedaRapL.get().setCompletato(0);
			moduloSchedaRapL.get().setAnnullato(1);
			
			moduloDocumentoRapL.get().setValidato(0);
			moduloDocumentoRapL.get().setCompletato(0);
			moduloDocumentoRapL.get().setAnnullato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumentoRapL.get());
		}
		
		// CASISTICA POSSIBILE IN CASO DI RAP LEGALE NON CORRISPONDENTE A REASPONSABILE DELL'ORGANISMO
		if(moduloQualificaMed.isPresent()) {
			moduloQualificaMed.get().setValidato(0);
			moduloQualificaMed.get().setCompletato(0);
			moduloQualificaMed.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
		}
		
		moduloScheda.get().setValidato(0);
		moduloScheda.get().setCompletato(0);
		moduloScheda.get().setAnnullato(1);
		
		moduloDocumento.get().setValidato(0);
		moduloDocumento.get().setCompletato(0);
		moduloDocumento.get().setAnnullato(1);
		
		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		// VERRA ANNULLATO L'ELENCO MA NON TUTTE LE SCHEDE ASSOCIATE
		moduloElencoRLAndRO.get().setValidato(0);
		moduloElencoRLAndRO.get().setCompletato(0);
		moduloQualificaMed.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaMediatore(Long idRichiesta, Long idAnagrafica) {
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloScheda = Optional.empty();
		Optional<StatoModuliRichiestaFigli> moduloDocumento = Optional.empty();
		
		if(anagrafica.get().getIdTipoAnagrafica() == 4) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, idAnagrafica);
			
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setAnnullato(1);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 5) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, idAnagrafica);

			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setAnnullato(1);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 6) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, idAnagrafica);

			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setAnnullato(1);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaElencoRapLegAndRespOrg(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForRapLAndOrg(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElencoRLAndRO = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloSchedaRap = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagrafica.getIdAnagrafica());
			//Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				//	.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository 
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloSchedaRap.isPresent()) {
				moduloSchedaRap.get().setValidato(0);
				moduloSchedaRap.get().setCompletato(0);
				moduloSchedaRap.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloSchedaRap.get());
			}
			
		/*	if(moduloAttoReqOnora.isPresent()) {
				moduloAttoReqOnora.get().setValidato(0);
				moduloAttoReqOnora.get().setCompletato(0);
				moduloAttoReqOnora.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
			}*/
			
			if(moduloDocumento.isPresent()) {
				moduloDocumento.get().setValidato(0);
				moduloDocumento.get().setCompletato(0);
				moduloDocumento.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
			}	
			
			if(anagrafica.getIdQualifica() == 2) {
				if(moduloQualificaMed.isPresent()) {
					moduloQualificaMed.get().setValidato(0);
					moduloQualificaMed.get().setCompletato(0);
					moduloQualificaMed.get().setAnnullato(1);
					statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
				}
			}
				
		}
		
		if(moduloElencoRLAndRO.isPresent()) {
			moduloElencoRLAndRO.get().setValidato(0);
			moduloElencoRLAndRO.get().setCompletato(0);
			moduloElencoRLAndRO.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloElencoRLAndRO.get());
		}
		
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaElencoCompOrgAmAndCompSoc(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForCompOrgAm(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 83, idRichiesta);
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, anagrafica.getIdAnagrafica());
			//Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				//	.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, anagrafica.getIdAnagrafica());
			
			/*if(moduloAttoReqOnora.isPresent()) {
				moduloAttoReqOnora.get().setValidato(0);
				moduloAttoReqOnora.get().setCompletato(0);
				moduloAttoReqOnora.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());	
			}*/
			
			if(moduloDocumento.isPresent()) {
				moduloDocumento.get().setValidato(0);
				moduloDocumento.get().setCompletato(0);
				moduloDocumento.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloDocumento.get());	
			}	
			
			if(moduloScheda.isPresent()) {
				moduloScheda.get().setValidato(0);
				moduloScheda.get().setCompletato(0);
				moduloScheda.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());	
			}
			
		}
		
		if(moduloElenco.isPresent()) {
			moduloElenco.get().setValidato(0);
			moduloElenco.get().setCompletato(0);
			moduloElenco.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());	
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaCompOrgAmAndCompSoc(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);
		
		moduloScheda.get().setValidato(0);
		moduloDocumento.get().setValidato(0);
		moduloScheda.get().setCompletato(0);
		moduloDocumento.get().setCompletato(0);
		moduloScheda.get().setAnnullato(1);
		moduloDocumento.get().setAnnullato(1);

		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAnnullamentoRapAndRespOrg(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		
		Optional<StatoModuliRichiestaFigli> moduloElencoRLAndRO = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);
		
		if(anagrafica.get().getIdQualifica() == 1) {
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, idAnagrafica);
			moduloSchedaRapL.get().setValidato(0);
			moduloSchedaRapL.get().setCompletato(0);
			moduloSchedaRapL.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
		}
		
		if(moduloElencoRLAndRO.isPresent() && moduloElencoRLAndRO.get().getValidato() == (Integer) 1) {
			moduloElencoRLAndRO.get().setValidato(0);
			moduloElencoRLAndRO.get().setCompletato(0);
			moduloElencoRLAndRO.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloElencoRLAndRO.get());
		}
		
		if(moduloDocumento.isPresent() && moduloDocumento.get().getValidato() == (Integer) 1) {
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		if(moduloQualificaMed.isPresent() && moduloQualificaMed.get().getValidato() == (Integer) 1) {
			moduloQualificaMed.get().setValidato(0);
			moduloQualificaMed.get().setCompletato(0);
			moduloQualificaMed.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
		}
		
		// ANNULLA CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(convalidaAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloDocumentoRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			
			if(moduloDocumentoRapL.get().getValidato() == (Integer) 1) {
				moduloDocumentoRapL.get().setValidato(0);
				moduloDocumentoRapL.get().setCompletato(0);
				moduloDocumentoRapL.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloDocumentoRapL.get());
			}
			
			if(moduloSchedaRapL.get().getValidato() == (Integer) 1) {
				moduloSchedaRapL.get().setValidato(0);
				moduloSchedaRapL.get().setCompletato(0);
				moduloSchedaRapL.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
			}
			
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAnnullamentoAttoRiepilogativoODM(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);

		if (statoModulo.isPresent() && statoModulo.get().getValidato() == (Integer) 1) {
			statoModulo.get().setValidato(0);
			statoModulo.get().setCompletato(0);
			statoModulo.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(statoModulo.get());
		} 
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAnnullamentoCompOrgAmAndCompSoc(Long idRichiesta, Long idAnagrafica) {		
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 83, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);

		if(moduloElenco.isPresent() && moduloElenco.get().getValidato() == (Integer) 1) {
			moduloElenco.get().setValidato(0);
			moduloElenco.get().setCompletato(0);
			moduloElenco.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
		
		if(moduloScheda.isPresent() && moduloScheda.get().getValidato() == (Integer) 1) {
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		}
		
		if(moduloAttoReqOnora.isPresent() && moduloAttoReqOnora.get().getValidato() == (Integer) 1) {
			moduloAttoReqOnora.get().setValidato(0);
			moduloAttoReqOnora.get().setCompletato(0);
			moduloAttoReqOnora.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		}
		
		if(moduloDocumento.isPresent() && moduloDocumento.get().getValidato() == (Integer) 1) {
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaElencoPrestServizio(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 3);
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 35, idRichiesta);	
		
		for(AnagraficaOdm anagrafica : anagrafiche) {
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, anagrafica.getIdAnagrafica());		
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, anagrafica.getIdAnagrafica());		
				
			if(moduloScheda.isPresent()) {
				moduloScheda.get().setValidato(0);
				moduloScheda.get().setCompletato(0);
				moduloScheda.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			}

			if(moduloDocumento.isPresent()) {
				moduloDocumento.get().setValidato(0);
				moduloDocumento.get().setCompletato(0);
				moduloDocumento.get().setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
			}

		}

		if(moduloElenco.isPresent()) {
			moduloElenco.get().setValidato(0);
			moduloElenco.get().setCompletato(0);
			moduloElenco.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaPrestatoreServizio(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 35, idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, idAnagrafica);		
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);		
			
		moduloScheda.get().setValidato(0);
		moduloScheda.get().setCompletato(0);
		moduloScheda.get().setAnnullato(1);
		
		moduloDocumento.get().setValidato(0);
		moduloDocumento.get().setCompletato(0);
		moduloDocumento.get().setAnnullato(1);

		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		if(moduloElenco.isPresent()) {
			moduloElenco.get().setValidato(0);
			moduloElenco.get().setCompletato(0);
			moduloElenco.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaElencoMediatoreA(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);	
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, anagrafica.getIdAnagrafica());
			
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setAnnullato(1);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaElencoMediatoreB(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);	
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, anagrafica.getIdAnagrafica());
			
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setAnnullato(1);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaElencoMediatoreC(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);	
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, anagrafica.getIdAnagrafica());
			
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setAnnullato(1);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setAnnullato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
		
	@Transactional(rollbackFor = Exception.class)
	public void annullaSchedaAnagraficaA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		}

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaSchedaAnagraficaA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		}

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaSchedaAnagraficaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaSchedaAnagraficaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaSchedaAnagraficaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaSchedaAnagraficaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaDocumentoAnagraficaA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaDocumentoAnagraficaA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaDocumentoAnagraficaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaDocumentoAnagraficaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaDocumentoAnagraficaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaDocumentoAnagraficaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaOnorabilitàA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 39, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		}

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaOnorabilitàA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 39, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		}

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaOnorabilitàB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 44, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaOnorabilitàB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 44, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaOnorabilitàC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 53, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaOnorabilitàC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 53, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaDisponibilitaA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 40, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

	    if(elencoMediatori.isPresent()) {
	    	elencoMediatori.get().setValidato(0);
	    	elencoMediatori.get().setCompletato(0);
	    	elencoMediatori.get().setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
	    }
	    
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	public void validaDisponibilitaA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 40, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

	    if(elencoMediatori.isPresent()) {
	    	elencoMediatori.get().setValidato(1);
	    	elencoMediatori.get().setCompletato(0);
	    	elencoMediatori.get().setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
	    }
	    
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaDisponibilitaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 45, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaDisponibilitaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 45, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaDisponibilitaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 54, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaDisponibilitaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 54, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaFormazioneInizialeA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 41, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(0);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaFormazioneInizialeA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 41, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(1);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaFormazioneInizialeB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 46, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(0);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaFormazioneInizialeB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 46, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(1);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaFormazioneInizialeC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 55, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(0);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaFormazioneInizialeC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 55, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(1);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaCertificazioneB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 50, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaCertificazioneB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 50, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		} 

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaCertificazioneC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 82, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(0);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaCertificazioneC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 82, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(1);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaFormazioneSpecificaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 77, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(0);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaFormazioneSpecificaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 77, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// VERRA' ANNULLATO ANCHE L'ELENCO DEI MEDIATORI	
	    Optional<StatoModuliRichiestaFigli> elencoMediatori = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	

    	elencoMediatori.get().setValidato(1);
    	elencoMediatori.get().setCompletato(0);
    	elencoMediatori.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoMediatori.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaFormazioneSpecificaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 80, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(0);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(1);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		}

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaFormazioneSpecificaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 80, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
				moduloAlleg.setValidato(1);
				moduloAlleg.setCompletato(0);
				moduloAlleg.setAnnullato(0);
				statoModuliRichiestaFigliRepository.save(moduloAlleg);
			}
		}

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaUlterioriRequisitiA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 75, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaUlterioriRequisitiA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 75, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaUlterioriRequisitiB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 78, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaUlterioriRequisitiB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 78, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());	
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaUlterioriRequisitiC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 81, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(0);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(1);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaUlterioriRequisitiC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 81, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(1);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(0);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		Optional<StatoModuliRichiestaFigli> elencoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		elencoModulo.get().setValidato(1);
		elencoModulo.get().setCompletato(0);
		elencoModulo.get().setAnnullato(0);
		statoModuliRichiestaFigliRepository.save(elencoModulo.get());
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaSchedaPrestatore(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}

		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaDocumentoPrestatore(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);

		for (StatoModuliRichiestaFigli moduloAlleg : statoModuliAllegati) {
			moduloAlleg.setValidato(0);
			moduloAlleg.setCompletato(0);
			moduloAlleg.setAnnullato(1);
			statoModuliRichiestaFigliRepository.save(moduloAlleg);
		}
		
		// SET STATO SE NON IN VALIDAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 3L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 3);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaPrestatore(Long idRichiesta, Long idAnagrafica) {
		annullaSchedaPrestatore(idRichiesta, idAnagrafica);
		annullaDocumentoPrestatore(idRichiesta, idAnagrafica);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaMediatoreA(Long idRichiesta, Long idAnagrafica) {
		annullaSchedaAnagraficaA(idRichiesta, idAnagrafica);
		annullaOnorabilitàA(idRichiesta, idAnagrafica);
		annullaDocumentoAnagraficaA(idRichiesta, idAnagrafica);
		annullaDisponibilitaA(idRichiesta, idAnagrafica);
		annullaFormazioneInizialeA(idRichiesta, idAnagrafica);
	}	
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaMediatoreB(Long idRichiesta, Long idAnagrafica) {
		annullaSchedaAnagraficaB(idRichiesta, idAnagrafica);
		annullaOnorabilitàB(idRichiesta, idAnagrafica);
		annullaDocumentoAnagraficaB(idRichiesta, idAnagrafica);
		annullaDisponibilitaB(idRichiesta, idAnagrafica);
		annullaFormazioneInizialeB(idRichiesta, idAnagrafica);
		annullaFormazioneSpecificaB(idRichiesta, idAnagrafica);
		annullaUlterioriRequisitiB(idRichiesta, idAnagrafica);
		annullaCertificazioneB(idRichiesta, idAnagrafica);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaMediatoreC(Long idRichiesta, Long idAnagrafica) {
		annullaSchedaAnagraficaC(idRichiesta, idAnagrafica);
		annullaOnorabilitàC(idRichiesta, idAnagrafica);
		annullaDocumentoAnagraficaC(idRichiesta, idAnagrafica);
		annullaDisponibilitaC(idRichiesta, idAnagrafica);
		annullaFormazioneInizialeC(idRichiesta, idAnagrafica);
		annullaFormazioneSpecificaC(idRichiesta, idAnagrafica);
		annullaUlterioriRequisitiC(idRichiesta, idAnagrafica);
		annullaCertificazioneC(idRichiesta, idAnagrafica);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaMediatoreA(Long idRichiesta, Long idAnagrafica) {
		validaSchedaAnagraficaA(idRichiesta, idAnagrafica);
		validaOnorabilitàA(idRichiesta, idAnagrafica);
		validaDocumentoAnagraficaA(idRichiesta, idAnagrafica);
		validaDisponibilitaA(idRichiesta, idAnagrafica);
		validaFormazioneInizialeA(idRichiesta, idAnagrafica);
		validaUlterioriRequisitiA(idRichiesta, idAnagrafica);
	}	
	
	@Transactional(rollbackFor = Exception.class)
	public void validaMediatoreB(Long idRichiesta, Long idAnagrafica) {
		validaSchedaAnagraficaB(idRichiesta, idAnagrafica);
		validaOnorabilitàB(idRichiesta, idAnagrafica);
		validaDocumentoAnagraficaB(idRichiesta, idAnagrafica);
		validaDisponibilitaB(idRichiesta, idAnagrafica);
		validaFormazioneInizialeB(idRichiesta, idAnagrafica);
		validaFormazioneSpecificaB(idRichiesta, idAnagrafica);
		validaUlterioriRequisitiB(idRichiesta, idAnagrafica);
		validaCertificazioneB(idRichiesta, idAnagrafica);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaMediatoreC(Long idRichiesta, Long idAnagrafica) {
		validaSchedaAnagraficaC(idRichiesta, idAnagrafica);
		validaOnorabilitàC(idRichiesta, idAnagrafica);
		validaDocumentoAnagraficaC(idRichiesta, idAnagrafica);
		validaDisponibilitaC(idRichiesta, idAnagrafica);
		validaFormazioneInizialeC(idRichiesta, idAnagrafica);
		validaFormazioneSpecificaC(idRichiesta, idAnagrafica);
		validaUlterioriRequisitiC(idRichiesta, idAnagrafica);
		validaCertificazioneC(idRichiesta, idAnagrafica);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAppendiceA(Long idRichiesta) {
		List<AnagraficaOdm> allMediatoriA = 
				anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);
		
		for(AnagraficaOdm persona : allMediatoriA) {
			annullaMediatoreA(idRichiesta, persona.getIdAnagrafica());		
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAppendiceB(Long idRichiesta) {
		List<AnagraficaOdm> allMediatoriB = 
				anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);
		
		for(AnagraficaOdm persona : allMediatoriB) {
			annullaMediatoreB(idRichiesta, persona.getIdAnagrafica());		
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAppendiceC(Long idRichiesta) {
		List<AnagraficaOdm> allMediatoriC = 
				anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);
		
		for(AnagraficaOdm persona : allMediatoriC) {
			annullaMediatoreC(idRichiesta, persona.getIdAnagrafica());		
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaAppendiceA(Long idRichiesta) {
		List<AnagraficaOdm> allMediatoriA = 
				anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);
		
		for(AnagraficaOdm persona : allMediatoriA) {
			validaMediatoreA(idRichiesta, persona.getIdAnagrafica());		
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaAppendiceB(Long idRichiesta) {
		List<AnagraficaOdm> allMediatoriB = 
				anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);
		
		for(AnagraficaOdm persona : allMediatoriB) {
			validaMediatoreB(idRichiesta, persona.getIdAnagrafica());		
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaAppendiceC(Long idRichiesta) {
		List<AnagraficaOdm> allMediatoriC = 
				anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);
		
		for(AnagraficaOdm persona : allMediatoriC) {
			validaMediatoreC(idRichiesta, persona.getIdAnagrafica());		
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaElencoMediatori(Long idRichiesta) {
		annullaAppendiceA(idRichiesta);
		annullaAppendiceB(idRichiesta);
		annullaAppendiceC(idRichiesta);	
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void validaElencoMediatori(Long idRichiesta) {
		validaAppendiceA(idRichiesta);
		validaAppendiceB(idRichiesta);
		validaAppendiceC(idRichiesta);	
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaAttoRiepilogativo(Long idRichiesta, Long idAnagrafica) {
		annullaAttoRiepilogativoODM(idRichiesta);
		annullaPolizzaAssicurativa(idRichiesta);
		annullaSediAttoRiepilogativo(idRichiesta);
		annullaSpeseMediazione(idRichiesta);
		
//		annullaAttoCostitutivoODM(idRichiesta);
//		annullaAttoCostitutivoODMNA(idRichiesta);
//		annullaStatutoOrganismo(idRichiesta);
//		annullaStatutoOrganismoNA(idRichiesta);
//		annullaRegolamentoProcedura(idRichiesta);
//		annullaCodiceEtico(idRichiesta);
//		annullaBilancioCertificaizoneBancaria(idRichiesta);
//		
//		annullaElencoCompOrgAmAndCompSoc(idRichiesta);
//		annullaElencoCompOrgAmAndCompSoc(idRichiesta);
//		
//		annullaAllPersonale(idRichiesta);
	}
	
	public boolean checkPreFinalizza(Long idRichiesta) {
		boolean esito = true;
		
		//MODULO DOMANDA
		if(!getModuloIsValidato((long) 1, idRichiesta) && !getModuloIsAnnullato((long) 1, idRichiesta))
			esito = false;
		if(!getModuloIsValidato((long) 3, idRichiesta) && !getModuloIsAnnullato((long) 3, idRichiesta))
			esito = false;
		if(!getModuloIsValidato((long) 4, idRichiesta) && !getModuloIsAnnullato((long) 4, idRichiesta))
			esito = false;
		if(!getModuloIsValidato((long) 5, idRichiesta) && !getModuloIsAnnullato((long) 5, idRichiesta))
			esito = false;
		if(!getModuloIsValidato((long) 16, idRichiesta) && !getModuloIsAnnullato((long) 16, idRichiesta))
			esito = false;
		if(!getModuloIsValidato((long) 73, idRichiesta) && !getModuloIsAnnullato((long) 73, idRichiesta))
			esito = false;
		if(!getModuloIsValidato((long) 17, idRichiesta) && !getModuloIsAnnullato((long) 17, idRichiesta))
			esito = false;
		if(!getModuloIsValidato((long) 23, idRichiesta) && !getModuloIsAnnullato((long) 23, idRichiesta))
			esito = false;
		
		//rifatta in dettaglio?
		if(!getStatusElencoMediatoriFinalizzaBO(idRichiesta))
			esito = false;
		
		//APPENDICE D
		if(!getModuloIsValidato((long) 27, idRichiesta) && !getModuloIsAnnullato((long) 27, idRichiesta)) 
			esito = false;
		if(!getModuloIsValidato((long) 83,idRichiesta) && !getModuloIsAnnullato((long) 83, idRichiesta))
			esito = false;
		
		//rifatta in dettaglio?
		//PRESTATORI
		if(!isModuliInteractedElencoPrestatoriEsito(idRichiesta))
			esito = false;
		
		//MODULO POLIZZA
		if(!getModuloIsValidato((long) 58, idRichiesta) && !getModuloIsAnnullato((long) 58, idRichiesta))
			esito = false;
		if(!getModuloIsValidato((long) 59, idRichiesta) && !getModuloIsAnnullato((long) 59, idRichiesta))
			esito = false;
				
		//rifatta in dettaglio?
		//APPENDICI
		if(!isModuliInteractedElencoMediatoriEsito(idRichiesta))
			esito = false;
		
		return esito;
	}
	
	public boolean checkAccessoEmettiPdg(Long idRichiesta) {		
		//MODULO DOMANDA
		if(!getModuloIsValidato((long) 1, idRichiesta))
			return false;
		if(!getModuloIsValidato((long) 3, idRichiesta))
			return false;
		if(!getModuloIsValidato((long) 4, idRichiesta))
			return false;
		if(!getModuloIsValidato((long) 5, idRichiesta))
			return false;
		if(!getModuloIsValidato((long) 16, idRichiesta))
			return false;
		if(!getModuloIsValidato((long) 73, idRichiesta))
			return false;
		if(!getModuloIsValidato((long) 17, idRichiesta))
			return false;
		if(!getModuloIsValidato((long) 23, idRichiesta))
			return false;
		
		//rifatta in dettaglio?
		if(!getStatusElencoMediatoriValidatoPerPdgBO(idRichiesta))
			return false;
		
		//APPENDICE D
		if(!getModuloIsValidato((long) 27, idRichiesta)) 
			return false;
		if(!getModuloIsValidato((long) 83,idRichiesta))
			return false;
		
		//rifatta in dettaglio?
		//PRESTATORI
		if(!isModuliValidatoElencoPrestatoriEsito(idRichiesta))
			return false;
		
		//MODULO POLIZZA
		if(!getModuloIsValidato((long) 58, idRichiesta))
			return false;
		if(!getModuloIsValidato((long) 59, idRichiesta))
			return false;
				
		//rifatta in dettaglio?
		//APPENDICI
		if(!isModuliValidatoElencoMediatoriEsito(idRichiesta))
			return false;
		
		// SE VERRANNO PASSATTE TUTTE LE CONDIZIONI VERRA CONSIDERATO VALIDO IL CONTROLLO
		return true;
	}

}
