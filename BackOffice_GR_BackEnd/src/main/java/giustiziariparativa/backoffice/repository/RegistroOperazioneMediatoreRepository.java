package giustiziariparativa.backoffice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import giustiziariparativa.backoffice.model.RegistroOperazioneMediatore;
import giustiziariparativa.util.TipoOperazione;

public interface RegistroOperazioneMediatoreRepository extends JpaRepository<RegistroOperazioneMediatore, Long> {

    List<RegistroOperazioneMediatore> findByCodiceFiscaleOperatore(String codiceFiscale);

    List<RegistroOperazioneMediatore> findByTipoOperazione(TipoOperazione tipoOperazione);

        @Query(value = 
        "SELECT CODICE_FISCALE_OPERATORE, TIPO_OPERAZIONE, DATA_UTILIMA_MODIFICA_MEDIATORE " +
        "FROM ( " +
        "    SELECT CODICE_FISCALE_OPERATORE, TIPO_OPERAZIONE, DATA_UTILIMA_MODIFICA_MEDIATORE, " +
        "           RANK() OVER (PARTITION BY CODICE_FISCALE_OPERATORE ORDER BY DATA_UTILIMA_MODIFICA_MEDIATORE DESC) AS r " +
        "    FROM REGISTRO_OPERAZIONE_MEDIATORE " +
        ") " +
        "WHERE r = 1", nativeQuery = true)
    List<Object[]> findTipoOperazioneAndUltimaModificaPerOperatore();

}
