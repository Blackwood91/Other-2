package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class AlboOdmPdgDto {
	String tipoPdg;
	Date dataEmissione;
	
	
	public AlboOdmPdgDto() {
		super();
	}
		
	public AlboOdmPdgDto(Object[] obj) {
		this.tipoPdg = (String)obj[0];
		this.dataEmissione = (Date)obj[1];
	}

	public String getTipoPdg() {
		return tipoPdg;
	}

	public void setTipoPdg(String tipoPdg) {
		this.tipoPdg = tipoPdg;
	}

	public Date getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	
	
	
	
}
