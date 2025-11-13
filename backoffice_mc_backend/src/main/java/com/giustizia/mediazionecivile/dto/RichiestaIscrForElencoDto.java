package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class RichiestaIscrForElencoDto {
	private Long idRichiesta;
	private Long numIscrAlbo;
	private Date dataRichiesta;
	private String ragioneSociale;
	private String pIva;
	private String codFiscSocieta;
	private String statoRichiesta;
	private Integer idTipoRichiesta;
	
	
	public RichiestaIscrForElencoDto() {
		super();
	}


	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public Long getNumIscrAlbo() {
		return numIscrAlbo;
	}


	public void setNumIscrAlbo(Long numIscrAlbo) {
		this.numIscrAlbo = numIscrAlbo;
	}


	public Date getDataRichiesta() {
		return dataRichiesta;
	}


	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}


	public String getRagioneSociale() {
		return ragioneSociale;
	}


	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}


	public String getPIva() {
		return pIva;
	}


	public void setPIva(String pIva) {
		this.pIva = pIva;
	}


	public String getCodFiscSocieta() {
		return codFiscSocieta;
	}


	public void setCodFiscSocieta(String codFiscSocieta) {
		this.codFiscSocieta = codFiscSocieta;
	}


	public String getStatoRichiesta() {
		return statoRichiesta;
	}


	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}


	public Integer getIdTipoRichiesta() {
		return idTipoRichiesta;
	}


	public void setIdTipoRichiesta(Integer idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}
	
}
