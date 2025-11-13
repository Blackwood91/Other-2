package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboOdmPdg;

public interface AlboOdmPdgRepository extends JpaRepository<AlboOdmPdg, Long> {
	
//	@Query(value = " SELECT  FROM AlboOdmPdg a WHERE a.numIscrAlbo = :numIscrAlbo")
//	public List<AlboOdmPdg> findByRom(@Param("numIscrAlbo")Long numIscrAlbo);
	
	@Query(value = "SELECT t.tipoPdg, e.dataEmissione "
			+ "FROM Richiesta r "
			+ "JOIN EmissionePdgOdm e ON r.numIscrAlbo = e.rom "
			+ "JOIN TipoPdg t ON e.idTipoPdg = t.idTipoPdg "
			+ "WHERE r.numIscrAlbo = :numIscrAlbo")
	public Page<Object[]> findByNumIscrAlbo(@Param("numIscrAlbo")Long numIscrAlbo, Pageable pageable);
	
}
