package giustiziariparativa.backoffice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import giustiziariparativa.backoffice.model.RegistroOperazioneUtente;
import giustiziariparativa.backoffice.model.UtenteAbilitato;
import giustiziariparativa.backoffice.repository.RegistroOperazioneUtenteRepository;
import giustiziariparativa.util.TipoOperazione;

@Service
public class RegistroOperazioneUtenteService {

    @Autowired
    RegistroOperazioneUtenteRepository registroOperazioneUtenteRepository;

    public void registraOperazioneUtente(UtenteAbilitato utente, TipoOperazione tipoOperazione) {
        RegistroOperazioneUtente operazioneUtente = new RegistroOperazioneUtente();
        operazioneUtente.setCodiceFiscaleOperatore(utente.getCodiceFiscaleUtente());
        operazioneUtente.setTipoOperazione(tipoOperazione);
        operazioneUtente.setDataOperazione(new Date());
        registroOperazioneUtenteRepository.save(operazioneUtente);
        operazioneUtente.setDataUltimaModificaUtente(new Date());
    }

    public List<RegistroOperazioneUtente> getOperazioniUtente(UtenteAbilitato utente) {
        return registroOperazioneUtenteRepository.findByCodiceFiscaleOperatore(utente.getCodiceFiscaleUtente());

    }

    public List<RegistroOperazioneUtente> getOperazioniPerTipo(TipoOperazione tipoOperazione) {
        return registroOperazioneUtenteRepository.findByTipoOperazione(tipoOperazione);
    }

    /*
     * Creare altri metodi in base alle esigenze (da vedere)
     * tipo "getAllOperazioni"
     */

}
