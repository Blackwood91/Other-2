package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_ANAGRAFICA")
public class TipoAnagrafica {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_ANAGRAFICA")
    private Long idTipoAnagrafica;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    public TipoAnagrafica() {
    }

    public TipoAnagrafica(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getIdTipoAnagrafica() {
        return idTipoAnagrafica;
    }

    public void setIdTipoAnagrafica(Long idTipoAnagrafica) {
        this.idTipoAnagrafica = idTipoAnagrafica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
