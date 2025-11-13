package com.giustizia.mediazionecivile.dto;

public class AutocertificazioneAppD {
	public Long idAnagrafica;
	public Long idQualifica;
	public String nome;
	public String cognome;
	public String codiceFiscale;
	// DATI STATOMODULI
    private Integer completato;
    private Integer validato;    
    private Integer annullato;
    
    
	public AutocertificazioneAppD() {
		super();
	}


	public Long getIdAnagrafica() {
		return idAnagrafica;
	}


	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}


	public Long getIdQualifica() {
		return idQualifica;
	}


	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public Integer getCompletato() {
		return completato;
	}


	public void setCompletato(Integer completato) {
		this.completato = completato;
	}


	public Integer getValidato() {
		return validato;
	}


	public void setValidato(Integer validato) {
		this.validato = validato;
	}


	public Integer getAnnullato() {
		return annullato;
	}


	public void setAnnullato(Integer annullato) {
		this.annullato = annullato;
	}
    
}
