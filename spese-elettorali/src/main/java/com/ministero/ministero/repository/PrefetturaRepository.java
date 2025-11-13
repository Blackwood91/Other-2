package com.ministero.ministero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ministero.ministero.model.Prefettura;

@Repository
public interface PrefetturaRepository extends JpaRepository<Prefettura, Long> {
    
}
