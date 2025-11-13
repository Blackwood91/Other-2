package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class AlboMediatoriElencoDto {
	Long idAnagrafica;
	String nome;
	String cognome;
	String codiceFiscale;
	Date dataNascita;
	Long medNumeroOrganismiDisp;
	Long idTipoAnagrafica;
	
	
	public AlboMediatoriElencoDto() {
		super();
	}
		
	public AlboMediatoriElencoDto(Object[] obj) {
		this.idAnagrafica = (Long)obj[0];
		this.nome = (String)obj[1];
		this.cognome = (String)obj[2];
		this.codiceFiscale = (String)obj[3];
		this.dataNascita = (Date)obj[4];
		this.medNumeroOrganismiDisp = (Long)obj[5];
		this.idTipoAnagrafica = (Long)obj[6];
	}
	
	
	public Long getIdAnagrafica() {
		return idAnagrafica;
	}
	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
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
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Long getMedNumeroOrganismiDisp() {
		return medNumeroOrganismiDisp;
	}
	public void setMedNumeroOrganismiDisp(Long medNumeroOrganismiDisp) {
		this.medNumeroOrganismiDisp = medNumeroOrganismiDisp;
	}
	public Long getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	public void setIdTipoAnagrafica(Long idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	
}
