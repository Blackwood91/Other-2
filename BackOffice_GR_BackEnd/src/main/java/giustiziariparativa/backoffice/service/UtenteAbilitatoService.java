package giustiziariparativa.backoffice.service;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import giustiziariparativa.backoffice.model.UtenteAbilitato;
import giustiziariparativa.backoffice.repository.UtenteAbilitatoRepository;
import giustiziariparativa.util.EstraiCsv;
import giustiziariparativa.util.TipoOperazione;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UtenteAbilitatoService {

    @Autowired
    private UtenteAbilitatoRepository utenteAbilitatoRepository;

    @Autowired
    private RegistroOperazioneUtenteService registroOperazioneUtenteService;

    public Page<UtenteAbilitato> getAllUtentiAbilitati(Pageable pageable, String searchText, String colonna) {
        Page<UtenteAbilitato> utentiElenco = utenteAbilitatoRepository.findAll(pageable);

        return utentiElenco;
    }

    public Page<UtenteAbilitato> getAllUtentiAbilitatiByText(Pageable pageable, String searchText, String colonna) {
        Page<UtenteAbilitato> utentiElenco = utenteAbilitatoRepository.getAllUtentiAbilitatiByText(searchText,
                pageable);

        return utentiElenco;
    }

    public Object inserisciUtenza(UtenteAbilitato utente) {
        utente.setDataInserimentoUtente(new Date());
        return utenteAbilitatoRepository.save(utente);
    }

    public Object UpdateUtenti(UtenteAbilitato utente) {
        utente.setDataModificaUtente(new Date());
        return utenteAbilitatoRepository.save(utente);
    }

    public UtenteAbilitato createUtente(String nomeUtente, String cognomeUtente, String codiceFiscaleUtente,
            String enteAppartenenza, Long idRuoloUtente, int isAbilitato) {
        Objects.requireNonNull(nomeUtente, "Il nome utente non può essere nullo");
        Objects.requireNonNull(cognomeUtente, "Il cognome utente non può essere nullo");
        Objects.requireNonNull(codiceFiscaleUtente, "Il codice fiscale utente non può essere nullo");

        UtenteAbilitato utente = new UtenteAbilitato();
        utente.setNomeUtente(nomeUtente);
        utente.setCognomeUtente(cognomeUtente);
        utente.setCodiceFiscaleUtente(codiceFiscaleUtente);
        utente.setEnteAppartenenza("");
        utente.setIdRuoloUtente(idRuoloUtente);
        utente.setIsAbilitato(0);

        utenteAbilitatoRepository.save(utente);

        utente.setDataInserimentoUtente(new Date());
        // registroOperazioneUtenteService.registraOperazioneUtente(utente,
        // TipoOperazione.INSERIMENTO_UTENTE);

        return utente;
    }

    public UtenteAbilitato abilitaUtenteByCodiceFiscale(String codiceFiscaleUtente) {
        UtenteAbilitato utente = utenteAbilitatoRepository.findByCodiceFiscaleUtente(codiceFiscaleUtente);

        if (utente != null) {
            utente.setIsAbilitato(1); // Imposta l'utente come abilitato

            // registroOperazioneUtenteService.registraOperazioneUtente(utente,
            // TipoOperazione.ABILITA);
            return utenteAbilitatoRepository.save(utente);

        } else {
            throw new EntityNotFoundException("Utente non trovato.");
        }
    }

    public UtenteAbilitato disabilitaUtenteByCodiceFiscale(String codiceFiscaleUtente) {
        UtenteAbilitato utente = utenteAbilitatoRepository.findByCodiceFiscaleUtente(codiceFiscaleUtente);

        if (utente != null) {
            utente.setIsAbilitato(0); // Imposta l'utente come disabilitato

            // registroOperazioneUtenteService.registraOperazioneUtente(utente,
            // TipoOperazione.DISABILITA);
            return utenteAbilitatoRepository.save(utente);
        } else {
            throw new EntityNotFoundException("Utente non trovato.");
        }
    }

    public void updateUtenteAbilitato(Long utenteId, String nomeUtente, String cognomeUtente,
            String codiceFiscaleUtente, String enteAppartenenza, Long idRuoloUtente) {
        UtenteAbilitato utente = utenteAbilitatoRepository.findById(utenteId).orElse(null);

        if (utente != null) {
            boolean modifiche = false; // Flag per tenere traccia delle modifiche

            if (nomeUtente != null) {
                utente.setNomeUtente(nomeUtente);
                modifiche = true;
            }

            if (cognomeUtente != null) {
                utente.setCognomeUtente(cognomeUtente);
                modifiche = true;
            }

            if (codiceFiscaleUtente != null) {
                utente.setCodiceFiscaleUtente(codiceFiscaleUtente);
                modifiche = true;
            }

            if (enteAppartenenza != null) {
                utente.setEnteAppartenenza(enteAppartenenza);
                modifiche = true;
            }

            if (idRuoloUtente != null) {
                utente.setIdRuoloUtente(idRuoloUtente);
                modifiche = true;
                registroOperazioneUtenteService.registraOperazioneUtente(utente, TipoOperazione.ASSEGNA_RUOLO);
            }

            if (modifiche) {
                utenteAbilitatoRepository.save(utente);
                registroOperazioneUtenteService.registraOperazioneUtente(utente, TipoOperazione.AGGIORNAMENTO);
            }
        }
    }

    // Esclude le righe vuote che potrebbero uscire in seguito a delle cancellazioni
    // nel file di caricamento di csv
    private boolean checkRowCsv(String row) {
        // (eclude questa casistica "[;;;;;;]")
        return !Pattern.matches("^[;\\[\\]]*$", row);
    }

    public String findCodiceFiscale(String codiceFiscale) {
        return utenteAbilitatoRepository.findCodiceFiscale(codiceFiscale);
    }

    public String getDataUltimaModificaUtenteMax() {
        return utenteAbilitatoRepository.getDataUltimaModificaUtenteMax();
    }

    public String getDataUltimoDisabilitaUtenteMax() {
        return utenteAbilitatoRepository.getDataUltimoDisabilitaUtenteMax();
    }
}
