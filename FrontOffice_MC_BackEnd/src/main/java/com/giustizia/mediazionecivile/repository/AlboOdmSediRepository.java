package com.giustizia.mediazionecivile.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.AlboOdmSedi;

public interface AlboOdmSediRepository extends JpaRepository<AlboOdmSedi, Long> {

	@Query(value = "SELECT s.sedeLegale, s.indirizzo, c.nomeComune, s.cap, rp.nomeProvincia, rp.nomeRegione, s.telefono, s.fax, s.pec, s.email "
			+ "FROM Richiesta r "
			+ "JOIN Sede s ON r.idRichiesta = s.idRichiesta "
			+ "JOIN Comune c ON s.idComune = c.idCodComune "
			+ "JOIN RegioneProvince rp ON c.codiceRegione = rp.codiceRegione AND c.codiceProvincia = rp.codiceProvincia "
			+ "WHERE r.numIscrAlbo = :numIscrAlbo")
	public Page<Object[]> findByNumIscrAlbo(@Param("numIscrAlbo")Long numIscrAlbo, Pageable pageable); //creare Dto
}
