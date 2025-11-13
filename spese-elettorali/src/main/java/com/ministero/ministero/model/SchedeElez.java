package com.ministero.ministero.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "schede_elez")
public class SchedeElez {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SCHEDE_ELEZ")
    private Long id;

    @Column(name = "ANNO")
    private int anno;

    @Column(name = "COD_ELEZIONE")
    private int codElezione;

    @Column(name = "TIPO_SCHEDA")
    private String tipoScheda;

    @Column(name = "NUM_SCHEDE")
    private int numSchede;

    @Column(name = "FK_ID_ELEZIONE")
    private Long fkIdElezione;

    @Column(name = "TIPO_SCHEDE_NOME_COMPLETO")
    private String tipoSchedeNomeCompleto;

    public SchedeElez() {

    }

    public SchedeElez(int anno, int codElezione, String tipoScheda, int numSchede, Long fkIdElezione, String tipoSchedeNomeCompleto) {
        this.anno = anno;
        this.codElezione = codElezione;
        this.tipoScheda = tipoScheda;
        this.numSchede = numSchede;
        this.fkIdElezione = fkIdElezione;
        this.tipoSchedeNomeCompleto = tipoSchedeNomeCompleto;
    }

    

    public SchedeElez(String tipoScheda, int numSchede) {
        this.tipoScheda = tipoScheda;
        this.numSchede = numSchede;
    }

    public Long getId() {
        return id;
    }

    public int getAnno() {
        return anno;
    }

    public Long getFkIdElezione() {
        return fkIdElezione;
    }

    public void setFkIdElezione(Long fkIdElezione) {
        this.fkIdElezione = fkIdElezione;
    }

    public int getCodElezione() {
        return codElezione;
    }

    public String getTipoScheda() {
        return tipoScheda;
    }

    public int getNumSchede() {
        return numSchede;
    }

    public String getTipoSchedeNomeCompleto() {
        return tipoSchedeNomeCompleto;
    }

    public void setTipoSchedeNomeCompleto(String tipoSchedeNomeCompleto) {
        this.tipoSchedeNomeCompleto = tipoSchedeNomeCompleto;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setCodElezione(int codElezione) {
        this.codElezione = codElezione;
    }

    public void setTipoScheda(String tipoScheda) {
        this.tipoScheda = tipoScheda;
    }

    public void setNumSchede(int numSchede) {
        this.numSchede = numSchede;
    }

    @Override
    public String toString() {
        return "SchedeElez [id=" + id + ", anno=" + anno + ", codElezione=" + codElezione + ", tipoScheda=" + tipoScheda
                + ", numSchede=" + numSchede + ", fkIdElezione=" + fkIdElezione + ", tipoSchedeNomeCompleto="
                + tipoSchedeNomeCompleto + "]";
    }

    

    
}
