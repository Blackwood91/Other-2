package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.dto.AlboMediatoriElencoDto;
import com.giustizia.mediazionecivile.model.AlboMediatori;

public interface AlboMediatoriRepository extends JpaRepository<AlboMediatori, Long> {
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.dataNascita, " +
			   "a.medNumeroOrganismiDisp, a.idTipoAnagrafica " +
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON a.idAnagrafica = s.idAnagrafica " +
		       "LEFT JOIN TipoPdg t ON t.idTipoPdg = a.idTipoAnagrafica " +
			   "JOIN Richiesta r ON r.idRichiesta = s.idRichiesta AND r.idStato = 4 " +
		       "WHERE (:searchText IS NULL OR a.nome LIKE %:searchText%) AND (a.idTipoAnagrafica = 4 OR a.idTipoAnagrafica = 5 OR a.idTipoAnagrafica = 6)")		  
	Page<Object[]> getAllAlboMediatoriByNome(@Param("searchText") String searchText, Pageable pageable );
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.dataNascita, " +
			   "a.medNumeroOrganismiDisp, a.idTipoAnagrafica " +
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON a.idAnagrafica = s.idAnagrafica " +
		       "LEFT JOIN TipoPdg t ON t.idTipoPdg = a.idTipoAnagrafica " +
			   "JOIN Richiesta r ON r.idRichiesta = s.idRichiesta AND r.idStato = 4 " +
		       "WHERE (:searchText IS NULL OR a.cognome LIKE %:searchText%) AND (a.idTipoAnagrafica = 4 OR a.idTipoAnagrafica = 5 OR a.idTipoAnagrafica = 6)")		  
	Page<Object[]> getAllAlboMediatoriByCognome(@Param("searchText") String searchText, Pageable pageable );
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.dataNascita, " +
			   "a.medNumeroOrganismiDisp, a.idTipoAnagrafica " +
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON a.idAnagrafica = s.idAnagrafica " +
		       "LEFT JOIN TipoPdg t ON t.idTipoPdg = a.idTipoAnagrafica " +
			   "JOIN Richiesta r ON r.idRichiesta = s.idRichiesta AND r.idStato = 4 " +
		       "WHERE (:searchText IS NULL OR a.codiceFiscale LIKE %:searchText%) AND (a.idTipoAnagrafica = 4 OR a.idTipoAnagrafica = 5 OR a.idTipoAnagrafica = 6)")		  
	Page<Object[]> getAllAlboMediatoriByCF(@Param("searchText") String searchText, Pageable pageable );
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.dataNascita, " +
			   "a.medNumeroOrganismiDisp, a.idTipoAnagrafica " +
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON a.idAnagrafica = s.idAnagrafica " +
		       "LEFT JOIN TipoPdg t ON t.idTipoPdg = a.idTipoAnagrafica " +
			   "JOIN Richiesta r ON r.idRichiesta = s.idRichiesta AND r.idStato = 4 " +
		       "WHERE (:tipoMed IS NULL OR a.idTipoAnagrafica = :tipoMed) AND (a.idTipoAnagrafica = 4 OR a.idTipoAnagrafica = 5 OR a.idTipoAnagrafica = 6)")
	Page<Object[]> getAllAlboMediatoriByTipoMed(@Param("tipoMed") Long tipoMed, Pageable pageable );
	
	
	
	@Query(value = "SELECT DISTINCT aodm.rom, aodm.denominazione_organismo, aodm.sito_web, natura_organismo, cancellato, aodm.data_cancellazione, "
			+ " am.nome, am.cognome, am.codice_fiscale, aodms.email FROM ALBO_MEDIATORI am "
			+ " JOIN ALBO_MEDIATORI_ODM amodm ON am.codice_fiscale = amodm.codice_fiscale_mediatore "
			+ " JOIN ALBO_ODM aodm ON amodm.rom = aodm.rom JOIN RICHIESTE odmr ON aodm.denominazione_organismo = odmr.denominazione_odm"
			+ " JOIN ALBO_ODM_SEDI aodms on aodm.rom = aodms.rom"
			+ " WHERE id_albo_mediatore = :idAlboMediatori", nativeQuery = true)
	public List<Object[]> findByIdAlboMediatori(@Param("idAlboMediatori")Long idAlboMediatori);
	
	
	// "LEFT JOIN Societa sa ON sa.id" +
		@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.dataNascita, " +
				   "a.medNumeroOrganismiDisp, a.idTipoAnagrafica " +
				   "FROM SoggettoRichiesta s " +
			       "JOIN AnagraficaOdm a ON a.idAnagrafica = s.idAnagrafica " +
			       "LEFT JOIN TipoPdg t ON t.idTipoPdg = a.idTipoAnagrafica " +
				   "JOIN Richiesta r ON r.idRichiesta = s.idRichiesta AND r.idStato = 4 " +
				   "WHERE (a.idTipoAnagrafica = 4 OR a.idTipoAnagrafica = 5 OR a.idTipoAnagrafica = 6)")
		public Page<Object[]> getAllMediatoriIscritti(Pageable pageable);
		
		
	@Query("SELECT DISTINCT r.denominazioneOdm, s.sitoWebSede, s.email, r.autonomo, r.codFiscSocieta, r.pIva, r.numIscrAlbo "
			+ "FROM Richiesta r "
			+ "JOIN Sede s ON r.idRichiesta = s.idRichiesta AND r.idStato = 4 "
			+ "JOIN SoggettoRichiesta sr ON r.idRichiesta = sr.idRichiesta "
			+ "JOIN AnagraficaOdm a ON sr.idAnagrafica = a.idAnagrafica AND a.idAnagrafica = :idAnagrafica")	  
	Page<Object[]> getAllOdmPagedByIdAnagrafica(@Param("idAnagrafica") Long idAnagrafica, Pageable pageable );

}
