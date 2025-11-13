package com.giustizia.mediazionecivile.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.model.SocietaUtenti;
import com.giustizia.mediazionecivile.model.UserLogin;
import com.giustizia.mediazionecivile.repository.SocietaUtentiRepository;
import com.giustizia.mediazionecivile.repository.UserLoginRepository;
import com.giustizia.mediazionecivile.security.UtenteLoggato;

@Service
public class CheckAccessService {
    @Autowired
    UserLoginRepository userLoginRepository;
    @Autowired
    SocietaUtentiRepository societaUtentiRepository;
    @Autowired
	private CacheManager cacheManager;
	
    @Cacheable(value = "cacheVE", key = "#idRichiesta")
    public void societaIsAssociateUser(Long idRichiesta) {
        UtenteLoggato utenteLoggato = (UtenteLoggato) cacheManager.getCache("cacheVE").get("utenteIAMG").get();
     	UserLogin userLogin = userLoginRepository.findByCodiceFiscale(utenteLoggato.getCodiceFiscale());    	
        Optional<SocietaUtenti> societaUtenti = societaUtentiRepository.findSocietaByIdSocietaAndIdUtente(idRichiesta, userLogin.getId());
        
        if(!societaUtenti.isPresent())
            throw new InsufficientAuthenticationException("L'utente non ha l'autorizzazione alla visualizzazione della pagina");
    }
	
}
