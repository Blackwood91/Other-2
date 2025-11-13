package giustiziariparativa.backoffice.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "STORICO_STATO_MEDIATORE")
public class StoricoStatoMediarore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "ISEQ$$_29924", sequenceName = "ISEQ$$_29924", allocationSize = 1) //ISEQ$$_32207
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ISEQ$$_29924")
    @Column(name = "ID_STORICO_STATO")
    private Long idStatoStorico;

    @Column(name = "ID_MEDIATORE")
    private Long idMediatore;

    @Column(name = "ID_STATO")
    private Long idStato;

    @Column(name = "TIPOLOGIA")
    private String tipologiaStato;

    @Column(name = "DATA_STATO")
    private Date dataStato; // data operazione

    @Column(name = "ID_PROVVEDIMENTO")
    private Long idProvvedimento;

    @Column(name = "DATA_FINE")
    private Date dataFine; // in caso di data di spospensione

    @Column(name = "MOTIVAZIONE")
    private String motivazioneStatoIscrizione;

    @Column(name = "DATA_INSERIMENTO")
    private Date dataInserimentoStato;

    @Column(name = "DATA_MODIFICA")
    private Date dataModificaStato;

    public StoricoStatoMediarore() {
    }

    public StoricoStatoMediarore(Long idStatoStorico, Long idMediatore, Long idStato, String tipologiaStato,
            Date dataStato, Long idProvvedimento, Date dataFine, String motivazioneStatoIscrizione,
            Date dataInserimentoStato, Date dataModificaStato) {
        this.idStatoStorico = idStatoStorico;
        this.idMediatore = idMediatore;
        this.idStato = idStato;
        this.tipologiaStato = tipologiaStato;
        this.dataStato = dataStato;
        this.idProvvedimento = idProvvedimento;
        this.dataFine = dataFine;
        this.motivazioneStatoIscrizione = motivazioneStatoIscrizione;
        this.dataInserimentoStato = dataInserimentoStato;
        this.dataModificaStato = dataModificaStato;
    }

    public Long getIdStatoStorico() {
        return idStatoStorico;
    }
    
    public void setIdStaticoStorico(Long idStatoStorico) {
        this.idStatoStorico = idStatoStorico;
    }

    public Long getIdMediatore() {
        return idMediatore;
    }

    public Long getIdStato() {
        return idStato;
    }

    public String getTipologiaStato() {
        return tipologiaStato;
    }

    public Date getDataStato() {
        return dataStato;
    }

    public Long getIdProvvedimento() {
        return idProvvedimento;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public String getMotivazioneStatoIscrizione() {
        return motivazioneStatoIscrizione;
    }

    public Date getDataInserimentoStato() {
        return dataInserimentoStato;
    }

    public Date getDataModificaStato() {
        return dataModificaStato;
    }

    public void setIdMediatore(Long idMediatore) {
        this.idMediatore = idMediatore;
    }

    public void setIdStato(Long idStato) {
        this.idStato = idStato;
    }

    public void setTipologiaStato(String tipologiaStato) {
        this.tipologiaStato = tipologiaStato;
    }

    public void setDataStato(Date dataStato) {
        this.dataStato = dataStato;
    }

    public void setIdProvvedimento(Long idProvvedimento) {
        this.idProvvedimento = idProvvedimento;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public void setMotivazioneStatoIscrizione(String motivazioneStatoIscrizione) {
        this.motivazioneStatoIscrizione = motivazioneStatoIscrizione;
    }

    public void setDataInserimentoStato(Date dataInserimentoStato) {
        this.dataInserimentoStato = dataInserimentoStato;
    }

    public void setDataModificaStato(Date dataModificaStato) {
        this.dataModificaStato = dataModificaStato;
    }

    @Override
    public String toString() {
        return "StoricoStatoMediarore [idStatoStorico=" + idStatoStorico + ", idMediatore=" + idMediatore + ", idStato="
                + idStato + ", tipologiaStato=" + tipologiaStato + ", dataStato=" + dataStato + ", idProvvedimento="
                + idProvvedimento + ", dataFine=" + dataFine + ", motivazioneStatoIscrizione="
                + motivazioneStatoIscrizione + ", dataInserimentoStato=" + dataInserimentoStato + ", dataModificaStato="
                + dataModificaStato + "]";
    }

}
