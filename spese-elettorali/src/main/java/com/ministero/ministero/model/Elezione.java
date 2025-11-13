package com.ministero.ministero.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ELEZIONI")
public class Elezione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ELEZIONE")
    private Long id;

    
    @Column(name = "ANNO")
    private int anno;

    @Column(name = "COD_ELEZIONE")
    private int codElezione;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "DESCR_ELEZIONE")
    private String descrElezione;

    @Column(name = "DATA_ELEZIONE")
    private String dataElezione;

    @Column(name = "NUM_SCHEDE")
    private int numSchede;

    @Column(name = "SCHEDE_STATO")
    private int schedeStato;

    @Column(name = "RIMBORSO_SPESE_PRES")
    private int rimborsoSpesePres;

    @Column(name = "PERC_ANTICIPO")
    private int percAnticipo;

    @Column(name = "RENDICONTO")
    private String rendiconto;

    @Column(name = "COVID")
    private String covid;

    @Column(name ="CALCOLO_ONORARI_FATTO")
    private String calcoloOnorariFatto;

    public Elezione() {
    }

    public Elezione(int anno, int codElezione, String tipo) {
        this.anno = anno;
        this.codElezione = codElezione;
        this.tipo = tipo;
    }


    public Elezione(Long id, int codElezione, String descrElezione) {
        this.id = id;
        this.codElezione = codElezione;
        this.descrElezione = descrElezione;
    }

    public Elezione(Long id, int anno, int codElezione, String tipo) {
        this.id = id;
        this.anno = anno;
        this.codElezione = codElezione;
        this.tipo = tipo;
    }

    

    public Elezione(int anno, int codElezione, String tipo, String descrElezione, String dataElezione, int numSchede,
            int schedeStato, int rimborsoSpesePres, int percAnticipo, String rendiconto, String covid, String calcoloOnorariFatto) {
        this.anno = anno;
        this.codElezione = codElezione;
        this.tipo = tipo;
        this.descrElezione = descrElezione;
        this.dataElezione = dataElezione;
        this.numSchede = numSchede;
        this.schedeStato = schedeStato;
        this.rimborsoSpesePres = rimborsoSpesePres;
        this.percAnticipo = percAnticipo;
        this.rendiconto = rendiconto;
        this.covid = covid;
        this.calcoloOnorariFatto = calcoloOnorariFatto;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalcoloOnorariFatto() {
        return calcoloOnorariFatto;
    }

    public void setCalcoloOnorariFatto(String calcoloOnorariFatto) {
        this.calcoloOnorariFatto = calcoloOnorariFatto;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getCodElezione() {
        return codElezione;
    }

    public void setCodElezione(int codElezione) {
        this.codElezione = codElezione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescrElezione() {
        return descrElezione;
    }

    public void setDescrElezione(String descrElezione) {
        this.descrElezione = descrElezione;
    }

    public String getDataElezione() {
        return dataElezione;
    }

    public void setDataElezione(String dataElezione) {
        this.dataElezione = dataElezione;
    }

    public int getNumSchede() {
        return numSchede;
    }

    public void setNumSchede(int numSchede) {
        this.numSchede = numSchede;
    }

    public int getSchedeStato() {
        return schedeStato;
    }

    public void setSchedeStato(int schedeStato) {
        this.schedeStato = schedeStato;
    }

    public int getRimborsoSpesePres() {
        return rimborsoSpesePres;
    }

    public void setRimborsoSpesePres(int rimborsoSpesePres) {
        this.rimborsoSpesePres = rimborsoSpesePres;
    }

    public int getPercAnticipo() {
        return percAnticipo;
    }

    public void setPercAnticipo(int percAnticipo) {
        this.percAnticipo = percAnticipo;
    }

    public String getRendiconto() {
        return rendiconto;
    }

    public void setRendiconto(String rendiconto) {
        this.rendiconto = rendiconto;
    }

    public String getCovid() {
        return covid;
    }

    public void setCovid(String covid) {
        this.covid = covid;
    }

    

    @Override
    public String toString() {
        return "Elezione [id=" + id + ", anno=" + anno + ", codElezione=" + codElezione + ", tipo=" + tipo
                + ", descrElezione=" + descrElezione + ", dataElezione=" + dataElezione + ", numSchede=" + numSchede
                + ", schedeStato=" + schedeStato + ", rimborsoSpesePres=" + rimborsoSpesePres + ", percAnticipo="
                + percAnticipo + ", rendiconto=" + rendiconto + ", covid=" + covid + ", calcoloOnorariFatto=" + calcoloOnorariFatto + "]";
    }



}
