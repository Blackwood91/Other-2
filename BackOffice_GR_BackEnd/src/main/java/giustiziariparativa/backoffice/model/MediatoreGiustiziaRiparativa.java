package giustiziariparativa.backoffice.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "MEDIATORE_GIUSTIZIA_RIPARATIVA")
public class MediatoreGiustiziaRiparativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "ISEQ$$_29911", sequenceName = "ISEQ$$_29911", allocationSize = 1)  //ISEQ$$_32194
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ISEQ$$_29911")
    @Column(name = "ID_MEDIATORE")
    private Long idMediatore;

    @Column(name = "NOME_MEDIATORE")
    private String nomeMediatore;

    @Column(name = "COGNOME_MEDIATORE")
    private String cognomeMediatore;

    @Column(name = "CODICE_FISCALE")
    private String codiceFiscale;

    @Column(name = "NUMERO_ISCRIZIONE")
    private String numeroIscrizioneElenco;

    @Column(name = "DATA_ISCRIZIONE")
    @Temporal(TemporalType.DATE)
    private Date dataIscrizioneElenco;

    @Column(name = "QUALIFICA_FORMATORE")
    private int isFormatore;

    @Column(name = "LUOGO_NASCITA")
    private String luogoDiNascita;

    @Column(name = "DATA_NASCITA")
    @Temporal(TemporalType.DATE)
    private Date dataDiNascita;

    @Column(name = "CITTA_RESIDENZA")
    private String cittaDiResidenza;

    @Column(name = "PROVICINA_RESIDENZA")
    private String provinciaDiResidenza;

    @Column(name = "INDIRIZZO")
    private String indirizzo;

    @Column(name = "NUMERO_CIVICO")
    private String numeroCivico;

    @Column(name = "CAP")
    private String cap;

    @Column(name = "INDIRIZZO_PEC")
    private String indirizzoPec;

    @Column(name = "REQUISITI_ISCRIZIONE")
    private String requisitiIscrizioneElenco;

    @Column(name = "ENTE_ATTESTATO_ID") // Associazione opzionale
    private Long enteAttestato;

    // @ManyToOne
    // @JoinColumn(name = "STATO_ISCRIZIONE_ID", nullable = false) // Associazione
    // obbligatoria
    @Column(name = "STATO_ISCRIZIONE_ID", nullable = false)
    private Long descrizioneStatoIscrizione;

    @Column(name = "DATA_INSERIMENTO")
    private Date dataInserimentoMediatore;

    @Column(name = "DATA_MODIFICA")
    private Date dataModificaMediatore;

    public MediatoreGiustiziaRiparativa() {
        super();
    }

    public MediatoreGiustiziaRiparativa(Object[] obj) {
        this.isFormatore = (int) obj[0];
        this.dataIscrizioneElenco = (Date) obj[1];
        this.dataDiNascita = (Date) obj[2];
        this.enteAttestato = (Long) obj[3];
        this.idMediatore = (Long) obj[4];
        this.descrizioneStatoIscrizione = (Long) obj[5];
        this.codiceFiscale = (String) obj[6];
        this.cognomeMediatore = (String) obj[7];
        this.indirizzo = (String) obj[8];
        this.indirizzoPec = (String) obj[9];
        this.luogoDiNascita = (String) obj[10];
        this.nomeMediatore = (String) obj[11];
        this.numeroIscrizioneElenco = (String) obj[12];
        this.requisitiIscrizioneElenco = (String) obj[13];
        this.dataInserimentoMediatore = (Date) obj[14];
        this.dataModificaMediatore = (Date) obj[15];

    }

    public MediatoreGiustiziaRiparativa(Long idMediatore, String nomeMediatore, String cognomeMediatore,
            String codiceFiscale, String numeroIscrizioneElenco, Date dataIscrizioneElenco, int isFormatore,
            String luogoDiNascita, Date dataDiNascita, String cittaDiResidenza, String provinciaDiResidenza,
            String indirizzo, String numeroCivico, String cap, String indirizzoPec, String requisitiIscrizioneElenco,
            Long enteAttestato, Long descrizioneStatoIscrizione, Date dataInserimentoMediatore,
            Date dataModificaMediatore) {
        this.idMediatore = idMediatore;
        this.nomeMediatore = nomeMediatore;
        this.cognomeMediatore = cognomeMediatore;
        this.codiceFiscale = codiceFiscale;
        this.numeroIscrizioneElenco = numeroIscrizioneElenco;
        this.dataIscrizioneElenco = dataIscrizioneElenco;
        this.isFormatore = isFormatore;
        this.luogoDiNascita = luogoDiNascita;
        this.dataDiNascita = dataDiNascita;
        this.cittaDiResidenza = cittaDiResidenza;
        this.provinciaDiResidenza = provinciaDiResidenza;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.cap = cap;
        this.indirizzoPec = indirizzoPec;
        this.requisitiIscrizioneElenco = requisitiIscrizioneElenco;
        this.enteAttestato = enteAttestato;
        this.descrizioneStatoIscrizione = descrizioneStatoIscrizione;
        this.dataInserimentoMediatore = dataInserimentoMediatore;
        this.dataModificaMediatore = dataModificaMediatore;
    }



    
    
    public void setIdMediatore(Long idMediatore) {
		this.idMediatore = idMediatore;
	}

	public Long getIdMediatore() {
        return idMediatore;
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

    public String getLuogoDiNascita() {
        return luogoDiNascita;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public String getCittaDiResidenza() {
        return cittaDiResidenza;
    }

    public String getProvinciaDiResidenza() {
        return provinciaDiResidenza;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public String getCap() {
        return cap;
    }

    public String getIndirizzoPec() {
        return indirizzoPec;
    }

    public String getRequisitiIscrizioneElenco() {
        return requisitiIscrizioneElenco;
    }

    public Long getEnteAttestato() {
        return enteAttestato;
    }

    public Long getDescrizioneStatoIscrizione() {
        return descrizioneStatoIscrizione;
    }

    public Date getDataInserimentoMediatore() {
        return dataInserimentoMediatore;
    }

    public Date getDataModificaMediatore() {
        return dataModificaMediatore;
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

    public void setLuogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public void setCittaDiResidenza(String cittaDiResidenza) {
        this.cittaDiResidenza = cittaDiResidenza;
    }

    public void setProvinciaDiResidenza(String provinciaDiResidenza) {
        this.provinciaDiResidenza = provinciaDiResidenza;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public void setIndirizzoPec(String indirizzoPec) {
        this.indirizzoPec = indirizzoPec;
    }

    public void setRequisitiIscrizioneElenco(String requisitiIscrizioneElenco) {
        this.requisitiIscrizioneElenco = requisitiIscrizioneElenco;
    }

    public void setEnteAttestato(Long enteAttestato) {
        this.enteAttestato = enteAttestato;
    }

    public void setDescrizioneStatoIscrizione(Long descrizioneStatoIscrizione) {
        this.descrizioneStatoIscrizione = descrizioneStatoIscrizione;
    }

    public void setDataInserimentoMediatore(Date dataInserimentoMediatore) {
        this.dataInserimentoMediatore = dataInserimentoMediatore;
    }

    public void setDataModificaMediatore(Date dataModificaMediatore) {
        this.dataModificaMediatore = dataModificaMediatore;
    }

    @Override
    public String toString() {
        return "MediatoreGiustiziaRiparativa [idMediatore=" + idMediatore + ", nomeMediatore=" + nomeMediatore
                + ", cognomeMediatore=" + cognomeMediatore + ", codiceFiscale=" + codiceFiscale
                + ", numeroIscrizioneElenco=" + numeroIscrizioneElenco + ", dataIscrizioneElenco="
                + dataIscrizioneElenco + ", isFormatore=" + isFormatore + ", luogoDiNascita=" + luogoDiNascita
                + ", dataDiNascita=" + dataDiNascita + ", cittaDiResidenza=" + cittaDiResidenza
                + ", provinciaDiResidenza=" + provinciaDiResidenza + ", indirizzo=" + indirizzo + ", numeroCivico="
                + numeroCivico + ", cap=" + cap + ", indirizzoPec=" + indirizzoPec + ", requisitiIscrizioneElenco="
                + requisitiIscrizioneElenco + ", enteAttestato=" + enteAttestato + ", descrizioneStatoIscrizione="
                + descrizioneStatoIscrizione + ", dataInserimentoMediatore=" + dataInserimentoMediatore
                + ", dataModificaMediatore=" + dataModificaMediatore + "]";
    }

}
