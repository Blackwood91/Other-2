package com.giustizia.mediazionecivile.dto;

public class DomandaIscrizioneOdmDto {
	private Long idRichiesta;
	private String denominazioneOdm;
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

	private String nome;
	private String cognome;
	
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
