package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class SezioneSecondaDomOdmDto {
	// RICHIESTE
	private Long idRichiesta;
	// ANAGRAFICHE
	private Long idTitoloAnagrafica;
	private String sesso;
	private String cognome;
	private String nome;
	private Date dataNascita;
	private String statoNascita;
	private Long idComuneNascita;
	private String codiceFiscale;
	private String cittadinanza;
	private String comuneNascitaEstero;
	// Residenza
	private String statoResidenza;
	private Long idComuneResidenza;
	private String indirizzo;
	private String numeroCivico;
	private String cap;
	private String comuneResidenzaEstero;
	// Domicilio
	private String indirizzoDomicilio;
	private String numeroCivicoDomicilio;
	private Long idComuneDomicilio;
	private String capDomicilio;
	private String statoDomicilio;
	private String comuneDomicilioEstero;
	// Contatti
	private String indirizzoEmail;
	private String indirizzoPec;
	private Long idQualifica;

	public SezioneSecondaDomOdmDto() {
		super();
	}

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Long getIdTitoloAnagrafica() {
		return idTitoloAnagrafica;
	}

	public void setIdTitoloAnagrafica(Long idTitoloAnagrafica) {
		this.idTitoloAnagrafica = idTitoloAnagrafica;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
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

	public String getStatoNascita() {
		return statoNascita;
	}

	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}

	public Long getIdComuneNascita() {
		return idComuneNascita;
	}

	public void setIdComuneNascita(Long idComuneNascita) {
		this.idComuneNascita = idComuneNascita;
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

	public String getComuneNascitaEstero() {
		return comuneNascitaEstero;
	}

	public void setComuneNascitaEstero(String comuneNascitaEstero) {
		this.comuneNascitaEstero = comuneNascitaEstero;
	}

	public String getStatoResidenza() {
		return statoResidenza;
	}

	public void setStatoResidenza(String statoResidenza) {
		this.statoResidenza = statoResidenza;
	}

	public Long getIdComuneResidenza() {
		return idComuneResidenza;
	}

	public void setIdComuneResidenza(Long idComuneResidenza) {
		this.idComuneResidenza = idComuneResidenza;
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

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getComuneResidenzaEstero() {
		return comuneResidenzaEstero;
	}

	public void setComuneResidenzaEstero(String comuneResidenzaEstero) {
		this.comuneResidenzaEstero = comuneResidenzaEstero;
	}

	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}

	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}

	public String getNumeroCivicoDomicilio() {
		return numeroCivicoDomicilio;
	}

	public void setNumeroCivicoDomicilio(String numeroCivicoDomicilio) {
		this.numeroCivicoDomicilio = numeroCivicoDomicilio;
	}

	public Long getIdComuneDomicilio() {
		return idComuneDomicilio;
	}

	public void setIdComuneDomicilio(Long idComuneDomicilio) {
		this.idComuneDomicilio = idComuneDomicilio;
	}

	public String getCapDomicilio() {
		return capDomicilio;
	}

	public void setCapDomicilio(String capDomicilio) {
		this.capDomicilio = capDomicilio;
	}

	public String getStatoDomicilio() {
		return statoDomicilio;
	}

	public void setStatoDomicilio(String statoDomicilio) {
		this.statoDomicilio = statoDomicilio;
	}

	public String getComuneDomicilioEstero() {
		return comuneDomicilioEstero;
	}

	public void setComuneDomicilioEstero(String comuneDomicilioEstero) {
		this.comuneDomicilioEstero = comuneDomicilioEstero;
	}

	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}

	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}

	public String getIndirizzoPec() {
		return indirizzoPec;
	}

	public void setIndirizzoPec(String indirizzoPec) {
		this.indirizzoPec = indirizzoPec;
	}

	public Long getIdQualifica() {
		return idQualifica;
	}

	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
	}

}
