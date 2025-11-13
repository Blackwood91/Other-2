package com.giustizia.mediazionecivile.security;

public class UtenteLoggato {
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String ruolo;
	private String isAdn;

	
	public UtenteLoggato() {}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getIsAdn() {
		return isAdn;
	}

	public void setIsAdn(String isAdn) {
		this.isAdn = isAdn;
	}
	
}
