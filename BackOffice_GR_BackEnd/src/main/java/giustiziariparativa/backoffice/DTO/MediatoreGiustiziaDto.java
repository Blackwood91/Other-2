package giustiziariparativa.backoffice.DTO;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;

// Creata per non trasportare dati doppi di MediatoreGiustiziaRiparativaTAB
public class MediatoreGiustiziaDto {
    // Dto Mediatore
	private Long idMediatore;
    private String nomeMediatore;
    private String cognomeMediatore;
    private String codiceFiscale;
    private String numeroIscrizioneElenco;
    private Date dataIscrizioneElenco;
    private int isFormatore;
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
    private Long statoIscrizioneId;
    
    private String dataStato;
    private String dataFine;
    private String tipologia;
    private String motivazione;
    private Long idStatoMediatore;
    private Date dataProvvedimento;
    
    // Dto Ente
    private Long tipologiaEnteFormatore;
    private Long enteAttestatoId;
    private String nomeEnteAttestato;
    private String tipologiaEnte;
    private int isConvenzionato;    
    
    // Dto Provvedimento
	private byte[] provvedimento;
    private String tipologiaStato;
    private String motivazioneStato;    
    
    
    public MediatoreGiustiziaDto() {
    	super();
    }
    
    public MediatoreGiustiziaDto(Object[] obj) {
    	this.isFormatore = (int) obj[0];
		this.dataIscrizioneElenco = (Date) obj[1]; 
		this.dataDiNascita = (Date) obj[2];
		this.enteAttestato = (String) obj[3];
		this.idMediatore =  (Long) obj[4];
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
		this.statoIscrizioneId = (Long) obj[19];
		this.cap = (String)obj[20];
		
		this.enteAttestatoId = (Long) obj[21];
		this.dataStato = (String)obj[22];
		this.dataFine = (String)obj[23];
		this.tipologia = (String) obj[24];
		this.motivazione = (String) obj[25];
		this.idStatoMediatore = (Long) obj[26];
		this.dataProvvedimento = (Date)obj[27];
		this.tipologiaEnte = (String) obj[28];
		this.isConvenzionato = obj[29] == null ? 0 : (int)obj[29];
		this.tipologiaStato = (String) obj[30];
		
		this.motivazioneStato = (String) obj[31];
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

	public int getIsFormatore() {
		return isFormatore;
	}

	public void setIsFormatore(int isFormatore) {
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

	public Long getStatoIscrizioneId() {
		return statoIscrizioneId;
	}

	public void setStatoIscrizioneId(Long statoIscrizioneId) {
		this.statoIscrizioneId = statoIscrizioneId;
	}

	public String getDataStato() {
		return dataStato;
	}

	public void setDataStato(String dataStato) {
		this.dataStato = dataStato;
	}

	public String getDataFine() {
		return dataFine;
	}

	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
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

	public Long getIdStatoMediatore() {
		return idStatoMediatore;
	}

	public void setIdStatoMediatore(Long idStatoMediatore) {
		this.idStatoMediatore = idStatoMediatore;
	}

	public Date getDataProvvedimento() {
		return dataProvvedimento;
	}

	public void setDataProvvedimento(Date dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}

	public Long getTipologiaEnteFormatore() {
		return tipologiaEnteFormatore;
	}

	public void setTipologiaEnteFormatore(Long tipologiaEnteFormatore) {
		this.tipologiaEnteFormatore = tipologiaEnteFormatore;
	}

	public Long getEnteAttestatoId() {
		return enteAttestatoId;
	}

	public void setEnteAttestatoId(Long enteAttestatoID) {
		this.enteAttestatoId = enteAttestatoID;
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
