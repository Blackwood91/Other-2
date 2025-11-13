package giustiziariparativa.util;

public enum TipoOperazione {
    INSERIMENTO_UTENTE("Hai inserito un nuovo utente"),
    INSERIMENTO_MEDIATORE("Hai inserito un nuovo mediatore nell'elenco"),
    AGGIORNAMENTO("Aggiornamento andato a buon fine"),
    ASSEGNA_RUOLO("E' stato assegnato un nuovo ruolo"),
    ABILITA("Utente abilitato"),
    DISABILITA("Utente disabilitato"),
    AGGIORNA_STATO("Lo stato di iscrizione è stato aggiornato");

    private String descrizioneTipoOperazione;

    TipoOperazione(String descrizione) {
        this.descrizioneTipoOperazione = descrizione;
    }

    public String getDescrizione() {
        return descrizioneTipoOperazione;
    }
}
