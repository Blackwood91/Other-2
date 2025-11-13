package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SOGGETTO_RICHIEDENTE")
public class SoggettoRichiedente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOGGETTO_RICHIEDENTE")
    private Long id;

    @Column(name = "SOGGETTO_RICHIEDENTE")
    private String soggettoRichiedente;

    public SoggettoRichiedente() {
    }

    public SoggettoRichiedente(String soggettoRichiedente) {
        this.soggettoRichiedente = soggettoRichiedente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoggettoRichiedente() {
        return soggettoRichiedente;
    }

    public void setSoggettoRichiedente(String soggettoRichiedente) {
        this.soggettoRichiedente = soggettoRichiedente;
    }

    @Override
    public String toString() {
        return "SoggettoRichiedente [id=" + id + ", soggettoRichiedente=" + soggettoRichiedente + "]";
    }

}
