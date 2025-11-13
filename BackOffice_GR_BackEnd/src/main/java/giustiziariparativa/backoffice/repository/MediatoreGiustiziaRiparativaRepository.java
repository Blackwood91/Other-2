package giustiziariparativa.backoffice.repository;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import giustiziariparativa.backoffice.model.MediatoreGiustiziaRiparativa;
import jakarta.transaction.Transactional;

public interface MediatoreGiustiziaRiparativaRepository extends JpaRepository<MediatoreGiustiziaRiparativa, Long> {

    @Query(value = "SELECT"
	    		+ "    		    	qualifica_formatore,"
	    		+ "    		    	data_iscrizione,"
	    		+ "    		    	data_nascita,"
	    		+ "    		    	ente_attestato.ente_attestato,"
	    		+ "    		    	mediatore_giustizia_riparativa.id_mediatore,"
	    		+ "    		    	stato_iscrizione.descrizione_stato AS stato,"
	    		+ "    		    	codice_fiscale,"
	    		+ "    		    	cognome_mediatore,"
	    		+ "    		    	indirizzo"
	    		+ "    		    	|| ', '"
	    		+ "    		    	|| nvl(numero_civico, 0)"
	    		+ "    		    	|| ' '"
	    		+ "    		    	|| nvl(cap, '')"
	    		+ "    		    	|| ' '"
	    		+ "    		    	|| nvl(citta_residenza, '')"
	    		+ "    		    	|| ' ('"
	    		+ "    		    	|| nvl(provicina_residenza, '')"
	    		+ "    		    	|| ')'                             AS indirizzo,"
	    		+ "    		    	indirizzo_pec,"
	    		+ "    		    	luogo_nascita,"
	    		+ "    		    	nome_mediatore,"
	    		+ "    		    	numero_iscrizione,"
	    		+ "    		    	requisiti_iscrizione,"
	    		+ "    		    	provvedimento.provvedimento, "
	    		+ "    		  	mediatore_giustizia_riparativa.indirizzo as indirizzo1, "
	    		+ "    		    	mediatore_giustizia_riparativa.numero_civico, "
	    		+ "    		    	mediatore_giustizia_riparativa.citta_residenza,"
	    		+ "    		   	mediatore_giustizia_riparativa.provicina_residenza,"
	    		+ "    		   	stato_iscrizione_id , "
	    		+ "    		    	mediatore_giustizia_riparativa.cap, "
	    		+ "    		    	mediatore_giustizia_riparativa.ente_attestato_id,"
	    		+ "    				to_char(storico_stato_mediatore.data_stato,'DD/MM/YY'),  "
	    		+ "    				TO_char(storico_stato_mediatore.data_fine,'DD/MM/YY'),  "
	    		+ "    	    		storico_stato_mediatore.tipologia, "
	    		+ "    	    		storico_stato_mediatore.motivazione, "
	    		+ "    	         storico_stato_mediatore.id_stato as id_stato_mediatore, "
	    		+ "    	        	provvedimento.data_provvedimento,"
	    		+ "                    ente_attestato.tipologia as tipologiaEnte,"
	    		+ "                    ente_attestato.convenzionato as isConvenzionato,                  "
	    		+ "                    storico_stato_mediatore.tipologia as tipologiaStato,"
	    		+ "                    storico_stato_mediatore.motivazione as motivazioneStato, "
	    		+ "					ente_attestato.id_tipologia_ente_formatore,t_ente_formatore.descrizione_ente "
	    		+ "    		 FROM "
	    		+ " mediatore_giustizia_riparativa "
				+ " inner JOIN storico_stato_mediatore ON mediatore_giustizia_riparativa.id_mediatore = storico_stato_mediatore.id_mediatore"
				+ "             AND mediatore_giustizia_riparativa.stato_iscrizione_id = storico_stato_mediatore.id_storico_stato "
				+ "  inner JOIN stato_iscrizione ON storico_stato_mediatore.id_stato = stato_iscrizione.id_stato "
				+ " LEFT JOIN provvedimento ON storico_stato_mediatore.id_provvedimento = provvedimento.id_provvedimento "
				+ " Left join ente_attestato on ente_attestato.id_ente=mediatore_giustizia_riparativa.ente_attestato_id "
				+ "	Left join t_ente_formatore on t_ente_formatore.id_tipologia_ente_formatore = ente_attestato.id_tipologia_ente_formatore "
			    //+ " order by numero_iscrizione , data_iscrizione "	    	
				+ "	ORDER BY TO_NUMBER(mediatore_giustizia_riparativa.numero_iscrizione) ASC, mediatore_giustizia_riparativa.data_iscrizione ASC "    		
	    		, nativeQuery = true)
	public Page<Object[]> findCustomFields(String searchText, Pageable pageable);
    
	@Query(value = "SELECT"
    		+ "    		    	qualifica_formatore,"
    		+ "    		    	data_iscrizione,"
    		+ "    		    	data_nascita,"
    		+ "    		    	ente_attestato.ente_attestato,"
    		+ "    		    	mediatore_giustizia_riparativa.id_mediatore,"
    		+ "    		    	stato_iscrizione.descrizione_stato AS stato,"
    		+ "    		    	codice_fiscale,"
    		+ "    		    	cognome_mediatore,"
    		+ "    		    	indirizzo"
    		+ "    		    	|| ', '"
    		+ "    		    	|| nvl(numero_civico, 0)"
    		+ "    		    	|| ' '"
    		+ "    		    	|| nvl(cap, '')"
    		+ "    		    	|| ' '"
    		+ "    		    	|| nvl(citta_residenza, '')"
    		+ "    		    	|| ' ('"
    		+ "    		    	|| nvl(provicina_residenza, '')"
    		+ "    		    	|| ')'                             AS indirizzo,"
    		+ "    		    	indirizzo_pec,"
    		+ "    		    	luogo_nascita,"
    		+ "    		    	nome_mediatore,"
    		+ "    		    	numero_iscrizione,"
    		+ "    		    	requisiti_iscrizione,"
    		+ "    		    	provvedimento.provvedimento, "
    		+ "    		  	mediatore_giustizia_riparativa.indirizzo as indirizzo1, "
    		+ "    		    	mediatore_giustizia_riparativa.numero_civico, "
    		+ "    		    	mediatore_giustizia_riparativa.citta_residenza,"
    		+ "    		   	mediatore_giustizia_riparativa.provicina_residenza,"
    		+ "    		   	stato_iscrizione_id , "
    		+ "    		    	mediatore_giustizia_riparativa.cap, "
    		+ "    		    	mediatore_giustizia_riparativa.ente_attestato_id,"
    		+ "    				to_char(storico_stato_mediatore.data_stato,'DD/MM/YY'),  "
    		+ "    				TO_char(storico_stato_mediatore.data_fine,'DD/MM/YY'),  "
    		+ "    	    		storico_stato_mediatore.tipologia, "
    		+ "    	    		storico_stato_mediatore.motivazione, "
    		+ "    	         storico_stato_mediatore.id_stato as id_stato_mediatore, "
    		+ "    	        	provvedimento.data_provvedimento,"
    		+ "                    ente_attestato.tipologia as tipologiaEnte,"
    		+ "                    ente_attestato.convenzionato as isConvenzionato,                  "
    		+ "                    storico_stato_mediatore.tipologia as tipologiaStato, "
    		+ "                    storico_stato_mediatore.motivazione as motivazioneStato, "
    		+"					ente_attestato.id_tipologia_ente_formatore,t_ente_formatore.descrizione_ente "
    		+ "    		 FROM "
    		+ " 		mediatore_giustizia_riparativa "
			+ " 		inner JOIN storico_stato_mediatore ON mediatore_giustizia_riparativa.id_mediatore = storico_stato_mediatore.id_mediatore"
			+ "         AND mediatore_giustizia_riparativa.stato_iscrizione_id = storico_stato_mediatore.id_storico_stato "
			+ "  		inner JOIN stato_iscrizione ON storico_stato_mediatore.id_stato = stato_iscrizione.id_stato "
			+ " 		LEFT JOIN provvedimento ON storico_stato_mediatore.id_provvedimento = provvedimento.id_provvedimento "
			+ " 		Left join ente_attestato on ente_attestato.id_ente=mediatore_giustizia_riparativa.ente_attestato_id "
			+ "    		Left join t_ente_formatore on t_ente_formatore.id_tipologia_ente_formatore = ente_attestato.id_tipologia_ente_formatore "
			+ "			WHERE LOWER(nome_mediatore) LIKE %:searchText% OR UPPER(nome_mediatore) LIKE %:searchText% "
			+ "			OR LOWER(cognome_mediatore) LIKE %:searchText% OR UPPER(cognome_mediatore) LIKE %:searchText% "
			+ " 		OR LOWER(codice_fiscale) LIKE %:searchText% OR UPPER(codice_fiscale) LIKE %:searchText% "
			+ " 		order by numero_iscrizione, data_iscrizione "
			, nativeQuery = true)
public Page<Object[]>  findCustomFieldsByText(@Param("searchText") String searchText, Pageable pageable);
	
@Query(value = "SELECT"
		+ "    		    	qualifica_formatore,"
		+ "    		    	data_iscrizione,"
		+ "    		    	data_nascita,"
		+ "    		    	ente_attestato.ente_attestato,"
		+ "    		    	mediatore_giustizia_riparativa.id_mediatore,"
		+ "    		    	stato_iscrizione.descrizione_stato AS stato,"
		+ "    		    	codice_fiscale,"
		+ "    		    	cognome_mediatore,"
		+ "    		    	indirizzo"
		+ "    		    	|| ', '"
		+ "    		    	|| nvl(numero_civico, 0)"
		+ "    		    	|| ' '"
		+ "    		    	|| nvl(cap, '')"
		+ "    		    	|| ' '"
		+ "    		    	|| nvl(citta_residenza, '')"
		+ "    		    	|| ' ('"
		+ "    		    	|| nvl(provicina_residenza, '')"
		+ "    		    	|| ')'                             AS indirizzo,"
		+ "    		    	indirizzo_pec,"
		+ "    		    	luogo_nascita,"
		+ "    		    	nome_mediatore,"
		+ "    		    	numero_iscrizione,"
		+ "    		    	requisiti_iscrizione,"
		+ "    		    	provvedimento.provvedimento, "
		+ "    		  	mediatore_giustizia_riparativa.indirizzo as indirizzo1, "
		+ "    		    	mediatore_giustizia_riparativa.numero_civico, "
		+ "    		    	mediatore_giustizia_riparativa.citta_residenza,"
		+ "    		   	mediatore_giustizia_riparativa.provicina_residenza,"
		+ "    		   	stato_iscrizione_id , "
		+ "    		    	mediatore_giustizia_riparativa.cap, "
		+ "    		    	mediatore_giustizia_riparativa.ente_attestato_id,"
		+ "    				to_char(storico_stato_mediatore.data_stato,'DD/MM/YY'),  "
		+ "    				TO_char(storico_stato_mediatore.data_fine,'DD/MM/YY'),  "
		+ "    	    		storico_stato_mediatore.tipologia, "
		+ "    	    		storico_stato_mediatore.motivazione, "
		+ "    	         storico_stato_mediatore.id_stato as id_stato_mediatore, "
		+ "    	        	provvedimento.data_provvedimento,"
		+ "                    ente_attestato.tipologia as tipologiaEnte,"
		+ "                    ente_attestato.convenzionato as isConvenzionato,                  "
		+ "                    storico_stato_mediatore.tipologia as tipologiaStato,"
		+ "                    storico_stato_mediatore.motivazione as motivazioneStato, "
		+ "					ente_attestato.id_tipologia_ente_formatore,t_ente_formatore.descrizione_ente "
		+ "    		 FROM "
		+ " mediatore_giustizia_riparativa "
		+ " inner JOIN storico_stato_mediatore ON mediatore_giustizia_riparativa.id_mediatore = storico_stato_mediatore.id_mediatore"
		+ "             AND mediatore_giustizia_riparativa.stato_iscrizione_id = storico_stato_mediatore.id_storico_stato "
		+ "  inner JOIN stato_iscrizione ON storico_stato_mediatore.id_stato = stato_iscrizione.id_stato "
		+ " LEFT JOIN provvedimento ON storico_stato_mediatore.id_provvedimento = provvedimento.id_provvedimento "
		+ " Left join ente_attestato on ente_attestato.id_ente=mediatore_giustizia_riparativa.ente_attestato_id "
		+ "	Left join t_ente_formatore on t_ente_formatore.id_tipologia_ente_formatore = ente_attestato.id_tipologia_ente_formatore "
		+ " where storico_stato_mediatore.id_stato in (1,4) "
		+ " order by numero_iscrizione desc, data_iscrizione desc "	    		
		, nativeQuery = true)
	public Page<Object[]> findMediatoriIscritti(String searchText, Pageable pageable);

	@Modifying
    @Query(value =    "    INSERT INTO mediatore_giustizia_riparativa ("
		    		+ "        nome_mediatore,"
		    		+ "        cognome_mediatore,"
		    		+ "        codice_fiscale,"
		    		+ "        numero_iscrizione,"
		    		+ "        data_iscrizione,"
		    		+ "        qualifica_formatore,"
		    		+ "        luogo_nascita,"
		    		+ "        data_nascita,"
		    		+ "        citta_residenza,"
		    		+ "        provicina_residenza,"
		    		+ "        indirizzo,"
		    		+ "        numero_civico,"
		    		+ "        cap,"
		    		+ "        indirizzo_pec,"
		    		+ "        requisiti_iscrizione,"
		    		+ "        stato_iscrizione_id," 
		    		+ "        data_inserimento"    				    		
		    		+ "    ) VALUES ("
		    		+ "        :nomeMediatore,"
		    		+ "        :cognomeMediatore,"
		    		+ "        :codiceFiscale,"
		    		+ "        :numIscrizione,"
		    		+ "        TO_DATE(:dataIscrizione,'DD/MM/YY'),"
		    		+ "        :qualificaFormatore,"    		
		    		+ "        :luogoNascita,"
		    		+ "        TO_DATE(:dataNascita,'DD/MM/YY'),"
		    		+ "        :cittaResidenza,"
		    		+ "        :provinciaResidenza,"
		    		+ "        :indirizzo,"
		    		+ "        :numCivico,"
		    		+ "        :cap,"
		    		+ "        :indirizzoPEC,"
		    		+ "        :requisitiIscrizione,"  
		    		+ "        :statoIscrizione,"
					+ "        sysdate "		 
		    		+ "    )", nativeQuery = true)		 

    @Transactional
	void inserimentoCSV(@Param("nomeMediatore") String nomeMediatore,@Param("cognomeMediatore") String cognomeMediatore,@Param("codiceFiscale") String codiceFiscale,
					    @Param("numIscrizione") String numIscrizione, @Param("dataIscrizione") String dataIscrizione, @Param("qualificaFormatore") int qualificaFormatore,
						@Param("luogoNascita") String luogoNascita,@Param("dataNascita") String dataIscrizione2, @Param("cittaResidenza") String cittaResidenza, 
						@Param("provinciaResidenza") String provinciaResidenza, @Param("indirizzo") String indirizzo, @Param("numCivico") String numCivico,
						@Param("cap") String cap, @Param("indirizzoPEC") String indirizzoPEC,@Param("requisitiIscrizione") String requisitiIscrizione, 
						@Param("statoIscrizione") int statoIscrizione);
	

	@Query(value = " SELECT codice_fiscale FROM mediatore_giustizia_riparativa WHERE codice_fiscale = :codiceFiscale", nativeQuery = true)
	public String findCodiceFiscale(@Param("codiceFiscale") String codiceFiscale);

	@Query(value = "SELECT MAX (data_modifica) FROM mediatore_giustizia_riparativa", nativeQuery = true)
	public String getDataUltimaModificaMax();
	

	@Query(value = "SELECT NVL(MAX(id_mediatore), 0) + 1 FROM mediatore_giustizia_riparativa", nativeQuery = true) 
    public Long getMaxId();
	
	@Query(value = "SELECT codice_fiscale from mediatore_giustizia_riparativa where codice_fiscale = :searchText", nativeQuery = true) 
	public String cercaCF(@Param("searchText") String searchText);
}  
