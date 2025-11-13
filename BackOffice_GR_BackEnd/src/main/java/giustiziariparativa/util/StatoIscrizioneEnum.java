package giustiziariparativa.util;

public enum StatoIscrizioneEnum {
    ISCRITTO("Iscritto"),
    SOSPESO("Sospeso"),
    CANCELLATO("Cancellato");

    private String descrizioneStatoIscrizione;

    StatoIscrizioneEnum(String descrizione) {
        this.descrizioneStatoIscrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizioneStatoIscrizione;
    }
}
