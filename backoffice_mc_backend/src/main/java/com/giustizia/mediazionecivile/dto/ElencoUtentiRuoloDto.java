package com.giustizia.mediazionecivile.dto;

public class ElencoUtentiRuoloDto {
	public Long id;	
	public String cognome;
	public String nome;
	public Long idRuolo;
	public int isAbilitato;
	public String codiceFiscale;
	public String ruolo;
    
    
	public ElencoUtentiRuoloDto() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public Long getIdRuolo() {
		return idRuolo;
	}


	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}


	public int getIsAbilitato() {
		return isAbilitato;
	}


	public void setIsAbilitato(int isAbilitato) {
		this.isAbilitato = isAbilitato;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getRuolo() {
		return ruolo;
	}


	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	
}