package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CERTIFICAZIONE_LINGUE_STRANIERE")
public class CertificazioneLingueStraniere {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CERTIFICAZIONE_LINGUA")
	private Long idCertificazione;

	@Column(name = "ID_ANAGRAFICA")
	private Long idAnagrafica;

	@Column(name = "DESCRIZIONE")
	private String descrizione;

	@Column(name = "DATA_CERTIFICAZIONE")
	private Date dataCertificazione;
	
	@Column(name = "ENTE_CERTIFICATORE")
	private String enteCertificatore;
	

	public Long getIdCertificazione() {
		return idCertificazione;
	}

	public void setIdCertificazione(Long idCertificazione) {
		this.idCertificazione = idCertificazione;
	}

	public Long getIdAnagrafica() {
		return idAnagrafica;
	}

	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataCertificazione() {
		return dataCertificazione;
	}

	public void setDataCertificazione(Date dataCertificazione) {
		this.dataCertificazione = dataCertificazione;
	}

	public String getEnteCertificatore() {
		return enteCertificatore;
	}

	public void setEnteCertificatore(String enteCertificatore) {
		this.enteCertificatore = enteCertificatore;
	}
	
	
	

}
