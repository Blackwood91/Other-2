package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class AnagraficaOdmDto {
	private Long idAnagrafica;
	private Long idRichiesta;
	private Long idTitoloAnagrafica;
	private String sesso;
	private String cognome;
	private String nome;
	private Date dataNascita;
	private Date poDataAssunzione;
	private String statoNascita;
	private Long idComuneNascita;
	private String codiceFiscale;
	private String cittadinanza;
	private String poTipoRappOdm;
	private String comuneNascitaEstero;
	// Residenza
	private String statoResidenza;
	private Long idComuneResidenza;
	private String indirizzo;
	private String numeroCivico;
	private String cap;
	private String comuneResidenzaEstero;
	// Domicilio
	private String indirizzoDomicilio;
	private String numeroCivicoDomicilio;
	private Long idComuneDomicilio;
	private String capDomicilio;
	private String statoDomicilio;
	private String comuneDomicilioEstero;
	private String indirizzoEmail;
	private String indirizzoPec;

	private String medTelefono;
	private String medCellulare;
	private String medPiva;
	private String medFax;
	
	private String medTitoloDiStudio;
	private Long idOrdiniCollegi;
	private Date medDataOrdineCollegioProfess;
	private String medRappGiuridicoEconomico;
	private Long medNumeroOrganismiDisp;

	private String nomeFile;
	private byte[] file;
	private byte[] file2;
	private boolean rapLegaleIsRespOrg;
	private Long idQualifica;
	private Long idTipoAnagrafica;
	private boolean updateAnagraficaRapLegale;
	private boolean convalidazioeRapOrRespOrOrgAm;
	private String lingueStraniere;
	private Long idCertificazioneLingue;
	private String descQualifica;
	private String medUniversita;
	// Dati vestigiali
	private Integer lpRappoAmm;
	// DATI STATOMODULI
    private Integer completato;
    private Integer validato;    
    private Integer annullato;
    // DATI RICHIESTA
    private Integer numMediatori;
    private Integer numMediatoriInter;
    private Integer numMediatoriCons;
	
	public AnagraficaOdmDto() {
		super();
	}
		
	public AnagraficaOdmDto(Object[] obj) {
		this.idAnagrafica = (Long)obj[0];
		this.nome = (String)obj[1];
		this.cognome = (String)obj[2];
		this.codiceFiscale = (String)obj[3];
		this.sesso = (String)obj[4];
		this.dataNascita = (Date)obj[5];
		this.idComuneNascita = (Long)obj[6];
		this.comuneNascitaEstero = (String)obj[7];
		this.idTipoAnagrafica = (Long)obj[8];
		this.medCellulare = (String)obj[9];
		this.medTelefono = (String)obj[10];
		this.medFax = (String)obj[11];
		this.idComuneResidenza = (Long)obj[12];
		this.completato = (Integer) obj[13];
		this.validato = (Integer) obj[14];
		this.annullato = (Integer) obj[15];
	}

	public Long getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Long getIdAnagrafica() {
		return idAnagrafica;
	}

	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}

	public Long getIdTitoloAnagrafica() {
		return idTitoloAnagrafica;
	}
	
	public void setIdTitoloAnagrafica(Long idTitoloAnagrafica) {
		this.idTitoloAnagrafica = idTitoloAnagrafica;
	}
	
	public String getSesso() {
		return sesso;
	}
	
	public void setSesso(String sesso) {
		this.sesso = sesso;
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
	
	public Date getDataNascita() {
		return dataNascita;
	}
	
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public Date getPoDataAssunzione() {
		return poDataAssunzione;
	}

	public void setPoDataAssunzione(Date poDataAssunzione) {
		this.poDataAssunzione = poDataAssunzione;
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
	
	public void setIdComuneNascita(Long idComuneNascita) {
		this.idComuneNascita = idComuneNascita;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}	
	
	public String getPoTipoRappOdm() {
		return poTipoRappOdm;
	}

	public void setPoTipoRappOdm(String poTipoRappOdm) {
		this.poTipoRappOdm = poTipoRappOdm;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}
	
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	
	public String getComuneNascitaEstero() {
		return comuneNascitaEstero;
	}
	
	public void setComuneNascitaEstero(String comuneNascitaEstero) {
		this.comuneNascitaEstero = comuneNascitaEstero;
	}
	
	public String getStatoResidenza() {
		return statoResidenza;
	}

	public void setStatoResidenza(String statoResidenza) {
		this.statoResidenza = statoResidenza;
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

	public String getComuneResidenzaEstero() {
		return comuneResidenzaEstero;
	}

	public void setComuneResidenzaEstero(String comuneResidenzaEstero) {
		this.comuneResidenzaEstero = comuneResidenzaEstero;
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

	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}

	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}

	public String getIndirizzoPec() {
		return indirizzoPec;
	}

	public void setIndirizzoPec(String indirizzoPec) {
		this.indirizzoPec = indirizzoPec;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public byte[] getFile2() {
		return file2;
	}

	public void setFile2(byte[] file2) {
		this.file2 = file2;
	}

	public boolean getRapLegaleIsRespOrg() {
		return rapLegaleIsRespOrg;
	}

	public void setRapLegaleIsRespOrg(boolean rapLegaleIsRespOrg) {
		this.rapLegaleIsRespOrg = rapLegaleIsRespOrg;
	}

	public Long getIdQualifica() {
		return idQualifica;
	}

	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
	}

	public Long getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}

	public void setIdTipoAnagrafica(Long idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}

	public Long getIdComuneResidenza() {
		return idComuneResidenza;
	}

	public void setIdComuneResidenza(Long idComuneResidenza) {
		this.idComuneResidenza = idComuneResidenza;
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

	public String getMedPiva() {
		return medPiva;
	}

	public void setMedPiva(String medPiva) {
		this.medPiva = medPiva;
	}

	public Integer getLpRappoAmm() {
		return lpRappoAmm;
	}

	public void setLpRappoAmm(Integer lpRappoAmm) {
		this.lpRappoAmm = lpRappoAmm;
	}

	public String getMedTitoloDiStudio() {
		return medTitoloDiStudio;
	}

	public void setMedTitoloDiStudio(String medTitoloDiStudio) {
		this.medTitoloDiStudio = medTitoloDiStudio;
	}

	public Long getIdOrdiniCollegi() {
		return idOrdiniCollegi;
	}

	public void setIdOrdiniCollegi(Long idOrdiniCollegi) {
		this.idOrdiniCollegi = idOrdiniCollegi;
	}
	
	public Date getMedDataOrdineCollegioProfess() {
		return medDataOrdineCollegioProfess;
	}

	public void setMedDataOrdineCollegioProfess(Date medDataOrdineCollegioProfess) {
		this.medDataOrdineCollegioProfess = medDataOrdineCollegioProfess;
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

	public boolean getUpdateAnagraficaRapLegale() {
		return updateAnagraficaRapLegale;
	}

	public void setUpdateAnagraficaRapLegale(boolean updateAnagraficaRapLegale) {
		this.updateAnagraficaRapLegale = updateAnagraficaRapLegale;
	}

	public boolean getConvalidazioeRapOrRespOrOrgAm() {
		return convalidazioeRapOrRespOrOrgAm;
	}

	public void setConvalidazioeRapOrRespOrOrgAm(boolean convalidazioeRapOrRespOrOrgAm) {
		this.convalidazioeRapOrRespOrOrgAm = convalidazioeRapOrRespOrOrgAm;
	}

	public String getLingueStraniere() {
		return lingueStraniere;
	}

	public void setLingueStraniere(String lingueStraniere) {
		this.lingueStraniere = lingueStraniere;
	}

	public Long getIdCertificazioneLingue() {
		return idCertificazioneLingue;
	}

	public void setIdCertificazioneLingue(Long idCertificazioneLingue) {
		this.idCertificazioneLingue = idCertificazioneLingue;
	}

	public String getMedUniversita() {
		return medUniversita;
	}

	public void setMedUniversita(String medUniversita) {
		this.medUniversita = medUniversita;
	}

	public String getDescQualifica() {
		return descQualifica;
	}

	public void setDescQualifica(String descQualifica) {
		this.descQualifica = descQualifica;
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

	public Integer getNumMediatori() {
		return numMediatori;
	}

	public void setNumMediatori(Integer numMediatori) {
		this.numMediatori = numMediatori;
	}

	public Integer getNumMediatoriInter() {
		return numMediatoriInter;
	}

	public void setNumMediatoriInter(Integer numMediatoriInter) {
		this.numMediatoriInter = numMediatoriInter;
	}

	public Integer getNumMediatoriCons() {
		return numMediatoriCons;
	}

	public void setNumMediatoriCons(Integer numMediatoriCons) {
		this.numMediatoriCons = numMediatoriCons;
	}
	
}
