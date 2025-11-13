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

import com.ministero.ministero.model.Prefettura;
import com.ministero.ministero.service.PrefetturaService;

@RestController
@RequestMapping("/api/prefettura")
@CrossOrigin
public class PrefetturaRestController {

    @Autowired
    PrefetturaService prefetturaService;

    @GetMapping("/all")
    public ResponseEntity<List<Prefettura>> getAllPrefetture() {
        return new ResponseEntity<>(prefetturaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Prefettura> getPrefetturaById(@RequestParam Long id) {
        return new ResponseEntity<Prefettura>(prefetturaService.findPrefetturaById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Prefettura> createPrefettura(@RequestBody Prefettura newPrefettura) {

        if (prefetturaService.creaPrefettura(newPrefettura) == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(prefetturaService.creaPrefettura(newPrefettura), HttpStatus.CREATED);

    }

    @PutMapping("/update")
    public ResponseEntity<Prefettura> updatePrefettura(@RequestBody Prefettura newPrefettura) {
        return new ResponseEntity<>(prefetturaService.updatePrefettura(newPrefettura), HttpStatus.OK);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePrefetturaById(@RequestParam Long id) {

        prefetturaService.deletePrefetturaById(id);

        return new ResponseEntity<>("Prefettura eliminata", HttpStatus.OK);
    }

}
