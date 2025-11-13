package com.giustizia.mediazionecivile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "QUALIFICA")
public class Qualifica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_QUALIFICA")
	private Long idQualifica;
	
	@Column(name = "QUALIFICA")
	private String qualifica;

	public Qualifica() {
		super();
	}

	public Long getIdQualifica() {
		return idQualifica;
	}

	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
	}

	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}
}
