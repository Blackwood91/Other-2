package com.ministero.ministero.Restcontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ministero.ministero.model.Elezione;
import com.ministero.ministero.model.SchedeElez;
import com.ministero.ministero.repository.ElezioneRepository;
import com.ministero.ministero.service.ElezioneService;
import com.ministero.ministero.service.SchedeElezService;
import com.ministero.ministero.util.ConnectionToDb;

@RestController
@CrossOrigin
@RequestMapping("/api/createElezioneAndSchedeElez")
@Transactional // (rollbackFor = Exception.class ) +++TESTARE ROLLBACK
public class CreateElezioneAndSchedeElezController {

    String query = "";

    @Autowired
    ElezioneService elezioneService;

    @Autowired
    SchedeElezService schedeElezService;

    @Autowired
    ElezioneRepository elezioneRepository;

    @GetMapping("/codElezione")
    public int getNewCodElezione(@RequestParam int anno) {

        int newCodElezione = 0;

        try {

            query = "SELECT MAX(cod_elezione) AS maxCod FROM elezioni WHERE anno = ?";
            Connection conn = ConnectionToDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, anno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getObject("maxCod") == null || rs.getInt("maxCod") == 0) {
                    newCodElezione = 1;
                } else
                    newCodElezione = rs.getInt("maxCod") + 1;
            }

            ps.close();
            rs.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newCodElezione;

    }

    @PostMapping("/1")
    public ResponseEntity<Elezione> createElezioneAndRelativeSchede1(@RequestBody Elezione newElezione) {

        // controlli su validità elezione
        try {
            if (newElezione.getAnno() < 2000 || newElezione.getAnno() > 2099) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (!newElezione.getTipo().equals("S") && !newElezione.getTipo().equals("A")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (!newElezione.getRendiconto().equals("S") && !newElezione.getRendiconto().equals("N")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (!newElezione.getCovid().equals("S") && !newElezione.getCovid().equals("N")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(elezioneService.createElezione(newElezione), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(elezioneService.createElezione(newElezione), HttpStatus.OK);
    }

    @GetMapping("/newIdElezione")
    public int getNeWIdElezione(@RequestParam int anno, @RequestParam int codElezione) {

        int newIdElezione = 0;

        try {
            query = "SELECT id_elezione FROM elezioni WHERE anno = ? AND cod_elezione = ?";
            Connection conn = ConnectionToDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, anno);
            ps.setInt(2, codElezione);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getObject("id_elezione") == null || rs.getInt("id_elezione") == 0) {
                    throw new Exception("Manca Id Elezione. Riprovare da capo");
                } else {
                    newIdElezione = rs.getInt("id_elezione");
                }
            }

            conn.close();
            ps.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newIdElezione;

    }

    @PostMapping("/2")
    public ResponseEntity<List<SchedeElez>> createElezioneAndRelativeSchede2(
            @RequestBody List<SchedeElez> listSchedeElez) {

        try {
            //controlli su Schede Elezioni prima del save
            for (SchedeElez schedeElez : listSchedeElez) {

                if (schedeElez.getTipoScheda().length() != 2 ||
                        schedeElez.getNumSchede() < 0 ||
                        schedeElez.getNumSchede() > 10) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    schedeElezService.createSchedeElez(schedeElez);
                }
            }

            return new ResponseEntity<List<SchedeElez>>(listSchedeElez, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/updateSchedeStato")
    public int updateSchedeStato(@RequestParam int anno, @RequestParam int codElezione) {

        Connection conn;
        int schedeStato = 0;

        try {
            conn = ConnectionToDb.getConnection();
            String query = "SELECT SUM(num_schede)as schedeStato FROM schede_elez WHERE anno = ? AND cod_elezione = ? AND tipo_scheda = 'RS' OR tipo_scheda = 'PS' OR tipo_scheda = 'EU' OR tipo_scheda = 'PC'";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, anno);
            ps.setInt(2, codElezione);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                schedeStato = rs.getInt("schedeStato");
            }

            Elezione vecchiaElezione = elezioneRepository.findByAnnoAndCodElezione(anno, codElezione);

            if (vecchiaElezione != null) {
                vecchiaElezione.setSchedeStato(schedeStato);
                elezioneRepository.save(vecchiaElezione);
            }

            ps.close();
            rs.close();

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return schedeStato;
    }

}
