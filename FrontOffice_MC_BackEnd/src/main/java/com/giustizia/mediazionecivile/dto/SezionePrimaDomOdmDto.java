package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class SezionePrimaDomOdmDto {
	private Long idRichiesta;
	private Date dataAttoCosti;
    private Date dataStatutoVig;
    private String codFiscSocieta;
    private String pIva;
    private Long idNaturaSoc;
    private Long idSoggettoRichiedente;
    private Integer autonomo;
    private String oggettoSociale;
    private Integer istitutoEntePub;
    private String denominaOdmPub;
    private Long idNaturaGiu;
    private String nome;
	private String cognome;

    
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

    
    
	public SezionePrimaDomOdmDto() {
		super();
	}

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Date getDataAttoCosti() {
		return dataAttoCosti;
	}

	public void setDataAttoCosti(Date dataAttoCosti) {
		this.dataAttoCosti = dataAttoCosti;
	}

	public Date getDataStatutoVig() {
		return dataStatutoVig;
	}

	public void setDataStatutoVig(Date dataStatutoVig) {
		this.dataStatutoVig = dataStatutoVig;
	}

	public String getCodFiscSocieta() {
		return codFiscSocieta;
	}

	public void setCodFiscSocieta(String codFiscSocieta) {
		this.codFiscSocieta = codFiscSocieta;
	}

	public String getPIva() {
		return pIva;
	}

	public void setPIva(String pIva) {
		this.pIva = pIva;
	}

	public Long getIdNaturaSoc() {
		return idNaturaSoc;
	}

	public void setIdNaturaSoc(Long idNaturaSoc) {
		this.idNaturaSoc = idNaturaSoc;
	}

	public Long getIdSoggettoRichiedente() {
		return idSoggettoRichiedente;
	}

	public void setIdSoggettoRichiedente(Long idSoggettoRichiedente) {
		this.idSoggettoRichiedente = idSoggettoRichiedente;
	}

	public Integer getAutonomo() {
		return autonomo;
	}

	public void setAutonomo(Integer autonomo) {
		this.autonomo = autonomo;
	}

	public String getOggettoSociale() {
		return oggettoSociale;
	}

	public void setOggettoSociale(String oggettoSociale) {
		this.oggettoSociale = oggettoSociale;
	}

	public String getpIva() {
		return pIva;
	}

	public void setpIva(String pIva) {
		this.pIva = pIva;
	}

	public Integer getIstitutoEntePub() {
		return istitutoEntePub;
	}

	public void setIstitutoEntePub(Integer istitutoEntePub) {
		this.istitutoEntePub = istitutoEntePub;
	}

	public String getDenominaOdmPub() {
		return denominaOdmPub;
	}

	public void setDenominaOdmPub(String denominaOdmPub) {
		this.denominaOdmPub = denominaOdmPub;
	}

	public Long getIdNaturaGiu() {
		return idNaturaGiu;
	}

	public void setIdNaturaGiu(Long idNaturaGiu) {
		this.idNaturaGiu = idNaturaGiu;
	}
	
}
