package com.ministero.ministero.util;

public class Messaggio {

    public String esito;

    public String descrizione;

    public Messaggio(){

    }

    
    public Messaggio(String esito, String descrizione) {
        this.esito = esito;
        this.descrizione = descrizione;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    
    
}
