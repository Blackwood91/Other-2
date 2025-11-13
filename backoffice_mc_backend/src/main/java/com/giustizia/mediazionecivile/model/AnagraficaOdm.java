package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ANAGRAFICHE")
public class AnagraficaOdm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ANAGRAFICA")
	private Long idAnagrafica;
	@Column(name = "CODICE_FISCALE", columnDefinition = "char(16)")
	private String codiceFiscale;
	@Column(name = "COGNOME")
	private String cognome;
	@Column(name = "NOME")
	private String nome;
	@Column(name = "SESSO")
	private String sesso;
	@Column(name = "ID_TITOLO_ANAGRAFICA")
	private Long idTitoloAnagrafica;
	@Column(name = "DATA_NASCITA")
	private Date dataNascita;
	@Column(name = "STATO_NASCITA")
	private String statoNascita;
	@Column(name = "ID_COMUNE_NASCITA")
	private Long idComuneNascita;
	@ManyToOne
	@JoinColumn(name = "ID_COMUNE_NASCITA", insertable = false, updatable = false)
	private Comune comune;
	@Column(name = "COMUNE_NASCITA_ESTERO")
	private String comuneNascitaEstero;
	@Column(name = "CITTADINANZA")
	private String cittadinanza;
	@Column(name = "INDIRIZZO")
	private String indirizzo;
	@Column(name = "NUMERO_CIVICO")
	private String numeroCivico;
	@Column(name = "STATO_RESIDENZA")
	private String statoResidenza;
	@Column(name = "ID_COMUNE_RESIDENZA")
	private Long idComuneResidenza;
	@Column(name = "COMUNE_RESIDENZA_ESTERO")
	private String comuneResidenzaEstero;
	@Column(name = "CAP", columnDefinition = "char(5)")
	private String cap;
	@Column(name = "ID_QUALIFICA")
	private Long idQualifica;
	@Column(name = "MED_EMAIL")
	private String medEmail;
	@Column(name = "MED_TITOLO_DI_STUDIO")
	private String medTitoloDiStudio;
	@Column(name = "MED_PIVA", columnDefinition = "char(11)")
	private String medPiva;
	@Column(name = "MED_ORDINE_COLLEGIO_PROFESS")
	private String medOrdineCollegioProfess;
	@Column(name = "MED_DATA_ORDINE_COLLEGIO_PROFESS")
	private Date medDataOrdineCollegioProfess;
	@Column(name = "MED_NUM_ISCRIZIONE_ORDINE_COLLEGGIO")
	private String medNumIscrizioneOrdineColleggio;
	@Column(name = "MED_SEDE_ISCRIZIONE_ORDINE_COLLEGGIO")
	private String medSedeIscrizioneOrdineColleggio;
	@Column(name = "MED_DURATA_CORSO_FORMAZIONE")
	private String medDurataCorsoFormazione;
	@Column(name = "MED_RAPP_GIURIDICO_ECONOMICO")
	private String medRappGiuridicoEconomico;
	@Column(name = "MED_NUMERO_ORGANISMI_DISP")
	private Long medNumeroOrganismiDisp;
	@Column(name = "MED_TELEFONO")
	private String medTelefono;
	@Column(name = "MED_CELLULARE")
	private String medCellulare;
	@Column(name = "MED_FAX")
	private String medFax;
	@Column(name = "MED_NOMI_ORGANISMI_DISPONIBILITA")
	private String medNomiOrganismiDisponibilita;
	@Column(name = "MED_TIPO_FORMAZIONE")
	private Integer medTipoFormazione;
	@Column(name = "MED_ENTE_FORMAZ_1")
	private String medEnteFormaz1;
	@Column(name = "MED_ENTE_FORMAZ_2")
	private String medEnteFormaz2;
	@Column(name = "MED_ODM_ISCRITTO_CONCILIATORE")
	private String medOdmIscrittoConciliatore;
	@Column(name = "LR_RAPP_O_AMM")
	private Integer lpRappoAmm;
	@Column(name = "COA_SOCIO_O_ASS")
	private Integer coaSociooAss;
	@Column(name = "PO_TIPO_RAPP_ODM")
	private String poTipoRappOdm;
	@Column(name = "PO_DATA_ASSUNZIONE")
	private Date poDataAssunzione;
	@Column(name = "PO_DATA_SCADENZA_RAPPORTO_LAVORO")
	private Date poDataScadenzaRapportoLavoro;
	@Column(name = "INDIRIZZO_DOMICILIO")
	private String indirizzoDomicilio;
	@Column(name = "NUMERO_CIVICO_DOMICILIO")
	private String numeroCivicoDomicilio;
	@Column(name = "ID_COMUNE_DOMICILIO")
	private Long idComuneDomicilio;
	@Column(name = "CAP_DOMICILIO")
	private String capDomicilio;
	@Column(name = "STATO_DOMICILIO")
	private String statoDomicilio;
	@Column(name = "COMUNE_DOMICILIO_ESTERO")
	private String comuneDomicilioEstero;
	@Column(name = "INDIRIZZO_PEC")
	private String indirizzoPec;
	@Column(name = "INDIRIZZO_EMAIL")
	private String indirizzoEmail;
	@Column(name = "MED_PEC")
	private String medPec;
	@Column(name = "ID_TIPO_ANAGRAFICA")
	private Long idTipoAnagrafica;
	@Column(name = "ID_ORDINE_COLLEGIO")
	private Long idOrdiniCollegi;
	@Column(name = "LINGUE_STRANIERE")
	private String lingueStraniere;	
	@Column(name = "ID_CERTIFICAZIONE_LINGUA")
	private Long idCertificazioneLingua;
	@Column(name = "DESCRIZIONE_QUALIFICA")
	private String descQualifica;	@Column(name = "MED_UNIVERSITA")
	private String medUniversita;

	
	public AnagraficaOdm() {
		super();
	}

	public Long getIdAnagrafica() {
		return idAnagrafica;
	}

	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
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

	public Long getIdTitoloAnagrafica() {
		return idTitoloAnagrafica;
	}

	public void setIdTitoloAnagrafica(Long idTitoloAnagrafica) {
		this.idTitoloAnagrafica = idTitoloAnagrafica;
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

	public Long getIdComuneNascita() {
		return idComuneNascita;
	}

	public Comune getComune() {
		return comune;
	}

	public void setComune(Comune comune) {
		this.comune = comune;
	}

	public void setIdComuneNascita(Long idComuneNascita) {
		this.idComuneNascita = idComuneNascita;
	}

	public String getComuneNascitaEstero() {
		return comuneNascitaEstero;
	}

	public void setComuneNascitaEstero(String comuneNascitaEstero) {
		this.comuneNascitaEstero = comuneNascitaEstero;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
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

	public String getStatoResidenza() {
		return statoResidenza;
	}

	public void setStatoResidenza(String statoResidenza) {
		this.statoResidenza = statoResidenza;
	}

	public Long getIdComuneResidenza() {
		return idComuneResidenza;
	}

	public void setIdComuneResidenza(Long idComuneResidenza) {
		this.idComuneResidenza = idComuneResidenza;
	}

	public String getComuneResidenzaEstero() {
		return comuneResidenzaEstero;
	}

	public void setComuneResidenzaEstero(String comuneResidenzaEstero) {
		this.comuneResidenzaEstero = comuneResidenzaEstero;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public Long getIdQualifica() {
		return idQualifica;
	}

	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
	}

	public String getMedEmail() {
		return medEmail;
	}

	public void setMedEmail(String medEmail) {
		this.medEmail = medEmail;
	}

	public String getMedTitoloDiStudio() {
		return medTitoloDiStudio;
	}

	public void setMedTitoloDiStudio(String medTitoloDiStudio) {
		this.medTitoloDiStudio = medTitoloDiStudio;
	}

	public String getMedPiva() {
		return medPiva;
	}

	public void setMedPiva(String medPiva) {
		this.medPiva = medPiva;
	}

	public String getMedOrdineCollegioProfess() {
		return medOrdineCollegioProfess;
	}

	public void setMedOrdineCollegioProfess(String medOrdineCollegioProfess) {
		this.medOrdineCollegioProfess = medOrdineCollegioProfess;
	}

	public Date getMedDataOrdineCollegioProfess() {
		return medDataOrdineCollegioProfess;
	}

	public void setMedDataOrdineCollegioProfess(Date medDataOrdineCollegioProfess) {
		this.medDataOrdineCollegioProfess = medDataOrdineCollegioProfess;
	}

	public String getMedNumIscrizioneOrdineColleggio() {
		return medNumIscrizioneOrdineColleggio;
	}

	public void setMedNumIscrizioneOrdineColleggio(String medNumIscrizioneOrdineColleggio) {
		this.medNumIscrizioneOrdineColleggio = medNumIscrizioneOrdineColleggio;
	}

	public String getMedSedeIscrizioneOrdineColleggio() {
		return medSedeIscrizioneOrdineColleggio;
	}

	public void setMedSedeIscrizioneOrdineColleggio(String medSedeIscrizioneOrdineColleggio) {
		this.medSedeIscrizioneOrdineColleggio = medSedeIscrizioneOrdineColleggio;
	}

	public String getMedDurataCorsoFormazione() {
		return medDurataCorsoFormazione;
	}

	public void setMedDurataCorsoFormazione(String medDurataCorsoFormazione) {
		this.medDurataCorsoFormazione = medDurataCorsoFormazione;
	}

	public String getMedRappGiuridicoEconomico() {
		return medRappGiuridicoEconomico;
	}

	public void setMedRappGiuridicoEconomico(String medRappGiuridicoEconomico) {
		this.medRappGiuridicoEconomico = medRappGiuridicoEconomico;
	}

	public Long getMedNumeroOrganismiDisp() {
		return medNumeroOrganismiDisp;
	}

	public void setMedNumeroOrganismiDisp(Long medNumeroOrganismiDisp) {
		this.medNumeroOrganismiDisp = medNumeroOrganismiDisp;
	}

	public String getMedTelefono() {
		return medTelefono;
	}

	public void setMedTelefono(String medTelefono) {
		this.medTelefono = medTelefono;
	}

	public String getMedCellulare() {
		return medCellulare;
	}

	public void setMedCellulare(String medCellulare) {
		this.medCellulare = medCellulare;
	}

	public String getMedFax() {
		return medFax;
	}

	public void setMedFax(String medFax) {
		this.medFax = medFax;
	}

	public String getMedNomiOrganismiDisponibilita() {
		return medNomiOrganismiDisponibilita;
	}

	public void setMedNomiOrganismiDisponibilita(String medNomiOrganismiDisponibilita) {
		this.medNomiOrganismiDisponibilita = medNomiOrganismiDisponibilita;
	}

	public Integer getMedTipoFormazione() {
		return medTipoFormazione;
	}

	public void setMedTipoFormazione(Integer medTipoFormazione) {
		this.medTipoFormazione = medTipoFormazione;
	}

	public String getMedEnteFormaz1() {
		return medEnteFormaz1;
	}

	public void setMedEnteFormaz1(String medEnteFormaz1) {
		this.medEnteFormaz1 = medEnteFormaz1;
	}

	public String getMedEnteFormaz2() {
		return medEnteFormaz2;
	}

	public void setMedEnteFormaz2(String medEnteFormaz2) {
		this.medEnteFormaz2 = medEnteFormaz2;
	}

	public String getMedOdmIscrittoConciliatore() {
		return medOdmIscrittoConciliatore;
	}

	public void setMedOdmIscrittoConciliatore(String medOdmIscrittoConciliatore) {
		this.medOdmIscrittoConciliatore = medOdmIscrittoConciliatore;
	}

	public Integer getLpRappoAmm() {
		return lpRappoAmm;
	}

	public void setLpRappoAmm(Integer lpRappoAmm) {
		this.lpRappoAmm = lpRappoAmm;
	}

	public Integer getCoaSociooAss() {
		return coaSociooAss;
	}

	public void setCoaSociooAss(Integer coaSociooAss) {
		this.coaSociooAss = coaSociooAss;
	}

	public String getPoTipoRappOdm() {
		return poTipoRappOdm;
	}

	public void setPoTipoRappOdm(String poTipoRappOdm) {
		this.poTipoRappOdm = poTipoRappOdm;
	}

	public Date getPoDataAssunzione() {
		return poDataAssunzione;
	}

	public void setPoDataAssunzione(Date poDataAssunzione) {
		this.poDataAssunzione = poDataAssunzione;
	}

	public Date getPoDataScadenzaRapportoLavoro() {
		return poDataScadenzaRapportoLavoro;
	}

	public void setPoDataScadenzaRapportoLavoro(Date poDataScadenzaRapportoLavoro) {
		this.poDataScadenzaRapportoLavoro = poDataScadenzaRapportoLavoro;
	}

	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}

	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}

	public String getNumeroCivicoDomicilio() {
		return numeroCivicoDomicilio;
	}

	public void setNumeroCivicoDomicilio(String numeroCivicoDomicilio) {
		this.numeroCivicoDomicilio = numeroCivicoDomicilio;
	}

	public Long getIdComuneDomicilio() {
		return idComuneDomicilio;
	}

	public void setIdComuneDomicilio(Long idComuneDomicilio) {
		this.idComuneDomicilio = idComuneDomicilio;
	}

	public String getCapDomicilio() {
		return capDomicilio;
	}

	public void setCapDomicilio(String capDomicilio) {
		this.capDomicilio = capDomicilio;
	}

	public String getStatoDomicilio() {
		return statoDomicilio;
	}

	public void setStatoDomicilio(String statoDomicilio) {
		this.statoDomicilio = statoDomicilio;
	}

	public String getComuneDomicilioEstero() {
		return comuneDomicilioEstero;
	}

	public void setComuneDomicilioEstero(String comuneDomicilioEstero) {
		this.comuneDomicilioEstero = comuneDomicilioEstero;
	}

	public String getIndirizzoPec() {
		return indirizzoPec;
	}

	public void setIndirizzoPec(String indirizzoPec) {
		this.indirizzoPec = indirizzoPec;
	}

	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}

	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}

	public String getMedPec() {
		return medPec;
	}

	public void setMedPec(String medPec) {
		this.medPec = medPec;
	}

	public Long getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}

	public void setIdTipoAnagrafica(Long idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}

	public Long getIdOrdiniCollegi() {
		return idOrdiniCollegi;
	}

	public void setIdOrdiniCollegi(Long idOrdiniCollegi) {
		this.idOrdiniCollegi = idOrdiniCollegi;
	}

	public String getLingueStraniere() {
		return lingueStraniere;
	}

	public void setLingueStraniere(String lingueStraniere) {
		this.lingueStraniere = lingueStraniere;
	}

	public Long getIdCertificazioneLingua() {
		return idCertificazioneLingua;
	}

	public void setIdCertificazioneLingua(Long idCertificazioneLingua) {
		this.idCertificazioneLingua = idCertificazioneLingua;
	}

	public String getDescQualifica() {
		return descQualifica;
	}

	public void setDescQualifica(String descQualifica) {
		this.descQualifica = descQualifica;
	}

	public String getMedUniversita() {
		return medUniversita;
	}

	public void setMedUniversita(String medUniversita) {
		this.medUniversita = medUniversita;
	}
	
	
	
}
