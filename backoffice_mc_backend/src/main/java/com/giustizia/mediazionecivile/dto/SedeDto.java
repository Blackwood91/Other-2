package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class SedeDto {
	// SEDE
	private Long idSede;
	private Long idRichiesta;
    private String indirizzo;
    private String numeroCivico;
    private String cap;
    private Long idComune;
    private String telefono;
    private String fax;
    private String pec;
    private String email;
    private Long idTitoloDetenzione;
    private String durataContratto;
    private Date dataContratto;
    private String strutOrgSeg;
    private String sitoWeb;
    private String registrazione;
    private String isSedeLegale;
    private String nomeFileCopContratto;
    private String nomeFilePlanimetria;
    private byte[] allegatoCopContratto;
    private byte[] allegatoPlanimetria;
    private boolean legaleIsOperativa;
   
    
	public SedeDto() {
		super();
	}


	public Long getIdSede() {
		return idSede;
	}


	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}


	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
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


	public Long getIdComune() {
		return idComune;
	}


	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getPec() {
		return pec;
	}


	public void setPec(String pec) {
		this.pec = pec;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Long getIdTitoloDetenzione() {
		return idTitoloDetenzione;
	}


	public void setIdTitoloDetenzione(Long idTitoloDetenzione) {
		this.idTitoloDetenzione = idTitoloDetenzione;
	}


	public String getDurataContratto() {
		return durataContratto;
	}


	public void setDurataContratto(String durataContratto) {
		this.durataContratto = durataContratto;
	}


	public Date getDataContratto() {
		return dataContratto;
	}


	public void setDataContratto(Date dataContratto) {
		this.dataContratto = dataContratto;
	}


	public String getStrutOrgSeg() {
		return strutOrgSeg;
	}


	public void setStrutOrgSeg(String strutOrgSeg) {
		this.strutOrgSeg = strutOrgSeg;
	}
	

	public String getSitoWeb() {
		return sitoWeb;
	}


	public void setSitoWeb(String sitoWeb) {
		this.sitoWeb = sitoWeb;
	}


	public String getRegistrazione() {
		return registrazione;
	}


	public void setRegistrazione(String registrazione) {
		this.registrazione = registrazione;
	}


	public String getIsSedeLegale() {
		return isSedeLegale;
	}


	public void setIsSedeLegale(String isSedeLegale) {
		this.isSedeLegale = isSedeLegale;
	}


	public byte[] getAllegatoCopContratto() {
		return allegatoCopContratto;
	}

	
	public void setAllegatoCopContratto(byte[] allegatoCopContratto) {
		this.allegatoCopContratto = allegatoCopContratto;
	}
	
	
	public String getNomeFileCopContratto() {
		return nomeFileCopContratto;
	}


	public void setNomeFileCopContratto(String nomeFileCopContratto) {
		this.nomeFileCopContratto = nomeFileCopContratto;
	}


	public String getNomeFilePlanimetria() {
		return nomeFilePlanimetria;
	}


	public void setNomeFilePlanimetria(String nomeFilePlanimetria) {
		this.nomeFilePlanimetria = nomeFilePlanimetria;
	}


	public byte[] getAllegatoPlanimetria() {
		return allegatoPlanimetria;
	}


	public void setAllegatoPlanimetria(byte[] allegatoPlanimetria) {
		this.allegatoPlanimetria = allegatoPlanimetria;
	}


	public boolean getLegaleIsOperativa() {
		return legaleIsOperativa;
	}


	public void setLegaleIsOperativa(boolean legaleIsOperativa) {
		this.legaleIsOperativa = legaleIsOperativa;
	}
	
}
