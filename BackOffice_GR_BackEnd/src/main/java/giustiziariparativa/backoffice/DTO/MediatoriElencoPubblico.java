package giustiziariparativa.backoffice.DTO;

import java.util.Date;

public class MediatoriElencoPubblico {

    private String nomeMediatore;
    private String cognomeMediatore;
    private String codiceFiscale;
    private String numeroIscrizioneElenco;
    private Date dataIscrizioneElenco;
    private int isFormatore;

    public MediatoriElencoPubblico() {
    }

    public String getNomeMediatore() {
        return nomeMediatore;
    }

    public String getCognomeMediatore() {
        return cognomeMediatore;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNumeroIscrizioneElenco() {
        return numeroIscrizioneElenco;
    }

    public Date getDataIscrizioneElenco() {
        return dataIscrizioneElenco;
    }

    public int getIsFormatore() {
        return isFormatore;
    }

    public void setNomeMediatore(String nomeMediatore) {
        this.nomeMediatore = nomeMediatore;
    }

    public void setCognomeMediatore(String cognomeMediatore) {
        this.cognomeMediatore = cognomeMediatore;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setNumeroIscrizioneElenco(String numeroIscrizioneElenco) {
        this.numeroIscrizioneElenco = numeroIscrizioneElenco;
    }

    public void setDataIscrizioneElenco(Date dataIscrizioneElenco) {
        this.dataIscrizioneElenco = dataIscrizioneElenco;
    }

    public void setIsFormatore(int isFormatore) {
        this.isFormatore = isFormatore;
    }

    @Override
    public String toString() {
        return "MediatoriElencoPubblico [nomeMediatore=" + nomeMediatore + ", cognomeMediatore=" + cognomeMediatore
                + ", codiceFiscale=" + codiceFiscale + ", numeroIscrizioneElenco=" + numeroIscrizioneElenco
                + ", dataIscrizioneElenco=" + dataIscrizioneElenco + ", isFormatore=" + isFormatore + "]";
    }

}
