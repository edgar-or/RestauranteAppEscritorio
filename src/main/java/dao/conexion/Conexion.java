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

<<<<<<< HEAD
     private static final String URL = "jdbc:postgresql://localhost:5432/restaurante";
=======
    private static final String URL = "jdbc:postgresql://localhost:5432/RESTAURANTEAPPESC";
>>>>>>> e6d8b6c9e74152442158920f8ed5e8b100944fc9
    private static final String USER = "postgres";
    private static final String PASS = "6074";
    
//    private static final String URL = "jdbc:postgresql://localhost:5432/RestauranteAppEscritorio";
//    private static final String USER = "postgres";
//    private static final String PASS = "eaadmin76rag19$";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

}
