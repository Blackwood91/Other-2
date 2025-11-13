package com.ministero.ministero.Restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ministero.ministero.model.SchedeElez;
import com.ministero.ministero.service.SchedeElezService;

@RestController
@RequestMapping("api/schedeElez")
@CrossOrigin
public class SchedElezRestController {

    @Autowired
    SchedeElezService schedeElezService;

    @GetMapping("/all")
    public ResponseEntity<List<SchedeElez>> getAllSchedeElez() {
        return new ResponseEntity<>(schedeElezService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<SchedeElez> createSchedeElez(@RequestBody SchedeElez newSchedeElez) {

        switch(newSchedeElez.getTipoScheda()){
            case "POLITICHE CAMERA":
            newSchedeElez.setTipoScheda("PC");
            break;

            case "POLITICHE SENATO":
            newSchedeElez.setTipoScheda("PS");
            break;

            case "EUROPEE":
            newSchedeElez.setTipoScheda("EU");
            break;

            case "REGIONALI":
            newSchedeElez.setTipoScheda("AR");
            break;

            case "COMUNALI":
            newSchedeElez.setTipoScheda("AC");
            break;

            case "REFERENDUM STATALE":
            newSchedeElez.setTipoScheda("RS");
            break;

            case "REFERENDUM LOCALE":
            newSchedeElez.setTipoScheda("RL");
            break;


        }

        //QUI
        return new ResponseEntity<SchedeElez>(schedeElezService.createSchedeElez(newSchedeElez), HttpStatus.CREATED);
    }
}
