package com.giustizia.mediazionecivile.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboEntiFormatori;

public interface AlboEntiFormatoriRepository extends JpaRepository<AlboEntiFormatori, Long> {
	
	@Query(value = " SELECT * "
    		+  " FROM albo_ef"
    		+  " WHERE LOWER(denominazione) LIKE :searchText% OR UPPER(denominazione) LIKE :searchText%", nativeQuery = true)
	Page<AlboEntiFormatori> getAllEntiFormatoriByDenominazione(@Param("searchText") String denominazione, Pageable pageable );
	
	@Query(value = " SELECT * "
    		+  " FROM albo_ef"
    		+  " WHERE LOWER(num_reg) LIKE :searchText% OR UPPER(num_reg) LIKE :searchText%", nativeQuery = true)
	Page<AlboEntiFormatori> getAllEntiFormatoriByNumeroRegistro(@Param("searchText") String searchText, Pageable pageable );
	
}
