package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "REGIONI_PROVINCE")
public class RegioneProvince {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGIONI_PROVINCE")
    private Long id;

    @Column(name = "CODICE_REGIONE")
    private int codiceRegione;

    @Column(name = "CODICE_PROVINCIA")
    private int codiceProvincia;

    @Column(name = "NOME_REGIONE")
    private String nomeRegione;

    @Column(name = "NOME_PROVINCIA")
    private String nomeProvincia;

    @Column(name = "SIGLA_PROVINCIA")
    private String siglaProvincia;

    public RegioneProvince() {
    }

    public RegioneProvince(Long id, int codiceRegione, int codiceProvincia, String nomeRegione, String nomeProvincia,
            			   String siglaProvincia) {
        this.id = id;
        this.codiceRegione = codiceRegione;
        this.codiceProvincia = codiceProvincia;
        this.nomeRegione = nomeRegione;
        this.nomeProvincia = nomeProvincia;
        this.siglaProvincia = siglaProvincia;
    }

    public Long getId() {
        return id;
    }

    public int getCodiceRegione() {
        return codiceRegione;
    }

    public int getCodiceProvincia() {
        return codiceProvincia;
    }

    public String getNomeRegione() {
        return nomeRegione;
    }

    public String getNomeProvincia() {
        return nomeProvincia;
    }

    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    public void setCodiceRegione(int codiceRegione) {
        this.codiceRegione = codiceRegione;
    }

    public void setCodiceProvincia(int codiceProvincia) {
        this.codiceProvincia = codiceProvincia;
    }

    public void setNomeRegione(String nomeRegione) {
        this.nomeRegione = nomeRegione;
    }

    public void setNomeProvincia(String nomeProvincia) {
        this.nomeProvincia = nomeProvincia;
    }

    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }



}
