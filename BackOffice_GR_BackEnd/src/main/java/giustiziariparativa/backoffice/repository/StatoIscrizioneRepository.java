package giustiziariparativa.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import giustiziariparativa.backoffice.model.StatoIscrizione;

public interface StatoIscrizioneRepository extends JpaRepository<StatoIscrizione, Long> {

	// Da spostare in StoricoStatoRepository
	@Query(value = "SELECT NVL(MAX(ID_STORICO_STATO), 0) + 1 FROM storico_stato_mediatore ", nativeQuery = true) 
    public Long getMaxId();
	
}
