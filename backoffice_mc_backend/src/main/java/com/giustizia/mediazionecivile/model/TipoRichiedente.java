package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_RICHIEDENTE")
public class TipoRichiedente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_RICHIEDENTE")
    private Long id;

    @Column(name = "TIPO_RICHIEDENTE")
    private String tipo;

    @Column(name = "CODICE")
    private String codice;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    public TipoRichiedente() {
    }

    public TipoRichiedente(String tipo, String codice, String descrizione) {
        this.tipo = tipo;
        this.codice = codice;
        this.descrizione = descrizione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "TipoRichiedente [id=" + id + ", tipo=" + tipo + ", codice=" + codice + ", descrizione=" + descrizione
                + "]";
    }

}
