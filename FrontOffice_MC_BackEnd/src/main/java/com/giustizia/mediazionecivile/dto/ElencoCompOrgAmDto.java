package com.giustizia.mediazionecivile.dto;

public class ElencoCompOrgAmDto {
	private Long idAnagrafica;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private Long idQualifica;
    private Integer completato;
    private Integer validato;    
    private Integer annullato;
    
    
	public ElencoCompOrgAmDto() {
		super();
	}

	public ElencoCompOrgAmDto(Object[] obj) {
		this.idAnagrafica = (Long)obj[0];
		this.codiceFiscale = (String)obj[1];
		this.cognome = (String)obj[2];
		this.nome = (String)obj[3];
		this.idQualifica = (Long)obj[4];
		this.completato = (Integer)obj[5];
		this.validato = (Integer)obj[6];
		this.annullato = (Integer)obj[7];
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

	public Long getIdQualifica() {
		return idQualifica;
	}

	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
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
}
