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

import com.giustizia.mediazionecivile.dto.RichiesteInviateDto;
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
	
	public boolean existModulo(Long idModulo, Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiesta(idModulo, idRichiesta);
	}
	
	public boolean getModuloIsValidato(Long idModulo, Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiestaAndValidato(idModulo, idRichiesta, 1);
	}
	
	public boolean getModuloIsConvalidato(Long idModulo, Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiestaAndCompletato(idModulo, idRichiesta, 1);
	}
	
	public boolean getModuloIsConvalidatoAdPersonam(Long idModulo, Long idRichiesta, Long idAnagrafica) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiestaAndIdAnagraficaAndCompletato(idModulo, idRichiesta, idAnagrafica, 1);
	}
	
	public boolean getAllModuliAreConvalidatoAdPersonam(Long idModulo, Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, idAnagrafica);
		
		for(StatoModuliRichiestaFigli modulo : statoModuliAllegati) {
			if(modulo.getCompletato() == null || modulo.getCompletato() == 0) 
				return false;
		}
		return true;
	}
	
	public boolean getModuloIsConvalidatoAllRows(Long idRichiesta, Long idModulo) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta(idModulo, idRichiesta);
		
		for(StatoModuliRichiestaFigli modulo : statoModuliAllegati) {
			if(modulo.getCompletato() != (Integer) 1 && modulo.getValidato() != (Integer) 1) 
				return false;
		}
		return true;
	}
	
	public boolean getModuloIsConvalidatoAllRowsCertB(Long idRichiesta, Long idModulo) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriB(idRichiesta);
		
		for(AnagraficaOdm anag : anagrafiche) {
			List<StatoModuliRichiestaFigli> listaCertificati = statoModuliRichiestaFigliRepository
					.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, anag.getIdAnagrafica());
			
			if((anag.getLingueStraniere() != null && anag.getLingueStraniere().isEmpty() == false) && listaCertificati.size() == 0)
				return false; //true?
			else {
				List<StatoModuliRichiestaFigli> statoModuli = statoModuliRichiestaFigliRepository
						.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, anag.getIdAnagrafica());
				
				for(StatoModuliRichiestaFigli modulo : statoModuli) {
					if(modulo.getCompletato() != (Integer) 1 && modulo.getValidato() != (Integer) 1) 
						return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean getModuloIsConvalidatoAllRowsCertC(Long idRichiesta, Long idModulo) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriC(idRichiesta);
		
		for(AnagraficaOdm anag : anagrafiche) {
			List<StatoModuliRichiestaFigli> listaCertificati = statoModuliRichiestaFigliRepository
					.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, anag.getIdAnagrafica());
			
			if((anag.getLingueStraniere() != null && anag.getLingueStraniere().isEmpty() == false) && listaCertificati.size() == 0)
				return false;
			else {
				List<StatoModuliRichiestaFigli> statoModuli = statoModuliRichiestaFigliRepository
						.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, anag.getIdAnagrafica());
				
				for(StatoModuliRichiestaFigli modulo : statoModuli) {
					if(modulo.getCompletato() != (Integer) 1 && modulo.getValidato() != (Integer) 1) 
						return false;
				}
			}
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
		// VERIFICA SE I MODULI SONO TUTTI CONVALIDATI O VALIDATI
		if (sedi.size() >= 2 && statoModuliAllegati.size() >= (sedi.size() * 3)) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				if(moduloAll.getAnnullato() == (Integer) 1)
					return "a";
				else if(moduloAll.getCompletato() != (Integer) 1 && moduloAll.getValidato() != (Integer) 1)
					return "";
			}
		} else {
			return "";
		}

		// SE E' C O V LO STATUS DELL'INSIEME DEI MODULI SARA' COMPLETATO
		return "c";
	}

	public String getStatusAttoRODM(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long)68, (long) 69, (long) 70, idRichiesta);
		List<Sede> sedi = sedeRepository.findByIdRichiesta(idRichiesta);

		// Controlli per procedere alla convalidazione
		if (sedi.size() >= 2 && statoModuliAllegati.size() >= (sedi.size() * 3)) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				if(moduloAll.getAnnullato() == (Integer) 1)
					return "a";
				else if(moduloAll.getCompletato() != (Integer) 1)
					return "";
			}
		} else {
			return "";
		}

		// check parametri idRichiesta?
		if (statoModulo.isPresent()) {
			if (statoModulo.get().getCompletato() != (Integer) 1) {
				return "";
			} else {
				return "c";
			}
		} else {
			return "";
		}
	}
	
	public boolean getStatusElencoMediatori(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
											.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);
	
		if(statoModulo.isPresent() == false || 
		   statoModulo.get().getCompletato() != (Integer) 1 && statoModulo.get().getValidato() != (Integer) 1) {
			return false;
		}
		else {
			return true;
		}

	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean getStatusRapLegOrRespOrOrgAm(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
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
		if(convalidaAnagraficaRapLegale) {
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
	
	public boolean getStatusElenRapLegAndRespOrg(Long idRichiesta) {
		//List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForAntScheMedLegAndOrg(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElencoRLAndRO = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		
		if(moduloElencoRLAndRO.isPresent() == false || moduloElencoRLAndRO.get().getCompletato() != (Integer) 1) {
			return false;
		}
			
		return true;
	}
	
	public String getStatusElencoRapLRespOrg(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForRapLAndOrg(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		
		if(moduloElenco.get().getAnnullato() == (Integer) 1) {
			return "";
		}
		else if(moduloElenco.get().getValidato() != (Integer) 1 && moduloElenco.get().getCompletato() != (Integer) 1) {
			return "";
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
				return "";
			}
			else if(moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloSchedaRap.get().getValidato() != (Integer) 1 && moduloSchedaRap.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1 && moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1 && moduloDocumento.get().getCompletato() != (Integer) 1) {
				return "";
			}
			
			if(anagrafica.getIdQualifica() != 1) {
				if(moduloQualificaMed.get().getAnnullato() == (Integer) 1) {
					return "";
				}
				else if(moduloQualificaMed.get().getValidato() != (Integer) 1 && moduloQualificaMed.get().getCompletato() != (Integer) 1) {
					return "";
				}
			}
			
		}

		return "c";		
	}
	
	public String getStatusElenCompOrgAm(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForCompOrgAm(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 83, idRichiesta);
		
		if(moduloElenco.isPresent() == false || moduloElenco.get().getAnnullato() == (Integer) 1) {
			return "";
		}
		else if(moduloElenco.get().getValidato() != (Integer) 1 && moduloElenco.get().getCompletato() != (Integer) 1) {
			return "";
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
				return "";
			}
			else if(moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloScheda.get().getValidato() != (Integer) 1 && moduloScheda.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1 && moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1 && moduloDocumento.get().getCompletato() != (Integer) 1) {
				return "";
			}			
		}

		return "c";
	}
	
	public String getStatusPrestatoreServizio(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, idAnagrafica);		
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);		
		
		if(moduloScheda.get().getAnnullato() == (Integer) 1 || moduloDocumento.get().getAnnullato() == (Integer) 1)
			return "a";
		else if(moduloScheda.get().getCompletato() == null || moduloScheda.get().getCompletato() == 0 ||
				moduloDocumento.get().getCompletato() == null || moduloDocumento.get().getCompletato() == 0)
			return "";
		
		return "c";
	}
	
	public String getStatusElencoMedGen(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriA(idRichiesta);	
		
		// CONTROLLO SE E' STATA INSERITA ALMENO UN ANAGRAFICA E SE CORRISPONDE CON QUELLE DICHIARATE NELL'ATTO
		if(anagrafiche.size() == 0 || richiesta.get().getNumMediatori() != anagrafiche.size())
			return "";
			
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 39, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloAttoReqOnora.isPresent() == false || moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1 ) {
				return "";
			}
			else if(moduloScheda.get().getValidato() != (Integer) 1 && moduloScheda.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1 && moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1 && moduloDocumento.get().getCompletato() != (Integer) 1) {
				return "";
			}
		}

		return "c";		
	}
	
	public String getStatusElencoMedInt(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriB(idRichiesta);	
		
		// CONTROLLO SE E' STATA INSERITA ALMENO UN ANAGRAFICA E SE CORRISPONDE CON QUELLE DICHIARATE NELL'ATTO
		if(anagrafiche.size() == 0 || richiesta.get().getNumMediatoriInter() != anagrafiche.size())
			return "";
	
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 44, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloAttoReqOnora.isPresent() == false || moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1 ) {
				return "";
			}
			else if(moduloScheda.get().getValidato() != (Integer) 1 && moduloScheda.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1 && moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1 && moduloDocumento.get().getCompletato() != (Integer) 1) {
				return "";
			}
		}

		return "c";		
	}
	
	public String getStatusElencoMedCons(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllAnagraficaMediatoriC(idRichiesta);	
		
		// CONTROLLO SE E' STATA INSERITA ALMENO UN ANAGRAFICA E SE CORRISPONDE CON QUELLE DICHIARATE NELL'ATTO
		if(anagrafiche.size() == 0 || richiesta.get().getNumMediatoriCons() != anagrafiche.size())
			return "";
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 53, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloAttoReqOnora.isPresent() == false || moduloAttoReqOnora.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getAnnullato() == (Integer) 1) {
				return "";
			}
			else if(moduloScheda.get().getValidato() != (Integer) 1 && moduloScheda.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloAttoReqOnora.get().getValidato() != (Integer) 1 && moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
				return "";
			}
			else if(moduloDocumento.get().getValidato() != (Integer) 1 && moduloDocumento.get().getCompletato() != (Integer) 1) {
				return "";
			}
		}

		return "c";
	}

	public List<StatoModuliRichiestaFigli> getAllModuloConvalidato(Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.findAllDistinctByIdModuloAndIdRichiesta(idRichiesta);
	}
	
	public List<RichiesteInviateDto> getAllRichieste(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliRichiestaFigli = 
				statoModuliRichiestaFigliRepository.findAllByIdModuloAndIdRichiesta((long) 85, idRichiesta);
		
		List<RichiesteInviateDto> listaRichiesteInviate = new ArrayList<>();
		for (StatoModuliRichiestaFigli statoModuliRichiestaFigli2 : statoModuliRichiestaFigli) {
			
			AnagraficaOdm anagraficaOdm = anagraficaOdmRepository.findByidAnagrafica(statoModuliRichiestaFigli2.getIdAnagrafica()).get();

			RichiesteInviateDto richiesteInviateDto = new RichiesteInviateDto();
			
			richiesteInviateDto.setIdRichiesta(statoModuliRichiestaFigli2.getId());
			
			richiesteInviateDto.setNome(anagraficaOdm.getNome());
			
			richiesteInviateDto.setDataRichiesta(statoModuliRichiestaFigli2.getDataInserimento());
			
			richiesteInviateDto.setDocumento("");
			
			listaRichiesteInviate.add(richiesteInviateDto);
		}
		return listaRichiesteInviate;
	}

	public void convalidazioneDomandaDiIscrizione(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 1, idRichiesta);
		if (statoModulo.isPresent()) {
			statoModulo.get().setAnnullato(0);
			statoModulo.get().setValidato(0);
			statoModulo.get().setCompletato(1);
			statoModuliRichiestaFigliRepository.save(statoModulo.get());
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException("-ErrorInfo Non è stata trovato nessun modulo associato");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneSediAttoRiepilogativo(Long idRichiesta) {//Convalidare anche sedi
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long) 68, (long) 69, (long) 70, idRichiesta);
		List<Sede> sedi = sedeRepository.findByIdRichiesta(idRichiesta);

		// Convalidazione di tutti i moduli inerenti alle sedi
		if (statoModuliAllegati != null && sedi.size() >= 2) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
				
				// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
				Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
				if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
					richiesta.get().setIdStato((long) 1);
				}
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno una sede legale e una operativa per convalidare l'atto");
		}

	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneOnorabilitaAppendici(Long idRichiesta, Long idModulo, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> moduli = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, idAnagrafica);
		
		AnagraficaOdm anagrafica = anagraficaOdmRepository.findById(idAnagrafica).get();
			
		if(moduli.isEmpty()) {
			
			StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();

			switch (anagrafica.getIdTipoAnagrafica().intValue()) {
			case 4:
				modulo.setIdModulo((long) 39); 
				break;
			case 5:
				modulo.setIdModulo((long) 44); 
				break;
			case 6:
				modulo.setIdModulo((long) 53); 
				break;
			default: 
				break;
			}
			modulo.setIdRichiesta(idRichiesta);
			modulo.setIdAnagrafica(idAnagrafica);
			modulo.setDataInserimento(new Date());
			modulo.setAnnullato(0);
			modulo.setValidato(0);
			modulo.setCompletato(1);

			statoModuliRichiestaFigliRepository.save(modulo);
					
		} else {
		// Controlli per procedere alla convalidazione
			for (StatoModuliRichiestaFigli moduloAll : moduli) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
		}
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneAttoRiepilogativoODM(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		List<StatoModuliRichiestaFigli> moduliSedi = statoModuliRichiestaFigliRepository
				.findAllByFor3IdModuliAndIdRichiesta((long) 68, (long) 69, (long) 70, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloSpeseMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 73, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloDicPolizza = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 58, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloPolizza = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 59, idRichiesta);
		String moduliNonConvalidati = "";

//		// CONTROLLI PER PROCEDE ALLA CONVALIDAZIONE DELL'ATTO RIEPILOGATIVO
//		if (moduliSedi != null && moduliSedi.size() >= 2) {
//			for (StatoModuliRichiestaFigli moduloS : moduliSedi) {
//				if (moduloS.getCompletato() != (Integer) 1) {
//					moduliNonConvalidati = moduliNonConvalidati + "<br>Prima di convalidare l'atto riepilogativo è necessario completare le seguenti sezioni: Sedi, Spese di mediazione ed Atto Riepilogativo";
//					break;
//				}
//			}
//		} else {
//			moduliNonConvalidati = moduliNonConvalidati + "<br>E' necessario inserire almeno una sede legale e una operativa per convalidare l'atto";
//		}
//		
//		if (moduloSpeseMed.isPresent() == false) {
//			moduliNonConvalidati = moduliNonConvalidati + "<br>E' necessario convalidare le spese di mediazione";
//		} 
//		else if (moduloPolizza.isPresent() == false || moduloDicPolizza.isPresent() == false) {
//			moduliNonConvalidati = moduliNonConvalidati + "<br>E' necessario completare e convalidare la polizza assicurativa";
//		} 
//		
//		// SE SARANNO PRESENTI DEI MODULI NON ASSOCIATI NEL MESSAGGIO VERRA LANCIATO UN ERRORE
//		if(moduliNonConvalidati.isEmpty() == false) {
//			throw new RuntimeException("-ErrorInfo " + moduliNonConvalidati);
//		}
//		else {
//			if (statoModulo.isPresent()) {
//				statoModulo.get().setAnnullato(0);
//				statoModulo.get().setValidato(0);
//				statoModulo.get().setCompletato(1);
//				statoModuliRichiestaFigliRepository.save(statoModulo.get());
//			} else {
//				throw new RuntimeException("-ErrorInfo Non è stata trovato nessun modulo associato");
//			}
//		}
		
		if(moduliSedi.isEmpty() && moduloSpeseMed.isEmpty() && (moduloPolizza.isEmpty() || moduloDicPolizza.isEmpty())) {
			moduliNonConvalidati = moduliNonConvalidati + "<br>Prima di convalidare l'atto riepilogativo è necessario completare le seguenti sezioni: Sedi, Spese di mediazione e Polizza assicurativa";
		} else {
		// CONTROLLI PER PROCEDE ALLA CONVALIDAZIONE DELL'ATTO RIEPILOGATIVO
			if (moduliSedi.isEmpty() == false && moduliSedi.size() >= 2) {
				for (StatoModuliRichiestaFigli moduloS : moduliSedi) {
					if (moduloS.getCompletato() != (Integer) 1) {
						moduliNonConvalidati = moduliNonConvalidati + "<br>E' necessario convalidare entrambe le sedi";
						break;
					}
				}
			} else {
				moduliNonConvalidati = moduliNonConvalidati + "<br>E' necessario inserire almeno una sede legale e una operativa per convalidare l'atto";
			}
			
			if (moduloSpeseMed.isPresent() == false) {
				moduliNonConvalidati = moduliNonConvalidati + "<br>E' necessario convalidare le spese di mediazione";
			} 
			if (moduloPolizza.isPresent() == false || moduloDicPolizza.isPresent() == false) {
				moduliNonConvalidati = moduliNonConvalidati + "<br>E' necessario completare e convalidare la polizza assicurativa";
			} 
		}
		
		// SE SARANNO PRESENTI DEI MODULI NON ASSOCIATI NEL MESSAGGIO VERRA LANCIATO UN ERRORE
		if(moduliNonConvalidati.isEmpty() == false) {
			throw new RuntimeException("-ErrorInfo " + moduliNonConvalidati);
		}
		else {
			if (statoModulo.isPresent()) {
				statoModulo.get().setAnnullato(0);
				statoModulo.get().setValidato(0);
				statoModulo.get().setCompletato(1);
				statoModuliRichiestaFigliRepository.save(statoModulo.get());
			} else {
				throw new RuntimeException("-ErrorInfo Non è stata trovato nessun modulo associato");
			}
		}
		
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneAttoCostitutivoODM(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 4, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file nell'atto costitutivo per proseguire");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneStatutoOrganismo(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 5, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file nello Statuto Organismo per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneAttoCostitutivoODMNA(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 71, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
				
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file nell'atto costitutivo per proseguire");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneStatutoOrganismoNA(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 72, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file nello Statuto Organismo per proseguire");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneRegolamentoProcedura(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 16, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file nel Regolamento di procedura per proseguire");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneSpeseMediazione(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 73, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException("-ErrorInfo E' necessario salvare una opzione per proseguire");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneCodiceEtico(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 17, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file nelle Spese di mediazione per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneBilancioCertificaizoneBancaria(Long idRichiesta) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta((long) 23, idRichiesta);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file nelle Spese di mediazione per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneRequisitiOnorabilita(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, idAnagrafica);
		
		// CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(convalidaAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnoraRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			
			moduloAttoReqOnoraRapL.get().setAnnullato(0);
			moduloAttoReqOnoraRapL.get().setValidato(0);
			moduloAttoReqOnoraRapL.get().setCompletato(1);			
			statoModuliRichiestaFigliRepository.save(moduloAttoReqOnoraRapL.get());
		}
		
		moduloAttoReqOnora.get().setAnnullato(0);
		moduloAttoReqOnora.get().setValidato(0);
		moduloAttoReqOnora.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneRequisitiOnorabilitaCompOrgAm(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, idAnagrafica);
		
		moduloAttoReqOnora.get().setAnnullato(0);
		moduloAttoReqOnora.get().setValidato(0);
		moduloAttoReqOnora.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneAttoCostNonAutonomo(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 71, idRichiesta);

		modulo.get().setAnnullato(0);
		modulo.get().setValidato(0);
		modulo.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneStatutoOrgNonAutonomo(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 72, idRichiesta);

		modulo.get().setAnnullato(0);
		modulo.get().setValidato(0);
		modulo.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneDichiaPolizzaAss(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 58, idRichiesta);

		modulo.get().setAnnullato(0);
		modulo.get().setValidato(0);
		modulo.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazionePolizzaAssicurativa(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 59, idRichiesta);

		modulo.get().setAnnullato(0);
		modulo.get().setValidato(0);
		modulo.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(modulo.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneRapLegAndRespOrg(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, idAnagrafica); 
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);
	
		// CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(convalidaAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumentoRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			
			moduloSchedaRapL.get().setAnnullato(0);
			moduloSchedaRapL.get().setValidato(0);
			moduloSchedaRapL.get().setCompletato(1);
			moduloDocumentoRapL.get().setAnnullato(0);
			moduloDocumentoRapL.get().setValidato(0);
			moduloDocumentoRapL.get().setCompletato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloDocumentoRapL.get());
			statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
		}
		
		moduloScheda.get().setAnnullato(0);
		moduloScheda.get().setValidato(0);
		moduloScheda.get().setCompletato(1);
		moduloDocumento.get().setAnnullato(0);
		moduloDocumento.get().setValidato(0);
		moduloDocumento.get().setCompletato(1);
		
		// CASISTICA POSSIBILE IN CASO DI RAP LEGALE NON CORRISPONDENTE A REASPONSABILE DELL'ORGANISMO
		if(moduloQualificaMed.isPresent()) {
			moduloQualificaMed.get().setAnnullato(0);
			moduloQualificaMed.get().setValidato(0);
			moduloQualificaMed.get().setCompletato(1);
			statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
		}
		
		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneMediatore(Long idRichiesta, Long idAnagrafica) {
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloScheda = Optional.empty();
		Optional<StatoModuliRichiestaFigli> moduloDocumento = Optional.empty();
		
		if(anagrafica.get().getIdTipoAnagrafica() == 4) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 38, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, idAnagrafica);
			
			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 5) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 43, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, idAnagrafica);
			
			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 6) {
			moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 52, idRichiesta, idAnagrafica);
			moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, idAnagrafica);

			moduloScheda.get().setAnnullato(0);
			moduloScheda.get().setValidato(0);
			moduloScheda.get().setCompletato(1);
			moduloDocumento.get().setAnnullato(0);
			moduloDocumento.get().setValidato(0);
			moduloDocumento.get().setCompletato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazionePrestatoreServizio(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, idAnagrafica);		
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);		
		
		moduloScheda.get().setAnnullato(0);;
		moduloScheda.get().setValidato(0);
		moduloScheda.get().setCompletato(1);
		moduloDocumento.get().setAnnullato(0);
		moduloDocumento.get().setValidato(0);
		moduloDocumento.get().setCompletato(1);

		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneElencoPrestServizio(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 3);
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 35, idRichiesta);	
		
		for(AnagraficaOdm anagrafica : anagrafiche) {
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 36, idRichiesta, anagrafica.getIdAnagrafica());		
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, anagrafica.getIdAnagrafica());		
				
			if(moduloScheda.get().getCompletato() != (Integer) 1)
				throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare tutti i Prestatori di Opera e Servizio");
			else if(moduloDocumento.get().getCompletato() != (Integer) 1)
				throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare tutti i Prestatori di Opera e Servizio");
		}

		if(moduloElenco.isPresent() == false) {
			StatoModuliRichiestaFigli newModuloElenco = new StatoModuliRichiestaFigli();
			newModuloElenco.setIdModulo((long) 35);
			newModuloElenco.setIdRichiesta(idRichiesta);
			newModuloElenco.setDataInserimento(new Date());
			moduloElenco = Optional.of(newModuloElenco);
		}
			
		moduloElenco.get().setAnnullato(0);
		moduloElenco.get().setValidato(0);
		moduloElenco.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneElencoRapLegAndRespOrg(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForAntScheMedLegAndOrg(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElencoRLAndRO = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		
		// SE NON CI SONO ALMENO DUE ANAGRAFICHE VUOL DIRE CHE E' STATA CREATA SOLO QUELLA DEL RAP.LEGALE
		if(anagrafiche.size() < 2) {
			throw new RuntimeException("-ErrorInfo Per proseguire è necessario avere un responsabile dell'organismo");
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
			
			if(moduloSchedaRap.isPresent() == false || moduloSchedaRap.get().getCompletato() != (Integer) 1) {
				throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare le schede del Rappresentante legale e il Responsabile dell'organismo");
			}
			else if(anagrafica.getIdQualifica() == 2) {
				if(moduloQualificaMed.isPresent() == false || moduloQualificaMed.get().getCompletato() != (Integer) 1) {
					throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare tutti i Responsabili dell'organismo");
				}
			}
			else if(moduloAttoReqOnora.isPresent() == false || moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
				throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare l'autocertificazione di requisiti di onorabilità,"
										   + " di tutti i Responsabili dell'organismo e del Rappresentante legale");
			}
			else if(moduloDocumento.isPresent() == false || moduloDocumento.get().getCompletato() != (Integer) 1) {
				throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare tutti i Responsabili dell'organismo e il Rappresentante legale");

			}		
			
		}
		
		if(moduloElencoRLAndRO.isPresent() == false) {
			StatoModuliRichiestaFigli newElencoRLAndRO = new StatoModuliRichiestaFigli();
			newElencoRLAndRO.setIdModulo((long) 27);
			newElencoRLAndRO.setIdRichiesta(idRichiesta);
			newElencoRLAndRO.setDataInserimento(new Date());
			moduloElencoRLAndRO = Optional.of(newElencoRLAndRO);
		}
		
		moduloElencoRLAndRO.get().setAnnullato(0);
		moduloElencoRLAndRO.get().setValidato(0);
		moduloElencoRLAndRO.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(moduloElencoRLAndRO.get());	
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneElencoCompOrgAmAndCompSoc(Long idRichiesta) {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForCompOrgAm(idRichiesta);	
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 83, idRichiesta);
		
		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, anagrafica.getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloAttoReqOnora.isPresent() == false || moduloAttoReqOnora.get().getCompletato() != (Integer) 1) {
				throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare l'autocertificazione di requisiti di onorabilità,"
										   + " di tutti i Responsabili dell'organismo e del Rappresentante legale");
			}
			else if(moduloDocumento.isPresent() == false || moduloDocumento.get().getCompletato() != (Integer) 1) {
				throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare tutti i Responsabili dell'organismo e il Rappresentante legale");

			}	
			else if(moduloScheda.isPresent() == false || moduloScheda.get().getCompletato() != (Integer) 1) {
				throw new RuntimeException("-ErrorInfo Per proseguire è necessario convalidare il Rappresentante legale");
			}
			
		}
		
		if(moduloElenco.isPresent() == false) {
			StatoModuliRichiestaFigli newElenco = new StatoModuliRichiestaFigli();
			newElenco.setIdModulo((long) 83);
			newElenco.setIdRichiesta(idRichiesta);
			newElenco.setDataInserimento(new Date());
			moduloElenco = Optional.of(newElenco);
		}
		
		moduloElenco.get().setAnnullato(0);
		moduloElenco.get().setValidato(0);
		moduloElenco.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(moduloElenco.get());	
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneElencoMediatori(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 26, idRichiesta);	
		// TO DO CONTROLLI
		
		if(moduloElenco.isPresent() == false) {
			StatoModuliRichiestaFigli newModuloElenco = new StatoModuliRichiestaFigli();
			newModuloElenco.setIdModulo((long) 26);
			newModuloElenco.setIdRichiesta(idRichiesta);
			newModuloElenco.setDataInserimento(new Date());
			moduloElenco = Optional.of(newModuloElenco);
		}
		
		moduloElenco.get().setAnnullato(0);
		moduloElenco.get().setValidato(0);
		moduloElenco.get().setCompletato(1);
		statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneCompOrgAmAndCompSoc(Long idRichiesta, Long idAnagrafica) {
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);
		
		moduloScheda.get().setAnnullato(0);
		moduloScheda.get().setValidato(0);
		moduloScheda.get().setCompletato(1);
		moduloDocumento.get().setAnnullato(0);
		moduloDocumento.get().setValidato(0);
		moduloDocumento.get().setCompletato(1);

		statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		
		// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
			richiesta.get().setIdStato((long) 1);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneDisponibilitaA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 40, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
				
				// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
				Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
				if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
					richiesta.get().setIdStato((long) 1);
				}
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneDisponibilitaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 45, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneDisponibilitaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 54, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneFormazioneInizialeA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 41, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneFormazioneInizialeB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 46, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneFormazioneInizialeC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 55, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneCertificazioneB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 50, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneCertificazioneC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 82, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneFormazioneSpecificaB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 77, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneFormazioneSpecificaC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 80, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneUlterioriRequisitiA(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 75, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneUlterioriRequisitiB(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 78, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void convalidazioneUlterioriRequisitiC(Long idRichiesta, Long idAnagrafica) {
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 81, idRichiesta, idAnagrafica);

		// Controlli per procedere alla convalidazione
		if (statoModuliAllegati != null && statoModuliAllegati.size() > 0) {
			for (StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
				moduloAll.setAnnullato(0);
				moduloAll.setValidato(0);
				moduloAll.setCompletato(1);
				statoModuliRichiestaFigliRepository.save(moduloAll);
			}
			
			// SET STATO SE NON IN COMPILAZIONE E NON ISCRITTO
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			if(richiesta.get().getIdStato() != (Long) 1L && richiesta.get().getIdStato() != (Long) 4L) {
				richiesta.get().setIdStato((long) 1);
			}
		} else {
			throw new RuntimeException(
					"-ErrorInfo E' necessario inserire almeno un file per proseguire");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaConvalidazioneRapAndRespOrg(Long idRichiesta, Long idAnagrafica, boolean convalidaAnagraficaRapLegale) {
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloElencoRLAndRO = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 27, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, idAnagrafica);
		
		/* NON UTILE?
		if(anagrafica.get().getIdQualifica() == 1) {
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, idAnagrafica);
			moduloSchedaRapL.get().setCompletato(0);
			statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
		}*/
		
		if(moduloElencoRLAndRO.isPresent() && (moduloElencoRLAndRO.get().getCompletato() == (Integer) 1 || moduloElencoRLAndRO.get().getValidato() == (Integer) 1)) {
			moduloElencoRLAndRO.get().setCompletato(0);
			moduloElencoRLAndRO.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloElencoRLAndRO.get());
		}
		
		if(moduloDocumento.isPresent() && (moduloDocumento.get().getCompletato() == (Integer) 1 || moduloDocumento.get().getValidato() == (Integer) 1)) {
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}
		
		if(moduloQualificaMed.isPresent() && (moduloQualificaMed.get().getCompletato() == (Integer) 1 || moduloQualificaMed.get().getValidato() == (Integer) 1)) {
			moduloQualificaMed.get().setCompletato(0);
			moduloQualificaMed.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloQualificaMed.get());
		}
		
		if(moduloScheda.isPresent() && (moduloScheda.get().getCompletato() == (Integer) 1 || moduloScheda.get().getValidato() == (Integer) 1)) {
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		}
		
		if(moduloAttoReqOnora.isPresent() && (moduloAttoReqOnora.get().getCompletato() == (Integer) 1 || moduloAttoReqOnora.get().getValidato() == (Integer) 1)) {
			moduloAttoReqOnora.get().setCompletato(0);
			moduloAttoReqOnora.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		}
		
		// ANNULLA CONVALIDAZIONE ANCHE DEL RAP. LEGALE UTILIZZATO PER IL CLONE DEL RESP. ORGN
		if(convalidaAnagraficaRapLegale) {
			Optional<AnagraficaOdm> anagraficaLeg = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloDocumentoRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnoraRapL = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagraficaLeg.get().getIdAnagrafica());
			
			if(moduloSchedaRapL.get().getCompletato() == (Integer) 1 || moduloSchedaRapL.get().getValidato() == (Integer) 1) {
				moduloSchedaRapL.get().setCompletato(0);
				moduloSchedaRapL.get().setValidato(0);
				statoModuliRichiestaFigliRepository.save(moduloSchedaRapL.get());
			}
			
			if(moduloDocumentoRapL.get().getCompletato() == (Integer) 1 || moduloDocumentoRapL.get().getValidato() == (Integer) 1) {
				moduloDocumentoRapL.get().setCompletato(0);
				moduloDocumentoRapL.get().setValidato(0);
				statoModuliRichiestaFigliRepository.save(moduloDocumentoRapL.get());
			}
			
			if(moduloAttoReqOnoraRapL.isPresent() && (moduloAttoReqOnoraRapL.get().getCompletato() == (Integer) 1 || moduloAttoReqOnoraRapL.get().getValidato() == (Integer) 1)) {
				moduloAttoReqOnoraRapL.get().setCompletato(0);
				moduloAttoReqOnoraRapL.get().setValidato(0);
				statoModuliRichiestaFigliRepository.save(moduloAttoReqOnoraRapL.get());
			}
			
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaConvalidazioneAttoRiepilogativoODM(Long idRichiesta) {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);

		if (statoModulo.isPresent() && statoModulo.get().getCompletato() == (Integer) 1) {
			statoModulo.get().setCompletato(0);
			statoModulo.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(statoModulo.get());
		} 
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void annullaConvalidazioneCompOrgAmAndCompSoc(Long idRichiesta, Long idAnagrafica) {		
		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 83, idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);

		if(moduloElenco.isPresent() && moduloElenco.get().getCompletato() == (Integer) 1) {
			moduloElenco.get().setCompletato(0);
			moduloElenco.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());
		}
		
		if(moduloScheda.isPresent() && moduloScheda.get().getCompletato() == (Integer) 1) {
			moduloScheda.get().setCompletato(0);
			moduloScheda.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloScheda.get());
		}
		
		if(moduloAttoReqOnora.isPresent() && moduloAttoReqOnora.get().getCompletato() == (Integer) 1) {
			moduloAttoReqOnora.get().setCompletato(0);
			moduloAttoReqOnora.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
		}
		
		if(moduloDocumento.isPresent() && moduloDocumento.get().getCompletato() == (Integer) 1) {
			moduloDocumento.get().setCompletato(0);
			moduloDocumento.get().setValidato(0);
			statoModuliRichiestaFigliRepository.save(moduloDocumento.get());
		}

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
	private boolean isModuliCompletatoElencoMediatoriEsito(Long idRichiesta) {
		
		String statusAppA = this.getStatusAppendiceA(idRichiesta);
		String statusAppB = this.getStatusAppendiceB(idRichiesta);
		String statusAppC = this.getStatusAppendiceC(idRichiesta);

		if(!statusAppA.equalsIgnoreCase("c")) {
			return false;
		}
		else if(!statusAppB.equalsIgnoreCase("c")) {
			return false;
		}
		else if(!statusAppC.equalsIgnoreCase("c")) {
			return false;
		}
		else {
			return true;
		}
		
		// DA CANCELLA DOPO CONFERMA MODIFICHE DI COLLAUDO
	/*	// MEDIATORI GENERICI
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
		if(getModuloIsConvalidatoAllRowsCertB(idRichiesta, (long) 50) == false)
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
		if(getModuloIsConvalidatoAllRowsCertC(idRichiesta, (long) 82) == false)
			return false;
		
		return true;*/

	}
	
	
	public String getStatusModuliAppendici(Long idRichiesta, Long idModulo, String tipoApp) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		List<StatoModuliRichiestaFigli> statoModuliAllegati = statoModuliRichiestaFigliRepository
				.findAllByIdModuloAndIdRichiesta(idModulo, idRichiesta);
		String status = "c";
		
		int numMediatori = 0;
		// DA VERIFICARE SE PRESENTE IL CONTROLLO SUL OBBLIGO DELLA CERTIFICAZIONE
		//boolean certObligatory = true;
		switch(tipoApp) {
			case "appA": 
				numMediatori = richiesta.get().getNumMediatori();				
				break;
			case "appB": 
				numMediatori = richiesta.get().getNumMediatoriInter();
				break;
			case "appC": 
				numMediatori = richiesta.get().getNumMediatoriCons();
				break;	
		}
		
		// CONTROLLO SUL NUMERO DICHIARATO DEI MEDIATORI, TRANNE NELLE CERTIFICAZIONI LINGUISTICHE
		if(numMediatori == statoModuliAllegati.size() || idModulo == 50 || idModulo == 82) {
			if(statoModuliAllegati.size() != 0) {
				for(StatoModuliRichiestaFigli moduloAll : statoModuliAllegati) {
					if(moduloAll.getAnnullato() == (Integer) 1)
						return "a";
					else if(moduloAll.getValidato() != (Integer) 1 && moduloAll.getCompletato() != (Integer) 1)
						return "";
				}
				
				return "c";	
			}
			else {
				return "";
			}
		}
		else {
			return "";
		}
	}
	
	public String getStatusAppendiceA(Long idRichiesta) {		
		ArrayList<Long> moduliA = 
				new ArrayList<Long>(Arrays.asList((long) 38, (long) 39, (long) 40, (long) 41, (long) 42, (long) 75));
		
		for(Long modulo : moduliA) {
			String statusModulo = getStatusModuliAppendici(idRichiesta, modulo, "appA");
			// VERIFICA SE I MODULI SONO TUTTI CONVALIDATI O VALIDATI
			if(statusModulo.equalsIgnoreCase("a"))
				return "a";
			else if(statusModulo.equalsIgnoreCase("c") == false && statusModulo.equalsIgnoreCase("v") == false)
				return "";
		}
		
		// SE E' C O V LO STATUS DELL'INSIEME DEI MODULI SARA' COMPLETATO
		return "c";
	}
	
	public String getStatusAppendiceB(Long idRichiesta) {		
		ArrayList<Long> moduliB = 
				new ArrayList<Long>(
						Arrays.asList(
								(long) 43, (long) 44, (long) 45, (long) 46, (long) 47, (long) 77, (long) 78));
		
		for(Long modulo : moduliB) {
			String statusModulo = getStatusModuliAppendici(idRichiesta, modulo, "appB");
			// VERIFICA SE I MODULI SONO TUTTI CONVALIDATI O VALIDATI
			if(statusModulo.equalsIgnoreCase("a"))
				return "a";
			else if(statusModulo.equalsIgnoreCase("c") == false && statusModulo.equalsIgnoreCase("v") == false)
				return "";
		}
		
		if(getModuloIsConvalidatoAllRowsCertB(idRichiesta, (long) 50) == false) {
			return "";
		}

		// SE E' C O V LO STATUS DELL'INSIEME DEI MODULI SARA' COMPLETATO
		return "c";
	}
	
	public String getStatusAppendiceC(Long idRichiesta) {		
		ArrayList<Long> moduliB = 
				new ArrayList<Long>(
						Arrays.asList(
								(long) 52, (long) 53, (long) 54, (long) 55, (long) 56, (long) 80, (long) 81));
		
		for(Long modulo : moduliB) {
			// VERIFICA SE I MODULI SONO TUTTI CONVALIDATI O VALIDATI
			String statusModulo = getStatusModuliAppendici(idRichiesta, modulo, "appC");
			if(statusModulo.equalsIgnoreCase("a"))
				return "a";
			else if(statusModulo.equalsIgnoreCase("c") == false && statusModulo.equalsIgnoreCase("v") == false)
				return "";
		}
		
		if(getModuloIsConvalidatoAllRowsCertC(idRichiesta, (long) 82) == false) {
			return "";
		}
		
		// SE E' C O V LO STATUS DELL'INSIEME DEI MODULI SARA' COMPLETATO
		return "c";
	}
	
	
	public boolean checkFinalizza(Long idRichiesta) {
		boolean esito = true;
		
		// VERIFICA CHE OGNI MODULO ABBIA ALMENO ESITO COMPLETATO O FINALIZZATO PER PROSEGUIRE
		if(!getModuloIsConvalidato((long) 1, idRichiesta) && !getModuloIsValidato((long) 1, idRichiesta))
			esito = false;
		if(!getModuloIsConvalidato((long) 3, idRichiesta) && !getModuloIsValidato((long) 3, idRichiesta))
			esito = false;
		if(!getModuloIsConvalidato((long) 4, idRichiesta) && !getModuloIsValidato((long) 4, idRichiesta))
			esito = false;
		if(!getModuloIsConvalidato((long) 5, idRichiesta) && !getModuloIsValidato((long) 5, idRichiesta))
			esito = false;
		if(!getModuloIsConvalidato((long) 16, idRichiesta) && !getModuloIsValidato((long) 16, idRichiesta))
			esito = false;
		if(!getModuloIsConvalidato((long) 73, idRichiesta) && !getModuloIsValidato((long) 73, idRichiesta))
			esito = false;
		if(!getModuloIsConvalidato((long) 17, idRichiesta) && !getModuloIsValidato((long) 17, idRichiesta))
			esito = false;
		if(!getModuloIsConvalidato((long) 23, idRichiesta) && !getModuloIsValidato((long) 23, idRichiesta))
			esito = false;
		if(!getStatusElencoMediatori(idRichiesta) )
			esito = false;
		
		//APPENDICE D
		if(!getModuloIsConvalidato((long) 27, idRichiesta) && !getModuloIsValidato((long) 27, idRichiesta)) 
			esito = false;
		if(!getModuloIsConvalidato((long) 83,idRichiesta) && !getModuloIsValidato((long) 83, idRichiesta))
			esito = false;
		
		//PRESTATORI
		if(!isModuliCompletatoElencoPrestatoriEsito(idRichiesta))
			esito = false;
		
		//MODULO POLIZZA
		if(!getModuloIsConvalidato((long) 58, idRichiesta) && !getModuloIsValidato((long) 58, idRichiesta))
			esito = false;
		if(!getModuloIsConvalidato((long) 59, idRichiesta) && !getModuloIsValidato((long) 59, idRichiesta))
			esito = false;
				
		//APPENDICI
		if(!isModuliCompletatoElencoMediatoriEsito(idRichiesta))
			esito = false;
		
		return esito;
	}
	
}
