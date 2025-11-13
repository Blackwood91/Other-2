package giustiziariparativa.backoffice.DTO;

import java.util.Date;


public class MediatoreEnte {
	
	private Long idMediatore;
	// TAB MEDIATORE
    private String nomeMediatore;

    private String cognomeMediatore;

    private String codiceFiscale;

    private String indirizzoPec;

    private String luogoDiNascita;

    private Date dataDiNascita;

    private String indirizzo;

    private String civico;
    
    private String cittaDiResidenza;

    private String provinciaDiResidenza;

    private String cap;

    private int isFormatore;
    
    private String numeroIscrizioneElenco;
    
    private String requisitiIscrizioneElenco;

    private Date dataIscrizioneElenco;

    private String stato;

    private String nomeEnteAttestato;
    
    private String statoIscrizioneId;

    
    //TAB ENTE
    private int enteAttestatoID;

    private String tipologiaEnte;

    private int isConvenzionato;
    

    //TAB PROVVEDIMENTO - FILE PDF
    private byte[] provvedimento;

    
    
	public MediatoreEnte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MediatoreEnte(Long idMediatore, String nomeMediatore, String cognomeMediatore, String codiceFiscale,
			String indirizzoPec, String luogoDiNascita, Date dataDiNascita, String indirizzo, String civico,
			String cittaDiResidenza, String provinciaDiResidenza, String cap, int isFormatore,
			String numeroIscrizioneElenco, String requisitiIscrizioneElenco, Date dataIscrizioneElenco, String stato,
			String nomeEnteAttestato, int enteAttestatoID, String tipologiaEnte, int isConvenzionato,
			byte[] provvedimento) {
		super();
		this.idMediatore = idMediatore;
		this.nomeMediatore = nomeMediatore;
		this.cognomeMediatore = cognomeMediatore;
		this.codiceFiscale = codiceFiscale;
		this.indirizzoPec = indirizzoPec;
		this.luogoDiNascita = luogoDiNascita;
		this.dataDiNascita = dataDiNascita;
		this.indirizzo = indirizzo;
		this.civico = civico;
		this.cittaDiResidenza = cittaDiResidenza;
		this.provinciaDiResidenza = provinciaDiResidenza;
		this.cap = cap;
		this.isFormatore = isFormatore;
		this.numeroIscrizioneElenco = numeroIscrizioneElenco;
		this.requisitiIscrizioneElenco = requisitiIscrizioneElenco;
		this.dataIscrizioneElenco = dataIscrizioneElenco;
		this.stato = stato;
		this.nomeEnteAttestato = nomeEnteAttestato;
		this.enteAttestatoID = enteAttestatoID;
		this.tipologiaEnte = tipologiaEnte;
		this.isConvenzionato = isConvenzionato;
		this.provvedimento = provvedimento;
	}


	public Long getIdMediatore() {
		return idMediatore;
	}

	public void setIdMediatore(Long idMediatore) {
		this.idMediatore = idMediatore;
	}

	public String getNomeMediatore() {
		return nomeMediatore;
	}

	public void setNomeMediatore(String nomeMediatore) {
		this.nomeMediatore = nomeMediatore;
	}

	public String getCognomeMediatore() {
		return cognomeMediatore;
	}

	public void setCognomeMediatore(String cognomeMediatore) {
		this.cognomeMediatore = cognomeMediatore;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getIndirizzoPec() {
		return indirizzoPec;
	}

	public void setIndirizzoPec(String indirizzoPec) {
		this.indirizzoPec = indirizzoPec;
	}

	public String getLuogoDiNascita() {
		return luogoDiNascita;
	}

	public void setLuogoDiNascita(String luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCittaDiResidenza() {
		return cittaDiResidenza;
	}

	public void setCittaDiResidenza(String cittaDiResidenza) {
		this.cittaDiResidenza = cittaDiResidenza;
	}

	public String getProvinciaDiResidenza() {
		return provinciaDiResidenza;
	}

	public void setProvinciaDiResidenza(String provinciaDiResidenza) {
		this.provinciaDiResidenza = provinciaDiResidenza;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public int getIsFormatore() {
		return isFormatore;
	}

	public void setIsFormatore(int isFormatore) {
		this.isFormatore = isFormatore;
	}

	public String getNumeroIscrizioneElenco() {
		return numeroIscrizioneElenco;
	}

	public void setNumeroIscrizioneElenco(String numeroIscrizioneElenco) {
		this.numeroIscrizioneElenco = numeroIscrizioneElenco;
	}

	public String getRequisitiIscrizioneElenco() {
		return requisitiIscrizioneElenco;
	}

	public void setRequisitiIscrizioneElenco(String requisitiIscrizioneElenco) {
		this.requisitiIscrizioneElenco = requisitiIscrizioneElenco;
	}

	public Date getDataIscrizioneElenco() {
		return dataIscrizioneElenco;
	}

	public void setDataIscrizioneElenco(Date dataIscrizioneElenco) {
		this.dataIscrizioneElenco = dataIscrizioneElenco;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNomeEnteAttestato() {
		return nomeEnteAttestato;
	}

	public void setNomeEnteAttestato(String nomeEnteAttestato) {
		this.nomeEnteAttestato = nomeEnteAttestato;
	}

	public String getStatoIscrizioneId() {
		return statoIscrizioneId;
	}

	public void setStatoIscrizioneId(String statoIscrizioneId) {
		this.statoIscrizioneId = statoIscrizioneId;
	}

	public int getEnteAttestatoID() {
		return enteAttestatoID;
	}



	public void setEnteAttestatoID(int enteAttestatoID) {
		this.enteAttestatoID = enteAttestatoID;
	}



	public String getTipologiaEnte() {
		return tipologiaEnte;
	}



	public void setTipologiaEnte(String tipologiaEnte) {
		this.tipologiaEnte = tipologiaEnte;
	}



	public int getIsConvenzionato() {
		return isConvenzionato;
	}



	public void setIsConvenzionato(int isConvenzionato) {
		this.isConvenzionato = isConvenzionato;
	}



	public byte[] getProvvedimento() {
		return provvedimento;
	}



	public void setProvvedimento(byte[] provvedimento) {
		this.provvedimento = provvedimento;
	}




    
}
