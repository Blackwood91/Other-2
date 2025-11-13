package com.giustizia.mediazionecivile.dto;

public class PdfDto {
	private String nomeFile;
	private byte[] file;
	

	public PdfDto() {
		super();
	}
	
	public PdfDto(String nomeFile, byte[] file) {
		super();
		this.nomeFile = nomeFile;
		this.file = file;
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
	
}
