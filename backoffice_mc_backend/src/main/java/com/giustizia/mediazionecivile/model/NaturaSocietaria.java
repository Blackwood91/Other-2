package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NATURA_SOCIETARIA")
public class NaturaSocietaria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_NATURA_SOCIETARIA")
	private Long idNaturaSoc;
	@Column(name = "DESCRIZIONE_BREVE")
	private String descrizioneBreve;
	@Column(name = "DESCRIZIONE")
	private String descrizione;

	
	public NaturaSocietaria() {
		super();
	}

	public Long getIdNaturaSoc() {
		return idNaturaSoc;
	}

	public void setIdNaturaSoc(Long idNaturaSoc) {
		this.idNaturaSoc = idNaturaSoc;
	}

	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}

	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
