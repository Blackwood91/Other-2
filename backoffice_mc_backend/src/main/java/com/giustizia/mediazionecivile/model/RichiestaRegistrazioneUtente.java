package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RICHIESTA_REGISTRAZIONE_UTENTI")
public class RichiestaRegistrazioneUtente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RICHIESTA_REGISTRAZIONE_UTENTI")
	private Long id;
	@Column(name = "ID_USER_LOGIN")
	private Long idUserLogin;
	@Column(name = "P_IVA")
	private String pIva;
	@Column(name = "ID_RUOLO")
	private int idRuolo;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "PEC")
	private String pec;
	@Column(name = "RICHIESTA_ISCRIZIONE")
	private String richiestaIscrizione;
	@Column(name = "DATA_INSERIMENTO")
	private Date dataInserimento;
	@Column(name = "RAGIONE_SOCIALE")
	private String ragioneSociale;
	
	public RichiestaRegistrazioneUtente() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUserLogin() {
		return idUserLogin;
	}

	public void setIdUserLogin(Long idUserLogin) {
		this.idUserLogin = idUserLogin;
	}

	public String getpIva() {
		return pIva;
	}

	public void setpIva(String pIva) {
		this.pIva = pIva;
	}

	public int getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getRichiestaIscrizione() {
		return richiestaIscrizione;
	}

	public void setRichiestaIscrizione(String richiestaIscrizione) {
		this.richiestaIscrizione = richiestaIscrizione;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
}
