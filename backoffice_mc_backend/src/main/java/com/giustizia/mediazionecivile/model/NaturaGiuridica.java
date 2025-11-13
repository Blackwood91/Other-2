package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NATURA_GIURIDICA")
public class NaturaGiuridica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NATURA_GIURIDICA")
    private Long idNaturaGiuridica;
    
    @Column(name = "DESCRIZIONE")
    private String descrizione;

	public NaturaGiuridica() {
		super();
	}

	public Long getIdNaturaGiuridica() {
		return idNaturaGiuridica;
	}

	public void setIdNaturaGiuridica(Long idNaturaGiuridica) {
		this.idNaturaGiuridica = idNaturaGiuridica;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
    
}
