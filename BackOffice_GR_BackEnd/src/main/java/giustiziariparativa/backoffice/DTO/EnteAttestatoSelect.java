package giustiziariparativa.backoffice.DTO;

public class EnteAttestatoSelect {
	
    private Long enteAttestatoID;

    private String nomeEnteAttestato;

    private String tipologiaEnte;

    private int isConvenzionato;
    
    
    public EnteAttestatoSelect() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EnteAttestatoSelect(Object[] obj) {
        this.enteAttestatoID = (Long) obj[0];
        this.nomeEnteAttestato = (String) obj[1];
        this.tipologiaEnte = (String) obj[2];
        this.isConvenzionato = obj[3] != null ? (int) obj[3] : 0;
    }


	public EnteAttestatoSelect(Long idEnteAttestato, String enteAttestato, String tipologiaEnte, int isConvenzionato) {
		super();
		this.enteAttestatoID = idEnteAttestato;
		this.nomeEnteAttestato = enteAttestato;
		this.tipologiaEnte = tipologiaEnte;
		this.isConvenzionato = isConvenzionato;
	}




	public Long getEnteAttestatoID() {
		return enteAttestatoID;
	}


	public void setEnteAttestatoID(Long enteAttestatoID) {
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


	public int getIsConvenzionato() {
		return isConvenzionato;
	}


	public void setIsConvenzionato(int isConvenzionato) {
		this.isConvenzionato = isConvenzionato;
	}
    
    
}
