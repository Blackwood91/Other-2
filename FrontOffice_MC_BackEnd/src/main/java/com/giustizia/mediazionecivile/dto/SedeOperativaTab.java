package com.giustizia.mediazionecivile.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SedeOperativaTab {
	private Object idSede;
	private char sedeLegale;
	private String indirizzo;
	private String numeroCivico;
	private String cap;
	private String email;
	private String pec;
	private String fax;
	private String telefono;
	private Object idComune;
	private String nomeComune;
	private String siglaProvincia;
	private Object idTitoloDetenzione;
	private Date dataContratto;
	private Date dataInserimentoSede;
	private String strutturaOrgSegreteria;
	private String detenzioneTitolo;
	private Integer completato;
	private Integer validato;
	private Integer annullato;
	
	public SedeOperativaTab() {
		super();
	}

	public Object getIdSede() {
		return idSede;
	}

	public void setIdSede(Object idSede) {
		this.idSede = idSede;
	}

	public char getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(char sedeLegale) {
		this.sedeLegale = sedeLegale;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Object getIdComune() {
		return idComune;
	}

	public void setIdComune(Object idComune) {
		this.idComune = idComune;
	}

	public String getNomeComune() {
		return nomeComune;
	}

	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public Object getIdTitoloDetenzione() {
		return idTitoloDetenzione;
	}

	public void setIdTitoloDetenzione(Object idTitoloDetenzione) {
		this.idTitoloDetenzione = idTitoloDetenzione;
	}


	public Date getDataContratto() {
		return dataContratto;
	}

	public void setDataContratto(Date dataContratto) {
		this.dataContratto = dataContratto;
	}

	public Date getDataInserimentoSede() {
		return dataInserimentoSede;
	}

	public void setDataInserimentoSede(Date dataInserimentoSede) {
		this.dataInserimentoSede = dataInserimentoSede;
	}

	public String getStrutturaOrgSegreteria() {
		return strutturaOrgSegreteria;
	}

	public void setStrutturaOrgSegreteria(String strutturaOrgSegreteria) {
		this.strutturaOrgSegreteria = strutturaOrgSegreteria;
	}

	public String getDetenzioneTitolo() {
		return detenzioneTitolo;
	}

	public void setDetenzioneTitolo(String detenzioneTitolo) {
		this.detenzioneTitolo = detenzioneTitolo;
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
