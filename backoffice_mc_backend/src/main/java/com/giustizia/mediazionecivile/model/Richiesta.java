package com.giustizia.mediazionecivile.model;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RICHIESTE")
public class Richiesta {
    @Id
    @Column(name = "ID_SOCIETA")
    private Long idRichiesta;
	
	@Column(name = "ID_PROVVISORIO")
    private Long idProvvisorio;

    @Column(name = "NUMERO_ISCRIZIONE_ALBO")
    private Long numIscrAlbo;

    @Column(name = "ID_TIPO_RICHIESTA")
    private int idTipoRichiesta;
    
    @Column(name = "ID_TIPO_RICHIEDENTE")
    private Long idTipoRichiedente;

    @Column(name = "ID_NATURA_GIURIDICA_RICHIEDENTE")
    private Long idNaturaGiuRic;

    @Column(name = "ID_NATURA_GIURIDICA_ORGANISMO")
    private Long idNaturaGiuOrg;
    
    @Column(name = "DATA_RICHIESTA")
    private Date dataRichiesta;

    @Column(name = "DATA_ISCRIZIONE_ALBO")
    private Date dataIscrAlbo;

    @Column(name = "ID_STATO")
    private Long idStato;
    
    @Column(name = "COGNOME")
    private String cognome;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "RAGIONE_SOCIALE")
    private String ragioneSociale;
    
    @Column(name = "ID_NATURA_SOCIETARIA")
    private Long idNaturaSoc;
    
    @Column(name = "REGIONIIDS")
    private String regioniIds;
    
    @Column(name = "PROVINCEIDS")
    private String provinceIds;
    
    @Column(name = "DATA_DOMANDA")
    private Date dataDomanda;
    
    @Column(name = "ID_SOGGETTO_RICHIEDENTE")
    private Long idSoggRichiedente;
    
    @Column(name = "DENOMINAZIONE_ODM")
    private String denominazioneOdm;
    
    @Column(name = "AUTONOMO")
    private int autonomo;
    
    @Column(name = "OGGETTO_SOCIALE")
    private String oggettoSociale;
    
    @Column(name = "COMPAGNIA_ASSICURATRICE")
    private String compagniaAss;
    
    @Column(name = "DATA_STIPULA_POLIZZA")
    private Date dataStipulaPoliz;
    
    @Column(name = "SCADENZA_POLIZZA")
    private Date scadenzaPoliza;
    
    @Column(name = "MASSIMALE_ASSICURATO")
    private BigDecimal massimaleAssic;
    
    @Column(name = "ART_OGGETTO_POLIZZA")
    private String artOggetPolizza;
    
    @Column(name = "COD_FIS_SOCIETA")
    private String codFiscSocieta;
    
    @Column(name = "P_IVA")
    private String pIva;
    
    @Column(name = "DATA_ATTO_COSTITUTIVO")
    private Date dataAttoCosti;
    
    @Column(name = "NUOVA_COSTITUZIONE")
    private int nuovaCostituzione;
    
    @Column(name = "DATA_STATUTO_VIGENTE")
    private Date dataStatutoVig;
    
    @Column(name = "NUM_COMPONENTI_COMPAGINE_SOCIALE")
    private Long numCompoCompSoc;
    
    @Column(name = "NUM_COMPONENTI_ORGANO_AMMINISTRAZIONE")
    private Long numCompoOrgAmm;
    
    @Column(name = "DURATA_CARICA")
    private String durataCarica;
    
    @Column(name = "ID_MODALITA_COSTITUZIONE_ORGANISMO")
    private Long idModalitaCostOrgani;
    
    @Column(name = "NUM_PERSONALE_ADDETTO")
    private int numPersonaleAdetto;
    
    @Column(name = "CAPITALE_SOCIALE")
    private BigDecimal capitaleSociale;
    
    @Column(name = "NUM_SOCI_ENTE")
    private int numSociEnte;
    
    @Column(name = "NUM_MEDIATORI")
    private int numMediatori;
    
    @Column(name = "NUM_MEDIATORI_INTERNAZIONALE")
    private int numMediatoriInter;
    
    @Column(name = "NUM_MEDIATORI_CONSUMO")
    private int numMediatoriCons;
    
    @Column(name = "SITO_WEB")
    private String sitoWeb;
    
    @Column(name = "ID_ORDINE_COLLEGIO")
    private Long idOrdineCol;
    
    @Column(name = "MATERIA_ODM_SPECIALE")
    private String materiaODMSpe;
    
    @Column(name = "DATA_COSTITUZIONE_ORGANISMO")
    private Date dataCostituOrg;
    
    @Column(name = "ISCRITTO_ALBO")
    private int iscrittoAlbo;
    
    @Column(name = "FONTI_DI_FINANZIAMENTO")
    private String fontiDiFinanziamento;
    
    @Column(name = "DURATA_COSTITUZIONE_ORGANISMO")
    private String durataCostituzioneOrganismo;
    
    @Column(name = "MODALITA_GESTIONE_CONTABILE")
    private String modalitaGestioneContabile;
    
    @Column(name = "ISTITUITO_ENTE_PUBBLICO")
    private Integer istitutoEntePub;
    
    @Column(name = "DENOMINAZIONE_ODM_PUBBLICO")
    private String denominaOdmPub;
    
    @Column(name = "ID_NATURA_GIURIDICA")
    private Long idNaturaGiu;

    public Richiesta() {}

	public Richiesta(Long idProvvisorio, Long idSocieta, Long numIscrAlbo, int idTipoRichiesta, Long idTipoRichiedente,
			Long idNaturaGiuRic, Long idNaturaGiuOrg, Date dataRichiesta, Date dataIscrAlbo, Long idStato,
			String cognome, String nome, String ragioneSociale, Long idNaturaSoc, String regioniIds, String provinceIds,
			Date dataDomanda, Long idSoggRichiedente, String denominazioneOdm, int autonomo, String oggettoSociale,
			String compagniaAss, Date dataStipulaPoliz, Date scadenzaPoliza, BigDecimal massimaleAssic,
			String artOggetPolizza, String codFiscSocieta, String pIva, Date dataAttoCosti, int nuovaCostituzione,
			Date dataStatutoVig, Long numCompoCompSoc, Long numCompoOrgAmm, String durataCarica,
			Long idModalitaCostOrgani, int numPersonaleAdetto, BigDecimal capitaleSociale, int numSociEnte,
			int numMediatori, int numMediatoriInter, int numMediatoriCons, String sitoWeb, Long idOrdineCol,
			String materiaODMSpe, Date dataCostituOrg, int iscrittoAlbo, String fontiDiFinanziamento, 
			String durataCostituzioneOrganismo, String modalitaGestioneContabile) {
		super();
		this.idProvvisorio = idProvvisorio;
		this.idRichiesta = idSocieta;
		this.numIscrAlbo = numIscrAlbo;
		this.idTipoRichiesta = idTipoRichiesta;
		this.idTipoRichiedente = idTipoRichiedente;
		this.idNaturaGiuRic = idNaturaGiuRic;
		this.idNaturaGiuOrg = idNaturaGiuOrg;
		this.dataRichiesta = dataRichiesta;
		this.dataIscrAlbo = dataIscrAlbo;
		this.idStato = idStato;
		this.cognome = cognome;
		this.nome = nome;
		this.ragioneSociale = ragioneSociale;
		this.idNaturaSoc = idNaturaSoc;
		this.regioniIds = regioniIds;
		this.provinceIds = provinceIds;
		this.dataDomanda = dataDomanda;
		this.idSoggRichiedente = idSoggRichiedente;
		this.denominazioneOdm = denominazioneOdm;
		this.autonomo = autonomo;
		this.oggettoSociale = oggettoSociale;
		this.compagniaAss = compagniaAss;
		this.dataStipulaPoliz = dataStipulaPoliz;
		this.scadenzaPoliza = scadenzaPoliza;
		this.massimaleAssic = massimaleAssic;
		this.artOggetPolizza = artOggetPolizza;
		this.codFiscSocieta = codFiscSocieta;
		this.pIva = pIva;
		this.dataAttoCosti = dataAttoCosti;
		this.nuovaCostituzione = nuovaCostituzione;
		this.dataStatutoVig = dataStatutoVig;
		this.numCompoCompSoc = numCompoCompSoc;
		this.numCompoOrgAmm = numCompoOrgAmm;
		this.durataCarica = durataCarica;
		this.idModalitaCostOrgani = idModalitaCostOrgani;
		this.numPersonaleAdetto = numPersonaleAdetto;
		this.capitaleSociale = capitaleSociale;
		this.numSociEnte = numSociEnte;
		this.numMediatori = numMediatori;
		this.numMediatoriInter = numMediatoriInter;
		this.numMediatoriCons = numMediatoriCons;
		this.sitoWeb = sitoWeb;
		this.idOrdineCol = idOrdineCol;
		this.materiaODMSpe = materiaODMSpe;
		this.dataCostituOrg = dataCostituOrg;
		this.iscrittoAlbo = iscrittoAlbo;
		this.fontiDiFinanziamento = fontiDiFinanziamento;
		this.durataCostituzioneOrganismo = durataCostituzioneOrganismo;
		this.modalitaGestioneContabile = modalitaGestioneContabile;
	}

	public Long getIdProvvisorio() {
		return idProvvisorio;
	}

	public void setIdProvvisorio(Long idProvvisorio) {
		this.idProvvisorio = idProvvisorio;
	}

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Long getNumIscrAlbo() {
		return numIscrAlbo;
	}

	public void setNumIscrAlbo(Long numIscrAlbo) {
		this.numIscrAlbo = numIscrAlbo;
	}

	public int getIdTipoRichiesta() {
		return idTipoRichiesta;
	}

	public void setIdTipoRichiesta(int idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}

	public Long getIdTipoRichiedente() {
		return idTipoRichiedente;
	}

	public void setIdTipoRichiedente(Long idTipoRichiedente) {
		this.idTipoRichiedente = idTipoRichiedente;
	}

	public Long getIdNaturaGiuRic() {
		return idNaturaGiuRic;
	}

	public void setIdNaturaGiuRic(Long idNaturaGiuRic) {
		this.idNaturaGiuRic = idNaturaGiuRic;
	}

	public Long getIdNaturaGiuOrg() {
		return idNaturaGiuOrg;
	}

	public void setIdNaturaGiuOrg(Long idNaturaGiuOrg) {
		this.idNaturaGiuOrg = idNaturaGiuOrg;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public Date getDataIscrAlbo() {
		return dataIscrAlbo;
	}

	public void setDataIscrAlbo(Date dataIscrAlbo) {
		this.dataIscrAlbo = dataIscrAlbo;
	}

	public Long getIdStato() {
		return idStato;
	}

	public void setIdStato(Long idStato) {
		this.idStato = idStato;
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

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Long getIdNaturaSoc() {
		return idNaturaSoc;
	}

	public void setIdNaturaSoc(Long idNaturaSoc) {
		this.idNaturaSoc = idNaturaSoc;
	}

	public String getRegioniIds() {
		return regioniIds;
	}

	public void setRegioniIds(String regioniIds) {
		this.regioniIds = regioniIds;
	}

	public String getProvinceIds() {
		return provinceIds;
	}

	public void setProvinceIds(String provinceIds) {
		this.provinceIds = provinceIds;
	}

	public Date getDataDomanda() {
		return dataDomanda;
	}

	public void setDataDomanda(Date dataDomanda) {
		this.dataDomanda = dataDomanda;
	}

	public Long getIdSoggRichiedente() {
		return idSoggRichiedente;
	}

	public void setIdSoggRichiedente(Long idSoggRichiedente) {
		this.idSoggRichiedente = idSoggRichiedente;
	}

	public String getDenominazioneOdm() {
		return denominazioneOdm;
	}

	public void setDenominazioneOdm(String denominazioneOdm) {
		this.denominazioneOdm = denominazioneOdm;
	}

	public int getAutonomo() {
		return autonomo;
	}

	public void setAutonomo(int autonomo) {
		this.autonomo = autonomo;
	}

	public String getOggettoSociale() {
		return oggettoSociale;
	}

	public void setOggettoSociale(String oggettoSociale) {
		this.oggettoSociale = oggettoSociale;
	}

	public String getCompagniaAss() {
		return compagniaAss;
	}

	public void setCompagniaAss(String compagniaAss) {
		this.compagniaAss = compagniaAss;
	}

	public Date getDataStipulaPoliz() {
		return dataStipulaPoliz;
	}

	public void setDataStipulaPoliz(Date dataStipulaPoliz) {
		this.dataStipulaPoliz = dataStipulaPoliz;
	}

	public Date getScadenzaPoliza() {
		return scadenzaPoliza;
	}

	public void setScadenzaPoliza(Date scadenzaPoliza) {
		this.scadenzaPoliza = scadenzaPoliza;
	}

	public BigDecimal getMassimaleAssic() {
		return massimaleAssic;
	}

	public void setMassimaleAssic(BigDecimal massimaleAssic) {
		this.massimaleAssic = massimaleAssic;
	}

	public String getArtOggetPolizza() {
		return artOggetPolizza;
	}

	public void setArtOggetPolizza(String artOggetPolizza) {
		this.artOggetPolizza = artOggetPolizza;
	}

	public String getCodFiscSocieta() {
		return codFiscSocieta;
	}

	public void setCodFiscSocieta(String codFiscSocieta) {
		this.codFiscSocieta = codFiscSocieta;
	}

	public String getPIva() {
		return pIva;
	}

	public void setPIva(String pIva) {
		this.pIva = pIva;
	}

	public Date getDataAttoCosti() {
		return dataAttoCosti;
	}

	public void setDataAttoCosti(Date dataAttoCosti) {
		this.dataAttoCosti = dataAttoCosti;
	}

	public int getNuovaCostituzione() {
		return nuovaCostituzione;
	}

	public void setNuovaCostituzione(int nuovaCostituzione) {
		this.nuovaCostituzione = nuovaCostituzione;
	}

	public Date getDataStatutoVig() {
		return dataStatutoVig;
	}

	public void setDataStatutoVig(Date dataStatutoVig) {
		this.dataStatutoVig = dataStatutoVig;
	}

	public Long getNumCompoCompSoc() {
		return numCompoCompSoc;
	}

	public void setNumCompoCompSoc(Long numCompoCompSoc) {
		this.numCompoCompSoc = numCompoCompSoc;
	}

	public Long getNumCompoOrgAmm() {
		return numCompoOrgAmm;
	}

	public void setNumCompoOrgAmm(Long numCompoOrgAmm) {
		this.numCompoOrgAmm = numCompoOrgAmm;
	}

	public String getDurataCarica() {
		return durataCarica;
	}

	public void setDurataCarica(String durataCarica) {
		this.durataCarica = durataCarica;
	}

	public Long getIdModalitaCostOrgani() {
		return idModalitaCostOrgani;
	}

	public void setIdModalitaCostOrgani(Long idModalitaCostOrgani) {
		this.idModalitaCostOrgani = idModalitaCostOrgani;
	}

	public int getNumPersonaleAdetto() {
		return numPersonaleAdetto;
	}

	public void setNumPersonaleAdetto(int numPersonaleAdetto) {
		this.numPersonaleAdetto = numPersonaleAdetto;
	}

	public BigDecimal getCapitaleSociale() {
		return capitaleSociale;
	}

	public void setCapitaleSociale(BigDecimal capitaleSociale) {
		this.capitaleSociale = capitaleSociale;
	}

	public int getNumSociEnte() {
		return numSociEnte;
	}

	public void setNumSociEnte(int numSociEnte) {
		this.numSociEnte = numSociEnte;
	}

	public int getNumMediatori() {
		return numMediatori;
	}

	public void setNumMediatori(int numMediatori) {
		this.numMediatori = numMediatori;
	}

	public int getNumMediatoriInter() {
		return numMediatoriInter;
	}

	public void setNumMediatoriInter(int numMediatoriInter) {
		this.numMediatoriInter = numMediatoriInter;
	}

	public int getNumMediatoriCons() {
		return numMediatoriCons;
	}

	public void setNumMediatoriCons(int numMediatoriCons) {
		this.numMediatoriCons = numMediatoriCons;
	}

	public String getSitoWeb() {
		return sitoWeb;
	}

	public void setSitoWeb(String sitoWeb) {
		this.sitoWeb = sitoWeb;
	}

	public Long getIdOrdineCol() {
		return idOrdineCol;
	}

	public void setIdOrdineCol(Long idOrdineCol) {
		this.idOrdineCol = idOrdineCol;
	}

	public String getMateriaODMSpe() {
		return materiaODMSpe;
	}

	public void setMateriaODMSpe(String materiaODMSpe) {
		this.materiaODMSpe = materiaODMSpe;
	}

	public Date getDataCostituOrg() {
		return dataCostituOrg;
	}

	public void setDataCostituOrg(Date dataCostituOrg) {
		this.dataCostituOrg = dataCostituOrg;
	}

	public int getIscrittoAlbo() {
		return iscrittoAlbo;
	}

	public void setIscrittoAlbo(int iscrittoAlbo) {
		this.iscrittoAlbo = iscrittoAlbo;
	}
	
	public String getFontiDiFinanziamento() {
		return fontiDiFinanziamento;
	}

	public void setFontiDiFinanziamento(String fontiDiFinanziamento) {
		this.fontiDiFinanziamento = fontiDiFinanziamento;
	}

	public String getDurataCostituzioneOrganismo() {
		return durataCostituzioneOrganismo;
	}

	public void setDurataCostituzioneOrganismo(String durataCostituzioneOrganismo) {
		this.durataCostituzioneOrganismo = durataCostituzioneOrganismo;
	}

	public String getModalitaGestioneContabile() {
		return modalitaGestioneContabile;
	}

	public void setModalitaGestioneContabile(String modalitaGestioneContabile) {
		this.modalitaGestioneContabile = modalitaGestioneContabile;
	}

	public Integer getIstitutoEntePub() {
		return istitutoEntePub;
	}

	public void setIstitutoEntePub(Integer istitutoEntePub) {
		this.istitutoEntePub = istitutoEntePub;
	}

	public String getDenominaOdmPub() {
		return denominaOdmPub;
	}

	public void setDenominaOdmPub(String denominaOdmPub) {
		this.denominaOdmPub = denominaOdmPub;
	}

	public Long getIdNaturaGiu() {
		return idNaturaGiu;
	}

	public void setIdNaturaGiu(Long idNaturaGiu) {
		this.idNaturaGiu = idNaturaGiu;
	}
}
