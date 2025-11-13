package com.ministero.ministero.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ministero.ministero.model.Elezione;
import com.ministero.ministero.model.SchedeElez;
import com.ministero.ministero.repository.SchedeElezRepository;
import com.ministero.ministero.service.ElezioneService;
import com.ministero.ministero.service.SchedeElezService;
import com.ministero.ministero.util.ConnectionToDb;
import com.ministero.ministero.util.Messaggio;

@Controller
@RequestMapping("/jsp")
public class createElezioneController {

    // Questo oggetto diventerà l'oggetto elezioneInput che viene dal form input
    Elezione elezione = new Elezione();

    SchedeElez schedeElez1 = new SchedeElez();
    SchedeElez schedeElez2 = new SchedeElez();
    SchedeElez schedeElez3 = new SchedeElez();
    SchedeElez schedeElez4 = new SchedeElez();
    SchedeElez schedeElez5 = new SchedeElez();

    List<SchedeElez> listaShedeElezDaRitornare = new ArrayList<>();

    @Autowired
    ElezioneService elezioneService;

    @Autowired
    SchedeElezRepository schedeElezRepository;

    @Autowired
    SchedeElezService schedeElezService;

    // PUNTO 1
    @RequestMapping("/createElezione")
    public ModelAndView createElezione() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("createElezione");

        return modelAndView;

    }

    

    // Qui Salvo l'Elezione e la prima SchedeElez
    @RequestMapping(value = "/creaPrimaSchedeElez", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView creaPrimaSchedeElez(@ModelAttribute("Elezione") Elezione elezione,
            @ModelAttribute("SchedeElez") SchedeElez schedeElez) {

        ModelAndView modelAndView = new ModelAndView();

        int newCodElezione = 0;
        String query = "";

        try {

            query = "SELECT MAX(cod_elezione) AS maxCod FROM elezioni WHERE anno = ?";
            Connection conn = ConnectionToDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, elezione.getAnno());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getObject("maxCod") == null || rs.getInt("maxCod") == 0) {
                    newCodElezione = 1;
                } else
                    newCodElezione = rs.getInt("maxCod") + 1;
            }

            ps.close();
            rs.close();
            conn.close();

            elezione.setCodElezione(newCodElezione);

            elezione.setCalcoloOnorariFatto("N");

            // Salvo Elezione perchè devo generare un Id auto-incrementatante da assegnare a
            // SchedeElez
            elezioneService.createElezione(elezione);

            // Cerco l'elezione salvata
            Elezione elezioneDaRitornare = new Elezione();
            elezioneDaRitornare = elezioneService.cercaELezionePerAnnoECodiceElezione(elezione.getAnno(),
                    elezione.getCodElezione());

            //Aggiorno SchedeElez PRIMA di salvarla su DB
            schedeElez.setAnno(elezioneDaRitornare.getAnno());
            schedeElez.setCodElezione(elezioneDaRitornare.getCodElezione());
            schedeElez.setFkIdElezione(elezioneDaRitornare.getId());

            // Salvo SchedeElez
            schedeElezService.createSchedeElez(schedeElez);

            // Cerco le tutte le Schede Elez tramite FkIdElezione
            List<SchedeElez> listaSchedeElezDaRitornare = new ArrayList<>();
            listaSchedeElezDaRitornare = schedeElezRepository.findByFkIdElezione(elezione.getId());

            // Ciclo le SchedeElez per determinare numSchede e schedeStato dell'elezione
            int numSchede = 0;
            int schedeStato = 0;

            for (SchedeElez SE : listaSchedeElezDaRitornare) {
                numSchede += SE.getNumSchede();

                // 'RS' OR tipo_scheda = 'PS' OR tipo_scheda = 'EU' OR tipo_scheda = 'PC'";
                if (SE.getTipoScheda().equals("RS") || SE.getTipoScheda().equals("PS") || SE.getTipoScheda().equals("EU")
                        || SE.getTipoScheda().equals("PC")) {
                    schedeStato += SE.getNumSchede();
                }
            }

            // Setto i campi numSchede e schedeStato di elezioneDaRitornare
            elezioneDaRitornare.setNumSchede(numSchede);
            elezioneDaRitornare.setSchedeStato(schedeStato);

            // Ora aggiorno Elezione già salvata
            elezioneService.updateElezione(elezioneDaRitornare);

            // Questo mi serve per controllo lato Front End
            modelAndView.addObject("elezioneEprimaSchedeElezCreate", "elezioneEprimaSchedeElezCreate");

            // Questo mi serve come Elezione da reinderizzare
            modelAndView.addObject("elezioneDaRitornare", elezioneDaRitornare);

            // Questa è la lista con le SchedeElez da reinderizzare
            modelAndView.addObject("listaSchedeElezDaRitornare", listaSchedeElezDaRitornare);

            // Aggiungo Messaggio success
            Messaggio messaggio = new Messaggio("success", "Elezione e Schede Elezione create correttamente.");
            modelAndView.addObject("messaggio", messaggio);

            modelAndView.setViewName("createElezione");

            return modelAndView;

        } catch (Exception e) {
            e.printStackTrace();
            Messaggio messaggio = new Messaggio("danger", "Errore nella creazione dell'elezione. Riprovare");

            modelAndView.addObject("messaggio", messaggio);

            return modelAndView;
        }

    }

    // Qui dalla seconda Schede Elez in poi
    @RequestMapping(value = "/creaDaSecondaSchedeElezInPoi", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView creaDaSecondaSchedeElezInPoi() {

        // AGGIUNGERE CONTROLLI VALIDITà OGGETTO schedeElez

        // Salvo dati input nella lista che utilizzerò
        // listaShedeElez.add(schedeElezInput);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("schedaElezCreata", "SchedaElezCreata");

        modelAndView.setViewName("createElezione");

        return modelAndView;
    }

    // +++EXTRA+++
    @RequestMapping("/createElezioneVecchio")
    public ModelAndView createElezioneVecchio() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("createElezioneVecchio");

        return modelAndView;

    }

    // ++++++++++++++ controller PROVA CAVIA ++++++++++++++++++
    @RequestMapping(value = "/provaCavia", method = { RequestMethod.POST })
    public ModelAndView cavia() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("provaCavia");

        return modelAndView;
    }

}
