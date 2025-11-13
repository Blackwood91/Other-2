package com.giustizia.mediazionecivile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giustizia.mediazionecivile.model.RichiestaRegistrazioneUtente;

public interface RichiestaRegistrazioneUtenteRepository extends JpaRepository<RichiestaRegistrazioneUtente, Long> {

	public RichiestaRegistrazioneUtente findByIdUserLogin(long idUserLogin);
}
