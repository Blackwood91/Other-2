package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class RichiestaSocietaDto {
	private Long idRichiesta;
	private Date dataRichiesta;
	private Date dataIscrAlbo;
	private Integer idTipoRichiesta;
	private Long idTipoRichiedente;
	private String denominazioneOdm;
	private Long idStato;
	private String statoRichiesta;
	
	
	public RichiestaSocietaDto() {
		super();
	}


	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public Date getDataRichiesta() {
		return dataRichiesta;
	}


	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}


	public Date getDataIscrAlbo() {
		return dataIscrAlbo;
	}


	public void setDataIscrAlbo(Date dataIscrAlbo) {
		this.dataIscrAlbo = dataIscrAlbo;
	}


	public Integer getIdTipoRichiesta() {
		return idTipoRichiesta;
	}


	public void setIdTipoRichiesta(Integer idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}


	public Long getIdTipoRichiedente() {
		return idTipoRichiedente;
	}


	public void setIdTipoRichiedente(Long idTipoRichiedente) {
		this.idTipoRichiedente = idTipoRichiedente;
	}


	public String getDenominazioneOdm() {
		return denominazioneOdm;
	}


	public void setDenominazioneOdm(String denominazioneOdm) {
		this.denominazioneOdm = denominazioneOdm;
	}

	
	public Long getIdStato() {
		return idStato;
	}


	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}


	public String getStatoRichiesta() {
		return statoRichiesta;
	}


	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}
	
}
