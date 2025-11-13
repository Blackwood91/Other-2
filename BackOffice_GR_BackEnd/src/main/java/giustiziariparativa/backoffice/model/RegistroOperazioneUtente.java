package giustiziariparativa.backoffice.model;

import java.util.Date;

import giustiziariparativa.util.TipoOperazione;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "REGISTRO_OPERAZIONE_UTENTE")
public class RegistroOperazioneUtente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "ISEQ$$_29919", sequenceName = "ISEQ$$_29919", allocationSize = 1) //ISEQ$$_32202
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ISEQ$$_29919")
    @Column(name = "ID_OPERAZIONE")
    private Long id;

    @Column(name = "CODICE_FISCALE_OPERATORE")
    private String codiceFiscaleOperatore;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_OPERAZIONE")
    private TipoOperazione tipoOperazione;

    @Column(name = "DATA_OPERAZIONE")
    private Date dataOperazione;

    @Column(name = "DATA_ULTIMA_MODIFICA_UTENTE")
    private Date dataUltimaModificaUtente;

    public RegistroOperazioneUtente() {
    }

    public RegistroOperazioneUtente(Long id, String codiceFiscaleOperatore, TipoOperazione tipoOperazione,
            Date dataOperazione, Date dataUltimaModificaUtente) {
        this.id = id;
        this.codiceFiscaleOperatore = codiceFiscaleOperatore;
        this.tipoOperazione = tipoOperazione;
        this.dataOperazione = dataOperazione;
        this.dataUltimaModificaUtente = dataUltimaModificaUtente;
    }

    public Long getId() {
        return id;
    }

    public String getCodiceFiscaleOperatore() {
        return codiceFiscaleOperatore;
    }

    public TipoOperazione getTipoOperazione() {
        return tipoOperazione;
    }

    public Date getDataOperazione() {
        return dataOperazione;
    }

    public Date getDataUltimaModificaUtente() {
        return dataUltimaModificaUtente;
    }

    public void setCodiceFiscaleOperatore(String codiceFiscaleOperatore) {
        this.codiceFiscaleOperatore = codiceFiscaleOperatore;
    }

    public void setTipoOperazione(TipoOperazione tipoOperazione) {
        this.tipoOperazione = tipoOperazione;
    }

    public void setDataOperazione(Date dataOperazione) {
        this.dataOperazione = dataOperazione;
    }

    public void setDataUltimaModificaUtente(Date dataUltimaModificaUtente) {
        this.dataUltimaModificaUtente = dataUltimaModificaUtente;
    }

    @Override
    public String toString() {
        return "RegistroOperazioneUtente [id=" + id + ", codiceFiscaleOperatore=" + codiceFiscaleOperatore
                + ", tipoOperazione=" + tipoOperazione + "]";
    }

}
