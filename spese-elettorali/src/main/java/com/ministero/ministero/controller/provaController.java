package com.ministero.ministero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jsp")
public class provaController {
    
    @GetMapping("/prova")
    public ModelAndView viewProva(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("prova");
        
        modelAndView.addObject("prova", "prova");
        modelAndView.addObject("messaggio", "benvenuti");

        return modelAndView;
    }
    
}
