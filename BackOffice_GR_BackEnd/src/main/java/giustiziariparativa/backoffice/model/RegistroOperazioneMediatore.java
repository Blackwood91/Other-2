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
@Table(name = "REGISTRO_OPERAZIONE_MEDIATORE")
public class RegistroOperazioneMediatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "ISEQ$$_29917", sequenceName = "ISEQ$$_29917", allocationSize = 1) //ISEQ$$_32200
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ISEQ$$_29917")
    @Column(name = "ID_OPERAZIONE_MEDIATORE")
    private Long id;

    @Column(name = "CODICE_FISCALE_OPERATORE")
    private String codiceFiscaleOperatore;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_OPERAZIONE")
    private TipoOperazione tipoOperazione;

    @Column(name = "DATA_OPERAZIONE")
    private Date dataOperazione;

    @Column(name = "DATA_UTILIMA_MODIFICA_MEDIATORE")
    private Date dataUltimaModificaMediatore;

    public RegistroOperazioneMediatore() {
    }

    public RegistroOperazioneMediatore(Long id, String codiceFiscaleOperatore, TipoOperazione tipoOperazione,
            Date dataOperazione, Date dataUltimaModificaMediatore) {
        this.id = id;
        this.codiceFiscaleOperatore = codiceFiscaleOperatore;
        this.tipoOperazione = tipoOperazione;
        this.dataOperazione = dataOperazione;
        this.dataUltimaModificaMediatore = dataUltimaModificaMediatore;
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

    public Date getDataUltimaModificaMediatore() {
        return dataUltimaModificaMediatore;
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

    public void setDataUltimaModificaMediatore(Date dataUltimaModificaMediatore) {
        this.dataUltimaModificaMediatore = dataUltimaModificaMediatore;
    }

    @Override
    public String toString() {
        return "RegistroOperazioneMediatore [id=" + id + ", codiceFiscaleOperatore=" + codiceFiscaleOperatore
                + ", tipoOperazione=" + tipoOperazione + "]";
    }

}
