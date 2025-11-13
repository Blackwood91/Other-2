package com.giustizia.mediazionecivile.projection;

public interface RichiestaSezPrimaProjection {
	Long getIdSoggRichiedente();
	Long getIdSocieta();
	String getDenominazioneOdm();
	int getAutonomo();
	String getOggettoSociale();	
	/*richiesta sez prima projection:
	id soggetto richiedente
	id società
	denominazione odm
	autonomo
	oggetto sociale*/

}
