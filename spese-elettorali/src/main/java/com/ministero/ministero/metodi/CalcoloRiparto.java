package com.ministero.ministero.metodi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ministero.ministero.model.Elezione;
import com.ministero.ministero.util.ConnectionToDb;
import com.ministero.ministero.util.InterfaceCalcolo;

// +++ CALCOLO RIPARTO LAVORA SOLAMENTE SULLA TABELLA SPESE_SEGGI +++

@RestController
@CrossOrigin
@RequestMapping("/api/calcoloRiparto")
public class CalcoloRiparto implements InterfaceCalcolo {

    // Lista ID
    ArrayList<Integer> listaId = new ArrayList<>();
    int idSpeseSeggi = 0;

    // anno e codElezione ---> +++ DA MODIFICARE 'STA ROBBA+++
    int anno = 0;
    int codElezione = 0;

    // Variabili Teniche
    String query = "";
    Connection conn;
    PreparedStatement ps;
    PreparedStatement psUpdate1;
    PreparedStatement psUpdate2;
    ResultSet rs;
    int volteUpdate = 0;
    int volteUpdate2 = 0;

    // Variabili di contenuto
    int NumeroSezioniElettori;

    double vImporto_fondo = 0.0;
    double vPerc_sez = 0.0;
    double vPerc_elett = 0.0;
    double vPerc_incr = 0.0;
    double vSez_min = 0.0;

    double vParIncr = 0.0;

    double vSpese_seggi = 0.0;

    double vImporto_riparto = 0.0;
    double vQuote_seggi = 0.0;
    double vQuote_elettori = 0.0;

    double vTSez = 0.0;
    double vTEle = 0.0;
    String vCod_ente = "";
    int vSchedeStato = 0;
    int vSchedeAmm = 0;

    double vPar_sez = 0.0;
    double vPar_elet = 0.0;

    int nr = 0;

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

            ps.close();
            rs.close();

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

            ps.close();
            rs.close();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaElezioni;

    }

    // Riga 147
    @RequestMapping("/start")
    public void Riparto_Fondo(@RequestParam int idElezClick) {

        LeggiDatiFondo(idElezClick);

        CalcoloTotaleSpese();

        DeterminoSezioniEdElettoriVirtuali();

        CalcoloParametriSezioniEdElettori();

        DeterminoGliImportiDaAssegnare();

    }

    // ++++ELENCO METODI+++

    // riga 167
    // Leggo dati fondo
    public void LeggiDatiFondo(int idElezClick) {

        try {

            Connection conn = ConnectionToDb.getConnection();
            query = "SELECT * FROM dati_fondo WHERE FK_ID_ELEZIONE = ?";
            ps = conn.prepareStatement(query);

            ps.setInt(1, idElezClick);

            // DA QUALCHE PARTE DEVI SETTARE ANNO E COD_ELEZIONE!!!

            rs = ps.executeQuery();

            while (rs.next()) {

                vImporto_fondo = rs.getDouble("valore_fondo");

                // Controllo per verificare se il Fondo è stato GIà calcolato !!!
                if (vImporto_fondo <= 0) {
                    System.out.println(
                            "Il Fondo per questa elezione non è settato correttamente! Modificare Fondo prima di procedere!");
                    System.exit(0);
                }

                anno = rs.getInt("anno");
                codElezione = rs.getInt("cod_elezione");

                vPerc_sez = rs.getDouble("perc_sezioni");
                vPerc_elett = rs.getDouble("perc_elettori");
                vPerc_incr = rs.getDouble("perc_incr");
                vSez_min = rs.getDouble("sez_minori");

                vParIncr = 1 + (vPerc_incr / 100);
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

    // riga 182
    // Leggo dati fondo
    public void CalcoloTotaleSpese() {

        // Ricavo da sezioni_elettori il numero di Enti partecipanti a quella
        // determinata elezione
        // la uso sotto per controllo

        try {

            Connection conn = ConnectionToDb.getConnection();
            query = "SELECT COUNT(*) as NumeroSezioniElettori FROM sezioni_elettori  WHERE anno = ? AND cod_elezione = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, anno);
            ps.setInt(2, codElezione);
            rs = ps.executeQuery();

            while (rs.next()) {
                NumeroSezioniElettori = rs.getInt("NumeroSezioniElettori");
            }

            System.out.println("+++Il numero di enti partecipanti è: " + NumeroSezioniElettori + "+++");

            ps.close();
            rs.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            Connection conn = ConnectionToDb.getConnection();
            query = "SELECT COUNT(*) as Nr, SUM(spese_totali) as Tspese FROM spese_seggi WHERE anno = ? AND cod_elezione = ?";
            ps = conn.prepareStatement(query);

            ps.setInt(1, anno);
            ps.setInt(2, codElezione);

            rs = ps.executeQuery();

            while (rs.next()) {

                vSpese_seggi = rs.getDouble("Tspese");
                nr = rs.getInt("Nr");

                // Controllo se la COUNT di spese_seggi è uguale alle sezioni in
                // sezioni_elettori
                // --> se non sono uguali vuol dire che mancano le righe in spese_seggi e quindi
                // calcoloOnorari() non è stato eseguito o è incompleto!!!

                if (NumeroSezioniElettori != nr) {
                    System.out.println(
                            "ERRORE:Eseguire Calcolo Onorari per questa elezione prima di procedere con Calcolo Riparto");
                    System.exit(0);
                }

                vSpese_seggi = rs.getDouble("Tspese");
                nr = rs.getInt("Nr");
            }

            ps.close();
            rs.close();

            conn.close();

            vImporto_riparto = vImporto_fondo - vSpese_seggi;
            vQuote_seggi = vImporto_riparto * vPerc_sez / 100;
            vQuote_elettori = vImporto_riparto * vPerc_elett / 100;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // riga 198
    // Determino le sezioni e gli elettori virtuali
    public void DeterminoSezioniEdElettoriVirtuali() {

        try {

            Connection conn = ConnectionToDb.getConnection();
            query = "SELECT * FROM spese_seggi WHERE anno = ? AND cod_elezione = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, anno);
            ps.setInt(2, codElezione);
            rs = ps.executeQuery();
            // Apro connUpdate1 fuori dal ciclo while()
            Connection connUpdate1 = ConnectionToDb.getConnection();
            while (rs.next()) {

                idSpeseSeggi = rs.getInt("id_spese_seggi");
                // vedere se sta lista serve
                listaId.add(idSpeseSeggi);

                vTSez = rs.getDouble("sez_ordinarie") + rs.getDouble("sez_speciali");
                vTEle = rs.getDouble("elettori");
                vCod_ente = rs.getString("cod_ente");
                vSchedeStato = rs.getInt("schede_stato");
                vSchedeAmm = rs.getInt("schede_ammin");

                if (vTSez <= 3) {
                    vTSez = vTSez * vParIncr;
                    vTEle = vTEle * vParIncr;
                }

                if (vSchedeAmm > 0) {
                    vTSez = vTSez * vSchedeStato / (vSchedeStato + vSchedeAmm);
                    vTEle = vTEle * vSchedeStato / (vSchedeStato + vSchedeAmm);
                }

                try {

                    query = "UPDATE spese_seggi SET sez_virtuali = ?, Elet_virtuali = ? WHERE id_spese_seggi = ?";
                    psUpdate1 = connUpdate1.prepareStatement(query);
                    psUpdate1.setDouble(1, vTSez);
                    psUpdate1.setDouble(2, vTEle);
                    psUpdate1.setInt(3, idSpeseSeggi);

                    psUpdate1.executeUpdate();

                    volteUpdate++;

                    System.out.println("Ho eseguito l'UPDATE " + volteUpdate + " volte");

                    // Chiudo il Prepared Statement dell'Update AD OGNI SINGOLO CICLO dell'Update!
                    psUpdate1.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            // Chiudo la Connection fuori alla fine del ciclo while();
            connUpdate1.close();

            // CHIUDO ResultSet e Prepared Statment principali!
            rs.close();
            ps.close();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // RIGA 237
    // Calcolo parametri sezioni ed elettori
    public void CalcoloParametriSezioniEdElettori() {

        try {
            // QUESTA COSA VA RI-CONTROLLATA QUANDO HO TUTTI SPESE_SEGGI!!!
            Connection conn = ConnectionToDb.getConnection();
            query = "SELECT  SUM(sez_virtuali) as Tsv, SUM(elet_virtuali) as Tev from spese_seggi WHERE anno = ? AND cod_elezione = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, anno);
            ps.setInt(2, codElezione);
            rs = ps.executeQuery();

            while (rs.next()) {
                vPar_sez = vQuote_seggi / rs.getDouble("Tsv");
                vPar_elet = vQuote_elettori / rs.getDouble("Tev");
            }

            conn.close();

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // RIGA 249
    // Determino gli importi da assegnare
    public void DeterminoGliImportiDaAssegnare() {

        // RIGA 262
        double vTSez2 = 0.0;
        double vTEle = 0.0;

        try {
            Connection conn = ConnectionToDb.getConnection();
            query = "SELECT id_spese_seggi, sez_virtuali, elet_virtuali FROM spese_seggi WHERE anno = ? AND cod_elezione = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, anno);
            ps.setInt(2, codElezione);
            rs = ps.executeQuery();

            // Apro fuori dal ciclo While();
            Connection connUpdate2 = ConnectionToDb.getConnection();
            while (rs.next()) {

                idSpeseSeggi = rs.getInt("id_spese_seggi");
                vTSez2 = rs.getDouble("sez_virtuali");
                vTEle = rs.getDouble("elet_virtuali");

                // INIZIO UDPDATE EFFETTIVO DI RIGA 267
                try {

                    // Connection connUpdate2 = ConnectionToDb.getConnection();
                    query = "UPDATE spese_seggi SET residuo_seggi = ?, residuo_elettori = ? WHERE id_spese_seggi = ?";
                    psUpdate2 = connUpdate2.prepareStatement(query);
                    psUpdate2.setDouble(1, vTSez2 * vPar_sez);
                    psUpdate2.setDouble(2, vTEle * vPar_elet);
                    psUpdate2.setInt(3, idSpeseSeggi);
                    psUpdate2.executeUpdate();

                    volteUpdate2++;
                    System.out.println("Ho eseguito l'UPDATE 2 " + volteUpdate2 + " volte");

                    // Chiudo qui il Prepared Statment di Update 2
                    psUpdate2.close();

                    // connUpdate2.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ps.close();
            rs.close();

            // Chiudo Entrambe le Connection qui!
            connUpdate2.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
