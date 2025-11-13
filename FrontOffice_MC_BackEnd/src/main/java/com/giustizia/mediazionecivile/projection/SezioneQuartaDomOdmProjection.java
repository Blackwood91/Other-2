package com.giustizia.mediazionecivile.projection;

import java.util.Date;

public interface SezioneQuartaDomOdmProjection {
	public String getNumCompoOrgAmm();
	public Long getNumCompoCompSoc();
	public String getDurataCarica();
	public Long getIdModalitaCostOrgani();
	public Date getDataCostituOrg();
	public Integer getAutonomo();
	public Integer getNumPersonaleAdetto();
	public String getFontiDiFinanziamento();
	public String getDurataCostituzioneOrganismo();
	public String getModalitaGestioneContabile();
	public Integer getNumMediatori();
	public Integer getNumMediatoriInter();
	public Integer getNumMediatoriCons();
}
