package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class OrganismoOdmDto {
	//Long rom; //creare ad hoc
	String denominazioneOdm;
	String sitoWebSede;
	String email;
	int autonomo;
	String codFiscSocieta;
	String pIva;
	Long numIscrAlbo;
	Long idRichiesta;
	
	
	public OrganismoOdmDto() {
		super();
	}
		
	public OrganismoOdmDto(Object[] obj) {
		this.denominazioneOdm = (String)obj[0];
		this.sitoWebSede = (String)obj[1];
		this.email = (String)obj[2];
		this.autonomo = (int)obj[3];
		this.codFiscSocieta = (String)obj[4];
		this.pIva = (String)obj[5];
		this.numIscrAlbo = (Long)obj[6];
	}
	
	
	public String getDenominazioneOdm() {
		return denominazioneOdm;
	}
	public void setDenominazioneOdm(String denominazioneOdm) {
		this.denominazioneOdm = denominazioneOdm;
	}
	public String getSitoWebSede() {
		return sitoWebSede;
	}
	public void setSitoWebSede(String sitoWebSede) {
		this.sitoWebSede = sitoWebSede;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAutonomo() {
		return autonomo;
	}
	public void setAutonomo(int autonomo) {
		this.autonomo = autonomo;
	}
	public String getCodFiscSocieta() {
		return codFiscSocieta;
	}
	public void setCodFiscSocieta(String codiceFiscale) {
		this.codFiscSocieta = codiceFiscale;
	}
	public String getpIva() {
		return pIva;
	}
	public void setpIva(String pIva) {
		this.pIva = pIva;
	}
	public Long getNumIscrAlbo() {
		return numIscrAlbo;
	}
	public void setNumIscrAlbo(Long numIscrAlbo) {
		this.numIscrAlbo = numIscrAlbo;
	}
	public Long getIdRichiesta() {
		return idRichiesta;
	}
	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	
}
