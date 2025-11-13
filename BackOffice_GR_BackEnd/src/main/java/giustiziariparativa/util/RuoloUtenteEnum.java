package giustiziariparativa.util;

public enum RuoloUtenteEnum {
    AMMINISTRATORE("Amministratore"),
    UTENTE_INTERNO_MINISTERO("Utente Interno Ministero della Giustizia"),
    UTENTE_ESTERNO("Utente Esterno");

    private String descrizioneRuoloUtente;

    RuoloUtenteEnum(String descrizione) {
        this.descrizioneRuoloUtente = descrizione;
    }

    public String getDescrizione() {
        return descrizioneRuoloUtente;
    }
}
