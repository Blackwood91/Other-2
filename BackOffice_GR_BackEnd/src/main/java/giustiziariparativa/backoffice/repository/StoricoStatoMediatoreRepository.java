package giustiziariparativa.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import giustiziariparativa.backoffice.model.StoricoStatoMediarore;

public interface StoricoStatoMediatoreRepository extends JpaRepository<StoricoStatoMediarore, Long>{
 
	@Query(value = "SELECT NVL(MAX(ID_STORICO_STATO), 0) + 1 FROM storico_stato_mediatore ", nativeQuery = true) 
    public Long getMaxId();
	
	
}
