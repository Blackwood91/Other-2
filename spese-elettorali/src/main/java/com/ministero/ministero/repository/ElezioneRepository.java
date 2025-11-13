package com.ministero.ministero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ministero.ministero.model.Elezione;


@Repository
public interface ElezioneRepository extends JpaRepository<Elezione, Long>{

    //+++CUSTOM+++
    public Elezione findByAnnoAndCodElezione(int anno, int codElezione);

    public List<Elezione> findByAnno(int anno);
    
}
