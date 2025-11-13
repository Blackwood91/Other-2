package com.giustizia.mediazionecivile.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;

public interface StatoModuliRichiestaFigliRepository extends JpaRepository<StatoModuliRichiestaFigli, Long> {
	
	boolean existsByIdModuloAndIdRichiesta(Long idModulo, Long idRichiesta);

	boolean existsByIdModuloAndIdRichiestaAndCompletato(Long idModulo, Long idRichiesta, int completato);
	
	boolean existsByIdModuloAndIdRichiestaAndValidato(Long idModulo, Long idRichiesta, int validato);
	
	boolean existsByIdModuloAndIdRichiestaAndAnnullato(Long idModulo, Long idRichiesta, int annullato);
	
	boolean existsByIdModuloAndIdRichiestaAndIdAnagraficaAndCompletato(Long idModulo, Long idRichiesta, Long idAnagrafica, int completato);
	
	boolean existsByIdModuloAndIdRichiestaAndIdAnagrafica(Long idModulo, Long idRichiesta, Long idAnagrafica);

	Optional<StatoModuliRichiestaFigli> findById(Long progressivoModulo);

	Optional<StatoModuliRichiestaFigli> findByIdModuloAndIdRichiesta(Long idModulo, Long idRichiesta);

	Optional<StatoModuliRichiestaFigli> findByIdModuloAndIdRichiestaAndIdRiferimento(Long idModulo, Long idRichiesta,
			Long idRiferimento);
	
	Optional<StatoModuliRichiestaFigli> findByIdModuloAndIdRichiestaAndIdAnagrafica(Long idModulo, Long idRichiesta,
			Long idAnagrafica);
	
	Page<StatoModuliRichiestaFigli> findByIdModuloAndIdRichiestaAndIdRiferimento(Long idModulo, Long idRichiesta, Long idRiferimento, Pageable pageable);

	Page<StatoModuliRichiestaFigli> findByIdModuloAndIdRichiesta(Long idModulo, Long idRichiesta, Pageable pageable);
	
	Page<StatoModuliRichiestaFigli> findByIdModuloAndIdRichiestaAndIdAnagrafica(Long idModulo, Long idRichiesta,
			Long idAnagrafica, Pageable pageable);
	
	Page<StatoModuliRichiestaFigli> findByIdModuloAndIdRichiestaOrderByDataInserimentoDesc(Long idModulo, Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s FROM StatoModuliRichiestaFigli s WHERE s.id IN (SELECT MIN(s2.id) FROM StatoModuliRichiestaFigli s2 WHERE s2.idRichiesta=:idRichiesta GROUP BY s2.idModulo) ORDER BY s.idModulo")
	List<StatoModuliRichiestaFigli> findAllDistinctByIdModuloAndIdRichiesta(@Param("idRichiesta") Long idRichiesta);

	@Query("SELECT s FROM StatoModuliRichiestaFigli s WHERE (s.idModulo = :idModulo) AND s.idRichiesta = :idRichiesta")
	List<StatoModuliRichiestaFigli> findAllByIdModuloAndIdRichiesta(@Param("idModulo") Long idModulo,
			@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT s FROM StatoModuliRichiestaFigli s WHERE (s.idModulo = :idModulo) AND s.idRichiesta = :idRichiesta AND s.completato = 1")
	List<StatoModuliRichiestaFigli> findAllByIdModuloAndIdRichiestaAndCompletato(@Param("idModulo") Long idModulo,
			@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT s FROM StatoModuliRichiestaFigli s WHERE (s.idModulo = :idModulo) AND s.idRichiesta = :idRichiesta AND s.idAnagrafica = :idAnagrafica")
	List<StatoModuliRichiestaFigli> findAllByIdModuloAndIdRichiestaAndIdAnagrafica(@Param("idModulo") Long idModulo,
			@Param("idRichiesta") Long idRichiesta, @Param("idAnagrafica") Long idAnagrafica);
	
	@Query("SELECT s FROM StatoModuliRichiestaFigli s WHERE (s.idModulo = :idModulo OR s.idModulo = :idModulo2) AND s.idRichiesta = :idRichiesta")
	List<StatoModuliRichiestaFigli> findAllByFor2IdModuliAndIdRichiesta(
			@Param("idModulo") Long idModulo, @Param("idModulo2") Long idModulo2, 
			@Param("idRichiesta") Long idRichiesta);

	@Query("SELECT s FROM StatoModuliRichiestaFigli s WHERE (s.idModulo = :idModulo OR s.idModulo = :idModulo2 OR s.idModulo = :idModulo3) AND s.idRichiesta = :idRichiesta")
	List<StatoModuliRichiestaFigli> findAllByFor3IdModuliAndIdRichiesta(
			@Param("idModulo") Long idModulo, @Param("idModulo2") Long idModulo2, 
			@Param("idModulo3") Long idModulo3, @Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT s FROM StatoModuliRichiestaFigli s WHERE (s.idModulo = :idModulo OR s.idModulo = :idModulo2 OR s.idModulo = :idModulo3)"
			+ " AND s.idRichiesta = :idRichiesta AND idRiferimento = :idRiferimento")
	List<StatoModuliRichiestaFigli> findAllByFor3IdModuliAndIdRichiestaAndIdRiferimento(
			@Param("idModulo") Long idModulo, @Param("idModulo2") Long idModulo2, 
			@Param("idModulo3") Long idModulo3, @Param("idRichiesta") Long idRichiesta, @Param("idRiferimento") Long idRiferimento);
	
	@Query(value = "SELECT cls.id_anagrafica, cls.ID_CERTIFICAZIONE_LINGUA, cls.data_certificazione, cls.ente_certificatore, smrf.NOME_ALLEGATO, "
			+ "smrf.data_inserimento, smrf.ID_STATO_MODULI_RICHIESTA_FIGLI, smrf.completato, smrf.validato, smrf.annullato "
			+ "FROM (SELECT id_anagrafica, ID_CERTIFICAZIONE_LINGUA, MAX(data_certificazione) AS data_certificazione,MAX(ente_certificatore) AS ente_certificatore "
			+ "FROM certificazione_lingue_straniere cls "
			+ "WHERE cls.ID_CERTIFICAZIONE_LINGUA IN ("
			+ "SELECT ID_RIFERIMENTO "
			+ "FROM stato_moduli_richiesta_figli "
			+ "WHERE id_modulo = :idModulo AND id_richiesta = :idRichiesta AND id_anagrafica = :idAnagrafica) "
			+ "GROUP BY id_anagrafica, ID_CERTIFICAZIONE_LINGUA"
			+ ") cls "
			+ "LEFT JOIN stato_moduli_richiesta_figli smrf ON cls.id_anagrafica = smrf.id_anagrafica AND cls.ID_CERTIFICAZIONE_LINGUA = smrf.ID_RIFERIMENTO",
			nativeQuery = true)
	Page<Object[]> getCertificatoByIdModuloAndIdRichiestaAndIdAnagrafica(Long idModulo, Long idRichiesta,
			Long idAnagrafica, Pageable pageable);

}
