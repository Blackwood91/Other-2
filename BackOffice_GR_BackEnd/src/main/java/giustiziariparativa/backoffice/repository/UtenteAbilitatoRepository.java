package giustiziariparativa.backoffice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import giustiziariparativa.backoffice.model.UtenteAbilitato;
import jakarta.transaction.Transactional;

public interface UtenteAbilitatoRepository extends JpaRepository<UtenteAbilitato, Long> {

    public UtenteAbilitato findByCodiceFiscaleUtente(String codiceFiscaleUtente);

	@Modifying
    @Query(value = "  INSERT INTO utente_abilitato ("
	    		+ "        nome_utente,"
	    		+ "        cognome_utente,"
	    		+ "        codice_fiscale_utente,"
	    		+ "        ente_appartenenza,"
	    		+ "        ruolo_utente_id,"
	    		+ "        abilitato"
	    		+ "    ) VALUES ("
	    		+ "        :nomeUtente,"
	    		+ "        :cognomeUtente,"
	    		+ "        :codiceFiscaleUtente,"
	    		+ "        :enteAppartenenza,"	    		
	    		+ "        :idRuoloUtente,"
	    		+ "        :abilitato"
	    		+ "    )", nativeQuery = true)
    @Transactional
	public void inserimentoUtenteCSV(@Param("nomeUtente") String nomeUtente, @Param("cognomeUtente") String cognomeUtente, @Param("codiceFiscaleUtente")String codiceFiscaleUtente,
									@Param("enteAppartenenza")String enteAppartenenza,@Param("idRuoloUtente") int idRuoloUtente, @Param("abilitato") int abilitato);
			   						
    @Query(value = "SELECT RUOLO_UTENTE_ID FROM utente_abilitato WHERE CODICE_FISCALE_UTENTE = :codiceFiscale", nativeQuery = true)
	Long utentiAbilitato(@Param("codiceFiscale") String codiceFiscale);
    
    @Query(value = " SELECT ABILITATO, DATA_LOGIN, DATA_LOGOUT, ID_UTENTE, RUOLO_UTENTE_ID, CODICE_FISCALE_UTENTE, COGNOME_UTENTE, ENTE_APPARTENENZA, "
	    		+  " NOME_UTENTE, DATA_MODIFICA, DATA_INSERIMENTO, DATA_DISABILITA "
	    		+  " FROM utente_abilitato"
	    		+  " WHERE LOWER(NOME_UTENTE) LIKE %:searchText% OR UPPER(NOME_UTENTE) LIKE %:searchText%"
	    		+  " OR LOWER(COGNOME_UTENTE) LIKE %:searchText% OR UPPER(COGNOME_UTENTE) LIKE %:searchText%"
	    		+  " OR LOWER(CODICE_FISCALE_UTENTE) LIKE %:searchText% OR UPPER(CODICE_FISCALE_UTENTE) LIKE %:searchText% ", nativeQuery = true)
    Page<UtenteAbilitato> getAllUtentiAbilitatiByText(@Param("searchText") String searchText, Pageable pageable );
    
	
	@Query(value = " SELECT codice_fiscale_utente FROM utente_abilitato WHERE codice_fiscale_utente = :codiceFiscale ", nativeQuery = true)
	public String findCodiceFiscale(@Param("codiceFiscale") String codiceFiscale);

	@Query(value = "SELECT MAX (data_modifica) FROM utente_abilitato", nativeQuery = true)
	public String getDataUltimaModificaUtenteMax();

	@Query(value = "SELECT MAX (data_disabilita) FROM utente_abilitato", nativeQuery = true)
	public String getDataUltimoDisabilitaUtenteMax();


}
