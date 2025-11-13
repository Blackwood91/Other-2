package com.giustizia.mediazionecivile.projection;

import java.math.BigDecimal;
import java.util.Date;

public interface PolizzaAssicurativaProjection {
	public String getCompagniaAss();
	public BigDecimal getMassimaleAssic();
	public Date getDataStipulaPoliz();
  	public Date getScadenzaPoliza();
	public String getArtOggetPolizza();
}
