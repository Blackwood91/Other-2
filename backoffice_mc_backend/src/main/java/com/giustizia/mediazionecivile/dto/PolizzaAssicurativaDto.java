package com.giustizia.mediazionecivile.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PolizzaAssicurativaDto {
	private Long idRichiesta;
	private String compagniaAssicuratrice; 
	private BigDecimal massimaleAssicurato; 
	private Date dataStipulaPoliz;
	private Date scadenzaPoliza;
	
	
	public PolizzaAssicurativaDto() {
		super();
	}


	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public String getCompagniaAssicuratrice() {
		return compagniaAssicuratrice;
	}


	public void setCompagniaAssicuratrice(String compagniaAssicuratrice) {
		this.compagniaAssicuratrice = compagniaAssicuratrice;
	}


	public BigDecimal getMassimaleAssicurato() {
		return massimaleAssicurato;
	}


	public void setMassimaleAssicurato(BigDecimal massimaleAssicurato) {
		this.massimaleAssicurato = massimaleAssicurato;
	}


	public Date getDataStipulaPoliz() {
		return dataStipulaPoliz;
	}


	public void setDataStipulaPoliz(Date dataStipulaPoliz) {
		this.dataStipulaPoliz = dataStipulaPoliz;
	}


	public Date getScadenzaPoliza() {
		return scadenzaPoliza;
	}


	public void setScadenzaPoliza(Date scadenzaPoliza) {
		this.scadenzaPoliza = scadenzaPoliza;
	}


	
	
}
