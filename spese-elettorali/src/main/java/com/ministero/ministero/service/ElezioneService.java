package com.ministero.ministero.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ministero.ministero.model.Elezione;
import com.ministero.ministero.model.SchedeElez;
import com.ministero.ministero.repository.ElezioneRepository;
import com.ministero.ministero.repository.SchedeElezRepository;

@Service
public class ElezioneService {

    List<Elezione> elezioni;

    @Autowired
    ElezioneRepository elezioneRepository;

    @Autowired
    SchedeElezRepository schedeElezRepository;

    @Autowired
    SchedeElezService schedaElezService;

    public List<Elezione> getAll() {

        return elezioneRepository.findAll();
    }

    public Elezione createElezione(Elezione elezione) {

        return elezioneRepository.save(elezione);
    }

    public Elezione findElezioneById(Long id) {
        if (!elezioneRepository.findById(id).isPresent()) {
            return null;
        } else
            return elezioneRepository.findById(id).get();
    }

    public Elezione updateElezione(Elezione newElezione) {

        Elezione vecchiaElezione = findElezioneById(newElezione.getId());

        if (vecchiaElezione != null) {
            vecchiaElezione.setAnno(newElezione.getAnno());
            vecchiaElezione.setCodElezione(newElezione.getCodElezione());
            vecchiaElezione.setTipo(newElezione.getTipo());

            return elezioneRepository.save(vecchiaElezione);
        } else {
            return null;
        }
    }

    public Elezione updateSchedeStatoElezione(Elezione newElezione) {

        Elezione vecchiaElezione = findElezioneById(newElezione.getId());

        if (vecchiaElezione != null) {
            vecchiaElezione.setAnno(newElezione.getAnno());
            vecchiaElezione.setCodElezione(newElezione.getCodElezione());
            vecchiaElezione.setTipo(newElezione.getTipo());

            return elezioneRepository.save(vecchiaElezione);
        } else {
            return null;
        }
    }

    public void deleteElezioneById(Long id) {

        // da testare

        List<SchedeElez> tutteLeSchedeElez = schedeElezRepository.findAll();

        for (SchedeElez schedeElez : tutteLeSchedeElez) {
            if (schedeElez.getFkIdElezione() == id) {
                schedeElezRepository.delete(schedeElez);
            }
        }
        elezioneRepository.deleteById(id);
    }

    public Elezione cercaELezionePerAnnoECodiceElezione(int anno, int codElezione) {
        return elezioneRepository.findByAnnoAndCodElezione(anno, codElezione);
    }

}
