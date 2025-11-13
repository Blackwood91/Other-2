package com.giustizia.mediazionecivile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.giustizia.mediazionecivile.model.SocietaUtenti;

@Repository
public interface SocietaUtentiRepository extends JpaRepository<SocietaUtenti, Long> {
    Optional<SocietaUtenti> findSocietaByIdSocietaAndIdUtente(Long idSocieta, Long idUtente);

//	@Query("SELECT u FROM SocietaUtenti u JOIN u.societa s WHERE s.codiceFiscale = :codiceFiscale")
//	Page<Object []> getAllbyUtente(@Param("idUserLogin") long idUser, Pageable pageable);

}
