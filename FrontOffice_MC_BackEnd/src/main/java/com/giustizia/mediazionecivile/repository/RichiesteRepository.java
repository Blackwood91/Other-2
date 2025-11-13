package com.giustizia.mediazionecivile.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.dto.SezioneQuartaDomOdmDto;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.projection.PolizzaAssicurativaProjection;
import com.giustizia.mediazionecivile.projection.RichiestaDomIscProjection;
import com.giustizia.mediazionecivile.projection.RichiestaProjection;
import com.giustizia.mediazionecivile.projection.SezionePrimaDOMODMProjection;
import com.giustizia.mediazionecivile.projection.SezioneQuartaDomOdmProjection;

public interface RichiesteRepository extends JpaRepository<Richiesta, Long> {
	
	//List<RichiestaSezPrimaProjection> findAllBy();
	
	@Query("SELECT r.idRichiesta, r.dataRichiesta, r.dataIscrAlbo, " +
		   "r.idTipoRichiesta, r.idTipoRichiedente, r.denominazioneOdm, " +
		   "r.idStato, st.descrizione " +
		   "FROM Richiesta r " + 
		   "LEFT JOIN Stato st ON (st.id = r.idStato AND r.idStato IS NOT NULL) " +
		   "WHERE r.idRichiesta = :idRichiesta ")
	Object[] findByIdRichiestaForRichiestaSocieta(@Param("idRichiesta") Long idRichiesta);

	@Query("SELECT r FROM Richiesta r WHERE r.idRichiesta = :idRichiesta")
	RichiestaDomIscProjection findProjectiondDomByIdRichiesta(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT r FROM Richiesta r WHERE r.idRichiesta = :idRichiesta")
	SezionePrimaDOMODMProjection findSezionePrimaDOMODMByIdRichiesta(@Param("idRichiesta") Long idRichiesta);
	
	@Query("SELECT r FROM Richiesta r WHERE r.idRichiesta = :idRichiesta")
	Optional<PolizzaAssicurativaProjection> findPolizzaAssicurativaDOMODMByIdRichiesta(@Param("idRichiesta") Long idRichiesta);

}
