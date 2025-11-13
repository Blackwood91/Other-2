package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "STATO_MODULI_RICHIESTA_FIGLI")
public class StatoModuliRichiestaFigli {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_STATO_MODULI_RICHIESTA_FIGLI")
    private Long id;
    
    @Column(name = "PROGRESSIVO_MODULO")
    private Long progressivoModulo;
    
    @Column(name = "ID_MODULO")
    private Long idModulo;
    
    @Column(name = "ID_RICHIESTA")
    private Long idRichiesta;
    
    @Column(name = "COMPLETATO")
    private Integer completato; //FO SAREBBE IL CONVALIDA CON ICONA VERDA
    
    @Column(name = "VALIDATO") // BO SAREBBE IL VALIDATO CON ICONA VERDA
    private Integer validato;
    
    @Column(name = "ANNULLATO")
    private Integer annullato; //FO E BO ANNULLATO DA PARTE DEL BO
    
    @Column(name = "ID_ALLEGATO_GED")
    private Long idAllegatoged;
    
    @Column(name = "ID_ANAGRAFICA")
    private Long idAnagrafica;

    @Column(name = "NOME_ALLEGATO")
    private String nomeAllegato;
    
    @Column(name = "DATA_INSERIMENTO")
    private Date dataInserimento;
    
    @Column(name = "ID_RIFERIMENTO")
    private Long idRiferimento;
    
    @Column(name = "DOCUMENT_ID_CLIENT")
    private String documentIdClient;
    
    @Column(name = "CONTENT_ID")
    private Integer contentId;
    
    @Column(name = "ID_STATO_MODULO")
    private Long idStatoModulo;
    
    @Column(name = "ID_LAVORAZIONE_MODULO")
    private Long idLavorazioneModulo;
    
    @Column(name = "DATA_PRIMA_ISCRIZIONE")
    private Date dataPrimaIscrizione;
    
    @Column(name = "DATA_CANCELLAZIONE")
    private Date dataCancellazione;
    
    
	public StatoModuliRichiestaFigli() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProgressivoModulo() {
		return progressivoModulo;
	}

	public void setProgressivoModulo(Long progressivoModulo) {
		this.progressivoModulo = progressivoModulo;
	}

	public Long getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Integer getCompletato() {
		return completato;
	}

	public void setCompletato(Integer completato) {
		this.completato = completato;
	}

	public Integer getValidato() {
		return validato;
	}

	public void setValidato(Integer validato) {
		this.validato = validato;
	}

	public Integer getAnnullato() {
		return annullato;
	}

	public void setAnnullato(Integer annullato) {
		this.annullato = annullato;
	}

	public Long getIdAllegatoged() {
		return idAllegatoged;
	}

	public void setIdAllegatoged(Long idAllegatoged) {
		this.idAllegatoged = idAllegatoged;
	}

	public Long getIdAnagrafica() {
		return idAnagrafica;
	}

	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Long getIdRiferimento() {
		return idRiferimento;
	}

	public void setIdRiferimento(Long idRiferimento) {
		this.idRiferimento = idRiferimento;
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

	public Long getIdStatoModulo() {
		return idStatoModulo;
	}

	public void setIdStatoModulo(Long idStatoModulo) {
		this.idStatoModulo = idStatoModulo;
	}

	public Long getIdLavorazioneModulo() {
		return idLavorazioneModulo;
	}

	public void setIdLavorazioneModulo(Long idLavorazioneModulo) {
		this.idLavorazioneModulo = idLavorazioneModulo;
	}

	public Date getDataPrimaIscrizione() {
		return dataPrimaIscrizione;
	}

	public void setDataPrimaIscrizione(Date dataPrimaIscrizione) {
		this.dataPrimaIscrizione = dataPrimaIscrizione;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
}
