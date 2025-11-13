package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SEDI")
public class Sede {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SEDE")
	private Long idSede;

	@Column(name = "ID_RICHIESTA")
	private Long idRichiesta;

	@Column(name = "SEDE_LEGALE")
	private char sedeLegale;

	@Column(name = "INDIRIZZO")
	private String indirizzo;

	@Column(name = "NUMERO_CIVICO")
	private String numeroCivico;

	@Column(name = "CAP")
	private String cap;

	@Column(name = "ID_COMUNE")
	private Long idComune;

	@Column(name = "TELEFONO")
	private String telefono;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PEC")
	private String pec;

	@Column(name = "ID_TITOLO_DETENZIONE")
	private Long idTitoloDefinizione;

	@Column(name = "NUM_ROM_SOGG_ACCORDO")
	private Long numRomSoggAccordo;

	@Column(name = "DATA_CONTRATTO")
	private Date dataContratto;

	@Column(name = "REGISTRAZIONE")
	private String registrazione;

	@Column(name = "DURATA_CONTRATTO")
	private String durataContratto;

	@Column(name = "STRUTTURA_ORG_SEGRETERIA")
	private String  strutturaOrgSegreteria;

	@Column(name = "DATA_INSERIMENTO_SEDE")
	private Date dataInserimentoSede;

	@Column(name = "SITO_WEB")
	private String sitoWebSede;

	public Sede() {
		super();
	}

	public Long getIdSede() {
		return idSede;
	}

	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public char getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(char sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public Long getIdComune() {
		return idComune;
	}

	public void setIdComune(Long idComune) {
		this.idComune = idComune;
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

	public Long getIdTitoloDefinizione() {
		return idTitoloDefinizione;
	}

	public void setIdTitoloDefinizione(Long idTitoloDefinizione) {
		this.idTitoloDefinizione = idTitoloDefinizione;
	}

	public Long getNumRomSoggAccordo() {
		return numRomSoggAccordo;
	}

	public void setNumRomSoggAccordo(Long numRomSoggAccordo) {
		this.numRomSoggAccordo = numRomSoggAccordo;
	}

	public Date getDataContratto() {
		return dataContratto;
	}

	public void setDataContratto(Date dataContratto) {
		this.dataContratto = dataContratto;
	}

	public String getRegistrazione() {
		return registrazione;
	}

	public void setRegistrazione(String registrazione) {
		this.registrazione = registrazione;
	}

	public Date getDataInserimentoSede() {
		return dataInserimentoSede;
	}

	public void setDataInserimentoSede(Date dataInserimentoSede) {
		this.dataInserimentoSede = dataInserimentoSede;
	}

	public String getStrutturaOrgSegreteria() {
		return strutturaOrgSegreteria;
	}

	public void setStrutturaOrgSegreteria(String strutturaOrgSegreteria) {
		this.strutturaOrgSegreteria = strutturaOrgSegreteria;
	}

	public String getSitoWebSede() {
		return sitoWebSede;
	}

	public void setSitoWebSede(String sitoWebSede) {
		this.sitoWebSede = sitoWebSede;
	}

	public String getDurataContratto() {
		return durataContratto;
	}

	public void setDurataContratto(String durataContratto) {
		this.durataContratto = durataContratto;
	}

	

}
