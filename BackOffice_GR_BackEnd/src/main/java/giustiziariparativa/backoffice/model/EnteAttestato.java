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
@Table(name = "ENTE_ATTESTATO")
public class EnteAttestato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name = "ISEQ$$_29909", sequenceName = "ISEQ$$_29909", allocationSize = 1) //SEQ_ENTE_ATTESTATO
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ISEQ$$_29909")    
    @Column(name = "ID_ENTE")
    private Long idEnteAttestato;

    @Column(name = "ENTE_ATTESTATO")
    private String enteAttestato;

    @Column(name = "TIPOLOGIA")
    private String tipologiaEnte;

    @Column(name = "CONVENZIONATO")
    private int isConvenzionato;

    @Column(name = "DATA_INSERIMENTO")
    private Date dataInserimentoEnte;

    @Column(name = "DATA_MODIFICA")
    private Date dataModificaEnte;

     @Column(name = "ID_TIPOLOGIA_ENTE_FORMATORE")
     private Long idTipologiaEnteFormatore;

    public EnteAttestato() {

    }

    public EnteAttestato(Long idEnteAttestato, String enteAttestato, String tipologiaEnte, int isConvenzionato,
            Date dataInserimentoEnte, Date dataModificaEnte, Long idTipologiaEnteFormatore) {
        this.idEnteAttestato = idEnteAttestato;
        this.enteAttestato = enteAttestato;
        this.tipologiaEnte = tipologiaEnte;
        this.isConvenzionato = isConvenzionato;
        this.dataInserimentoEnte = dataInserimentoEnte;
        this.dataModificaEnte = dataModificaEnte;
        this.idTipologiaEnteFormatore = idTipologiaEnteFormatore;
    }

    public void setIdEnteAttestato(Long idEnteAttestato) {
        this.idEnteAttestato = idEnteAttestato;
    }

    public Long getIdEnteAttestato() {
        return idEnteAttestato;
    }

    public String getEnteAttestato() {
        return enteAttestato;
    }

    public String getTipologiaEnte() {
        return tipologiaEnte;
    }

    public int getIsConvenzionato() {
        return isConvenzionato;
    }

    public Date getDataInserimentoEnte() {
        return dataInserimentoEnte;
    }

    public Date getDataModificaEnte() {
        return dataModificaEnte;
    }

    public void setEnteAttestato(String enteAttestato) {
        this.enteAttestato = enteAttestato;
    }

    public void setTipologiaEnte(String tipologiaEnte) {
        this.tipologiaEnte = tipologiaEnte;
    }

    public void setIsConvenzionato(int isConvenzionato) {
        this.isConvenzionato = isConvenzionato;
    }

    public void setDataInserimentoEnte(Date dataInserimentoEnte) {
        this.dataInserimentoEnte = dataInserimentoEnte;
    }

    public void setDataModificaEnte(Date dataModificaEnte) {
        this.dataModificaEnte = dataModificaEnte;
    }

    @Override
    public String toString() {
        return "EnteAttestato [idEnteAttestato=" + idEnteAttestato + ", enteAttestato=" + enteAttestato
                + ", tipologiaEnte=" + tipologiaEnte + ", isConvenzionato=" + isConvenzionato + ", dataInserimentoEnte="
                + dataInserimentoEnte + ", dataModificaEnte=" + dataModificaEnte + "]";
    }

    public Long getIdTipologiaEnteFormatore() {
    	return idTipologiaEnteFormatore;
    }

    public void setIdTipologiaEnteFormatore(Long idTipologiaEnteFormatore) {
    	this.idTipologiaEnteFormatore = idTipologiaEnteFormatore;
    }

}
