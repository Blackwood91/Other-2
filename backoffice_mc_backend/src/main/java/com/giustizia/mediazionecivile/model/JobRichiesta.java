package com.giustizia.mediazionecivile.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JOB_RICHIESTE")
public class JobRichiesta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_JOB")
	private Long idJob;
	@Column(name = "DATA_ORA_RICHIESTA")
	private Date dataOraRichiesta;
	@Column(name = "TIPO_JOB")
	private String tipoJob;
	@Column(name = "TIPO_RICHIESTA", columnDefinition = "CHAR(3)")
	private String tipoRichiesta;
	@Column(name = "ID_RICHIESTA")
	private Long idRichiesta;
	@Column(name = "ID_UTENTE_RICHIEDENTE")
	private Long idUtenteRichiedente;
	@Column(name = "STATO_JOB")
	private String statoJob;
	@Column(name = "DATA_ULTIMO_STATO")
	private Date dataUltimoStato;
	@Column(name = "PATH_DOWNLOAD")
	private String pathDownload;
	public Long getIdJob() {
		return idJob;
	}
	public void setIdJob(Long idJob) {
		this.idJob = idJob;
	}
	public Date getDataOraRichiesta() {
		return dataOraRichiesta;
	}
	public void setDataOraRichiesta(Date dataOraRichiesta) {
		this.dataOraRichiesta = dataOraRichiesta;
	}
	public String getTipoJob() {
		return tipoJob;
	}
	public void setTipoJob(String tipoJob) {
		this.tipoJob = tipoJob;
	}
	public String getTipoRichiesta() {
		return tipoRichiesta;
	}
	public void setTipoRichiesta(String tipoRichiesta) {
		this.tipoRichiesta = tipoRichiesta;
	}
	public Long getIdRichiesta() {
		return idRichiesta;
	}
	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	public Long getIdUtenteRichiedente() {
		return idUtenteRichiedente;
	}
	public void setIdUtenteRichiedente(Long idUtenteRichiedente) {
		this.idUtenteRichiedente = idUtenteRichiedente;
	}
	public String getStatoJob() {
		return statoJob;
	}
	public void setStatoJob(String statoJob) {
		this.statoJob = statoJob;
	}
	public Date getDataUltimoStato() {
		return dataUltimoStato;
	}
	public void setDataUltimoStato(Date dataUltimoStato) {
		this.dataUltimoStato = dataUltimoStato;
	}
	public String getPathDownload() {
		return pathDownload;
	}
	public void setPathDownload(String pathDownload) {
		this.pathDownload = pathDownload;
	}
	
	

}
