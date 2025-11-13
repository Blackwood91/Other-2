package com.giustizia.mediazionecivile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.model.SoggettoRichiedente;
import com.giustizia.mediazionecivile.service.SoggettoRichiedenteService;

@RestController
@RequestMapping("/soggetto-richiedente")
public class SoggettoRichiedenteController {

    @Autowired
    SoggettoRichiedenteService soggettoRichiedenteService;

    @GetMapping("/getAllSoggettoRichiedente")
    public ResponseEntity<List<SoggettoRichiedente>> getAllSoggettoRichiedente() {
        return new ResponseEntity<>(soggettoRichiedenteService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<SoggettoRichiedente> getSoggettoRichiedenteById(Long id) {
        return new ResponseEntity<>(soggettoRichiedenteService.findSoggettoRichiedenteById(id), HttpStatus.OK);
    }

    @GetMapping("/find-soggetto-richiedente")
    public ResponseEntity<SoggettoRichiedente> findSoggettoRichiedenteBySoggettoRichiedente(
            @RequestParam String soggettoRichiedente) {
        return new ResponseEntity<SoggettoRichiedente>(
                soggettoRichiedenteService.findSoggettoRichiedenteBySoggettoRichiedente(soggettoRichiedente),
                HttpStatus.OK);
    }

}
