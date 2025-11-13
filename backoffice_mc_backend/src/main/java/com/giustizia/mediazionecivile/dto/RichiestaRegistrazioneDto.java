package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class RichiestaRegistrazioneDto {
	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String ruolo;
	private Date dataRichiesta;
	private String richiestaIscrizione;
	private String ragioneSociale;
	private String pIva;
	private String pec;

	
	public RichiestaRegistrazioneDto() {
		super();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public String getRichiestaIscrizione() {
		return richiestaIscrizione;
	}

	public void setRichiestaIscrizione(String richiestaIscrizione) {
		this.richiestaIscrizione = richiestaIscrizione;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}


	public String getpIva() {
		return pIva;
	}


	public void setpIva(String pIva) {
		this.pIva = pIva;
	}


	public String getPec() {
		return pec;
	}


	public void setPec(String pec) {
		this.pec = pec;
	}
	
	
	
}
