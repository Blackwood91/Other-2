package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALBO_ODM_PDG")
public class AlboOdmPdg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALBO_ODM_PDG")
	private Long idAlboOdmPdg;
	
	@Column(name = "ROM")
	private Long rom;
	
	@Column(name = "DATA_PDG")
	private Date dataPdg;
	
	@Column(name = "TIPO_PDG")
	private String tipoPdg;
	
	@Column(name = "NOTE")
	private String note;
	
	

	public Long getIdAlboOdmPdg() {
		return idAlboOdmPdg;
	}

	public void setIdAlboOdmPdg(Long idAlboOdmPdg) {
		this.idAlboOdmPdg = idAlboOdmPdg;
	}

	public Long getRom() {
		return rom;
	}

	public void setRom(Long rom) {
		this.rom = rom;
	}

	public Date getDataPdg() {
		return dataPdg;
	}

	public void setDataPdg(Date dataPdg) {
		this.dataPdg = dataPdg;
	}

	public String getTipoPdg() {
		return tipoPdg;
	}

	public void setTipoPdg(String tipoPdg) {
		this.tipoPdg = tipoPdg;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
