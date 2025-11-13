package com.ministero.ministero.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_SCHEDE")
public class TipoSchede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_SCHEDE")
    private Long id;
    
    @Column(name = "TIPO_ELEZIONE")
    private String tipoElezione;

    @Column(name = "COD_SCHEDA")
    private String codScheda;

    @Column(name = "DESCR_SCHEDA")
    private String descrScheda;

    @Column(name = "COMP_STATO")
    private String competenzaStato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoElezione() {
        return tipoElezione;
    }

    public void setTipoElezione(String tipoElezione) {
        this.tipoElezione = tipoElezione;
    }

    public String getCodScheda() {
        return codScheda;
    }

    public void setCodScheda(String codScheda) {
        this.codScheda = codScheda;
    }

    public String getDescrScheda() {
        return descrScheda;
    }

    public void setDescrScheda(String descrScheda) {
        this.descrScheda = descrScheda;
    }

    public String getCompetenzaStato() {
        return competenzaStato;
    }

    public void setCompetenzaStato(String competenzaStato) {
        this.competenzaStato = competenzaStato;
    }

    
}
