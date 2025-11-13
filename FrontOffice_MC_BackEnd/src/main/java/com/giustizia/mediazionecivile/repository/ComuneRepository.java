package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.giustizia.mediazionecivile.model.Comune;
import com.giustizia.mediazionecivile.projection.ComuneProjection;

public interface ComuneRepository extends JpaRepository<Comune, Long> {	
    List<ComuneProjection> findByNomeComuneIgnoreCaseStartingWithAndStatoOrderByNomeComuneAsc(String nomeComune, String stato, Pageable pageable);
    
    ComuneProjection findByIdCodComune(Long idComune);

}
