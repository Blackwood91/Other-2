package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboEfSedi;

public interface AlboEfSediRepository extends JpaRepository<AlboEfSedi, Long> {
	
	@Query(value = " SELECT a FROM AlboEfSedi a WHERE a.numeReg = :numeReg")
	public List<AlboEfSedi> findByNumReg(@Param("numeReg")Long numeReg);

}
