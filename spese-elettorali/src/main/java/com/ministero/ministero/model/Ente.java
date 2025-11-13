package com.ministero.ministero.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ELENCO_ENTI")
public class Ente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENTE")
    private Long id;

    @Column(name = "COD_ENTE")
    private String codiceEnte;

    @Column(name = "COD_SUT")
    private String codiceSut;

    @Column(name = "DESCR_ENTE")
    private String descrizioneEnte;

    @Column(name = "COD_PROVINCIA")
    private int codiceProvincia;

    @Column(name = "COD_REGIONE")
    private int codiceRegione;

    @Column(name = "COD_ZONA")
    private int codiceZona;

    @Column(name = "COD_CATASTALE")
    private String codiceCatastale;

    @Column(name = "FASCIA_DEMOGRAFICA")
    private int fasciaDemografica;

    @Column(name = "POPOLAZIONE")
    private int popolazione;

    @Column(name = "PREFETTURA")
    private String prefettura;

    @Column(name = "PREFETTURA_CAP")
    private String prefetturaCap;

    @Column(name = "COD_UTG")
    private int codiceUtg;

    public Ente() {
    }

    public Ente(String codiceEnte, String codiceSut, String descrizioneEnte, int codiceUtg) {
        this.codiceEnte = codiceEnte;
        this.codiceSut = codiceSut;
        this.descrizioneEnte = descrizioneEnte;
        this.codiceUtg = codiceUtg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodiceEnte() {
        return codiceEnte;
    }

    public void setCodiceEnte(String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }

    public String getCodiceSut() {
        return codiceSut;
    }

    public void setCodiceSut(String codiceSut) {
        this.codiceSut = codiceSut;
    }

    public String getDescrizioneEnte() {
        return descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte) {
        this.descrizioneEnte = descrizioneEnte;
    }

    public int getCodiceProvincia() {
        return codiceProvincia;
    }

    public void setCodiceProvincia(int codiceProvincia) {
        this.codiceProvincia = codiceProvincia;
    }

    public int getCodiceRegione() {
        return codiceRegione;
    }

    public void setCodiceRegione(int codiceRegione) {
        this.codiceRegione = codiceRegione;
    }

    public int getCodiceZona() {
        return codiceZona;
    }

    public void setCodiceZona(int codiceZona) {
        this.codiceZona = codiceZona;
    }

    public String getCodiceCatastale() {
        return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {
        this.codiceCatastale = codiceCatastale;
    }

    public int getFasciaDemografica() {
        return fasciaDemografica;
    }

    public void setFasciaDemografica(int fasciaDemografica) {
        this.fasciaDemografica = fasciaDemografica;
    }

    public int getPopolazione() {
        return popolazione;
    }

    public void setPopolazione(int popolazione) {
        this.popolazione = popolazione;
    }

    public String getPrefettura() {
        return prefettura;
    }

    public void setPrefettura(String prefettura) {
        this.prefettura = prefettura;
    }

    public String getPrefetturaCap() {
        return prefetturaCap;
    }

    public void setPrefetturaCap(String prefetturaCap) {
        this.prefetturaCap = prefetturaCap;
    }

    public int getCodiceUtg() {
        return codiceUtg;
    }

    public void setCodiceUtg(int codiceUtg) {
        this.codiceUtg = codiceUtg;
    }

    @Override
    public String toString() {
        return "Ente [id=" + id + ", codiceEnte=" + codiceEnte + ", codiceSut=" + codiceSut + ", descrizioneEnte="
                + descrizioneEnte + ", codiceProvincia=" + codiceProvincia + ", codiceRegione=" + codiceRegione
                + ", codiceZona=" + codiceZona + ", codiceCatastale=" + codiceCatastale + ", fasciaDemografica="
                + fasciaDemografica + ", popolazione=" + popolazione + ", prefettura=" + prefettura + ", prefetturaCap="
                + prefetturaCap + ", codiceUtg=" + codiceUtg + "]";
    }

}
