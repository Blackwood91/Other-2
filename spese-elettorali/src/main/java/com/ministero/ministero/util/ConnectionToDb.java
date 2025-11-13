package com.ministero.ministero.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionToDb {
    static String url = "jdbc:oracle:thin:@//192.168.60.100:1521/freepdb1";
    static String username = "Spese_Elettorali_Svil";
    static String password = "oracle";

    public static Connection getConnection() {

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
