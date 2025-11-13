package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.EmissionePdgOdm;

public interface EmissionePdgOdmRepository extends JpaRepository<EmissionePdgOdm, Long> {

	@Query("SELECT e.idEmissionePdg, t.tipoPdg, e.dataEmissione"
		       + " FROM EmissionePdgOdm e "
		       + " LEFT JOIN TipoPdg t ON t.idTipoPdg = e.idTipoPdg"
		       + " WHERE e.idRichiesta = :idRichiesta")
	Page<Object[]> findAllForElenco(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
}
