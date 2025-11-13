package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class SezioneQuartaDomOdmDto {
	private Long idRichiesta;
	private Long numCompoOrgAmm;
    private Long numCompoCompSoc;
    private String durataCarica;
    private Long idModalitaCostOrgani;
    private Date dataCostituOrg;
    private int autonomo;
    private int numPersonaleAdetto;
    private String fontiDiFinanziamento;
    private String durataCostituzioneOrganismo;
    private String modalitaGestioneContabile;    
    private int numMediatori;
    private int numMediatoriInter;
    private int numMediatoriCons;
    private String respOrganismo;    
    
    
    public SezioneQuartaDomOdmDto() {
		super();
	}

    
	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public Long getNumCompoOrgAmm() {
		return numCompoOrgAmm;
	}


	public void setNumCompoOrgAmm(Long numCompoOrgAmm) {
		this.numCompoOrgAmm = numCompoOrgAmm;
	}


	public Long getNumCompoCompSoc() {
		return numCompoCompSoc;
	}


	public void setNumCompoCompSoc(Long numCompoCompSoc) {
		this.numCompoCompSoc = numCompoCompSoc;
	}


	public String getDurataCarica() {
		return durataCarica;
	}


	public void setDurataCarica(String durataCarica) {
		this.durataCarica = durataCarica;
	}


	public Long getIdModalitaCostOrgani() {
		return idModalitaCostOrgani;
	}


	public void setIdModalitaCostOrgani(Long idModalitaCostOrgani) {
		this.idModalitaCostOrgani = idModalitaCostOrgani;
	}


	public Date getDataCostituOrg() {
		return dataCostituOrg;
	}


	public void setDataCostituOrg(Date dataCostituOrg) {
		this.dataCostituOrg = dataCostituOrg;
	}


	public int getAutonomo() {
		return autonomo;
	}


	public void setAutonomo(int autonomo) {
		this.autonomo = autonomo;
	}


	public int getNumPersonaleAdetto() {
		return numPersonaleAdetto;
	}


	public void setNumPersonaleAdetto(int numPersonaleAdetto) {
		this.numPersonaleAdetto = numPersonaleAdetto;
	}


	public String getFontiDiFinanziamento() {
		return fontiDiFinanziamento;
	}


	public void setFontiDiFinanziamento(String fontiDiFinanziamento) {
		this.fontiDiFinanziamento = fontiDiFinanziamento;
	}


	public String getDurataCostituzioneOrganismo() {
		return durataCostituzioneOrganismo;
	}


	public void setDurataCostituzioneOrganismo(String durataCostituzioneOrganismo) {
		this.durataCostituzioneOrganismo = durataCostituzioneOrganismo;
	}


	public String getModalitaGestioneContabile() {
		return modalitaGestioneContabile;
	}


	public void setModalitaGestioneContabile(String modalitaGestioneContabile) {
		this.modalitaGestioneContabile = modalitaGestioneContabile;
	}


	public int getNumMediatori() {
		return numMediatori;
	}


	public void setNumMediatori(int numMediatori) {
		this.numMediatori = numMediatori;
	}


	public int getNumMediatoriInter() {
		return numMediatoriInter;
	}


	public void setNumMediatoriInter(int numMediatoriInter) {
		this.numMediatoriInter = numMediatoriInter;
	}


	public int getNumMediatoriCons() {
		return numMediatoriCons;
	}


	public void setNumMediatoriCons(int numMediatoriCons) {
		this.numMediatoriCons = numMediatoriCons;
	}


	public String getRespOrganismo() {
		return respOrganismo;
	}


	public void setRespOrganismo(String respOrganismo) {
		this.respOrganismo = respOrganismo;
	}
	
}
