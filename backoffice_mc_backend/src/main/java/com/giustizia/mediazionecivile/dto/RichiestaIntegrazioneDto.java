package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class RichiestaIntegrazioneDto {
	private Integer idTipoRichiesta;
	private Date dataRichiesta;
	private String userRichiesta;
	
	
	public RichiestaIntegrazioneDto() {
		super();
	}

	public Integer getIdTipoRichiesta() {
		return idTipoRichiesta;
	}


	public void setIdTipoRichiesta(Integer idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}


	public Date getDataRichiesta() {
		return dataRichiesta;
	}


	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}


	public String getUserRichiesta() {
		return userRichiesta;
	}


	public void setUserRichiesta(String userRichiesta) {
		this.userRichiesta = userRichiesta;
	}
	
}
