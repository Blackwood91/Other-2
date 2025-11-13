package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ORDINI_COLLEGI")
public class OrdiniCollegi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ORDINE_COLLEGIO")
	private Long idOrdiniCollegi;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;

	public OrdiniCollegi() {
		super();
	}

	
	public Long getIdOrdiniCollegi() {
		return idOrdiniCollegi;
	}

	public void setIdOrdiniCollegi(Long idOrdiniCollegi) {
		this.idOrdiniCollegi = idOrdiniCollegi;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
