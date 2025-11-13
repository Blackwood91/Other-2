package com.giustizia.mediazionecivile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giustizia.mediazionecivile.model.SoggettoRichiesta;


public interface SoggettoRichiestaRepository extends JpaRepository<SoggettoRichiesta, Long> {
	Optional<SoggettoRichiesta> findByIdRichiesta(Long idRichiesta);
	
	Optional<SoggettoRichiesta> findByIdRichiestaAndIdAnagrafica(Long idRichiesta, Long idAnagrafica);
	
	List<SoggettoRichiesta> findAllByIdRichiesta(Long idRichiesta);
}
