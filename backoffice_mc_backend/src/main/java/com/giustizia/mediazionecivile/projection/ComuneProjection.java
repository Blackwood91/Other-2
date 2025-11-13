package com.giustizia.mediazionecivile.projection;

import com.giustizia.mediazionecivile.model.RegioneProvince;

public interface ComuneProjection {
	Long getIdCodComune();
	String getNomeComune();
	RegioneProvince getRegioneProvince();
}
