package giustiziariparativa.backoffice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import giustiziariparativa.backoffice.DTO.EnteAttestatoSelect;
import giustiziariparativa.backoffice.DTO.MediatoreGiustiziaRiparativaTAB;
import giustiziariparativa.backoffice.model.EnteAttestato;
import giustiziariparativa.backoffice.model.UtenteAbilitato;
import giustiziariparativa.backoffice.repository.EnteAttestatoRepository;
import giustiziariparativa.util.TipoOperazione;

@Service
public class EnteAttestatoService {

    @Autowired
    EnteAttestatoRepository enteAttestatoRepository;

    @Autowired
    RegistroOperazioneMediatoreService registroOperazioneMediatoreService;

    public Map<String, Object> getAllEnti(){
	    Map<String, Object> response = new HashMap<>();
	    
	    List<Object[]> resultList = enteAttestatoRepository.getAllEnte();

		List<EnteAttestatoSelect> EnteAttestatoList = new ArrayList<>();
 
		for (Object[] obj : resultList) {
			EnteAttestatoSelect EnteAttestatoROW = new EnteAttestatoSelect(obj);
			EnteAttestatoList.add(EnteAttestatoROW);
		}
  
		response.put("result", EnteAttestatoList);

		return response;
    }
    
    public Map<String, Object> getAllEntiFormatore(String ente){
	    Map<String, Object> response = new HashMap<>();
	    
	    List<Object[]> resultList = enteAttestatoRepository.getAllEntiFormatore(Long.parseLong(ente));

		List<EnteAttestatoSelect> EnteAttestatoList = new ArrayList<>();
 
		for (Object[] obj : resultList) {
			EnteAttestatoSelect EnteAttestatoROW = new EnteAttestatoSelect(obj);
			EnteAttestatoList.add(EnteAttestatoROW);
		}
	    
		response.put("result", EnteAttestatoList);

		return response;
    }

    public EnteAttestato inserisciEnteAttestato(EnteAttestato ente) {
        ente.setDataInserimentoEnte(new Date());
        enteAttestatoRepository.save(ente);
        return ente;
    }
    
    public EnteAttestato UpdateEnteAttestato(EnteAttestato ente, Long id) {
        UtenteAbilitato utente = new UtenteAbilitato();

        ente.setIdEnteAttestato(id);
        enteAttestatoRepository.updateEnte(ente.getIsConvenzionato(), ente.getIdEnteAttestato(), ente.getEnteAttestato(), ente.getTipologiaEnte());
        registroOperazioneMediatoreService.registraOperazioneMediatore(utente, TipoOperazione.AGGIORNAMENTO);
        return ente;
    }
    
    public Boolean findByIdEnte(Long idEnte) {
    	String valore = enteAttestatoRepository.findByIdEnte(idEnte);
		return valore == null ? true : false;
    	
    }
    

    // public void elimininaEnteAttestato(Long id) {
    //     //registroOperazioneMediatoreService.registraOperazioneMediatore(utente, TipoOperazione.INSERIMENTO_MEDIATORE);
    //     return ente;
    // }

    public EnteAttestato findEnteAttestatoById(Long idEnteAttestato){
        return enteAttestatoRepository.findById(idEnteAttestato).get();
    }

}
