package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class DatiRappreLegaleDto {
	private String cognome;
	private String nome;
	private Date dataNascita;
	private Long idComuneNascita;
	private String comuneNascitaEstero;
	private String statoNascita; 
	private String codiceFiscale;
	private String cittadinanza;
	// ANAGRAFICAODM -RESIDENZA
	private String indirizzo; 
	private String numeroCivico; 
	private Long idComuneResidenza; 
	private String comuneResidenzaEstero;
	private String cap;
	private String statoResidenza;
	// ANAGRAFICAODM -DOMICILIO
	
	
	public DatiRappreLegaleDto() {
		super();
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Date getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}


	public Long getIdComuneNascita() {
		return idComuneNascita;
	}


	public void setIdComuneNascita(Long idComuneNascita) {
		this.idComuneNascita = idComuneNascita;
	}


	public String getComuneNascitaEstero() {
		return comuneNascitaEstero;
	}


	public void setComuneNascitaEstero(String comuneNascitaEstero) {
		this.comuneNascitaEstero = comuneNascitaEstero;
	}


	public String getStatoNascita() {
		return statoNascita;
	}


	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getCittadinanza() {
		return cittadinanza;
	}


	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getNumeroCivico() {
		return numeroCivico;
	}


	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}


	public Long getIdComuneResidenza() {
		return idComuneResidenza;
	}


	public void setIdComuneResidenza(Long idComuneResidenza) {
		this.idComuneResidenza = idComuneResidenza;
	}


	public String getComuneResidenzaEstero() {
		return comuneResidenzaEstero;
	}


	public void setComuneResidenzaEstero(String comuneResidenzaEstero) {
		this.comuneResidenzaEstero = comuneResidenzaEstero;
	}


	public String getCap() {
		return cap;
	}


	public void setCap(String cap) {
		this.cap = cap;
	}


	public String getStatoResidenza() {
		return statoResidenza;
	}


	public void setStatoResidenza(String statoResidenza) {
		this.statoResidenza = statoResidenza;
	}

}
