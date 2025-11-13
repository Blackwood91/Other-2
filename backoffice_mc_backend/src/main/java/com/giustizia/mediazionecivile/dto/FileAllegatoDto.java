package com.giustizia.mediazionecivile.dto;

public class FileAllegatoDto {
	Long id;
	Long idRichiesta;
	Long idAnagrafica;
	byte[] file;
	String nomeFile;
	Long idRiferimento;
	
	
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
		
}
