package giustiziariparativa.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import giustiziariparativa.backoffice.model.RuoloUtente;

public interface RuoloUtenteRepository extends JpaRepository<RuoloUtente, Long> {
    
}
