package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALBO_ODM_SEDI")
public class AlboOdmSedi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALBO_ODM_SEDI")
	private Long idAlboOdmSedi;
	
	@Column(name = "ROM")
	private Long rom;
	
	@Column(name = "SEDE_LEGALE")
	private Long sedeLegale;
	
	@Column(name = "INDIRIZZO")
	private String indirizzo;
	
	@Column(name = "COMUNE")
	private String comune;
	
	@Column(name = "CAP", columnDefinition = "char(5)")
	private String cap;
	
	@Column(name = "PROVINCIA")
	private String provincia;
	
	@Column(name = "PROVINCIA_SIGLA", columnDefinition = "char(2)")
	private String provinciaSigla;
	
	@Column(name = "REGIONE")
	private String regione;
	
	@Column(name = "TELEFONO")
	private String telefono;
	
	@Column(name = "FAX")
	private String fax;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PEC")
	private String pec;

	
	
	public Long getIdAlboOdmSedi() {
		return idAlboOdmSedi;
	}

	public void setIdAlboOdmSedi(Long idAlboOdmSedi) {
		this.idAlboOdmSedi = idAlboOdmSedi;
	}

	public Long getRom() {
		return rom;
	}

	public void setRom(Long rom) {
		this.rom = rom;
	}

	public Long getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(Long sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getProvinciaSigla() {
		return provinciaSigla;
	}

	public void setProvinciaSigla(String provinciaSigla) {
		this.provinciaSigla = provinciaSigla;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	
	
	
}
