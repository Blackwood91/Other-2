package com.giustizia.mediazionecivile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.JobRichiesta;

public interface JobRichiestaRepository extends JpaRepository<JobRichiesta, Long>{
	
	boolean existsByIdRichiestaAndTipoRichiesta(Long idRichiesta, String tipoRichiesta);
	
	Optional<JobRichiesta> findByIdRichiestaAndTipoRichiesta(Long idRichiesta, String tipoRichiesta);
	
	Optional<JobRichiesta> findFirstByIdRichiestaAndTipoRichiestaOrderByDataOraRichiestaDesc(Long idRichiesta, 
			   String tipoRichiesta);
	
	@Query(value = "SELECT DATA_RICHIESTA FROM RICHIESTA_INTEGRAZIONE WHERE "
			+ "ID_RICHIESTA = :idRichiesta ORDER BY DATA_RICHIESTA DESC FETCH FIRST 1 ROW ONLY"
			, nativeQuery = true)
	public Object[] getAllAnagraficaMediatoriC(@Param("idRichiesta") Long idRichiesta);
}
