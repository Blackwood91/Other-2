package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboOdmPdg;

public interface AlboOdmPdgRepository extends JpaRepository<AlboOdmPdg, Long> {
	
	@Query(value = " SELECT a FROM AlboOdmPdg a WHERE a.rom = :rom")
	public List<AlboOdmPdg> findByRom(@Param("rom")Long rom);
}
