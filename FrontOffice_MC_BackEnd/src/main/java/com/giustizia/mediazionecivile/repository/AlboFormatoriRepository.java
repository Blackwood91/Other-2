package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboEfSedi;
import com.giustizia.mediazionecivile.model.AlboFormatori;
import com.giustizia.mediazionecivile.projection.SezioneSecDomOdmProjection;

public interface AlboFormatoriRepository extends JpaRepository<AlboFormatori, Long> {
	
	@Query(value = " SELECT * "
    		+  " FROM albo_formatori"
    		+  " WHERE LOWER(NOME) LIKE :searchText% OR UPPER(NOME) LIKE :searchText%", nativeQuery = true)
	Page<AlboFormatori> getAllFormatoriByNome(@Param("searchText") String searchText, Pageable pageable );
	
	@Query(value = " SELECT * "
    		+  " FROM albo_formatori"
    		+  " WHERE LOWER(COGNOME) LIKE :searchText% OR UPPER(COGNOME) LIKE :searchText%", nativeQuery = true)
	Page<AlboFormatori> getAllFormatoriByCognome(@Param("searchText") String searchText, Pageable pageable );
	
	@Query(value = " SELECT * "
    		+  " FROM albo_formatori"
    		+  " WHERE LOWER(CODICE_FISCALE) LIKE :searchText% OR UPPER(CODICE_FISCALE) LIKE :searchText%", nativeQuery = true)
	Page<AlboFormatori> getAllFormatoriByCF(@Param("searchText") String searchText, Pageable pageable );
	
	@Query(value = " SELECT * "
    		+  " FROM albo_formatori"
    		+  " WHERE ID_TIPO_FORMATORE = :tipoForm", nativeQuery = true)
	Page<AlboFormatori> getAllFormatoriByTipoForm(@Param("tipoForm") Long tipoForm, Pageable pageable );
	
	@Query(value = " SELECT aef.num_reg, aef.denominazione, aef.sito_web, "
			+ " natura, cancellato, aef.data_cancellazione, af.nome, af.cognome, af.codice_fiscale, aefs.email"
			+ " FROM ALBO_FORMATORI af JOIN ALBO_FORMATORI_EF afef ON af.codice_fiscale = afef.codice_fiscale_formatore"
			+ " JOIN ALBO_EF aef ON afef.num_reg = aef.num_reg JOIN EF_RICHIESTE efr ON aef.denominazione = efr.denominazione_odm"
			+ " JOIN ALBO_EF_SEDI aefs on aef.num_reg = aefs.num_reg"
			+ " WHERE id_albo_formatori = :idAlboFormatori", nativeQuery = true)
	public List<Object[]> findByIdAlboFormatori(@Param("idAlboFormatori")Long idAlboFormatori);

}
