package com.ministero.ministero.Restcontroller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ministero.ministero.model.Elezione;
import com.ministero.ministero.repository.ElezioneRepository;
import com.ministero.ministero.service.ElezioneService;
import com.ministero.ministero.service.SchedeElezService;
import com.ministero.ministero.util.ConnectionToDb;

@RestController
@RequestMapping("/api/elezione")
@CrossOrigin
public class ElezioneRestController {

    String newElezioneString = "";
    String newArraySchedeElezioneString = "";

    @Autowired
    ElezioneRepository elezioneRepository;

    @Autowired
    ElezioneService elezioneService;

    @Autowired
    SchedeElezService schedeElezService;

    @GetMapping("/all")
    public ResponseEntity<List<Elezione>> getAllElezioni() {
        return new ResponseEntity<>(elezioneService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Elezione> getElezioneById(@RequestParam Long id) {

        return new ResponseEntity<Elezione>(elezioneService.findElezioneById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Elezione> createElezione(@RequestBody Elezione newElezione) {
        if (elezioneService.createElezione(newElezione) == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(elezioneService.createElezione(newElezione), HttpStatus.CREATED);

    }

    // CUSTOM
    @PostMapping("/createCustom")
    public ResponseEntity<Elezione> createElezioneCustom(@RequestBody Elezione newElezione) {
        if (elezioneService.createElezione(newElezione) == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(elezioneService.createElezione(newElezione), HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<Elezione> updateElezione(@RequestBody Elezione newElezione) {
        return new ResponseEntity<>(elezioneService.updateElezione(newElezione), HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteElezioneById(@RequestParam String id) {

        Long idCancellazioneLong = Long.parseLong(id, 10);


        if (elezioneService.findElezioneById(idCancellazioneLong) == null) {

            return new ResponseEntity<>("Elezione non presente", HttpStatus.BAD_REQUEST);

        } else {

            elezioneService.deleteElezioneById(idCancellazioneLong);

            return new ResponseEntity<>("Elezione eliminata", HttpStatus.OK);

        }

    }

    // CUSTOM
    @GetMapping("/findByAnnoECodElezione")
    public ResponseEntity<Elezione> cercaELezionePerAnnoECodiceElezione(@RequestParam int anno, int codElezione) {
        return new ResponseEntity<Elezione>(elezioneService.cercaELezionePerAnnoECodiceElezione(anno, codElezione),
                HttpStatus.OK);
    }

    // Rest API per Calcolo Onorari e Calcolo Riparto
    @GetMapping("/anniElezioni")
    public ResponseEntity<List<Integer>> getAnniElezioni() {

        List<Integer> anniElezioni = new ArrayList<>();

        Connection conn = ConnectionToDb.getConnection();

        try {
            String query = "SELECT DISTINCT anno FROM elezioni";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int anno = rs.getInt("anno");
                anniElezioni.add(anno);
            }

            return new ResponseEntity<List<Integer>>(anniElezioni, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/elezioniByAnno")
    public ResponseEntity<List<Elezione>> getElezioniByAnno(@RequestParam int anno) {
        return new ResponseEntity<List<Elezione>>(elezioneRepository.findByAnno(anno), HttpStatus.OK);
    }

}
