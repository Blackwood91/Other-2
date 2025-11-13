package giustiziariparativa.backoffice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import giustiziariparativa.backoffice.model.RegistroOperazioneUtente;
import giustiziariparativa.util.TipoOperazione;

public interface RegistroOperazioneUtenteRepository extends JpaRepository<RegistroOperazioneUtente, Long> {

    List<RegistroOperazioneUtente> findByCodiceFiscaleOperatore(String codiceFiscale);

    List<RegistroOperazioneUtente> findByTipoOperazione(TipoOperazione tipoOperazione);

}
