package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giustizia.mediazionecivile.model.TipoRichiedente;

import com.giustizia.mediazionecivile.projection.TipoRichiedenteProjection;

public interface TipoRichiedenteRepository extends JpaRepository<TipoRichiedente, Long>{
    
    List<TipoRichiedenteProjection> findAllBy();
}
