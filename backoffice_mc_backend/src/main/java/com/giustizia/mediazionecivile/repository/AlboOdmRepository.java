package com.giustizia.mediazionecivile.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboOdm;

public interface AlboOdmRepository extends JpaRepository<AlboOdm, Long> {
	
	@Query(value = " SELECT * "
    		+  " FROM albo_odm"
    		+  " WHERE LOWER(denominazione_organismo) LIKE :searchText% OR UPPER(denominazione_organismo) LIKE :searchText%", nativeQuery = true)
	Page<AlboOdm> getAllOdmByDenominazione(@Param("searchText") String denominazione, Pageable pageable );
	
	@Query(value = " SELECT * "
    		+  " FROM albo_odm"
    		+  " WHERE LOWER(rom) LIKE :searchText% OR UPPER(rom) LIKE :searchText%", nativeQuery = true)
	Page<AlboOdm> getAllOdmByNumeroRegistro(@Param("searchText") String searchText, Pageable pageable );

}
