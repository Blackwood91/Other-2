package com.giustizia.mediazionecivile.dto;

public class ModuloAnnullatoRicInteDto {    
    private Long progressivoModulo;    
    private Long idModulo;        
    private Integer completato;     
    private Integer validato;    
    private Integer annullato; 
    private String nomeFile;
    private String descrizioneModulo;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String numeroCivico;
    private String nomeComune;
    private String siglaProvincia;
    
    
	public ModuloAnnullatoRicInteDto() {
		super();
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
	
	
	public String getNomeFile() {
		return nomeFile;
	}


	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}


	public String getDescrizioneModulo() {
		return descrizioneModulo;
	}


	public void setDescrizioneModulo(String descrizioneModulo) {
		this.descrizioneModulo = descrizioneModulo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
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


	public String getNomeComune() {
		return nomeComune;
	}


	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}


	public String getSiglaProvincia() {
		return siglaProvincia;
	}


	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

}
