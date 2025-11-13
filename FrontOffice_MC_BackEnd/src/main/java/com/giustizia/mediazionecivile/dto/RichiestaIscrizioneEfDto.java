package com.giustizia.mediazionecivile.dto;

public class RichiestaIscrizioneEfDto {
	private Long idSocieta;
	private Long idTipoRichiedente;
	private int idTipoRichiesta;
	
	public RichiestaIscrizioneEfDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdSocieta() {
		return idSocieta;
	}

	public void setIdSocieta(Long idSocieta) {
		this.idSocieta = idSocieta;
	}

	public Long getIdTipoRichiedente() {
		return idTipoRichiedente;
	}

	public void setIdTipoRichiedente(Long idTipoRichiedente) {
		this.idTipoRichiedente = idTipoRichiedente;
	}

	public int getIdTipoRichiesta() {
		return idTipoRichiesta;
	}

	public void setIdTipoRichiesta(int idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}
	
}
