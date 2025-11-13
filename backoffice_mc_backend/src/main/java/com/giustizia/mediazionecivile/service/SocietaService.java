package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.dto.RichiestaIscrizioneEfDto;
import com.giustizia.mediazionecivile.dto.RichiestaIscrizioneOdmDto;
import com.giustizia.mediazionecivile.dto.SocietaDTO;
import com.giustizia.mediazionecivile.model.EfRichiesta;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.Societa;
import com.giustizia.mediazionecivile.projection.SocietaDomIscProjection;
import com.giustizia.mediazionecivile.repository.EfRichiesteRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.SocietaRepository;
import com.giustizia.mediazionecivile.utility.StatusCheck;
import com.giustizia.mediazionecivile.utility.VerificaPIva;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class SocietaService {
    List<Societa> societa;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    SocietaRepository societaRepository;
    @Autowired
    RichiesteRepository richiesteRepository;
    @Autowired
    EfRichiesteRepository efRichiesteRepository;

    public HashMap<String, Object> getAllSocieta(Pageable pageable, String searchText) {
        HashMap<String, Object> response = new HashMap<>();
        Page<Object[]> resultPage;

        if (searchText.isEmpty()) {
            resultPage = societaRepository.findAllForSocietaAndRichiesta(pageable);
        } else {
            resultPage = societaRepository.findAllForSocietaAndRichiestaByText(searchText, pageable);
        }

        List<Object[]> resultList = resultPage.getContent();

        List<SocietaDTO> SocietaDTO = new ArrayList<>();
        for (Object[] obj : resultList) {
            SocietaDTO SocietaDTORow = new SocietaDTO(obj);
            SocietaDTO.add(SocietaDTORow);
        }
        response.put("result", SocietaDTO);
        response.put("totalResult", resultPage.getTotalElements());

        return response;
    }

    public StatusCheck insertSocieta(SocietaDTO societa) {
        StatusCheck statusCheck = new StatusCheck();
        VerificaPIva verificaPIva = new VerificaPIva();

        // controllo PIva
        boolean piIsValid = verificaPIva.verificaPIva(societa.getPartitaIva());

        // controllo se PIva esiste (e Ragione Sociale)
        List<Societa> societaControllo = societaRepository.findSocietaByPartitaIvaAndRagioneSociale(
                societa.getPartitaIva(), societa.getRagioneSociale());

        if (societaControllo.size() != 0) {
            statusCheck.setEsito(false);
            statusCheck.setDescrizioneEsito("La Partita Iva è già associata ad un utente");
        } else if (piIsValid == false && (societa.getCodiceFiscaleSocieta()
        		== null || societa.getCodiceFiscaleSocieta().equals(""))) {
            statusCheck.setEsito(false);
            statusCheck.setDescrizioneEsito("La Partita Iva non è valida");
        } else {
            try {
                Societa newSocieta = new Societa();
                newSocieta.setPartitaIva(societa.getPartitaIva());
                newSocieta.setCodiceFiscaleSocieta(societa.getCodiceFiscaleSocieta());
                newSocieta.setRagioneSociale(societa.getRagioneSociale());
                
                societaRepository.save(newSocieta);
                
                statusCheck.setEsito(true);
                statusCheck.setDescrizioneEsito("Il salvataggio è andato a buon fine");
            } catch (Exception e) {
                statusCheck.setEsito(false);
                statusCheck.setDescrizioneEsito("Si è verificato un errore nell'operazione");
            }
        }

        return statusCheck;
    }

    public SocietaDTO findSocietaById(Long id) {
        return new SocietaDTO((Object[])societaRepository.findByIdForSocietaAndRichiesta(id));
    }
    
    public Societa updateSocieta(SocietaDTO societaDto) {
        VerificaPIva verificaPIva = new VerificaPIva();
        if(verificaPIva.verificaPIva(societaDto.getPartitaIva()) == false && (societaDto.getCodiceFiscaleSocieta()
        		== null || societaDto.getCodiceFiscaleSocieta().equals(""))){
			throw new RuntimeException("-ErrorInfo La Partita Iva non è valida");
        }
        else if(checkExistPartitaIva(societaDto.getPartitaIva(), societaDto.getRagioneSociale())  == false) {
			throw new RuntimeException("-ErrorInfo La Partita Iva è già associata ad un utente");
    	}
    	
        Societa societa = societaRepository.findById(Long.parseLong(societaDto.getId().toString())).get();
        societa.setRagioneSociale(societaDto.getRagioneSociale());
        societa.setPartitaIva(societaDto.getPartitaIva());
        societa.setCodiceFiscaleSocieta(societaDto.getCodiceFiscaleSocieta());

        return societaRepository.save(societa);
    }

    public Richiesta richiestaIscrizioneODM(RichiestaIscrizioneOdmDto richiestaDto) {
        Richiesta richiesta = new Richiesta();
        richiesta.setIdRichiesta(richiestaDto.getIdSocieta());
        richiesta.setIdTipoRichiedente(richiestaDto.getIdTipoRichiedente());
        richiesta.setIdTipoRichiesta(richiestaDto.getIdTipoRichiesta());
        richiesta.setDataRichiesta(new Date());
        return richiesteRepository.save(richiesta);
    }

    public EfRichiesta richiestaIscrizioneEF(RichiestaIscrizioneEfDto richiestaDto) {
        EfRichiesta richiesta = new EfRichiesta();
        richiesta.setIdRichiesta(richiestaDto.getIdSocieta());
        richiesta.setIdTipoRichiedente(richiestaDto.getIdTipoRichiedente());
        richiesta.setIdTipoRichiesta(richiestaDto.getIdTipoRichiesta());
        richiesta.setDataRichiesta(new Date());
        return efRichiesteRepository.save(richiesta);
    }
    
    public SocietaDomIscProjection getSocietaDomIscr(Long IdSocieta) {
    	return societaRepository.findProjectedByIdSocieta(IdSocieta);
    }
    
    private boolean checkExistPartitaIva(String pIva, String ragioneSociale) {
        // controllo se PIva esiste (e Ragione Sociale)
        List<Societa> societaControllo = societaRepository.findSocietaByPartitaIvaAndRagioneSociale(pIva, ragioneSociale);

        if (societaControllo.size() != 0) {
            return false;
        }
        else {
        	return true;
        }
    }
    

    
}
