package com.ministero.ministero.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ministero.ministero.model.Elezione;
import com.ministero.ministero.service.ElezioneService;

@Controller
@RequestMapping("/jsp")
public class esempioFormController {

    @GetMapping("/esempioForm")
        public ModelAndView viewEsempioForm(){

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("esempioForm");
            
            return modelAndView;
        }

    //ciao
    
}
