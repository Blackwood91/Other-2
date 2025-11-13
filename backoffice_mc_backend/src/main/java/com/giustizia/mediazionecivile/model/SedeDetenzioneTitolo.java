package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SEDI_DETENZIONE_TITOLO")
public class SedeDetenzioneTitolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TITOLO_DETENZIONE")
    private Long idTitoloDetenzione;
    
    @Column(name = "DESCRIZIONE")
    private String descrizione;

    
	public SedeDetenzioneTitolo() {
		super();
	}

	public Long getIdTitoloDetenzione() {
		return idTitoloDetenzione;
	}

	public void setIdTitoloDetenzione(Long idTitoloDetenzione) {
		this.idTitoloDetenzione = idTitoloDetenzione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
