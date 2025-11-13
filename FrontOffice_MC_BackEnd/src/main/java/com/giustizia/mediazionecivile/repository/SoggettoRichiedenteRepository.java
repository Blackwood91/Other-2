package com.giustizia.mediazionecivile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giustizia.mediazionecivile.model.SoggettoRichiedente;

public interface SoggettoRichiedenteRepository extends JpaRepository<SoggettoRichiedente, Long> {
    
    public SoggettoRichiedente findSoggettoRichiedenteBySoggettoRichiedente(String soggettoRichiedente);
}
