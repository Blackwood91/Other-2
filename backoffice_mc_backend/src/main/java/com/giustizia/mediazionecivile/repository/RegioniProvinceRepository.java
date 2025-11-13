package com.giustizia.mediazionecivile.repository;

import java.util.List;
import java.util.Optional;

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

	@Query(value = "SELECT * FROM regioni_province WHERE LOWER(nome_provincia) = LOWER(:nomeComune) ", nativeQuery = true)
	Optional<RegioneProvince> findByNomeProvincia(@Param("nomeComune") String nomeComune);

	@Query(value = "SELECT * FROM regioni_province WHERE codice_provincia = :codiceProvincia", nativeQuery = true)	
	RegioneProvince findByCodiceProvincia(@Param("codiceProvincia") Long codiceProvincia); 
}
