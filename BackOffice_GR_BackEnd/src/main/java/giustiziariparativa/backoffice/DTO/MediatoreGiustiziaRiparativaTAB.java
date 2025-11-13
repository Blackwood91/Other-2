package giustiziariparativa.backoffice.DTO;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;
import java.math.BigDecimal;


public class MediatoreGiustiziaRiparativaTAB {
    // Dto Mediatore
	private Object idMediatore;
    private String nomeMediatore;
    private String cognomeMediatore;
    private String codiceFiscale;
    private String numeroIscrizioneElenco;
    private Date dataIscrizioneElenco;
    private Object isFormatore;
    private String luogoDiNascita;
    private Date dataDiNascita;
    private String cittaDiResidenza;
    private String provinciaDiResidenza;
    private String indirizzo;
    private String numeroCivico;
    private String cap;
    private String indirizzoPec;
    private String requisitiIscrizioneElenco;
    private String enteAttestato;
    private String Stato;
    
    // Dto StoricoStatoMediatore
    private String indirizzo1;
    private String numero_civico;
    private String citta_residenza;
    private String provicina_residenza;
    private Object stato_iscrizione_id;
    
    private String data_stato;
    private String data_fine;
    private String tipologia;
    private String motivazione;
    private Object id_stato_mediatore;
    private Date data_provvedimento;
    
    // Dto Ente
    private Object tipologiaEnteFormatore;
    private Object enteAttestatoID;
    private String nomeEnteAttestato;
    private String tipologiaEnte;
    private Object isConvenzionato;    
    
    // Dto Provvedimento
	private byte[] provvedimento;
    private String tipologiaStato;
    private String motivazioneStato;    
    
    //dati ente formatore
    private Object idTipologiaFormatore;
    private String descrizione;
    
    
    public Object getIdTipologiaFormatore() {
		return idTipologiaFormatore;
	}

	public void setIdTipologiaFormatore(Object idTipologiaFormatore) {
		this.idTipologiaFormatore = idTipologiaFormatore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public MediatoreGiustiziaRiparativaTAB() {
    	super();
    }
    
    public MediatoreGiustiziaRiparativaTAB(Object[] obj) {
    	this.isFormatore = obj[0];
		this.dataIscrizioneElenco = (Date) obj[1]; 
		this.dataDiNascita = (Date) obj[2];
		this.enteAttestato = (String) obj[3];
		this.idMediatore =  obj[4];
		this.Stato = (String) obj[5];
		this.codiceFiscale =  (String) obj[6];
		this.cognomeMediatore =  (String) obj[7];
		this.indirizzo = (String) obj[8];
		this.indirizzoPec = (String) obj[9];
		this.luogoDiNascita = (String) obj[10];
		
		this.nomeMediatore = (String) obj[11];	
		this.numeroIscrizioneElenco = (String) obj[12];
		this.requisitiIscrizioneElenco = (String) obj[13]; 
		this.provvedimento = obj[14] != null ? blobToByteArray((Blob) obj[14]) : null;
		this.indirizzo1= (String) obj[15];
		this.numero_civico= (String) obj[16];
		this.citta_residenza = (String) obj[17];
		this.provicina_residenza = (String) obj[18];
		this.stato_iscrizione_id = obj[19];
		this.cap = (String)obj[20];

		this.enteAttestatoID = obj[21];
		//this.enteAttestatoID = (BigDecimal) obj[21];
		this.data_stato = (String)obj[22];
		this.data_fine = (String)obj[23];
		this.tipologia = (String) obj[24];
		this.motivazione = (String) obj[25];
		this.id_stato_mediatore = obj[26];
		this.data_provvedimento = (Date)obj[27];
		this.tipologiaEnte = (String) obj[28];
		this.isConvenzionato = obj[29] == null ? 0 : (Object)obj[29];
		this.tipologiaStato = (String) obj[30];
		
		this.motivazioneStato = (String) obj[31];
		
		this.idTipologiaFormatore = obj[32];
		this.descrizione = (String) obj[33];

    }

	public Object getIdMediatore() {
		return idMediatore;
	}

	public void setIdMediatore(Object idMediatore) {
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

	public String getNumeroIscrizioneElenco() {
		return numeroIscrizioneElenco;
	}

	public void setNumeroIscrizioneElenco(String numeroIscrizioneElenco) {
		this.numeroIscrizioneElenco = numeroIscrizioneElenco;
	}

	public Date getDataIscrizioneElenco() {
		return dataIscrizioneElenco;
	}

	public void setDataIscrizioneElenco(Date dataIscrizioneElenco) {
		this.dataIscrizioneElenco = dataIscrizioneElenco;
	}

	public Object getIsFormatore() {
		return isFormatore;
	}

	public void setIsFormatore(Object isFormatore) {
		this.isFormatore = isFormatore;
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

	public String getIndirizzoPec() {
		return indirizzoPec;
	}

	public void setIndirizzoPec(String indirizzoPec) {
		this.indirizzoPec = indirizzoPec;
	}

	public String getRequisitiIscrizioneElenco() {
		return requisitiIscrizioneElenco;
	}

	public void setRequisitiIscrizioneElenco(String requisitiIscrizioneElenco) {
		this.requisitiIscrizioneElenco = requisitiIscrizioneElenco;
	}

	public String getEnteAttestato() {
		return enteAttestato;
	}

	public void setEnteAttestato(String enteAttestato) {
		this.enteAttestato = enteAttestato;
	}

	public String getStato() {
		return Stato;
	}

	public void setStato(String stato) {
		Stato = stato;
	}

	public String getIndirizzo1() {
		return indirizzo1;
	}

	public void setIndirizzo1(String indirizzo1) {
		this.indirizzo1 = indirizzo1;
	}

	public String getNumero_civico() {
		return numero_civico;
	}

	public void setNumero_civico(String numero_civico) {
		this.numero_civico = numero_civico;
	}

	public String getCitta_residenza() {
		return citta_residenza;
	}

	public void setCitta_residenza(String citta_residenza) {
		this.citta_residenza = citta_residenza;
	}

	public String getProvicina_residenza() {
		return provicina_residenza;
	}

	public void setProvicina_residenza(String provicina_residenza) {
		this.provicina_residenza = provicina_residenza;
	}

	public Object getStato_iscrizione_id() {
		return stato_iscrizione_id;
	}

	public void setStato_iscrizione_id(Object stato_iscrizione_id) {
		this.stato_iscrizione_id = stato_iscrizione_id;
	}

	public String getData_stato() {
		return data_stato;
	}

	public void setData_stato(String data_stato) {
		this.data_stato = data_stato;
	}

	public String getData_fine() {
		return data_fine;
	}

	public void setData_fine(String data_fine) {
		this.data_fine = data_fine;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public Object getId_stato_mediatore() {
		return id_stato_mediatore;
	}

	public void setId_stato_mediatore(Object id_stato_mediatore) {
		this.id_stato_mediatore = id_stato_mediatore;
	}

	public Date getData_provvedimento() {
		return data_provvedimento;
	}

	public void setData_provvedimento(Date data_provvedimento) {
		this.data_provvedimento = data_provvedimento;
	}

	public Object getTipologiaEnteFormatore() {
		return tipologiaEnteFormatore;
	}

	public void setTipologiaEnteFormatore(Object tipologiaEnteFormatore) {
		this.tipologiaEnteFormatore = tipologiaEnteFormatore;
	}

	public Object getEnteAttestatoId() {
		return enteAttestatoID;
	}

	public void setEnteAttestatoId(Object enteAttestatoID) {
		this.enteAttestatoID = enteAttestatoID;
	}

	public String getNomeEnteAttestato() {
		return nomeEnteAttestato;
	}

	public void setNomeEnteAttestato(String nomeEnteAttestato) {
		this.nomeEnteAttestato = nomeEnteAttestato;
	}

	public String getTipologiaEnte() {
		return tipologiaEnte;
	}

	public void setTipologiaEnte(String tipologiaEnte) {
		this.tipologiaEnte = tipologiaEnte;
	}

	public Object getIsConvenzionato() {
		return isConvenzionato;
	}

	public void setIsConvenzionato(Object isConvenzionato) {
		this.isConvenzionato = isConvenzionato;
	}

	public byte[] getProvvedimento() {
		return provvedimento;
	}

	public void setProvvedimento(byte[] provvedimento) {
		this.provvedimento = provvedimento;
	}

	public String getTipologiaStato() {
		return tipologiaStato;
	}

	public void setTipologiaStato(String tipologiaStato) {
		this.tipologiaStato = tipologiaStato;
	}

	public String getMotivazioneStato() {
		return motivazioneStato;
	}

	public void setMotivazioneStato(String motivazioneStato) {
		this.motivazioneStato = motivazioneStato;
	}

   // Per il casto da blob ad array byte 
   public static byte[] blobToByteArray(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace(); // Gestisci l'eccezione in modo appropriato per il tuo caso d'uso
            return null;
        }
    }
    
}
