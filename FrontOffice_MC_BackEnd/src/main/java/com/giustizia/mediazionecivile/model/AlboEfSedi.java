package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALBO_EF_SEDI")
public class AlboEfSedi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALBO_EF_SEDI")
	private Long idAlboEfSedi;
	
	@Column(name = "NUM_REG")
	private Long numeReg;
	
	@Column(name = "TIPO_SEDE")
	private Long tipo;
	
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

	
	
	public Long getIdAlboEfSedi() {
		return idAlboEfSedi;
	}

	public void setIdAlboEfSedi(Long idAlboEfSedi) {
		this.idAlboEfSedi = idAlboEfSedi;
	}

	public Long getNumeReg() {
		return numeReg;
	}

	public void setNumeReg(Long numeReg) {
		this.numeReg = numeReg;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
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
