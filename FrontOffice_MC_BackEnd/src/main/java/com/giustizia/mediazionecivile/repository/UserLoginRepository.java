package com.giustizia.mediazionecivile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giustizia.mediazionecivile.model.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
	 public UserLogin findByCodiceFiscale(String codiceFiscale);
	 
	 public UserLogin findByCodiceFiscaleAndIsAbilitato(String codiceFiscale, int isAbilitato);
}
