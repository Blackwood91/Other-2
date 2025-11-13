package giustiziariparativa.backoffice.model;

import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROVVEDIMENTO")
public class Provvedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "ISEQ$$_29913", sequenceName = "ISEQ$$_29913", allocationSize = 1) //ISEQ$$_32196
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ISEQ$$_29913")
    @Column(name = "ID_PROVVEDIMENTO")
    private Long idProvvedimento;

    @Column(name = "PROVVEDIMENTO")
    @Lob
    private byte[] provvedimentoPdf;

    @Column(name = "DATA_PROVVEDIMENTO")
    private Date dataEmissioneProvvedimento;

    @Column(name = "DATA_INSERIMENTO")
    private Date dataInsertProvvedimento;

    @Column(name = "DATA_MODIFICA")
    private Date dataModificaProvvedimento;

    public Provvedimento() {
    }

    public Provvedimento(Long idProvvedimento, byte[] provvedimentoPdf, Date dataEmissioneProvvedimento,
            Date dataInsertProvvedimento, Date dataModificaProvvedimento) {
        this.idProvvedimento = idProvvedimento;
        this.provvedimentoPdf = provvedimentoPdf;
        this.dataEmissioneProvvedimento = dataEmissioneProvvedimento;
        this.dataInsertProvvedimento = dataInsertProvvedimento;
        this.dataModificaProvvedimento = dataModificaProvvedimento;
    }

    public Long getIdProvvedimento() {
        return idProvvedimento;
    }

    public byte[] getProvvedimentoPdf() {
        return provvedimentoPdf;
    }

    public Date getDataEmissioneProvvedimento() {
        return dataEmissioneProvvedimento;
    }

    public Date getDataInsertProvvedimento() {
        return dataInsertProvvedimento;
    }

    public Date getDataModificaProvvedimento() {
        return dataModificaProvvedimento;
    }

    public void setProvvedimentoPdf(byte[] provvedimentoPdf) {
        this.provvedimentoPdf = provvedimentoPdf;
    }

    public void setDataEmissioneProvvedimento(Date dataEmissioneProvvedimento) {
        this.dataEmissioneProvvedimento = dataEmissioneProvvedimento;
    }

    public void setDataInsertProvvedimento(Date dataInsertProvvedimento) {
        this.dataInsertProvvedimento = dataInsertProvvedimento;
    }

    public void setDataModificaProvvedimento(Date dataModificaProvvedimento) {
        this.dataModificaProvvedimento = dataModificaProvvedimento;
    }

    @Override
    public String toString() {
        return "Provvedimento [idProvvedimento=" + idProvvedimento + ", provvedimentoPdf="
                + Arrays.toString(provvedimentoPdf) + ", dataEmissioneProvvedimento=" + dataEmissioneProvvedimento
                + ", dataInsertProvvedimento=" + dataInsertProvvedimento + ", dataModificaProvvedimento="
                + dataModificaProvvedimento + "]";
    }

}
