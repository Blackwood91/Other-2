package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RUOLO")
public class Ruolo {
	@Id
	@Column(name = "ID_RUOLO")
	private Long idRuolo;
	
	@Column(name = "RUOLO")
	private String ruolo;

	
	public Ruolo() {
		super();
	}


	public Long getIdRuolo() {
		return idRuolo;
	}


	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}


	public String getRuolo() {
		return ruolo;
	}


	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
}
