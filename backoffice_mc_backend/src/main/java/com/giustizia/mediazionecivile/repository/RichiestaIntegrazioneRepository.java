package com.giustizia.mediazionecivile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.RichiestaIntegrazione;

public interface RichiestaIntegrazioneRepository extends JpaRepository<RichiestaIntegrazione, Long> {

    Optional<RichiestaIntegrazione> findByIdRichiesta(Long idRichiesta);
    
    Optional<RichiestaIntegrazione> findFirstByIdRichiestaOrderByDataRichiestaDesc(Long idRichiesta);
    
    
}
