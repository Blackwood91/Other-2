package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.RegioneProvince;
import com.giustizia.mediazionecivile.projection.ProvinceProjection;
import com.giustizia.mediazionecivile.projection.RegioneProjection;

public interface RegioniProvinceRepository extends JpaRepository<RegioneProvince, Long> {
    @Query("SELECT r FROM RegioneProvince r WHERE r.id IN (SELECT MAX(rp.id) FROM RegioneProvince rp GROUP BY rp.codiceRegione)")
	List<RegioneProjection> findAllByRegioni();
    
    @Query("SELECT r FROM RegioneProvince r WHERE r.codiceRegione = :codiceRegione")
	List<ProvinceProjection> findAllByRegioneForProvince(@Param("codiceRegione") int codiceRegione); 
}
