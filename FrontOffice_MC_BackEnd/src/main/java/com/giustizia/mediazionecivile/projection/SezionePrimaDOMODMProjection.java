package com.giustizia.mediazionecivile.projection;

import java.util.Date;

import jakarta.persistence.Column;

public interface SezionePrimaDOMODMProjection {
	Date getDataAttoCosti();
	Date getDataStatutoVig();
	String getCodFiscSocieta();
	String getPIva();
	Long getIdNaturaSoc();
	Long getIdSoggRichiedente();
	Integer getAutonomo();
	String getOggettoSociale();
    Integer getIstitutoEntePub();
    String getDenominaOdmPub();
    Integer getIdNaturaGiu();
}
