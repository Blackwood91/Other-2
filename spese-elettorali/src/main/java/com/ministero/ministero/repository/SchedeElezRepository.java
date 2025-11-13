package com.ministero.ministero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ministero.ministero.model.SchedeElez;

@Repository
public interface SchedeElezRepository extends JpaRepository<SchedeElez, Long> {

    public List<SchedeElez> findByFkIdElezione(Long fkIdElezione);

    
    
}
