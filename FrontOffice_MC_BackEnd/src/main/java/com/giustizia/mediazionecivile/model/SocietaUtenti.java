package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SocietaUtenti {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SOCIETA_UTENTI")
	private Long idSocietaUtente;
	
	@Column(name = "ID_SOCIETA")
	private Long idSocieta;
	
	@Column(name = "ID_USER_LOGIN")
	private Long idUtente;
	
	@Column(name = "ATTIVA")
	private long attivita;

	
	public Long getIdSocietaUtente() {
		return idSocietaUtente;
	}

	public void setIdSocietaUtente(Long idSocietaUtente) {
		this.idSocietaUtente = idSocietaUtente;
	}

	public Long getIdSocieta() {
		return idSocieta;
	}

	public void setIdSocieta(Long idSocieta) {
		this.idSocieta = idSocieta;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public long getAttivita() {
		return attivita;
	}

	public void setAttivita(long attivita) {
		this.attivita = attivita;
	}

	
}
