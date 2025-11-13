package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SOCIETA")
public class Societa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SOCIETA")
	private Long id;

	@Column(name = "RAGIONE_SOCIALE")
	private String ragioneSociale;

	@Column(name = "PARTITA_IVA")
	private String partitaIva;

	@Column(name = "CODICE_FISCALE_SOCIETA")
	private String codiceFiscaleSocieta;


	public Societa() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public String getCodiceFiscaleSocieta() {
		return codiceFiscaleSocieta;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public void setCodiceFiscaleSocieta(String codiceFiscaleSocieta) {
		this.codiceFiscaleSocieta = codiceFiscaleSocieta;
	}

}
