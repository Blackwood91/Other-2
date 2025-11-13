package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class CertificazioneLingueDto {
	// SAREBBE L'IDSTATOMODULIFIGLIO (FIX NAME PER RISOLVERE PROBLEMA CON CENTRALITA' E ADATTABILITA' AGLI ALTRI METODI NEL FRONT END)
	private Long id;
	private Long idCertificazioneLingua;
	private Date dataCertificazione;
	private String enteCertificatore;
	private Date dataInserimento;
	private String nomeAllegato;
    private Integer completato;
    private Integer validato;    
    private Integer annullato;
	

	public CertificazioneLingueDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public Long getIdCertificazioneLingua() {
		return idCertificazioneLingua;
	}

	public void setIdCertificazioneLingua(Long idCertificazioneLingua) {
		this.idCertificazioneLingua = idCertificazioneLingua;
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
