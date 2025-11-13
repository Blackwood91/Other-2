package com.giustizia.mediazionecivile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.Sede;

public interface SedeRepository extends JpaRepository<Sede, Long> {
		@Query(value="SELECT " + 
	            "SEDI.ID_SEDE, " + 
	            "SEDI.ID_RICHIESTA, " + 
	            "SEDI.SEDE_LEGALE, " + 
	            "SEDI.INDIRIZZO, " + 
	            "SEDI.NUMERO_CIVICO, " + 
	            "SEDI.CAP, " + 
	            "SEDI.ID_COMUNE, " + 
	            "SEDI.TELEFONO, " + 
	            "SEDI.FAX, " + 
	            "SEDI.EMAIL, " + 
	            "SEDI.PEC, " + 
	            "SEDI.ID_TITOLO_DETENZIONE, " + 
	            "SEDI.NUM_ROM_SOGG_ACCORDO, " + 
	            "SEDI.DATA_CONTRATTO, " + 
	            "SEDI.REGISTRAZIONE, " + 
	            "SEDI.DURATA_CONTRATTO, " + 
	            "SEDI.STRUTTURA_ORG_SEGRETERIA, " + 
	            "SEDI.DATA_INSERIMENTO_SEDE, " + 
	            "COMUNI.CODICE_COMUNE, " + 
	            "COMUNI.CODICE_REGIONE, " + 
	            "COMUNI.CODICE_PROVINCIA, " + 
	            "COMUNI.NOME_COMUNE, " + 
	            "COMUNI.ISTAT, " + 
	            "COMUNI.CAPOLUOGO_PROVINCIA, " + 
	            "COMUNI.CODICE_CATASTALE, " + 
	            "regioni_province.ID_REGIONI_PROVINCE, " + 
	            "regioni_province.CODICE_REGIONE, " + 
	            "regioni_province.CODICE_PROVINCIA, " + 
	            "regioni_province.NOME_REGIONE, " + 
	            "regioni_province.NOME_PROVINCIA, " + 
	            "regioni_province.SIGLA_PROVINCIA, " + 
	            "SEDI_DETENZIONE_TITOLO.DESCRIZIONE, " + 
	            "STATO_MODULI_RICHIESTA_FIGLI.COMPLETATO, " +
	            "STATO_MODULI_RICHIESTA_FIGLI.VALIDATO, " +
	            "STATO_MODULI_RICHIESTA_FIGLI.ANNULLATO " +
	            "FROM SEDI " + 
	            "LEFT JOIN COMUNI ON SEDI.ID_COMUNE = COMUNI.CODICE_COMUNE " + 
	            "LEFT JOIN regioni_province ON COMUNI.CODICE_PROVINCIA = regioni_province.CODICE_PROVINCIA " + 
	            "LEFT JOIN SEDI_DETENZIONE_TITOLO ON SEDI.ID_TITOLO_DETENZIONE = SEDI_DETENZIONE_TITOLO.ID_TITOLO_DETENZIONE " + 
	            "LEFT JOIN STATO_MODULI_RICHIESTA_FIGLI ON STATO_MODULI_RICHIESTA_FIGLI.ID_RIFERIMENTO = SEDI.ID_SEDE AND " + 
	            "STATO_MODULI_RICHIESTA_FIGLI.ID_MODULO = 68 AND STATO_MODULI_RICHIESTA_FIGLI.ID_RICHIESTA = :idRichiesta " +
	            "WHERE SEDI.ID_RICHIESTA = :idRichiesta " +
	            "ORDER BY SEDI.SEDE_LEGALE DESC", nativeQuery = true)
	Page<Object[]> findByRichiestaId(Pageable pageable, @Param("idRichiesta") Long idRichiesta);

	@Query(value = " SELECT s FROM Sede s WHERE s.idRichiesta = :idRichiesta AND sedeLegale = :sedeLegale")
	Sede findSedeLegale(Long idRichiesta, char sedeLegale);
	
	List<Sede> findByIdRichiesta(Long idRichiesta);

	List<Sede> findByIdRichiestaAndSedeLegale(Long idRichiesta, char sedeLegale);

	boolean existsByIdRichiestaAndSedeLegale(Long idRichiesta, char sedeLegale);
}
