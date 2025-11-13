package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Moduli")
public class Modulo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MODULO")
	private Long idModulo;

	@Column(name = "ID_TIPOALLEGATO_GED")
	private Long idTipologiaGed;

	@Column(name = "SEZIONE")
	private Long sezione;

	@Column(name = "ORDINE")
	private Long ordine;

	@Column(name = "FIGLI_IDS")
	private String figliIds;

	@Column(name = "DESCRIZIONE")
	private String descrizione;

	@Column(name = "ALLEGATO")
	private Long allegati;

	@Column(name = "OCCORRENZE_MINIME")
	private Long occorenzeMinime;

	@Column(name = "URL")
	private String url;

	@Column(name = "MODELLO")
	private String modello;


	public Modulo() {
		super();
	}


	public Long getIdModulo() {
		return idModulo;
	}


	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}


	public Long getIdTipologiaGed() {
		return idTipologiaGed;
	}


	public void setIdTipologiaGed(Long idTipologiaGed) {
		this.idTipologiaGed = idTipologiaGed;
	}


	public Long getSezione() {
		return sezione;
	}


	public void setSezione(Long sezione) {
		this.sezione = sezione;
	}


	public Long getOrdine() {
		return ordine;
	}


	public void setOrdine(Long ordine) {
		this.ordine = ordine;
	}


	public String getFigliIds() {
		return figliIds;
	}


	public void setFigliIds(String figliIds) {
		this.figliIds = figliIds;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public Long getAllegati() {
		return allegati;
	}


	public void setAllegati(Long allegati) {
		this.allegati = allegati;
	}


	public Long getOccorenzeMinime() {
		return occorenzeMinime;
	}


	public void setOccorenzeMinime(Long occorenzeMinime) {
		this.occorenzeMinime = occorenzeMinime;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getModello() {
		return modello;
	}


	public void setModello(String modello) {
		this.modello = modello;
	}

}
