package com.giustizia.mediazionecivile.repository;

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


	@Query(value = "SELECT societa.ID_SOCIETA, societa.RAGIONE_SOCIALE, societa.PARTITA_IVA, societa.CODICE_FISCALE_SOCIETA,"
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
			+ " LEFT JOIN ef_richieste ON societa.id_societa = ef_richieste.id_societa", nativeQuery = true)
	public Page<Object[]> findAllForSocietaAndRichiesta(Pageable pageable);

	@Query(value = "SELECT SELECT societa.ID_SOCIETA, societa.RAGIONE_SOCIALE, societa.PARTITA_IVA, societa.CODICE_FISCALE_SOCIETA,"
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
			+ " WHERE LOWER(societa.RAGIONE_SOCIALE) LIKE %:searchText% OR LOWER(societa.PARTITA_IVA) LIKE %:searchText%", nativeQuery = true)
	public Page<Object[]> findAllForSocietaAndRichiestaByText(@Param("searchText") String searchText, Pageable pageable);

	// controllo Update Società
	@Query(value = "SELECT * FROM societa WHERE partita_iva = :partitaIva AND ragione_sociale = :ragioneSociale", nativeQuery = true)
	public List<Societa> findSocietaByPartitaIvaAndRagioneSociale(@Param("partitaIva") String partitaIva,
			@Param("ragioneSociale") String ragioneSociale);
	
    @Query("SELECT s FROM Societa s WHERE s.id = :idSocieta")
	   SocietaDomIscProjection findProjectedByIdSocieta(@Param("idSocieta") Long idSocieta);

}
