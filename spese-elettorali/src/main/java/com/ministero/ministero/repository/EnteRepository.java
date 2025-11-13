package com.ministero.ministero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ministero.ministero.model.Ente;

@Repository
public interface EnteRepository extends JpaRepository<Ente, Long>{
    
}
