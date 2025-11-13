package giustiziariparativa.backoffice.DTO;

public class CreateUtenteRequest {
    private String nomeUtente;
    private String cognomeUtente;
    private String codiceFiscaleUtente;
    private String enteAppartenenza;
    private Long idRuoloUtente;
    private int isAbilitato;

    public CreateUtenteRequest() {
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getCognomeUtente() {
        return cognomeUtente;
    }

    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente = cognomeUtente;
    }

    public String getCodiceFiscaleUtente() {
        return codiceFiscaleUtente;
    }

    public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
        this.codiceFiscaleUtente = codiceFiscaleUtente;
    }

    public String getEnteAppartenenza() {
        return enteAppartenenza;
    }

    public void setEnteAppartenenza(String enteAppartenenza) {
        this.enteAppartenenza = enteAppartenenza;
    }

    public Long getIdRuoloUtente() {
        return idRuoloUtente;
    }

    public void setIdRuoloUtente(Long idRuoloUtente) {
        this.idRuoloUtente = idRuoloUtente;
    }

    public int getIsAbilitato() {
        return isAbilitato;
    }

    public void setIsAbilitato(int isAbilitato) {
        this.isAbilitato = isAbilitato;
    }

    @Override
    public String toString() {
        return "CreateUtenteRequest [nomeUtente=" + nomeUtente + ", cognomeUtente=" + cognomeUtente
                + ", codiceFiscaleUtente=" + codiceFiscaleUtente + ", enteAppartenenza=" + enteAppartenenza
                + ", idRuoloUtente=" + idRuoloUtente + ", isAbilitato=" + isAbilitato + "]";
    }

}
