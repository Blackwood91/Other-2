package com.giustizia.mediazionecivile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.giustizia.mediazionecivile.projection.EfTipoRichiedenteProjection;
import com.giustizia.mediazionecivile.projection.TipoRichiedenteProjection;
import com.giustizia.mediazionecivile.service.TipoRichiedenteService;

@RestController
@RequestMapping("/api/tipoRichiedente")
public class TipoRichiedenteController {

    @Autowired
    TipoRichiedenteService tipoRichiedenteService;

    @GetMapping("/getAll")
    public ResponseEntity<List<TipoRichiedenteProjection>> getAllTipoRichiedente() {
        return new ResponseEntity<>(tipoRichiedenteService.getAllTipoRichiedente(), HttpStatus.OK);
    }
    
    @GetMapping("/getAllEf")
    public ResponseEntity<List<EfTipoRichiedenteProjection>> getAllTipoRichiedenteEf() {
        return new ResponseEntity<>(tipoRichiedenteService.getAllTipoRichiedenteEf(), HttpStatus.OK);
    }
}
