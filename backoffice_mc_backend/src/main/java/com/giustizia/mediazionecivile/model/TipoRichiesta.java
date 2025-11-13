package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_RICHIESTA")
public class TipoRichiesta {
	@Id
	@Column(name = "ID_TIPO_RICHIESTA")
	private Long idTipoRichiesta;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;

	
	public TipoRichiesta() {
		super();
	}


	public Long getIdTipoRichiesta() {
		return idTipoRichiesta;
	}


	public void setIdTipoRichiesta(Long idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
