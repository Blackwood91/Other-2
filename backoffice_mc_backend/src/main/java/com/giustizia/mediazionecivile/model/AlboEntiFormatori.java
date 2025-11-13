package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALBO_EF")
public class AlboEntiFormatori {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALBO_EF")
	private Long idAlboEntiFormatori;
	
	@Column(name = "NUM_REG")
	private Long numeroRegistro;
	
	@Column(name = "DENOMINAZIONE")
	private String denominazione;
	
	@Column(name = "SITO_WEB")
	private String sitoWeb;
	
	@Column(name = "LR_COGNOME")
	private String lrCognome;
	
	@Column(name = "LR_NOME")
	private String lrNome;
	
	@Column(name = "LR_DATANASCITA")
	private Date lrDataNascita;
	
	@Column(name = "LR_COMUNENASCITA")
	private String lrComuneNascita;
	
	@Column(name = "LR_PROVNASCITA")
	private String lrProvNascita;
	
	@Column(name = "LR_STATONASCITA")
	private String lrStatoNascita;
	
	@Column(name = "NATURA")
	private String natura;
	
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name = "PARTITA_IVA")
	private String partitaIva;
	
	@Column(name = "CANCELLATO")
	private Integer cancellato;
	
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	

	public Long getIdAlboEntiFormatori() {
		return idAlboEntiFormatori;
	}

	public void setIdAlboEntiFormatori(Long idAlboEntiFormatori) {
		this.idAlboEntiFormatori = idAlboEntiFormatori;
	}

	public Long getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(Long numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getSitoWeb() {
		return sitoWeb;
	}

	public void setSitoWeb(String sitoWeb) {
		this.sitoWeb = sitoWeb;
	}

	public String getLrCognome() {
		return lrCognome;
	}

	public void setLrCognome(String lrCognome) {
		this.lrCognome = lrCognome;
	}

	public String getLrNome() {
		return lrNome;
	}

	public void setLrNome(String lrNome) {
		this.lrNome = lrNome;
	}

	public Date getLrDataNascita() {
		return lrDataNascita;
	}

	public void setLrDataNascita(Date lrDataNascita) {
		this.lrDataNascita = lrDataNascita;
	}

	public String getLrComuneNascita() {
		return lrComuneNascita;
	}

	public void setLrComuneNascita(String lrComuneNascita) {
		this.lrComuneNascita = lrComuneNascita;
	}

	public String getLrProvNascita() {
		return lrProvNascita;
	}

	public void setLrProvNascita(String lrProvNascita) {
		this.lrProvNascita = lrProvNascita;
	}

	public String getLrStatoNascita() {
		return lrStatoNascita;
	}

	public void setLrStatoNascita(String lrStatoNascita) {
		this.lrStatoNascita = lrStatoNascita;
	}

	public String getNatura() {
		return natura;
	}

	public void setNatura(String natura) {
		this.natura = natura;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public Integer getCancellato() {
		return cancellato;
	}

	public void setCancellato(Integer cancellato) {
		this.cancellato = cancellato;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
	

}
