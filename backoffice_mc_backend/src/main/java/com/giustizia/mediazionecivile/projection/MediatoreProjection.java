package com.giustizia.mediazionecivile.projection;

import java.util.Date;

public interface MediatoreProjection {
	public Long getIdAnagrafica();
    public String getCognome();
    public String getNome(); 
    public String getCodiceFiscale ();
    public String getMedNumeroOrganismiDisp(); 
    public Date getDataNascita(); 
    public String getComuneNascitaEstero(); 
    public String getComuneResidenzaEstero(); 
    public String getStatoNascita(); 
    //public String getLavorazione(); 
    //public Date getDataIscrizione(); 
   // public Date getDataCancellazione(); 
}
