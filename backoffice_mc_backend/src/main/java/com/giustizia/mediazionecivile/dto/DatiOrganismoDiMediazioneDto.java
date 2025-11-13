package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class DatiOrganismoDiMediazioneDto {
    // RICHIESTA
	private Long idRichiesta;
	private Date dataAttoCosti;
    private Date dataStatutoVig;
    private String codFiscSocieta;
    private String pIva;
    private Long idNaturaSoc;
	// SEDE
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
    private String virtuContratDi;
    private byte[] allegatoCopContratto;
    private byte[] allegatoPlanimetria;
    
    // CONCIDE SEDE OPERATIVA CON LEGALE
    private boolean concideSedeOper;
    
    // private String denominazioneOdm; // in attesa di capire i flussi  dei dati
     //denominazione o ragione sociale ?
     //natura giuridica ?
    
	public DatiOrganismoDiMediazioneDto() {
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


	public String getpIva() {
		return pIva;
	}


	public void setpIva(String pIva) {
		this.pIva = pIva;
	}


	public Long getIdNaturaSoc() {
		return idNaturaSoc;
	}


	public void setIdNaturaSoc(Long idNaturaSoc) {
		this.idNaturaSoc = idNaturaSoc;
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


	public boolean isConcideSedeOper() {
		return concideSedeOper;
	}


	public void setConcideSedeOper(boolean concideSedeOper) {
		this.concideSedeOper = concideSedeOper;
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


	public String getVirtuContratDi() {
		return virtuContratDi;
	}


	public void setVirtuContratDi(String virtuContratDi) {
		this.virtuContratDi = virtuContratDi;
	}


	public byte[] getAllegatoCopContratto() {
		return allegatoCopContratto;
	}


	public void setAllegatoCopContratto(byte[] allegatoCopContratto) {
		this.allegatoCopContratto = allegatoCopContratto;
	}


	public byte[] getAllegatoPlanimetria() {
		return allegatoPlanimetria;
	}


	public void setAllegatoPlanimetria(byte[] allegatoPlanimetria) {
		this.allegatoPlanimetria = allegatoPlanimetria;
	}
	
	
}
