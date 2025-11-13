package com.giustizia.mediazionecivile.projection;

import java.util.Date;

public interface RichiestaProjection {
	Long getIdRichiesta();
	Date getDataRichiesta();
	Date getDataIscrAlbo();
	Long getIdTipoRichiedente();
	String getDenominazioneOdm();
}
