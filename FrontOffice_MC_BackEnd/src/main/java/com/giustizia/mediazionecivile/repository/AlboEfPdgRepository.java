package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboEfPdg;

public interface AlboEfPdgRepository extends JpaRepository<AlboEfPdg, Long> {

	@Query(value = " SELECT a FROM AlboEfPdg a WHERE a.numReg = :numReg")
	public List<AlboEfPdg> findByNumReg(@Param("numReg")Long numReg);
}
