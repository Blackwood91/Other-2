package giustiziariparativa.backoffice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import giustiziariparativa.backoffice.DTO.EnteAttestatoSelect;
import giustiziariparativa.backoffice.model.EnteAttestato;
import jakarta.transaction.Transactional;

public interface EnteAttestatoRepository extends JpaRepository<EnteAttestato, Long> {
    
	EnteAttestato findFirstByEnteAttestatoAndTipologiaEnteAndIsConvenzionato(String enteAttestato, String tipologiaEnte, int isConvenzionato);

    @Query(value = "SELECT id_ente, ente_attestato, tipologia, convenzionato FROM ente_attestato", nativeQuery = true)
    List<Object[]> getAllEnte();
    
    @Query(value = "SELECT id_ente, ente_attestato, tipologia, convenzionato FROM ente_attestato where ID_TIPOLOGIA_ENTE_FORMATORE = :idEnte", nativeQuery = true)
    List<Object[]> getAllEntiFormatore(@Param("idEnte") Long idEnte);
    
    @Query(value = "Select ID_ENTE from ente_attestato where ID_ENTE = :idEnte", nativeQuery = true)
    String findByIdEnte(Long idEnte); 
    
	@Query(value = "SELECT NVL(MAX(id_ente), 0) + 1 FROM ente_attestato", nativeQuery = true) 
    public Long getMaxId();
    
	@Modifying    
    @Query(value = " UPDATE ente_attestato "
	    		 + " SET "
	    		 + "    convenzionato = :convenzionato, "
	    		 + "    ente_attestato = :nomeEnte, "
	    		 + "    tipologia = :tipologia, "
	    		 + "    data_modifica = sysdate "
	    		 + " WHERE "
	    		 + "    id_ente = :idEnte  ", nativeQuery = true)
    @Transactional
   	void updateEnte(@Param("convenzionato") int convenzionato, @Param("idEnte") Long idEnte, 
   					@Param("nomeEnte") String nomeEnte, @Param("tipologia") String tipologia);
   	

}
