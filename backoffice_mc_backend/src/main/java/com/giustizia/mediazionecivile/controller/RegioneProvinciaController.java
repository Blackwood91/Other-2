package com.giustizia.mediazionecivile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 

import com.giustizia.mediazionecivile.projection.ProvinceProjection;
import com.giustizia.mediazionecivile.projection.RegioneProjection;
import com.giustizia.mediazionecivile.service.RegioneProvinciaService;

@RestController
@RequestMapping("/api/regioneProvincia")
public class RegioneProvinciaController {

    @Autowired
    RegioneProvinciaService regioneProvinciaService;

    @GetMapping("/getAllRegioni")
    public ResponseEntity<List<RegioneProjection>> getAllRegioni() {
        return new ResponseEntity<>(regioneProvinciaService.getAllRegioni(), HttpStatus.OK);
    }

    @GetMapping("/getAllProvince")
    public ResponseEntity<List<ProvinceProjection>> getAllProvince(@RequestParam int codiceRegione) {
        return new ResponseEntity<>(regioneProvinciaService.getAllProvince(codiceRegione), HttpStatus.OK);
    }


}
