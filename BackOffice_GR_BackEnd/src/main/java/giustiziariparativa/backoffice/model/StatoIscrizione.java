package giustiziariparativa.backoffice.model;

//import giustiziariparativa.backoffice.util.StatoIscrizioneEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
//import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "STATO_ISCRIZIONE")
public class StatoIscrizione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "ISEQ$$_29922", sequenceName = "ISEQ$$_29922", allocationSize = 1) //ISEQ$$_32205
    //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ISEQ$$_29922")
    @Column(name = "ID_STATO")
    private Long idStato;
    @Column(name = "DESCRIZIONE_STATO")
    // @Enumerated(EnumType.STRING)
    // private StatoIscrizioneEnum descrizioneStatoIscrizione = StatoIscrizioneEnum.ISCRITTO;
    public String descrizioneStatoIscrizione;

    // @Column(name = "TIPOLOGIA")
    // private String tipologia;

    // @Column(name = "DATA")
    // private String dataAggiornamentoStato;

    // @Column(name = "PROVVEDIMENTO")
    // @Lob
    // private byte[] provvedimentoPdf;

    public StatoIscrizione() {
    }

    public StatoIscrizione(Long id, String descrizioneStatoIscrizione) {
        this.idStato = id;
        this.descrizioneStatoIscrizione = descrizioneStatoIscrizione;

    }

    public Long getId() {
        return idStato;
    }

    public String getDescrizioneStatoIscrizione() {
        return descrizioneStatoIscrizione;
    }

    public void setDescrizioneStatoIscrizione(String descrizioneStatoIscrizione) {
        this.descrizioneStatoIscrizione = descrizioneStatoIscrizione;
    }

    @Override
    public String toString() {
        return "StatoIscrizione [id=" + idStato + ", descrizioneStatoIscrizione=" + descrizioneStatoIscrizione + "]";
    }

}
