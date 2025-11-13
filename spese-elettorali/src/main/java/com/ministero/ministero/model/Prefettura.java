package com.ministero.ministero.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ELENCO_PREFETTURE")
public class Prefettura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PREFETTURA")
    private Long id;

    @Column(name = "COD_UTG")
    private int codiceUtg;

    @Column(name = "COD_REGIONE")
    private String codiceRegione;

    @Column(name = "DESCR_UTG")
    private String descrizioneUtg;

    @Column(name = "SIGLA")
    private String sigla;

    @Column(name = "INDIRIZZO")
    private String indirizzo;

    @Column(name = "CAP")
    private String cap;

    @Column(name = "MAIL")
    private String mail;

    @Column(name = "PEC")
    private String pec;

    @Column(name = "TELEFONO")
    private String telefono;

    public Prefettura() {
    }

    public Prefettura(int codiceUtg, String codiceRegione, String descrizioneUtg, String sigla) {
        this.codiceUtg = codiceUtg;
        this.codiceRegione = codiceRegione;
        this.descrizioneUtg = descrizioneUtg;
        this.sigla = sigla;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodiceUtg() {
        return codiceUtg;
    }

    public void setCodiceUtg(int codiceUtg) {
        this.codiceUtg = codiceUtg;
    }

    public String getCodiceRegione() {
        return codiceRegione;
    }

    public void setCodiceRegione(String codiceRegione) {
        this.codiceRegione = codiceRegione;
    }

    public String getDescrizioneUtg() {
        return descrizioneUtg;
    }

    public void setDescrizioneUtg(String descrizioneUtg) {
        this.descrizioneUtg = descrizioneUtg;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return "Prefettura [id=" + id + ", codiceUtg=" + codiceUtg + ", codiceRegione=" + codiceRegione
                + ", descrizioneUtg=" + descrizioneUtg + ", sigla=" + sigla + "]";
    }

}
