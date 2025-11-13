package giustiziariparativa.backoffice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RUOLO_UTENTE")
public class RuoloUtente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RUOLO_UTENTE")
    private Long idRuoloUtente;

    @Column(name = "RUOLO_UTENTE")
    private String ruoloUtente;

    public RuoloUtente() {
    }

    public RuoloUtente(Long idRuoloUtente, String ruoloUtente) {
        this.idRuoloUtente = idRuoloUtente;
        this.ruoloUtente = ruoloUtente;
    }

    public Long getIdRuoloUtente() {
        return idRuoloUtente;
    }

    public String getRuoloUtente() {
        return ruoloUtente;
    }

    public void setRuoloUtente(String ruoloUtente) {
        this.ruoloUtente = ruoloUtente;
    }

    @Override
    public String toString() {
        return "RuoloUtente [idRuoloUtente=" + idRuoloUtente + ", ruoloUtente=" + ruoloUtente + "]";
    }

}
