package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "EMISSIONE_PDG_ODM")
public class EmissionePdgOdm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EMISSIONE_PDG_ODM")
	private Long idEmissionePdg;
	
	@Column(name = "ROM")
	private Long rom;
	
	@Column(name = "ID_RICHIESTA")
	private Long idRichiesta;
	
	@Column(name = "ID_TIPO_PDG")
	private Long idTipoPdg;
	
	@Column(name = "DATA_EMISSIONE")
	private Date dataEmissione;
	
	@Column(name = "DOCUMENT_ID_CLIENT")
	private String documentIdClient;
	
	@Column(name = "CONTENT_ID")
	private Integer contentId;

    @Column(name = "NOME_ALLEGATO")
    private String nomeAllegato;

	
	public EmissionePdgOdm() {
		super();
	}


	public Long getIdEmissionePdg() {
		return idEmissionePdg;
	}


	public void setIdEmissionePdg(Long idEmissionePdg) {
		this.idEmissionePdg = idEmissionePdg;
	}


	public Long getRom() {
		return rom;
	}


	public void setRom(Long rom) {
		this.rom = rom;
	}


	public Long getIdRichiesta() {
		return idRichiesta;
	}


	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}


	public Long getIdTipoPdg() {
		return idTipoPdg;
	}


	public void setIdTipoPdg(Long idTipoPdg) {
		this.idTipoPdg = idTipoPdg;
	}


	public Date getDataEmissione() {
		return dataEmissione;
	}


	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}


	public String getDocumentIdClient() {
		return documentIdClient;
	}


	public void setDocumentIdClient(String documentIdClient) {
		this.documentIdClient = documentIdClient;
	}


	public Integer getContentId() {
		return contentId;
	}


	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}


	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}
	
}
