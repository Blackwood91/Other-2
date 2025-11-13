package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.AnagraficaOdmDto;
import com.giustizia.mediazionecivile.dto.ElencoUtentiRuoloDto;
import com.giustizia.mediazionecivile.dto.RichiestaRegistrazioneDto;
import com.giustizia.mediazionecivile.dto.SelectAutocertReqOnoDto;
import com.giustizia.mediazionecivile.model.RichiestaRegistrazioneUtente;
import com.giustizia.mediazionecivile.model.UserLogin;
import com.giustizia.mediazionecivile.projection.UserLoginProjection;
import com.giustizia.mediazionecivile.repository.RichiestaRegistrazioneUtenteRepository;
import com.giustizia.mediazionecivile.repository.UserLoginRepository;

@Service
public class UserService {
	@Autowired
	RichiestaRegistrazioneUtenteRepository richiestaRegistrazioneUtenteRepository;
	@Autowired
	UserLoginRepository userLoginRepository;
	

	public HashMap<String, Object> getAllUtenti(Pageable pageable, String searchText) {
		// LISTA TUTTI UTENTI ABILITATI O NON
        HashMap<String, Object> response = new HashMap<>();
        List<Object[]> result= userLoginRepository.findAllUtentiByElencoRuolo(pageable);
        
        List<ElencoUtentiRuoloDto> utentiRuolo = new ArrayList<ElencoUtentiRuoloDto>();
        
		for (Object[] obj : result) {
			ElencoUtentiRuoloDto allUtenti = new ElencoUtentiRuoloDto();
			allUtenti.setId((Long) obj[0]);
			allUtenti.setCognome((String) obj[1]);
			allUtenti.setNome((String) obj[2]);
			allUtenti.setIdRuolo((Long) obj[3]);
			allUtenti.setIsAbilitato((int) obj[7]);
			allUtenti.setCodiceFiscale((String) obj[8]);
			allUtenti.setRuolo((String) obj[9]);		    

		utentiRuolo.add(allUtenti);
		}
        response.put("result", utentiRuolo);
		return response;	
	}
	
	public HashMap<String, Object> getAllRegistrazioniUtente(Pageable pageable, String searchText) {
		// LISTA TUTTI UTENTI ABILITATI O NON
		HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> results = userLoginRepository.findAllUtentiByElencoRegistrazioni(pageable);
		List<RichiestaRegistrazioneDto> richieste = new ArrayList<RichiestaRegistrazioneDto>();
		for(Object[] obj : results.getContent()) {
			RichiestaRegistrazioneDto richiesta = new RichiestaRegistrazioneDto();
			richiesta.setId((Long) obj[0]);
			richiesta.setNome((String) obj[1]);
			richiesta.setCognome((String) obj[2]);
			richiesta.setCodiceFiscale((String) obj[3]);
			richiesta.setRuolo((String) obj[4]);
			richiesta.setDataRichiesta((Date) obj[5]);
			richiesta.setRichiestaIscrizione((String) obj[6]);
			richiesta.setRagioneSociale((String) obj[7]);
			richiesta.setpIva((String) obj[8]);
			richiesta.setPec((String) obj[9]);;
			
			richieste.add(richiesta);
		}
        
        response.put("result", richieste);
        response.put("totalResult", results.getTotalElements());
        return response;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void approvaUtente (Long idUtente) {
		RichiestaRegistrazioneUtente richiestaRegistrazioneUtente = richiestaRegistrazioneUtenteRepository.findByIdUserLogin(idUtente);			
		// PRENDO L'UTENTE ASSOCIATO E GLI SETTO L'ABILITATO A TRUE (1)
		UserLogin user = userLoginRepository.findById(richiestaRegistrazioneUtente.getIdUserLogin()).get();
		user.setIsAbilitato(1);		
		userLoginRepository.save(user);
		
		// ELIMINAZIONE DELLA RICHIESTA POST ABILITAZIONE DELL'UTENTE
		richiestaRegistrazioneUtenteRepository.delete(richiestaRegistrazioneUtente);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void rifutaUtente (Long idUtente) {
		RichiestaRegistrazioneUtente richiestaRegistrazioneUtente = richiestaRegistrazioneUtenteRepository.findByIdUserLogin(idUtente);
		//NEL CASO IN CUI L'UTENTE NON È STATO ABILITATO ELIMINO LA RICHIESTA E L'UTENTE
		userLoginRepository.delete(userLoginRepository.findById(richiestaRegistrazioneUtente.getIdUserLogin()).get());
		richiestaRegistrazioneUtenteRepository.delete(richiestaRegistrazioneUtente);		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean abilitaUtente(Long idUtente) {
		UserLogin user = userLoginRepository.findById(idUtente).get();			
		user.setIsAbilitato(1);
		userLoginRepository.save(user);		
		return false;	
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean disabilitaUtente(Long idUtente) {
		UserLogin user = userLoginRepository.findById(idUtente).get();			
		user.setIsAbilitato(0);
		userLoginRepository.save(user);		
		return false;	
	}
	
}
