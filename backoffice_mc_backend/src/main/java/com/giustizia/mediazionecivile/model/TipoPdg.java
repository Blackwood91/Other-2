package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_PDG")
public class TipoPdg {
    @Id
    @Column(name = "ID_TIPO_PDG")
    private Long idTipoPdg;

    @Column(name = "TIPO_PDG")
    private String tipoPdg;
    
    @Column(name = "ID_STATO_RICHIESTA")
    private Long idStatoRichiesta;
    
    @Column(name = "ID_TIPO_ALLEGATO_GED")
    private Long idTipoAllegatoGed;

    
	public TipoPdg() {
		super();
	}
	

	public Long getIdTipoPdg() {
		return idTipoPdg;
	}


	public void setIdTipoPdg(Long idTipoPdg) {
		this.idTipoPdg = idTipoPdg;
	}


	public String getTipoPdg() {
		return tipoPdg;
	}


	public void setTipoPdg(String tipoPdg) {
		this.tipoPdg = tipoPdg;
	}


	public Long getIdStatoRichiesta() {
		return idStatoRichiesta;
	}


	public void setIdStatoRichiesta(Long idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}


	public Long getIdTipoAllegatoGed() {
		return idTipoAllegatoGed;
	}


	public void setIdTipoAllegatoGed(Long idTipoAllegatoGed) {
		this.idTipoAllegatoGed = idTipoAllegatoGed;
	}
    
}
