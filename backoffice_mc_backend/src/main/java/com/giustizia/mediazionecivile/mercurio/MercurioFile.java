package com.giustizia.mediazionecivile.mercurio;

import com.itextpdf.io.source.ByteArrayOutputStream;

public class MercurioFile {
	private String documentIdClient;
	private Integer contentId;
	private ByteArrayOutputStream file;
	
	public MercurioFile(String documentIdClient, Integer contentId, ByteArrayOutputStream file) {
		super();
		this.documentIdClient = documentIdClient;
		this.contentId = contentId;
		this.file = file;
	}
	
	public String getDocumentIdClient() {
		return documentIdClient;
	}
	public void setDocumentIdClient(String documentIdClient) {
		this.documentIdClient = documentIdClient;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public ByteArrayOutputStream getFile() {
		return file;
	}
	public void setFile(ByteArrayOutputStream file) {
		this.file = file;
	}
}
