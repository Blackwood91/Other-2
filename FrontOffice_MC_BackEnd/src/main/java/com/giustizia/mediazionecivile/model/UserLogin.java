package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_LOGIN")
public class UserLogin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USER_LOGIN")
	private Long id;
	@Column(name = "COGNOME")
	private String cognome;
	@Column(name = "NOME")
	private String nome;
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;
	@Column(name = "ID_RUOLO")
	private int idRuolo;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "DATA_CREAZIONE")
	private Date dataCreazione;
	@Column(name = "DATA_MODIFICA")
	private Date dataModifica;
	@Column(name = "IS_ABILITATO")
	private int isAbilitato;

	
	public UserLogin() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public int getIsAbilitato() {
		return isAbilitato;
	}

	public void setIsAbilitato(int isAbilitato) {
		this.isAbilitato = isAbilitato;
	}

}
