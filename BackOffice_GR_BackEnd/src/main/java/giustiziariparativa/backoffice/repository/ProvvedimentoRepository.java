package giustiziariparativa.backoffice.repository;

import java.sql.Blob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import giustiziariparativa.backoffice.model.Provvedimento;
import jakarta.transaction.Transactional;

public interface ProvvedimentoRepository extends JpaRepository<Provvedimento, Long> {
	
	@Modifying
	@Query(value= "INSERT INTO provvedimento( provvedimento ) VALUES(  :filePdf )", nativeQuery = true)
	@Transactional
    void caricaPdf(@Param("filePdf")byte[] provvedimentoPdfBytes);	   
	
	@Query(value = "SELECT NVL(MAX(ID_PROVVEDIMENTO), 0) + 1 FROM Provvedimento", nativeQuery = true) 
    public Long getMaxId();
	
}
