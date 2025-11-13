package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "RICHIESTA_INTEGRAZIONE")
public class RichiestaIntegrazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RICHIESTA_INTEGRAZIONE")
    private Long id;
    
    @Column(name = "ID_RICHIESTA")
    private Long idRichiesta;
    
    @Column(name = "DATA_RICHIESTA")
    private Date dataRichiesta;
    
    @Column(name = "DENOMINAZIONE_ODM")
    private String denominazioneOdm;
    
    @Column(name = "ROM")
    private Long rom;
    
    @Column(name = "MOTIVAZIONE")
	@Lob
    private String motivazione;
    
    @Column(name = "ID_MODULI_ANNULLATI")
    private String idModuliAnnulati;

    
	public RichiestaIntegrazione() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public Date getDataRichiesta() {
		return dataRichiesta;
	}


	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}


	public String getDenominazioneOdm() {
		return denominazioneOdm;
	}


	public void setDenominazioneOdm(String denominazioneOdm) {
		this.denominazioneOdm = denominazioneOdm;
	}


	public Long getRom() {
		return rom;
	}


	public void setRom(Long rom) {
		this.rom = rom;
	}


	public String getMotivazione() {
		return motivazione;
	}


	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}


	public String getIdModuliAnnulati() {
		return idModuliAnnulati;
	}


	public void setIdModuliAnnulati(String idModuliAnnulati) {
		this.idModuliAnnulati = idModuliAnnulati;
	}
    
}
