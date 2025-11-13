package com.ministero.ministero.metodi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ministero.ministero.util.ConnectionToDb;

public class ProvaRollBack {

    //ESEMPIO DI CONNECTION CON ROLLBACK E COMMIT!!!
    public void MetodoProvaRollBack() {

        Connection conn = ConnectionToDb.getConnection();

        try {

            conn.setAutoCommit(false);
            String query = "INSERT INTO suppletive (anno, cod_elezione, cod_ente) VALUES ( 22, 22, 22)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("+++1+++");

            String query2 = "INSERT INTO suppletive (anno, cod_elezione, cod_ente) VALUES ( 33 , 22, 22)";
            PreparedStatement ps2 = conn.prepareStatement(query2);
            ps2.executeUpdate();
            System.out.println("+++2+++");

            String query3 = "INSERT INTO suppletive (anno, cod_elezione, cod_ente) VALUES (<sruzdutztdu>)";
            PreparedStatement ps3 = conn.prepareStatement(query3);
            ps3.executeUpdate();
            System.out.println("+++3+++");

            conn.commit();

        } catch (SQLException e) {

            try {

                if (conn != null) {
                    System.out.println("+++RollBack+++");
                    conn.rollback();
                }

            } catch (Exception e2) {

                e2.printStackTrace();

            }

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {


            try {

                if (conn != null) {
                    conn.close();
                    if (conn.isClosed()) {
                        System.out.println("connection chiusa");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
