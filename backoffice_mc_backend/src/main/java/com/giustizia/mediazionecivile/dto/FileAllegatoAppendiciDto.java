package com.giustizia.mediazionecivile.dto;

import java.util.Date;

public class FileAllegatoAppendiciDto {
	Long id;
	Long idRichiesta;
	Long idAnagrafica;
	Long idCertificazioneLingua;
	byte[] file;
	String nomeFile;
	Long idRiferimento;
	Date dataCertificazione;
	String enteCertificatore;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public Long getIdRiferimento() {
		return idRiferimento;
	}
	public void setIdRiferimento(Long idRiferimento) {
		this.idRiferimento = idRiferimento;
	}
	public Date getDataCertificazione() {
		return dataCertificazione;
	}
	public void setDataCertificazione(Date dataCertificazione) {
		this.dataCertificazione = dataCertificazione;
	}
	public String getEnteCertificatore() {
		return enteCertificatore;
	}
	public void setEnteCertificatore(String enteCertificatore) {
		this.enteCertificatore = enteCertificatore;
	}
	public Long getIdCertificazioneLingua() {
		return idCertificazioneLingua;
	}
	public void setIdCertificazioneLingua(Long idCertificazioneLingua) {
		this.idCertificazioneLingua = idCertificazioneLingua;
	}
	
}
