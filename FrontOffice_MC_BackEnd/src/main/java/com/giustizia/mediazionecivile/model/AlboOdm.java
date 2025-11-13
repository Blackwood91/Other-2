package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALBO_ODM")
public class AlboOdm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALBO_ODM")
	private Long idAlboOdm;
	
	@Column(name = "ROM")
	private Long rom;
	
	@Column(name = "DENOMINAZIONE_ORGANISMO")
	private String denominazione;
	
	@Column(name = "SITO_WEB")
	private String sitoWeb;
	
	@Column(name = "LR_COGNOME")
	private String lrCognome;
	
	@Column(name = "LR_NOME")
	private String lrNome;
	
	@Column(name = "LR_DATA_NASCITA")
	private Date lrDataNascita;
	
	@Column(name = "LR_COMUNE_NASCITA")
	private String lrComuneNascita;
	
	@Column(name = "LR_PROV_NASCITA")
	private String lrProvNascita;
	
	@Column(name = "LR_STATO_NASCITA")
	private String lrStatoNascita;
	
	@Column(name = "NATURA_ORGANISMO")
	private String natura;
	
	@Column(name = "CODICE_FISCALE_ODM")
	private String codiceFiscale;
	
	@Column(name = "PARTITA_IVA_ODM")
	private String partitaIva;
	
	@Column(name = "CANCELLATO")
	private Long cancellato;
	
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@Column(name = "TIPO_CANCELLAZIONE")
	private String tipoCancellazione;
	
	@Column(name = "SEDE_LEGALE")
	private String sedeLegale;
	
	@Column(name = "TIPO_ISCRIZIONE")
	private String tipoIscrizione;
	
	@Column(name = "REGIONE")
	private String regione;
	

	public Long getIdAlboOdm() {
		return idAlboOdm;
	}

	public void setIdAlboOdm(Long idAlboOdm) {
		this.idAlboOdm = idAlboOdm;
	}

	public Long getRom() {
		return rom;
	}

	public void setRom(Long rom) {
		this.rom = rom;
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

	public Long getCancellato() {
		return cancellato;
	}

	public void setCancellato(Long cancellato) {
		this.cancellato = cancellato;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(String sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public String getTipoIscrizione() {
		return tipoIscrizione;
	}

	public void setTipoIscrizione(String tipoIscrizione) {
		this.tipoIscrizione = tipoIscrizione;
	}
	
	public String getTipoCancellazione() {
		return tipoCancellazione;
	}

	public void setTipoCancellazione(String tipoCancellazione) {
		this.tipoCancellazione = tipoCancellazione;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

}
