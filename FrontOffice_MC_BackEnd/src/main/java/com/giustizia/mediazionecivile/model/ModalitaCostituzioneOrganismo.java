package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MODALITA_COSTITUZIONE_ORGANISMO")
public class ModalitaCostituzioneOrganismo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODALITA_COSTITUZIONE_ORGANISMO")
    private Long id;
    
    @Column(name = "DESCRIZIONE")
    private String descrizione;

	public ModalitaCostituzioneOrganismo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}  
}
