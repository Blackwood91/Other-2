package com.giustizia.mediazionecivile.dto;

public class AlboOdmSediDto {
	char sedeLegale;
	String indirizzo;
	String comune;
	String cap;
	String nomeProvincia;
	String nomeRegione;
	String telefono;
	String fax;
	String pec;
	String mail;
	
	
	public AlboOdmSediDto() {
		super();
	}
		
	public AlboOdmSediDto(Object[] obj) {
		this.sedeLegale = (char)obj[0];
		this.indirizzo = (String)obj[1];
		this.comune = (String)obj[2];
		this.cap = (String)obj[3];
		this.nomeProvincia = (String)obj[4];
		this.nomeRegione = (String)obj[5];
		this.telefono = (String)obj[6];
		this.fax = (String)obj[7];
		this.pec = (String)obj[8];
		this.mail = (String)obj[9];
	}

	public char getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(char sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getNomeProvincia() {
		return nomeProvincia;
	}

	public void setNomeProvincia(String nomeProvincia) {
		this.nomeProvincia = nomeProvincia;
	}

	public String getNomeRegione() {
		return nomeRegione;
	}

	public void setNomeRegione(String nomeRegione) {
		this.nomeRegione = nomeRegione;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	
	
}
