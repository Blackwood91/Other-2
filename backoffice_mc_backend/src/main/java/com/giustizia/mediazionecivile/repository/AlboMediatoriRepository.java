package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboMediatori;

public interface AlboMediatoriRepository extends JpaRepository<AlboMediatori, Long> {
	
	@Query(value = " SELECT * "
    		+  " FROM albo_mediatori"
    		+  " WHERE LOWER(NOME) LIKE :searchText% OR UPPER(NOME) LIKE :searchText%", nativeQuery = true)
	Page<AlboMediatori> getAllAlboMediatoriByNome(@Param("searchText") String searchText, Pageable pageable );
	
	@Query(value = " SELECT * "
    		+  " FROM albo_mediatori"
    		+  " WHERE LOWER(COGNOME) LIKE :searchText% OR UPPER(COGNOME) LIKE :searchText%", nativeQuery = true)
	Page<AlboMediatori> getAllAlboMediatoriByCognome(@Param("searchText") String searchText, Pageable pageable );
	
	@Query(value = " SELECT * "
    		+  " FROM albo_mediatori"
    		+  " WHERE LOWER(CODICE_FISCALE) LIKE :searchText% OR UPPER(CODICE_FISCALE) LIKE :searchText%", nativeQuery = true)
	Page<AlboMediatori> getAllAlboMediatoriByCF(@Param("searchText") String searchText, Pageable pageable );
	
	@Query(value = " SELECT * "
    		+  " FROM albo_mediatori"
    		+  " WHERE ID_TIPO_ANAGRAFICA = :tipoMed", nativeQuery = true)
	Page<AlboMediatori> getAllAlboMediatoriByTipoMed(@Param("tipoMed") Long tipoMed, Pageable pageable );
	
	@Query(value = "SELECT aodm.rom, aodm.denominazione_organismo, aodm.sito_web, natura_organismo, cancellato, aodm.data_cancellazione, "
			+ " am.nome, am.cognome, am.codice_fiscale, aodms.email FROM ALBO_MEDIATORI am "
			+ " JOIN ALBO_MEDIATORI_ODM amodm ON am.codice_fiscale = amodm.codice_fiscale_mediatore "
			+ " JOIN ALBO_ODM aodm ON amodm.rom = aodm.rom JOIN RICHIESTE odmr ON aodm.denominazione_organismo = odmr.denominazione_odm"
			+ " JOIN ALBO_ODM_SEDI aodms on aodm.rom = aodms.rom"
			+ " WHERE id_albo_mediatore = :idAlboMediatori", nativeQuery = true)
	public List<Object[]> findByIdAlboMediatori(@Param("idAlboMediatori")Long idAlboMediatori);

}
