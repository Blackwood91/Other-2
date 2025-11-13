package giustiziariparativa.backoffice.model;

import java.util.Date;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter.DataWithMediaType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UTENTE_ABILITATO")
public class UtenteAbilitato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "ISEQ$$_29927", sequenceName = "ISEQ$$_29927", allocationSize = 1) //ISEQ$$_32210
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ISEQ$$_29927")
    @Column(name = "ID_UTENTE")
    private Long utenteId;

    @Column(name = "NOME_UTENTE")
    private String nomeUtente;

    @Column(name = "COGNOME_UTENTE")
    private String cognomeUtente;

    @Column(name = "CODICE_FISCALE_UTENTE")
    private String codiceFiscaleUtente;

    @Column(name = "ENTE_APPARTENENZA")
    private String enteAppartenenza;

    @Column(name = "ABILITATO")
    private int isAbilitato;

    // @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "DATA_LOGIN")
    private Date dataLogin;

    // @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "DATA_LOGOUT")
    private Date dataLogout;

    // @Enumerated(EnumType.STRING)
    @Column(name = "RUOLO_UTENTE_ID")
    private Long idRuoloUtente;

    @Column(name = "DATA_INSERIMENTO")
    private Date dataInserimentoUtente;

    @Column(name = "DATA_MODIFICA")
    private Date dataModificaUtente;

    @Column(name = "DATA_DISABILITA")
    private Date dataDisabilitaUtente;

    public UtenteAbilitato() {
    }

    public UtenteAbilitato(Long utenteId, String nomeUtente, String cognomeUtente, String codiceFiscaleUtente,
            String enteAppartenenza, int isAbilitato, Date dataLogin, Date dataLogout, Long idRuoloUtente,
            Date dataInserimentoUtente, Date dataModificaUtente, Date dataDisabilitaUtente) {
        this.utenteId = utenteId;
        this.nomeUtente = nomeUtente;
        this.cognomeUtente = cognomeUtente;
        this.codiceFiscaleUtente = codiceFiscaleUtente;
        this.enteAppartenenza = enteAppartenenza;
        this.isAbilitato = isAbilitato;
        this.dataLogin = dataLogin;
        this.dataLogout = dataLogout;
        this.idRuoloUtente = idRuoloUtente;
        this.dataInserimentoUtente = dataInserimentoUtente;
        this.dataModificaUtente = dataModificaUtente;
        this.dataDisabilitaUtente = dataDisabilitaUtente;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public String getCognomeUtente() {
        return cognomeUtente;
    }

    public String getCodiceFiscaleUtente() {
        return codiceFiscaleUtente;
    }

    public String getEnteAppartenenza() {
        return enteAppartenenza;
    }

    public int getIsAbilitato() {
        return isAbilitato;
    }

    public Date getDataLogin() {
        return dataLogin;
    }

    public Date getDataLogout() {
        return dataLogout;
    }

    public Long getIdRuoloUtente() {
        return idRuoloUtente;
    }

    public Date getDataInserimentoUtente() {
        return dataInserimentoUtente;
    }

    public Date getDataModificaUtente() {
        return dataModificaUtente;
    }

    public Date getDataDisabilitaUtente() {
        return dataDisabilitaUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente = cognomeUtente;
    }

    public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
        this.codiceFiscaleUtente = codiceFiscaleUtente;
    }

    public void setEnteAppartenenza(String enteAppartenenza) {
        this.enteAppartenenza = enteAppartenenza;
    }

    public void setIsAbilitato(int isAbilitato) {
        this.isAbilitato = isAbilitato;
    }

    public void setDataLogin(Date dataLogin) {
        this.dataLogin = dataLogin;
    }

    public void setDataLogout(Date dataLogout) {
        this.dataLogout = dataLogout;
    }

    public void setIdRuoloUtente(Long idRuoloUtente) {
        this.idRuoloUtente = idRuoloUtente;
    }

    public void setDataInserimentoUtente(Date dataInserimentoUtente) {
        this.dataInserimentoUtente = dataInserimentoUtente;
    }

    public void setDataModificaUtente(Date dataModificaUtente) {
        this.dataModificaUtente = dataModificaUtente;
    }

    public void setDataDisabilitaUtente(Date dataDisabilitaUtente) {
        this.dataDisabilitaUtente = dataDisabilitaUtente;
    }

    @Override
    public String toString() {
        return "UtenteAbilitato [utenteId=" + utenteId + ", nomeUtente=" + nomeUtente + ", cognomeUtente="
                + cognomeUtente + ", codiceFiscaleUtente=" + codiceFiscaleUtente + ", enteAppartenenza="
                + enteAppartenenza + ", isAbilitato=" + isAbilitato + ", dataLogin=" + dataLogin + ", dataLogout="
                + dataLogout + ", idRuoloUtente=" + idRuoloUtente + ", dataInserimentoUtente=" + dataInserimentoUtente
                + ", dataModificaUtente=" + dataModificaUtente + ", dataDisabilitaUtente=" + dataDisabilitaUtente + "]";
    }

}