package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMUNI")
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODICE_COMUNE")
    private Long idCodComune;
    
    @Column(name = "CODICE_REGIONE")
    private Long codiceRegione;
    
    @Column(name = "CODICE_PROVINCIA", insertable = false, updatable = false)
    //@Column(name = "CODICE_PROVINCIA")
    private Long codiceProvincia;
    
    @Column(name = "NOME_COMUNE")
    private String nomeComune;
    
    @Column(name = "ISTAT")
    private String istat;
    
    @Column(name = "CAPOLUOGO_PROVINCIA")
    private char capoluogoProvincia;
    
    @Column(name = "CODICE_CATASTALE")
    private String codiceCatastale;
    
    @Column(name = "DATA_ISTITUZIONE")
    private Date dataIstituzione;
    
    @Column(name = "DATA_CESSAZIONE")
    private Date dataCessazione;
    
    @Column(name = "STATO")
    private String stato;

    @ManyToOne
    @JoinColumn(name = "CODICE_PROVINCIA", referencedColumnName = "CODICE_PROVINCIA")
    private RegioneProvince regioneProvince;
    
	public Comune() {
		super();
	}

	public Long getIdCodComune() {
		return idCodComune;
	}

	public void setIdCodComune(Long idCodComune) {
		this.idCodComune = idCodComune;
	}

	public Long getCodiceRegione() {
		return codiceRegione;
	}

	public void setCodiceRegione(Long codiceRegione) {
		this.codiceRegione = codiceRegione;
	}

	public Long getCodiceProvincia() {
		return codiceProvincia;
	}

	public void setCodiceProvincia(Long codiceProvincia) {
		this.codiceProvincia = codiceProvincia;
	}

	public String getNomeComune() {
		return nomeComune;
	}

	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}

	public String getIstat() {
		return istat;
	}

	public void setIstat(String istat) {
		this.istat = istat;
	}

	public char getCapoluogoProvincia() {
		return capoluogoProvincia;
	}

	public void setCapoluogoProvincia(char capoluogoProvincia) {
		this.capoluogoProvincia = capoluogoProvincia;
	}

	public String getCodiceCatastale() {
		return codiceCatastale;
	}

	public void setCodiceCatastale(String codiceCatastale) {
		this.codiceCatastale = codiceCatastale;
	}
	
	public Date getDataIstituzione() {
		return dataIstituzione;
	}

	public void setDataIstituzione(Date dataIstituzione) {
		this.dataIstituzione = dataIstituzione;
	}

	public Date getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public RegioneProvince getRegioneProvince() {
		return regioneProvince;
	}

	public void setRegioneProvince(RegioneProvince regioneProvince) {
		this.regioneProvince = regioneProvince;
	}
	
}
