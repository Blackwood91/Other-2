package com.giustizia.mediazionecivile.dto;

public class DomandaIscrizioneOdmDto {
	private Long idRichiesta;
	private String denominazioneOdm;
	
	public DomandaIscrizioneOdmDto() {
		super();
	}

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public String getDenominazioneOdm() {
		return denominazioneOdm;
	}

	public void setDenominazioneOdm(String denominazioneOdm) {
		this.denominazioneOdm = denominazioneOdm;
	}
		
}
