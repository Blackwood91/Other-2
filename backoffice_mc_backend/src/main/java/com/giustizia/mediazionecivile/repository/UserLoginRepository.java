package com.giustizia.mediazionecivile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.giustizia.mediazionecivile.dto.ElencoUtentiRuoloDto;
import com.giustizia.mediazionecivile.model.RegioneProvince;
import com.giustizia.mediazionecivile.model.UserLogin;
import com.giustizia.mediazionecivile.projection.UserLoginProjection;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
	 public UserLogin findByCodiceFiscale(String codiceFiscale);
	 
	 public UserLogin findByCodiceFiscaleAndIsAbilitato(String codiceFiscale, int isAbilitato);
	 	 
	 @Query("SELECT u FROM UserLogin u WHERE isAbilitato != 2")
	 public Page<UserLoginProjection> findAllUtentiByElenco(Pageable pageable);
	 
	 @Query(value="SELECT u.* ,ro.ruolo FROM USER_LOGIN u LEFT JOIN Ruolo ro ON "
	 		+ "ro.id_ruolo = u.id_ruolo WHERE u.is_abilitato != 2", nativeQuery= true)
	 public List<Object[]>findAllUtentiByElencoRuolo(Pageable pageable);

	 @Query("SELECT u.id, u.nome, u.cognome, u.codiceFiscale, ro.ruolo, ru.dataInserimento, ru.richiestaIscrizione, ru.ragioneSociale, ru.pIva, ru.pec " + 
		       "FROM UserLogin u " + 
		       "LEFT JOIN RichiestaRegistrazioneUtente ru ON ru.idUserLogin = u.id " + 
		       "LEFT JOIN Ruolo ro ON ro.idRuolo = ru.idRuolo " +
		       "WHERE u.isAbilitato = 2")
	 public Page<Object[]> findAllUtentiByElencoRegistrazioni(Pageable pageable);
}
