package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALBO_EF_PDG")
public class AlboEfPdg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALBO_EF_PDG")
	private Long idAlboEfPdg;
	
	@Column(name = "NUM_REG")
	private Long numReg;
	
	@Column(name = "DATA_PDG")
	private Date dataPdg;
	
	@Column(name = "TIPO_PDG")
	private String tipoPdg;
	
	@Column(name = "NOTE")
	private String note;
	
	

	public Long getIdAlboEfPdg() {
		return idAlboEfPdg;
	}

	public void setIdAlboEfPdg(Long idAlboEfPdg) {
		this.idAlboEfPdg = idAlboEfPdg;
	}

	public Long getNumReg() {
		return numReg;
	}

	public void setNumReg(Long numReg) {
		this.numReg = numReg;
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
