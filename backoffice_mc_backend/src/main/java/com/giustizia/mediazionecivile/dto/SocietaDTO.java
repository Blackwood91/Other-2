package com.giustizia.mediazionecivile.dto;

import java.math.BigDecimal;

import com.giustizia.mediazionecivile.model.Societa;
import com.giustizia.mediazionecivile.utility.DtoUtility;


public class SocietaDTO {
	// Societa
	private Object id;
	private String ragioneSociale;
	private String partitaIva;
	private String codiceFiscaleSocieta;

	// Richieste
	private String statoRichiestaOdm;
	private String statoRichiestaEf;
	
	public SocietaDTO() {
		super();
	}

	public SocietaDTO(Object[] obj) {
		this.id = obj[0];
		this.ragioneSociale = (String) obj[1];
		this.partitaIva = (String) obj[2];
		this.codiceFiscaleSocieta = (String) obj[3];
		this.statoRichiestaOdm = DtoUtility.castWithType(String.class, obj[4]);
		this.statoRichiestaEf = DtoUtility.castWithType(String.class, obj[5]);
	}

	public Object getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getCodiceFiscaleSocieta() {
		return codiceFiscaleSocieta;
	}

	public void setCodiceFiscaleSocieta(String codiceFiscaleSocieta) {
		this.codiceFiscaleSocieta = codiceFiscaleSocieta;
	}

	public String getStatoRichiestaOdm() {
		return statoRichiestaOdm;
	}

	public void setStatoRichiestaOdm(String statoRichiestaOdm) {
		this.statoRichiestaOdm = statoRichiestaOdm;
	}

	public String getStatoRichiestaEf() {
		return statoRichiestaEf;
	}

	public void setStatoRichiestaEf(String statoRichiestaEf) {
		this.statoRichiestaEf = statoRichiestaEf;
	}

}
