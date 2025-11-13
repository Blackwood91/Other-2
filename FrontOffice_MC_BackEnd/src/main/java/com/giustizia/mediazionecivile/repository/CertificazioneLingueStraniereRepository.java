package com.giustizia.mediazionecivile.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.giustizia.mediazionecivile.model.CertificazioneLingueStraniere;

public interface CertificazioneLingueStraniereRepository extends JpaRepository<CertificazioneLingueStraniere, Long> {
	
	Optional<CertificazioneLingueStraniere> findByIdAnagraficaAndDataCertificazioneAndEnteCertificatore(
			Long idAnagrafica, Date dataCertificazione, String enteCertificazione);

}
