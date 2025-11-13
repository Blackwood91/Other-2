package giustiziariparativa.backoffice.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import giustiziariparativa.backoffice.model.RegistroOperazioneMediatore;
import giustiziariparativa.backoffice.model.UtenteAbilitato;
import giustiziariparativa.backoffice.repository.RegistroOperazioneMediatoreRepository;
import giustiziariparativa.util.TipoOperazione;

@Service
public class RegistroOperazioneMediatoreService {

    @Autowired
    RegistroOperazioneMediatoreRepository registroOperazioneMediatoreRepository;

    public void registraOperazioneMediatore(UtenteAbilitato utente, TipoOperazione tipoOperazione) {
        RegistroOperazioneMediatore operazioneMediatore = new RegistroOperazioneMediatore();
        operazioneMediatore.setCodiceFiscaleOperatore(utente.getCodiceFiscaleUtente());
        operazioneMediatore.setTipoOperazione(tipoOperazione);
        operazioneMediatore.setDataOperazione(new Date());
        registroOperazioneMediatoreRepository.save(operazioneMediatore);
        operazioneMediatore.setDataUltimaModificaMediatore(new Date());
    }

    public List<RegistroOperazioneMediatore> getOperazioniMediatore(UtenteAbilitato utente) {
        return registroOperazioneMediatoreRepository.findByCodiceFiscaleOperatore(utente.getCodiceFiscaleUtente());

    }

    public List<RegistroOperazioneMediatore> getOperazioniMediatorePerTipo(TipoOperazione tipoOperazione) {
        return registroOperazioneMediatoreRepository.findByTipoOperazione(tipoOperazione);
    }

    /*
     * Creare altri metodi in base alle esigenze (da vedere)
     * tipo "getAllOperazioni"
     */

}
