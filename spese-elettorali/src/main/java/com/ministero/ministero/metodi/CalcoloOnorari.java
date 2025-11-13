package com.ministero.ministero.metodi;

import java.beans.JavaBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ministero.ministero.model.Elezione;
import com.ministero.ministero.util.ConnectionToDb;
import com.ministero.ministero.util.InterfaceCalcolo;

@RestController
@CrossOrigin
@RequestMapping("/api/calcoloOnorari")
public class CalcoloOnorari implements InterfaceCalcolo {

    int volteUpdate;

    // Variabili Teniche
    String query = "";
    PreparedStatement ps;
    ResultSet rs;

    // Id spese seggi
    int idSpeseSeggi = 0;
    List<Integer> listaId = new ArrayList<>();

    // Variabili di contenuto
    int anno = 0;
    int codElezione = 0; // da cambiare a
    int numSchede = 0;
    int schedeStato = 0;
    double ripSpese = 0.0;
    String vSa = "";
    String tipoElezione = "";

    int onoPresOrd, onoPresSpe, onoPresEst = 0;
    int onoScruOrd, onoScruSpe, onoScruEst = 0;
    int maggPresOrd, maggPresSpe, maggPresEst = 0;
    int maggScruOrd, maggScruSpe, maggScruEst = 0;
    int compOrd, compSpe, compEst = 0;

    int numMagg = 0;

    double speseMissioni = 0.0;
    boolean isReferendum = false;
    boolean isCovid = false;

    // queste variabili nel codice originali sono globali di CalcoloSpese()
    String codEnte = "";
    int codProvincia = 0;
    int sezOrd = 0;
    int sezSpe = 0;
    int totaleElettori = 0;
    int seggiVolanti = 0;

    double onoPresOrd2 = 0.0;
    double onoScruOrd2 = 0.0;

    double onoPresSpe2 = 0.0;
    double onoScruSpe2 = 0.0;

    double totSpese = 0.0;
    double speseOrd = 0.0;
    double speseSpe = 0.0;
    double speseVolanti = 0.0;

    int totaleSezioni = 0;

    int schedeAmm = 0;
    int schedeStato2 = 0;

    boolean isAmm = false;
    boolean isSuppletive = false;

    int numRowSelezionate = 0;

    // +++Leggi Anni e Leggi Elezioni le toglieremo +++
    // PER ORA LE LASCIO
    
    @Override
    public List<Integer> LeggiAnni() {

        List<Integer> listaAnni = new ArrayList<>();

        try {

            query = "SELECT * FROM elezioni";

            Connection conn = ConnectionToDb.getConnection();

            ps = conn.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                int annoEstratto = rs.getInt("anno");

                listaAnni.add(annoEstratto);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaAnni;

    }

    @Override
    public List<Elezione> LeggiElezioni(int anno) {

        List<Elezione> listaElezioni = new ArrayList<>();

        try {

            query = "SELECT id_elezione, cod_elezione, descr_elezione FROM elezioni WHERE anno = ? order by cod_elezione DESC";

            Connection conn = ConnectionToDb.getConnection();

            ps = conn.prepareStatement(query);

            ps.setInt(1, anno);

            rs = ps.executeQuery();

            while (rs.next()) {

                Long idELezione = rs.getLong("id_elezione");
                int codElezione = rs.getInt("cod_elezione");
                String descrElezione = rs.getString("descr_elezione");

                Elezione elezione = new Elezione(idELezione, codElezione, descrElezione);

                listaElezioni.add(elezione);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaElezioni;

    }

    @RequestMapping("/start")
    public ResponseEntity<String> Calcolo_Onorari(@RequestParam int idElezClick) {

        LeggiDatiElezione(idElezClick);

        LeggiSchedaElezione();

        CalcoloSpese();

        //Qui ho già eseguito: Calcolo Onorari --> ORA imposto valore 'calcolo_onorari_fatto' su 'S'

        try {

            String query = "UPDATE elezioni SET calcolo_onorari_fatto = 'S' WHERE id_elezione = ?";
            Connection conn = ConnectionToDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idElezClick);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //devo capire come recuperare questa stringa lato front end
        return new ResponseEntity<String>("Calcolo_Onorari_Completato", HttpStatus.OK);

    }

    public void LeggiDatiElezione(int idElezClick) {

        try {

            isCovid = false;

            query = "SELECT * FROM elezioni WHERE id_elezione = ?";

            Connection conn = ConnectionToDb.getConnection();

            ps = conn.prepareStatement(query);

            ps.setInt(1, idElezClick);

            rs = ps.executeQuery();

            while (rs.next()) {

                anno = rs.getInt("anno");
                codElezione = rs.getInt("cod_elezione");
                vSa = rs.getString("tipo");
                numSchede = rs.getInt("num_schede");
                schedeStato = rs.getInt("schede_stato");
                speseMissioni = rs.getDouble("rimborso_spese_pres");

                // VEDERE 149
                if (rs.getString("covid").equals("S")) {
                    isCovid = true;
                }

            }

            ps.close();
            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void LeggiSchedaElezione() {

        try {

            isReferendum = false;

            Connection conn = ConnectionToDb.getConnection();

            query = "SELECT * FROM schede_elez INNER JOIN tipo_schede ON schede_elez.Tipo_scheda = tipo_schede.Cod_scheda WHERE anno = ? AND cod_elezione = ? AND Comp_stato = 'S'";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, anno);
            ps.setInt(2, codElezione);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tipoElezione = rs.getString("tipo_elezione");
                if (rs.getString("cod_scheda").equals("RS")) {
                    isReferendum = true;
                }
            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CalcoloSpese() {

        try {

            ripSpese = 1.0;

            LeggiOnorari();

            SelectIdSpeseSeggi();

            // prova delete Spese Seggi()
            DeleteFromSpeseSeggi();

            MetodoPrincipaleCalcoloSpese();

            // RIGA 384
            CalcoloEstero();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // ELenco Metodi

    public void LeggiOnorari() {

        try {

            Connection conn = ConnectionToDb.getConnection();

            query = "SELECT * FROM onorari WHERE tipo_elezione = ?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, tipoElezione);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String check = rs.getString("tipo_seggio");

                if (check.equals("O")) {

                    compOrd = rs.getInt("num_componenti");
                    onoPresOrd = rs.getInt("onorario_presidente");
                    onoScruOrd = rs.getInt("onorario_scrutatori");
                    maggPresOrd = rs.getInt("maggior_presidente");
                    maggScruOrd = rs.getInt("maggior_scrutatori");

                } else if (check.equals("S")) {

                    compSpe = rs.getInt("num_componenti");
                    onoPresSpe = rs.getInt("onorario_presidente");
                    onoScruSpe = rs.getInt("onorario_scrutatori");
                    maggPresSpe = rs.getInt("maggior_presidente");
                    maggScruSpe = rs.getInt("maggior_scrutatori");

                } else {

                    compEst = rs.getInt("num_componenti");
                    onoPresEst = rs.getInt("onorario_presidente");
                    onoScruEst = rs.getInt("onorario_scrutatori");
                    maggPresEst = rs.getInt("maggior_presidente");
                    maggScruEst = rs.getInt("maggior_scrutatori");
                }

            }

            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SelectIdSpeseSeggi() {

        try {

            Connection connSelect = ConnectionToDb.getConnection();

            query = "SELECT id_spese_seggi FROM spese_seggi WHERE anno = ? AND cod_elezione = ?";

            PreparedStatement psSelect = connSelect.prepareStatement(query);

            psSelect.setInt(1, anno);

            psSelect.setInt(2, codElezione);

            ResultSet rsSelect = psSelect.executeQuery();

            while (rsSelect.next()) {

                idSpeseSeggi = rsSelect.getInt("id_spese_seggi");

                listaId.add(idSpeseSeggi);

            }

            psSelect.close();
            rsSelect.close();

            connSelect.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // RIGA 223
    public void DeleteFromSpeseSeggi() {

        try {

            Connection connDelete = ConnectionToDb.getConnection();
            query = "DELETE FROM spese_seggi WHERE anno = ? AND cod_elezione = ?";
            PreparedStatement psDelete = connDelete.prepareStatement(query);
            psDelete.setInt(1, anno);
            psDelete.setInt(2, codElezione);
            int rowsEliminate = psDelete.executeUpdate();
            System.out.println("+++Ho eliminato" + rowsEliminate + "righe+++");

            psDelete.close();
            connDelete.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // riga 233
    public void MetodoPrincipaleCalcoloSpese() {

        // TENTATIVO DI
        // AGGIUNGERE LA DELETE
        // commit presente E SI VOLA

        // riga 233
        try {

            query = "SELECT * FROM sezioni_elettori WHERE anno = ? and cod_elezione = ? ORDER BY cod_ente";

            Connection conn = ConnectionToDb.getConnection();

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, anno);
            ps.setInt(2, codElezione);

            rs = ps.executeQuery();

            // nel vecchio progetto questo sarebbe il FOR gigante a RIGA ---> 241

            // QUI APRO conn2 fuori dal while()
            // Connection conn2 = ConnectionToDb.getConnection();

            // QUI APRO conn3 fuori dal while()
            // Connection conn3 = ConnectionToDb.getConnection();

            // QUI APRO connInsert fuori dal while()
            Connection connInsert = ConnectionToDb.getConnection();

            while (rs.next()) {
                numRowSelezionate++;

                // vPb = (n / nr) * 100
                // RadPGB.Minimum = 0
                // RadPGB.Maximum = 100
                // RadPGB.Value1 = vPb

                codEnte = rs.getString("cod_ente");
                codProvincia = rs.getInt("cod_provincia");
                sezOrd = rs.getInt("totale_sezioni");
                sezSpe = rs.getInt("num_sez_speciali");
                totaleElettori = rs.getInt("totale_elettori");
                seggiVolanti = 0;

                // riga 349 covide vecchio
                totaleSezioni = rs.getInt("totale_sezioni");

                // RIGA 257
                // Variabile Nullable ---> .getObject può ritornare null da Db
                var SeggiVolantiNullaBle = rs.getObject("seggi_volanti");

                // se diversa da null entra nell'if
                if (SeggiVolantiNullaBle != null) {
                    seggiVolanti = rs.getInt("seggi_volanti");
                }

                // Test amministrative ---> riga 261

                isAmm = false;

                query = "SELECT * FROM enti_amministrative WHERE anno = ? AND cod_elezione = ? AND cod_ente = ?";

                PreparedStatement ps2 = conn.prepareStatement(query);

                ps2.setInt(1, anno);
                ps2.setInt(2, codElezione);
                ps2.setString(3, codEnte);

                ResultSet rs2 = ps2.executeQuery();

                // questo è l'if a riga 270 ---> ho usato questo Boolean per sopperire
                // alla mancanza del ---> If tbx.Rows.Count > 0 Then

                // +++DA RIVEDERE+++

                Boolean isEntrataNelWhile = false;

                schedeAmm = 0;

                while (rs2.next()) {

                    System.out.println("Sono entrato nel While di Enti Amministrative");

                    isEntrataNelWhile = true;
                    schedeAmm++;
                    isAmm = true;

                    // RIGA 274
                    // Valido per elezioni settembre 2020
                    if (isCovid == true) {
                        LeggiOnorariSuppletive();
                    }

                    // +++IMPOSTARE COLONNA CIRCOSCRIZIONAL COME NON NULLABLE PER EVITARE STO
                    // CASINO--->ANCHE SOPRA
                    var circoscrizionaliNullable = rs2.getObject("circoscrizionali");
                    if (circoscrizionaliNullable != null) {
                        if (rs2.getInt("circoscrizionali") == 1) {
                            schedeAmm += 1;
                        }
                    }

                }

                rs2.close();
                ps2.close();

                if (!isEntrataNelWhile) {
                    schedeAmm = 0;
                }

                // Questa cosa succede con schedeStato NON con schedeAmm
                schedeStato2 = schedeStato;
                // schedeStato2 ---> vSchedeStato (var. locale) ---> riga 373
                // schedeStato ---> vSchede_Stato (var. globale)

                // RICOMINCIA DA QUI
                // Test Suppletive ---> riga 294
                isSuppletive = false;

                query = "SELECT * FROM suppletive WHERE anno = ? AND cod_elezione = ? AND cod_ente = ?";

                // Connection conn3 = ConnectionToDb.getConnection();

                PreparedStatement ps3 = conn.prepareStatement(query);

                ps3.setInt(1, anno);
                ps3.setInt(2, codElezione);
                ps3.setString(3, codEnte);

                ResultSet rs3 = ps3.executeQuery();

                if (rs3.next()) {
                    schedeStato2 += 1;
                    isSuppletive = true;
                    LeggiOnorariSuppletive();

                }

                ps3.close();
                rs3.close();

                // conn3.close();

                // conn2.close();

                // Vecchio progetto RIGA ---> 309

                ripSpese = (double) schedeStato2 / (schedeStato2 + schedeAmm);

                numMagg = (schedeStato2 - 1) + schedeAmm;

                // Fino a un max di 4 maggiorazioni

                if (numMagg > 4) {
                    numMagg = 4;
                }

                // Ordinarie

                // double sezOrdDouble = sezOrd;

                onoPresOrd2 = onoPresOrd + (maggPresOrd * numMagg);
                onoPresOrd2 = onoPresOrd2 * (double) sezOrd * ripSpese;

                onoScruOrd2 = onoScruOrd + (maggScruOrd * numMagg);
                onoScruOrd2 = onoScruOrd2 * (double) (compOrd - 1) * (double) sezOrd * ripSpese;

                speseOrd = onoPresOrd2 + onoScruOrd2;

                // Volanti in caso di Referendum

                speseVolanti = 0.0;
                if (isReferendum) {
                    if (!isCovid) {
                        speseVolanti = seggiVolanti * (double) onoScruOrd * ripSpese;
                    } else {
                        if (!isAmm && !isSuppletive) {
                            speseVolanti = (double) seggiVolanti * (double) onoScruOrd * ripSpese;
                        }
                    }
                }

                // vOno_Pres_spe ---> onoPresSpe
                // kOno_Pres_spe ---> onoPresSpe2

                // Speciali ---> RIGA 339
                onoPresSpe2 = onoPresSpe + (maggPresSpe * numMagg);
                onoPresSpe2 = onoPresSpe2 * (double) sezSpe;

                onoScruSpe2 = onoScruSpe + (maggScruSpe * numMagg);
                onoScruSpe2 = onoScruSpe2 * (compSpe - 1) * (double) sezSpe;

                speseSpe = (onoPresSpe2 + onoScruSpe2) * ripSpese;

                // Spese Missione Presidente

                speseMissioni = totaleSezioni * speseMissioni * 0.03 * ripSpese;

                // Totale Spese Onorari

                totSpese = speseOrd + speseSpe + speseVolanti + speseMissioni;

                // Non so xk faccia così ---> riga 354
                if (isSuppletive || isAmm) {
                    LeggiOnorari();
                }

                // INSER FINALE TOMBALE!!!
                try {

                    query = "INSERT INTO spese_seggi (anno, cod_elezione, cod_ente, cod_provincia, elettori, sez_ordinarie, sez_speciali, spese_ordinarie, spese_speciali, spese_volanti, missioni_presidenti, spese_totali, schede_stato, schede_ammin, suppletive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement psInsert = connInsert.prepareStatement(query);

                    psInsert.setInt(1, anno);
                    psInsert.setInt(2, codElezione);
                    psInsert.setString(3, codEnte);
                    psInsert.setInt(4, codProvincia);
                    psInsert.setInt(5, totaleElettori);
                    psInsert.setInt(6, sezOrd);
                    psInsert.setInt(7, sezSpe);
                    psInsert.setDouble(8, speseOrd);
                    psInsert.setDouble(9, speseSpe);
                    psInsert.setDouble(10, speseVolanti);
                    psInsert.setDouble(11, speseMissioni);
                    psInsert.setDouble(12, totSpese);
                    psInsert.setInt(13, schedeStato2);
                    psInsert.setInt(14, schedeAmm);

                    if (isSuppletive) {
                        psInsert.setString(15, "S");
                    } else {
                        psInsert.setString(15, null);
                    }

                    // QUI USO L'ARRAY CREATO A RIGA 316 PIENO DI ID per TARGHETTARE LE MIE RIGHE
                    // psUpdate.setInt(16, listaId.get(volteUpdate));

                    psInsert.executeUpdate();

                    volteUpdate++;
                    System.out.println("+++ HO ESEGUITO L'INSERT " + volteUpdate + " volte +++");

                    psInsert.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            connInsert.close();
            rs.close();
            ps.close();

            // conn3.close();
            // conn2.close();

            System.out.println("+++ FINE MetodoPrincipaleCalcoloSpese() +++ ");

            // +++capire se questo for serve
            // for(int i=0; i< numRowSelezionate; i++ ) {
            // vPbInt = ( i / numRowSelezionate) * 100;
            // vPb = (double) vPbInt;
            // //manca questa cose
            // // RadPGB.Minimum = 0
            // // RadPGB.Maximum = 100
            // // RadPGB.Value1 = vPb
            // }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void LeggiOnorariSuppletive() {

        try {

            // System.out.println("+++Sono in LeggiOnorariSuppletive()+++");

            query = "SELECT * FROM onorari WHERE tipo_elezione = 'PO'";

            Connection conn = ConnectionToDb.getConnection();

            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {

                String check = rs.getString("tipo_seggio");

                if (check.equals("O")) {

                    compOrd = rs.getInt("num_componenti");
                    onoPresOrd = rs.getInt("onorario_presidente");
                    onoScruOrd = rs.getInt("onorario_scrutatori");
                    maggPresOrd = rs.getInt("maggior_presidente");
                    maggScruOrd = rs.getInt("maggior_scrutatori");

                } else if (check.equals("S")) {

                    compSpe = rs.getInt("num_componenti");
                    onoPresSpe = rs.getInt("onorario_presidente");
                    onoScruSpe = rs.getInt("onorario_scrutatori");
                    maggPresSpe = rs.getInt("maggior_presidente");
                    maggScruSpe = rs.getInt("maggior_scrutatori");

                } else {

                    compEst = rs.getInt("num_componenti");
                    onoPresEst = rs.getInt("onorario_presidente");
                    onoScruEst = rs.getInt("onorario_scrutatori");
                    maggPresEst = rs.getInt("maggior_presidente");
                    maggScruEst = rs.getInt("maggior_scrutatori");
                }

            }

            s.close();
            rs.close();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CalcoloEstero() {

        // anno = 2019;
        // codElezione = 4;
        // tipoElezione = "EU";

        int idSpeseSeggi = 0;
        double speseTotaliPiuEstero = 0.0; // speseTotaliPiuEstero

        // numSezioniEst ---> sarebbe vSez ---> riga 523
        int numSezioniEst = 0;
        String codEnteEst = "";

        // sarebbe ---> vSpese
        double speseEst = 0.0; // speseEst

        double onoPresEst2 = 0.0; // onoPresEst2
        double onoScruEst2 = 0.0; // onoScruEst2

        try {

            LeggiOnorari();

            System.out.println("---sono in Calcolo Estero---");

            Connection connEstero = ConnectionToDb.getConnection();

            query = "SELECT * FROM circos_estero WHERE anno = ? AND cod_elezione = ? AND tipo_elezione = ?";

            PreparedStatement psEstero = connEstero.prepareStatement(query);

            psEstero.setInt(1, anno);
            psEstero.setInt(2, codElezione);
            psEstero.setString(3, tipoElezione);

            ResultSet rsEstero = psEstero.executeQuery();

            Connection connEstero2 = ConnectionToDb.getConnection();

            Connection connEsteroUpdate = ConnectionToDb.getConnection();

            while (rsEstero.next()) {

                numSezioniEst = rsEstero.getInt("num_sezioni");
                codEnteEst = rsEstero.getString("cod_ente");

                try {

                    // Connection connEstero2 = ConnectionToDb.getConnection();

                    query = "SELECT * FROM spese_seggi WHERE anno = ? AND cod_elezione = ? AND cod_ente = ?";

                    PreparedStatement psEstero2 = connEstero2.prepareStatement(query);

                    psEstero2.setInt(1, anno);
                    psEstero2.setInt(2, codElezione);
                    psEstero2.setString(3, codEnteEst);

                    ResultSet rsEstero2 = psEstero2.executeQuery();

                    while (rsEstero2.next()) {

                        idSpeseSeggi = rsEstero2.getInt("id_spese_seggi");

                        speseTotaliPiuEstero = rsEstero2.getDouble("spese_totali");

                        onoPresEst2 = onoPresEst + (maggPresEst * numMagg);
                        onoPresEst2 = onoPresEst2 * numSezioniEst;

                        onoScruEst2 = onoScruEst + (maggScruEst * numMagg);
                        onoScruEst2 = onoScruEst2 * (compEst - 1) * numSezioniEst;

                        speseEst = onoPresEst2 + onoScruEst2;

                        speseTotaliPiuEstero += speseEst;

                        try {

                            // Connection connEsteroUpdate = ConnectionToDb.getConnection();

                            query = "UPDATE spese_seggi SET spese_estero = ?, spese_totali = ? WHERE anno = ? AND cod_elezione = ? AND cod_ente = ?";

                            PreparedStatement psEstero3 = connEsteroUpdate.prepareStatement(query);

                            psEstero3.setDouble(1, speseEst);
                            psEstero3.setDouble(2, speseTotaliPiuEstero);

                            psEstero3.setInt(3, anno);
                            psEstero3.setInt(4, codElezione);
                            psEstero3.setString(5, codEnteEst);

                            psEstero3.executeUpdate();

                            System.out.println("---Ho effettuato UPDATE estero---");

                            psEstero3.close();

                            // connEsteroUpdate.close();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    psEstero2.close();
                    rsEstero2.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            psEstero.close();
            rsEstero.close();

            connEsteroUpdate.close();
            connEstero2.close();
            connEstero.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
