package com.giustizia.mediazionecivile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.dto.AnagraficaOdmDto;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.projection.AnagraficaOdmSezSecProjection;
import com.giustizia.mediazionecivile.projection.ElencoCompOrgAmProjection;
import com.giustizia.mediazionecivile.projection.ElencoRappresentantiProjection;
import com.giustizia.mediazionecivile.projection.MediatoreProjection;
import com.giustizia.mediazionecivile.projection.SelectAutocertReqOnoProjection;
import com.giustizia.mediazionecivile.projection.SezioneSecDomOdmProjection;

public interface AnagraficaOdmRepository extends JpaRepository<AnagraficaOdm, Long> {

	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE s.idRichiesta = :idRichiesta AND"
			   + " a.idQualifica = 1")
	SezioneSecDomOdmProjection findByIdProjectionSezSecOdm(@Param("idRichiesta") Long idRichiesta);
	
//	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE"
//			   + " a.idAnagrafica = :idAnagrafica")
//	Optional<AnagraficaOdm> findByIdDto(@Param("idAnagrafica") Long idAnagrafica);
	
	@Query("SELECT a FROM AnagraficaOdm a WHERE a.idAnagrafica = :idAnagrafica")
	AnagraficaOdmSezSecProjection findByidAnagraficaProj(Long idAnagrafica);

	Optional<AnagraficaOdm> findByidAnagrafica(Long idAnagrafica);
	
	@Query("SELECT s.idAnagrafica, a.idQualifica, a.nome, a.cognome, a.codiceFiscale," +
			   "m.completato, m.validato, m.annullato " + 
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 29 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta AND a.idQualifica = :idQualifica")
	Optional<Object[]> findForSelectAutocertReqOno(@Param("idRichiesta") Long idRichiesta, @Param("idQualifica") Long idQualifica);	
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE "
			+ "s.idRichiesta = :idRichiesta AND a.idQualifica = :idQualifica")
	Optional<AnagraficaOdm> findByIdRichiestaAndIdQualifica(@Param("idRichiesta") Long idRichiesta, @Param("idQualifica") Long idQualifica);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE s.idRichiesta = :idRichiesta "
			+ "AND a.idQualifica = 1 AND a.codiceFiscale IN (SELECT a2.codiceFiscale FROM AnagraficaOdm a2 GROUP BY a2.codiceFiscale "
			+ "HAVING COUNT(a2.codiceFiscale) > 1)")
	// IN QUESTO METODO VERRA' PRESOLO SOLO IL RAP.LEGALE SE CLONATO
	Optional<AnagraficaOdm> findByIdForAntScheMedLegAndOrgOnlyClone(@Param("idRichiesta") Long idRichiesta);
		
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE s.idRichiesta = :idRichiesta AND"
			   + " a.idQualifica = 1")
	Optional<AnagraficaOdm> getAnagraficaRapLegaleByIdRichiesta(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE "
			+ "s.idRichiesta = :idRichiesta")
	List<AnagraficaOdm> findByIdRichiesta(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE "
			+ "s.idRichiesta = :idRichiesta AND (a.idQualifica = :idQualifica OR a.idQualifica = :idQualifica2) "
			+ "ORDER BY a.idQualifica")
	List<AnagraficaOdm> findByIdRichiestaAndTwoIdQualifica(@Param("idRichiesta") Long idRichiesta, @Param("idQualifica") Long idQualifica, 
															   @Param("idQualifica2") Long idQualifica2);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE "
			+ "s.idRichiesta = :idRichiesta AND (a.idQualifica = 1 OR a.idQualifica = 2)")
	List<AnagraficaOdm> findAllByIdRichiestaForAntScheMedLegAndOrg(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE s.idRichiesta = :idRichiesta AND"
			   + " (a.idQualifica != 1 AND a.idQualifica != 2)")
	List<AnagraficaOdm> findAllByIdRichiestaForCompOrgAm(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE "
			+ "s.idRichiesta = :idRichiesta AND a.idQualifica = :idQualifica")
	List<AnagraficaOdm> findAllByIdRichiestaAndIdQualifica(@Param("idRichiesta") Long idRichiesta, @Param("idQualifica") Long idQualifica);
	
	//idTipoAnagrafica
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE "
		   + "s.idRichiesta = :idRichiesta AND a.idTipoAnagrafica = :idTipoAnagrafica")
	List<AnagraficaOdm> findAllByIdRichiestaAndIdTipoAnagrafica(@Param("idRichiesta") Long idRichiesta, @Param("idTipoAnagrafica") Long idTipoAnagrafica);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE "
		       + "s.idRichiesta = :idRichiesta AND (a.idTipoAnagrafica = :idTipoAnagrafica OR "
		       + "a.idTipoAnagrafica = :idTipoAnagrafica2 OR a.idTipoAnagrafica = :idTipoAnagrafica3)")
	List<AnagraficaOdm> findAllByIdRichiestaAndFor3IdTipoAnagrafica(
	    @Param("idRichiesta") Long idRichiesta,
	    @Param("idTipoAnagrafica") Long idTipoAnagrafica,
	    @Param("idTipoAnagrafica2") Long idTipoAnagrafica2,
	    @Param("idTipoAnagrafica3") Long idTipoAnagrafica3);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica " +
	       "WHERE s.idRichiesta = :idRichiesta AND (a.idQualifica != 1 AND a.idQualifica != 2) " + 
	       "ORDER BY a.idQualifica")
	List<AnagraficaOdm> getAllAnagraficaByIdRichiestaCompOrgAm(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE "
			+ "s.idRichiesta = :idRichiesta AND (a.idQualifica = 1 OR a.idQualifica = 2)")
	List<AnagraficaOdm> findAllByIdRichiestaForRapLAndOrg(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica " +
		       "WHERE s.idRichiesta = :idRichiesta AND " +
		       "(a.idQualifica != 1 AND a.idQualifica != 2) " + 
		       "ORDER BY a.idQualifica")
	List<AnagraficaOdm> getAllAnagraficaByIdRichiestaForEleCompOrgAm(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT s.idAnagrafica, a.idQualifica, a.nome, a.cognome, a.codiceFiscale," +
			   "m.completato, m.validato, m.annullato " + 
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 29 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta AND a.idQualifica = :idQualifica " +
		       "ORDER BY a.idQualifica")
	List<Object[]> findAllForSelectAutocertReqOno(@Param("idRichiesta") Long idRichiesta, @Param("idQualifica") Long idQualifica);
	
	@Query("SELECT s.idAnagrafica, a.idQualifica, a.nome, a.cognome, a.codiceFiscale," +
			   "m.completato, m.validato, m.annullato " + 
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 32 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta AND (a.idQualifica != 1 AND a.idQualifica != 2) " +
		       "ORDER BY a.idQualifica")
	List<Object[]> findAllForSelectAutocertReqOnoForCompOrgAm(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a " +
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 4 " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public List<AnagraficaOdm> findAllAnagraficaMediatoriA(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a " +
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 5 " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public List<AnagraficaOdm> findAllAnagraficaMediatoriB(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT a " +
			   "FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 6 " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public List<AnagraficaOdm> findAllAnagraficaMediatoriC(@Param("idRichiesta") Long idRichiesta);
	
	@Query(value = "SELECT * FROM anagrafiche a "
			+ "JOIN tipo_anagrafica ta ON a.id_tipo_anagrafica = ta.id_tipo_anagrafica "
			+ "WHERE a.id_tipo_anagrafica = 4 "
			+ "OR a.id_tipo_anagrafica = 5 "
			+ "OR a.id_tipo_anagrafica = 6 ",  nativeQuery = true)
	public List<Object[]> getAllAnagraficaMediatori();
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 4 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 39 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllAutocertificazioneMediatoriA(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 5 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 44 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllAutocertificazioneMediatoriB(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 6 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 53 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllAutocertificazioneMediatoriC(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 4 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 40 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllDicDispMediatoriA(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 5 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 45 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllDicDispMediatoriB(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 6 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 54 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllDicDispMediatoriC(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 4 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 41 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllFormazIniMediatoriA(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 5 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 46 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllFormazIniMediatoriB(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 6 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 55 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllFormazIniMediatoriC(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 5 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 77 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllFormazSpeciMediatoriB(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 6 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 80 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllFormazSpeciMediatoriC(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 4 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 75 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllUlteReqMediatoriA(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 5 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 78 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllUlteReqMediatoriB(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 6 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 81 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllUlteReqMediatoriC(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT DISTINCT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 5 AND a.lingueStraniere IS NOT NULL  " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 50 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllCertificaMediatoriB(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT DISTINCT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
		       "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
		       "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 6 AND a.lingueStraniere IS NOT NULL  " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 82 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllCertificaMediatoriC(@Param("idRichiesta") Long idRichiesta, Pageable pageable); 
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato, a.medNumeroOrganismiDisp FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 4 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 38 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllAnagraficaMediatoriA(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato, a.medNumeroOrganismiDisp FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 5 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 43 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllAnagraficaMediatoriB(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato, a.medNumeroOrganismiDisp FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 6 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 52 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta " +
		       "ORDER BY a.idQualifica")
	public Page<Object[]> getAllAnagraficaMediatoriC(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.nome, a.cognome, a.codiceFiscale, a.sesso, a.dataNascita, a.idComuneNascita," +
			   "a.comuneNascitaEstero, a.idTipoAnagrafica, a.medCellulare, a.medTelefono, a.medFax, a.idComuneResidenza, " +
			   "m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica AND a.idTipoAnagrafica = 3 " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 36 AND m.idRichiesta = :idRichiesta " +
		       "WHERE s.idRichiesta = :idRichiesta ")
	Page<Object[]> getAllAnagraficaPrestatori(@Param("idRichiesta") Long idRichiesta, Pageable pageable);

	@Query(value = " SELECT * " + " FROM anagrafiche"
			+ " WHERE LOWER(COGNOME) LIKE :searchText% OR UPPER(COGNOME) LIKE :searchText%", nativeQuery = true)
	Page<AnagraficaOdm> getAllAnagraficaByCognome(@Param("searchText") String searchText, Pageable pageable);

	@Query(value = " SELECT * " + " FROM anagrafiche"
			+ " WHERE LOWER(CODICE_FISCALE) LIKE :searchText% OR UPPER(CODICE_FISCALE) LIKE :searchText%", nativeQuery = true)
	Page<AnagraficaOdm> getAllAnagraficaByCF(@Param("searchText") String searchText, Pageable pageable);

	@Query(value = " SELECT * " + " FROM anagrafiche" + " WHERE ID_TIPO_ANAGRAFICA = :tipoMed", nativeQuery = true)
	Page<AnagraficaOdm> getAllAnagraficaByTipoMed(@Param("tipoMed") Long tipoMed, Pageable pageable);

	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE s.idRichiesta = :idRichiesta")
	Page<ElencoRappresentantiProjection> getAllAnagraficaByIdRichiesta(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica "
		   + "WHERE s.idRichiesta = :idRichiesta AND a.idTipoAnagrafica = :idTipoAnagrafica")
	Page<MediatoreProjection> getAllMediatoriForIdTipoAnagrafica(@Param("idRichiesta") Long idRichiesta, 
															  @Param("idTipoAnagrafica") Long idTipoAnagrafica, Pageable pageable);
	
	// LA VERIFICA DELLO STATUS DELLO STATO MODULO VERRA' FATTA SOLO SUL DOCUMENTO ESSENDO I MODULI DI RAPP L E RESP.ORG 
	// STRETTAMENTE DIPEDENTI DALLO STESSO STATO
	@Query("SELECT s.idAnagrafica, a.codiceFiscale, a.cognome, a.nome, a.idQualifica, m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 28 " +
		       "WHERE s.idRichiesta = :idRichiesta AND " +
		       "(a.idQualifica = 1 OR a.idQualifica = 2) " + 
		       "ORDER BY a.idQualifica")
	Page<Object[]> getAllAnagraficaByIdRichiestaForRapLegAndRespOrg(@Param("idRichiesta") Long idRichiesta, Pageable pageable);
	
	@Query("SELECT s.idAnagrafica, a.codiceFiscale, a.cognome, a.nome, a.idQualifica, m.completato, m.validato, m.annullato FROM SoggettoRichiesta s " +
		       "JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica " +
		       "LEFT JOIN StatoModuliRichiestaFigli m ON m.idAnagrafica = a.idAnagrafica AND m.idModulo = 31" +
		       "WHERE s.idRichiesta = :idRichiesta AND " +
		       "(a.idQualifica != 1 AND a.idQualifica != 2) " + 
		       "ORDER BY a.idQualifica")
	Page<Object[]> getAllAnagraficaByIdRichiestaForEleCompOrgAm(@Param("idRichiesta") Long idRichiesta, Pageable pageable);

	@Query("SELECT a FROM SoggettoRichiesta s JOIN AnagraficaOdm a ON s.idAnagrafica = a.idAnagrafica WHERE s.idRichiesta = :idRichiesta AND"
			   + " a.codiceFiscale = :codiceFiscale AND a.idQualifica = 2")
	ElencoRappresentantiProjection getAnagraficaCloneRespOrg(@Param("idRichiesta") Long idRichiesta, @Param("codiceFiscale") String codiceFiscale);
}
