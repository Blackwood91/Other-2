package com.giustizia.mediazionecivile.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.dto.SocietaDTO;
import com.giustizia.mediazionecivile.model.Societa;
import com.giustizia.mediazionecivile.projection.RichiestaDomIscProjection;
import com.giustizia.mediazionecivile.projection.SocietaDomIscProjection;

public interface SocietaRepository extends JpaRepository<Societa, Long> {
	
	@Query("SELECT s.id, s.ragioneSociale, s.partitaIva, s.codiceFiscaleSocieta, " +
		       "st.descrizione AS statoDescrizione, stEf.descrizione AS statoEfDescrizione " +
		       "FROM Societa s " +
		       "LEFT JOIN Richiesta r ON s.id = r.idRichiesta " +
		       "LEFT JOIN EfRichiesta efr ON s.id = efr.idRichiesta " +
		       "LEFT JOIN Stato st ON (st.id = r.idStato AND r.idStato IS NOT NULL) " +
		       "LEFT JOIN Stato stEf ON (stEf.id = efr.idStato AND efr.idStato IS NOT NULL) " +
		       "JOIN SocietaUtenti su ON su.idSocieta = s.id " +
		       "WHERE su.idUtente = :idUtente")
		Page<Object[]> getAllByUtente(@Param("idUtente") long idUtente, Pageable pageable);
		
		@Query(value = "SELECT s.id, s.ragioneSociale, s.partitaIva, s.codiceFiscaleSocieta, " +
	               "st.descrizione, stEf.descrizione " +
	               "FROM Societa s " +
	               "LEFT JOIN Richiesta r ON s.id = r.idRichiesta " +
	               "LEFT JOIN EfRichiesta efr ON s.id = efr.idRichiesta " +
	               "LEFT JOIN Stato st ON (st.id = r.idStato AND r.idStato IS NOT NULL) " +
	               "LEFT JOIN Stato stEf ON (stEf.id = efr.idStato AND efr.idStato IS NOT NULL) " +
	               "JOIN SocietaUtenti su ON su.idSocieta = s.id " +
	               "WHERE su.idUtente = :idUtente " +
	               "AND (LOWER(s.ragioneSociale) LIKE %:searchText% " +
	               "OR LOWER(s.partitaIva) LIKE %:searchText%)")
	public Page<Object[]> findAllForSocietaAndRichiestaByTexts(@Param("searchText") String searchText, @Param("idUtente") long idUtente, Pageable pageable);

		
	
	@Query(value = "SELECT societa.*,"
			+ "       CASE WHEN societa.id_societa = richieste.id_societa"
			+ "            THEN 'In Compilazione'"
			+ "            ELSE NULL"
			+ "       END as stato_richiesta_odm,"
			+ "       CASE WHEN societa.id_societa = ef_richieste.id_societa"
			+ "            THEN 'In Compilazione'"
			+ "            ELSE NULL "
			+ "       END as stato_richiesta_ef"
			+ " FROM societa"
			+ " LEFT JOIN richieste ON societa.id_societa = richieste.id_societa"
			+ " LEFT JOIN ef_richieste ON societa.id_societa = ef_richieste.id_societa"
			+ " WHERE societa.id_societa = :idSocieta"
			, nativeQuery = true)
	public Object findByIdForSocietaAndRichiesta(@Param("idSocieta") Long idSocieta);
	
	
	@Query(value = "SELECT societa.*,"
			+ "       CASE WHEN societa.id_societa = richieste.id_societa"
			+ "            THEN 'In Compilazione'"
			+ "            ELSE NULL"
			+ "       END as stato_richiesta_odm,"
			+ "       CASE WHEN societa.id_societa = ef_richieste.id_societa"
			+ "            THEN 'In Compilazione'"
			+ "            ELSE NULL "
			+ "       END as stato_richiesta_ef"
			+ " FROM societa"
			+ " LEFT JOIN richieste ON societa.id_societa = richieste.id_societa"
			+ " LEFT JOIN ef_richieste ON societa.id_societa = ef_richieste.id_societa"
			+ " WHERE societa.id_societa = :idSocieta AND richieste.id_tipo_richiesta = 1"
			, nativeQuery = true)
	public Object findByIRichiestaAndIdTipoRichiesta(@Param("idSocieta") Long idSocieta);


	@Query(value = "SELECT s.id, s.ragioneSociale, s.partitaIva, s.codiceFiscaleSocieta,"
			+ " st.descrizione, stEf.descrizione " +
            "FROM Societa s " +
            "LEFT JOIN Richiesta r ON s.id = r.idRichiesta " +
            "LEFT JOIN EfRichiesta efr ON s.id = efr.idRichiesta " +
            "LEFT JOIN Stato st ON (st.id = r.idStato AND r.idStato IS NOT NULL) " +
            "LEFT JOIN Stato stEf ON (st.id = efr.idStato AND efr.idStato IS NOT NULL)")
	public Page<Object[]> findAllForSocietaAndRichiesta(Pageable pageable);


	@Query(value = "SELECT s.id, s.ragioneSociale, s.partitaIva, s.codiceFiscaleSocieta,"
			+ " st.descrizione, stEf.descrizione " +
            "FROM Societa s " +
            "LEFT JOIN Richiesta r ON s.id = r.idRichiesta " +
            "LEFT JOIN EfRichiesta efr ON s.id = efr.idRichiesta " +
            "LEFT JOIN Stato st ON (st.id = r.idStato AND r.idStato IS NOT NULL) " +
            "LEFT JOIN Stato stEf ON (st.id = efr.idStato AND efr.idStato IS NOT NULL) " +
			"WHERE LOWER(s.ragioneSociale) LIKE %:searchText% " +
			"OR LOWER(s.partitaIva) LIKE %:searchText%")
	public Page<Object[]> findAllForSocietaAndRichiestaByText(@Param("searchText") String searchText, Pageable pageable);

	// controllo Update Società
	@Query(value = "SELECT * FROM societa WHERE partita_iva = :partitaIva AND ragione_sociale = :ragioneSociale", nativeQuery = true)
	public List<Societa> findSocietaByPartitaIvaAndRagioneSociale(@Param("partitaIva") String partitaIva,
			@Param("ragioneSociale") String ragioneSociale);
	
	@Query(value = "SELECT * FROM societa WHERE partita_iva = :partitaIva AND ragione_sociale = :ragioneSociale AND id_societa != :id", nativeQuery = true)
	public List<Societa> findSocietaByPartitaIvaAndRagioneSocialeAndId(@Param("partitaIva") String partitaIva,
			@Param("ragioneSociale") String ragioneSociale, @Param("id") BigDecimal id);
	
    @Query("SELECT s FROM Societa s WHERE s.id = :idSocieta")
	   SocietaDomIscProjection findProjectedByIdSocieta(@Param("idSocieta") Long idSocieta);
    
    List<Societa> findSocietaByPartitaIva(String piva);

}
