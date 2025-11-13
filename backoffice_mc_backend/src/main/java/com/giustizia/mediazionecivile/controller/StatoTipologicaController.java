package com.giustizia.mediazionecivile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.model.Stato;
import com.giustizia.mediazionecivile.service.StatoTipologicaService;

@RestController
@RequestMapping("/api/stato")
public class StatoTipologicaController {

    @Autowired
    StatoTipologicaService statoService;

    @GetMapping("/find")
    public ResponseEntity<Stato> getStatoByNome(@RequestParam String descrizione) {
        return new ResponseEntity<Stato>(statoService.findStatoByNome(descrizione), HttpStatus.OK);
    }
}
