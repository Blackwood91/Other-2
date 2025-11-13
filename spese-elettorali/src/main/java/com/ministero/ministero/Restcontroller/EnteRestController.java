package com.ministero.ministero.Restcontroller;

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

import com.ministero.ministero.model.Ente;
import com.ministero.ministero.service.EnteService;

@RestController
@RequestMapping("/api/ente")
@CrossOrigin
public class EnteRestController {

    @Autowired
    EnteService enteService;

    @GetMapping("/all")
    public ResponseEntity<List<Ente>> getAllEnti() {
        return new ResponseEntity<>(enteService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Ente> getEnteById(@RequestParam Long id) {
        return new ResponseEntity<Ente>(enteService.findEnteById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Ente> createEnte(@RequestBody Ente newEnte) {

        if (enteService.creaEnte(newEnte) == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(enteService.creaEnte(newEnte), HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<Ente> updateEnte(@RequestBody Ente newEnte) {
        return new ResponseEntity<>(enteService.updateEnte(newEnte), HttpStatus.OK);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEnteById(@RequestParam Long id) {

        enteService.deleteEnteById(id);

        return new ResponseEntity<>("Ente eliminato", HttpStatus.OK);
    }

}
