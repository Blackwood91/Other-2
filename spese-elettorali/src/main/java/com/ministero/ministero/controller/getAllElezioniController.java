package com.ministero.ministero.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ministero.ministero.model.Elezione;
import com.ministero.ministero.service.ElezioneService;

@Controller
@RequestMapping("/jsp")
public class getAllElezioniController {

    @Autowired
    ElezioneService elezioneService;

    //+++ALESSIO+++
    @RequestMapping(value = "/getAllElezioni", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView elezioni() {

        ModelAndView modelAndView = new ModelAndView();

        List<Elezione> allElezioni = elezioneService.getAll();

        modelAndView.addObject("allElezioni", allElezioni);

        modelAndView.addObject("messaggio", "");

        modelAndView.setViewName("getAllElezioni");

        return modelAndView;

    }

    //QUI DEVO PASSARE UNA LISTA e non un oggetto
    // .addAttribute --> devo usare .addAttribute
    @RequestMapping(value = "/getAllElezioniVECCHIO", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getAllElezioni(){

        ModelAndView modelAndView = new ModelAndView();

        List<Elezione> allElezioni = elezioneService.getAll();

        List<String> listaStringhe = new ArrayList<>();
        listaStringhe.add("1");
        listaStringhe.add("2");
        listaStringhe.add("3");
        listaStringhe.add("4");


        modelAndView.addObject("allElezioni", allElezioni);
        modelAndView.addObject("allElezioni", allElezioni);

        modelAndView.addObject("messaggio", "");

        modelAndView.addObject("listaStringhe", listaStringhe);

        modelAndView.setViewName("getAllElezioniVecchio");

        return modelAndView;
    }


    @GetMapping("/delete")
    public ModelAndView deleteElezioneById(@RequestParam String idCancellazione) {

        Long idCancellazioneLong = Long.parseLong(idCancellazione, 10);
        
        if (elezioneService.findElezioneById(idCancellazioneLong) == null) {

            ModelAndView modelAndView = new ModelAndView();

            List<Elezione> allElezioni = elezioneService.getAll();

            modelAndView.addObject("allElezioni", allElezioni);

            modelAndView.addObject("messaggio", "Errore: Nessuna Elezione eliminata");

            modelAndView.setViewName("getAllElezioni");

            return modelAndView;


        } else {

            ModelAndView modelAndView = new ModelAndView();

            elezioneService.deleteElezioneById(idCancellazioneLong);

            List<Elezione> allElezioni = elezioneService.getAll();

            modelAndView.addObject("allElezioni", allElezioni);

            modelAndView.addObject("messaggio", "Elezione Eliminata Correttamente");

            modelAndView.setViewName("getAllElezioni");

            return modelAndView;

        }

    }
    
}
