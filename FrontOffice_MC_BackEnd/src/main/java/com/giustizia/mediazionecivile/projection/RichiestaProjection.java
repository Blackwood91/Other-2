package com.giustizia.mediazionecivile.projection;

import java.util.Date;

public interface RichiestaProjection {
	Long getIdRichiesta();
	Date getDataRichiesta();
	Date getDataIscrAlbo();
	Long getIdTipoRichiesta();
	Long getIdTipoRichiedente();
	String getDenominazioneOdm();
	Long getIdStato();
}
