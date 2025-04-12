/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author KyRilloS
 */



public class DatabaseConnection {

    private static final String URL = "jdbc:sqlserver://DESKTOP-9IA4C2H;databaseName=EncuentraTuDoctor;trustServerCertificate=true;integratedSecurity=true;encrypt=false";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
    }
}
