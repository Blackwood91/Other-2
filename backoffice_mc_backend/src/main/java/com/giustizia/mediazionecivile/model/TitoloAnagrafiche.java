package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TITOLI_ANAGRAFICHE")
public class TitoloAnagrafiche {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TITOLI_ANAGRAFICHE")
	private Long idTitoloAnagrafiche;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;

	public TitoloAnagrafiche() {
		super();
	}

	public Long getIdTitoloAnagrafiche() {
		return idTitoloAnagrafiche;
	}

	public void setIdTitoloAnagrafiche(Long idTitoloAnagrafiche) {
		this.idTitoloAnagrafiche = idTitoloAnagrafiche;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
