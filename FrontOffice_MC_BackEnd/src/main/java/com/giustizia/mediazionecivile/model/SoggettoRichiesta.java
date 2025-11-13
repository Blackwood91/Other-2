package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SOGGETTI_RICHIESTA")
public class SoggettoRichiesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOGGETTO_RICHIESTA")
    private Long idSoggRichiesta;
    
    @Column(name = "ID_RICHIESTA")
    private Long idRichiesta;
    
    @Column(name = "ID_ANAGRAFICA")
    private Long idAnagrafica;
    
    @Column(name = "ID_TIPO_ANAGRAFICA")
    private Integer idTipoAnagrafica;

	public SoggettoRichiesta() {
		super();
	}

	public Long getidSoggRichiesta() {
		return idSoggRichiesta;
	}

	public void setIdSoggRichiesta(Long idSoggRichiesta) {
		this.idSoggRichiesta = idSoggRichiesta;
	}

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Long getIdAnagrafica() {
		return idAnagrafica;
	}

	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}

	public Integer getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}

	public void setIdTipoAnagrafica(Integer idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
    
}
