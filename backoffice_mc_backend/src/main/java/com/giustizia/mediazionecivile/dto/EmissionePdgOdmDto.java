package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class EmissionePdgOdmDto {
	private Long idEmissionePdg;
	private String TipoPdg;	
	private Date dataEmissione;
	private Long idRichiesta;
	private Long idTipoPdg;
	private byte[] file;
	private String nomeFile;
	
	public EmissionePdgOdmDto() {
		super();
	}


	public Long getIdEmissionePdg() {
		return idEmissionePdg;
	}


	public void setIdEmissionePdg(Long idEmissionePdg) {
		this.idEmissionePdg = idEmissionePdg;
	}


	public String getTipoPdg() {
		return TipoPdg;
	}


	public void setTipoPdg(String tipoPdg) {
		TipoPdg = tipoPdg;
	}


	public Date getDataEmissione() {
		return dataEmissione;
	}


	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	
	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public Long getIdTipoPdg() {
		return idTipoPdg;
	}


	public void setIdTipoPdg(Long idTipoPdg) {
		this.idTipoPdg = idTipoPdg;
	}


	public byte[] getFile() {
		return file;
	}


	public void setFile(byte[] file) {
		this.file = file;
	}	

	
	public String getNomeFile() {
		return nomeFile;
	}


	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
}
