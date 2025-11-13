package com.giustizia.mediazionecivile.projection;

import java.util.Date;

public interface AnagraficaOdmSezSecProjection {
	public String getCodiceFiscale();
	public String getCognome();
	public String getNome();
	public String getSesso();
	public Long getIdTitoloAnagrafica();
	public Date getDataNascita();
	public String getStatoNascita();
	public Long getIdComuneNascita();
    public String getComuneNascitaEstero();
    public String getCittadinanza();
    public String getIndirizzo();
    public String getNumeroCivico();
    public String getStatoResidenza();
    public Long getIdComuneResidenza();
    public String getComuneResidenzaEstero();
    public String getCap();
	public String getStatoDomicilio();
	public String getIndirizzoDomicilio();
	public String getNumeroCivicoDomicilio();
	public Long getIdComuneDomicilio();
	public String getCapDomicilio();
	public String getComuneDomicilioEstero();
	public String getIndirizzoPec();
	public String getIndirizzoEmail();
	public Long getIdQualifica();
	public String getMedCellulare();
	public String getMedTelefono();
	public String getMedFax();
	public String getDescQualifica();
	public String getPoTipoRappOdm();
	public Date getPoDataAssunzione();
}
