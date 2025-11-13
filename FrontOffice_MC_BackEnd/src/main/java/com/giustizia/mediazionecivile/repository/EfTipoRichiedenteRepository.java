package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giustizia.mediazionecivile.model.EfTipoRichiedenta;
import com.giustizia.mediazionecivile.projection.EfTipoRichiedenteProjection;

public interface EfTipoRichiedenteRepository extends JpaRepository<EfTipoRichiedenta, Long> {
   List<EfTipoRichiedenteProjection> findAllBy();
}
