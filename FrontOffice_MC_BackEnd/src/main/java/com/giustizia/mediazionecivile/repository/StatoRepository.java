package com.giustizia.mediazionecivile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giustizia.mediazionecivile.model.Stato;

public interface StatoRepository extends JpaRepository<Stato, Long> {
    
    public Stato findStatoByDescrizione(String descrizione);
}
