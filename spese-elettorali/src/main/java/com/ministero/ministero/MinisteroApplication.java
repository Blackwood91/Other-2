package com.ministero.ministero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.ministero.ministero.metodi.CalcoloOnorari;
import com.ministero.ministero.metodi.CalcoloRiparto;
import com.ministero.ministero.metodi.ProvaRollBack;
import com.ministero.ministero.util.ConnectionToDb;
import com.ministero.ministero.util.Messaggio;

@SpringBootApplication
@EntityScan("com.ministero.ministero.model")
public class MinisteroApplication {

    public static void main(String[] args) {

        SpringApplication.run(MinisteroApplication.class, args);

        System.out.println("Application started");

        // +++PROVA di CalcoloOnorari+++
        // CalcoloOnorari co = new CalcoloOnorari();
        // co.LeggiDatiElezione(97090);

        // +++PROVA di CalcoloRiparto+++
        // CalcoloRiparto cr = new CalcoloRiparto();
        // cr.Riparto_Fondo();

        // ProvaRollBack prb = new ProvaRollBack();
        // prb.MetodoProvaRollBack();

        // Provo Calcolo Estero
        // CalcoloOnorari co = new CalcoloOnorari();
        // co.CalcoloEstero();


        System.out.println("+++FINE+++");

    }

}
