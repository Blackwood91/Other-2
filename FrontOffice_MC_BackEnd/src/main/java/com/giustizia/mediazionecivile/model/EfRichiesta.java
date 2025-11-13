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
@Table(name = "EfRichieste")
public class EfRichiesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @Column(name = "REGIONI_IDS")
    private String regioniIds;
    
    @Column(name = "PROVINCE_IDS")
    private String provinceIds;
    
    @Column(name = "SOCIETA_O_ASSOCIAZIONE")
    private Long societaOAss;
    
    @Column(name = "DATA_DOMANDA")
    private Date dataDomanda;
    
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
    
    @Column(name = "COD_FIS_SOCIETA")
    private String codFiscSocieta;
    
    @Column(name = "P_IVA")
    private String pIva;
    
    @Column(name = "DATA_ATTO_COSTITUTIVO")
    private Date dataAttoCosti;
    
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
    
    @Column(name = "SITO_WEB")
    private String sitoWeb;
    
    @Column(name = "ID_ORDINE_COLLEGIO")
    private Long idOrdineCol;
    
    @Column(name = "DATA_COSTITUZIONE_ORGANISMO")
    private Date dataCostituOrg;
    
    @Column(name = "ISCRITTO_ALBO")
    private int iscrittoAlbo;

    @Column(name = "NUM_RESPONSABILI_SCIENTIFICI")
    private int numRespScientifici;

    
    public EfRichiesta() {
    	super();
    }
    
	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Long getIdProvvisorio() {
		return idProvvisorio;
	}

	public void setIdProvvisorio(Long idProvvisorio) {
		this.idProvvisorio = idProvvisorio;
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

	public Long getSocietaOAss() {
		return societaOAss;
	}

	public void setSocietaOAss(Long societaOAss) {
		this.societaOAss = societaOAss;
	}

	public Date getDataDomanda() {
		return dataDomanda;
	}

	public void setDataDomanda(Date dataDomanda) {
		this.dataDomanda = dataDomanda;
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

	public String getCodFiscSocieta() {
		return codFiscSocieta;
	}

	public void setCodFiscSocieta(String codFiscSocieta) {
		this.codFiscSocieta = codFiscSocieta;
	}

	public String getpIva() {
		return pIva;
	}

	public void setpIva(String pIva) {
		this.pIva = pIva;
	}

	public Date getDataAttoCosti() {
		return dataAttoCosti;
	}

	public void setDataAttoCosti(Date dataAttoCosti) {
		this.dataAttoCosti = dataAttoCosti;
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

	public int getNumRespScientifici() {
		return numRespScientifici;
	}

	public void setNumRespScientifici(int numRespScientifici) {
		this.numRespScientifici = numRespScientifici;
	}

}
