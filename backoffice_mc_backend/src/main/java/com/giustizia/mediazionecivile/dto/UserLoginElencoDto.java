package com.giustizia.mediazionecivile.dto;

public class UserLoginElencoDto {
	private Long id;
	private String cognome;
	private String nome;
	private String codiceFiscale;
	private int idRuolo;
	private int isAbilitato;
	
	
	public UserLoginElencoDto() {
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


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public int getIdRuolo() {
		return idRuolo;
	}


	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}


	public int getIsAbilitato() {
		return isAbilitato;
	}


	public void setIsAbilitato(int isAbilitato) {
		this.isAbilitato = isAbilitato;
	}
	
}
