package com.giustizia.mediazionecivile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giustizia.mediazionecivile.model.NaturaSocietaria;
import com.giustizia.mediazionecivile.model.SoggettoRichiedente;

public interface NaturaSocietariaRepository extends JpaRepository<NaturaSocietaria, Long> {

}
