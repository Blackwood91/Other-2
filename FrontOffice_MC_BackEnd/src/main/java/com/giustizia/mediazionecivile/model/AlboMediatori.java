package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALBO_MEDIATORI")
public class AlboMediatori {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALBO_MEDIATORE")
	private Long idAlboMediatore;
	
	@Column(name = "CODICE_FISCALE", columnDefinition = "char(16)")
	private String codiceFiscale;
	
	@Column(name = "COGNOME")
	private String cognome;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "SESSO", columnDefinition = "char(1)")
	private String sesso;
	
	@Column(name = "DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name = "STATO_NASCITA")
	private String statoNascita;
	
	@Column(name = "COMUNE_NASCITA")
	private String comuneNascita;
	
	@Column(name = "CITTADINANZA")
	private String cittadinanza;
	
	@Column(name = "ID_TIPO_ANAGRAFICA")
	private Long idTipoAnagrafica;

	
	public Long getIdAlboMediatore() {
		return idAlboMediatore;
	}

	public void setIdAlboMediatore(Long idAlboMediatore) {
		this.idAlboMediatore = idAlboMediatore;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getStatoNascita() {
		return statoNascita;
	}

	public void setStatoNascita(String statoNascita) {
		this.statoNascita = statoNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Long getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}

	public void setIdTipoAnagrafica(Long idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
}
