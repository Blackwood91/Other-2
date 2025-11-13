package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "STATO_MODULI_RICHIESTA")
public class StatoModuliRichiesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODULO")
    private Long idModulo;
    
    @Column(name = "ID_RICHIESTA")
    private Long idRichiesta;
    
    @Column(name = "COMPLETATO")
    private Integer completato;

    @Column(name = "VALIDATO")
    private Integer validato;    
    
    @Column(name = "ANNULLATO")
    private Integer annulato;  
    
    // PROBABILE COLONNA INUTILE PER LA LOGICA DEL NUOVO DOCUMENTALE
    @Column(name = "ID_ALLEGATO_GED")
    private Integer idAllegatoGed;
    
    
	public StatoModuliRichiesta() {
		super();
	}


	public Long getIdModulo() {
		return idModulo;
	}


	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}


	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public Integer getCompletato() {
		return completato;
	}


	public void setCompletato(Integer completato) {
		this.completato = completato;
	}


	public Integer getValidato() {
		return validato;
	}


	public void setValidato(Integer validato) {
		this.validato = validato;
	}


	public Integer getAnnulato() {
		return annulato;
	}


	public void setAnnulato(Integer annulato) {
		this.annulato = annulato;
	}


	public Integer getIdAllegatoGed() {
		return idAllegatoGed;
	}


	public void setIdAllegatoGed(Integer idAllegatoGed) {
		this.idAllegatoGed = idAllegatoGed;
	}  
    
}
