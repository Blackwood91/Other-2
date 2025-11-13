package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboOdmSedi;

public interface AlboOdmSediRepository extends JpaRepository<AlboOdmSedi, Long> {

	@Query(value = " SELECT a FROM AlboOdmSedi a WHERE a.rom = :rom")
	public List<AlboOdmSedi> findByRom(@Param("rom")Long rom);
}
