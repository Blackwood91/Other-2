package com.giustizia.mediazionecivile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.Qualifica;

public interface QualificaRepository extends JpaRepository<Qualifica, Long> {
    @Query("SELECT e FROM Qualifica e WHERE e.idQualifica NOT IN (:idQualifica, :idQualifica2)")
    List<Qualifica> findAllExcludingIdQualificaFor2(@Param("idQualifica") Long idQualifica, @Param("idQualifica2") Long idQualifica2);
}
