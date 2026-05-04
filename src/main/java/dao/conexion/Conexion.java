/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ayala
 */
public class Conexion {

    private static final String URL = "jdbc:postgresql://localhost:5432/RESTAURANTEAPPESC";
    private static final String USER = "postgres";
    private static final String PASS = "eaadmin76rag19$";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

}
