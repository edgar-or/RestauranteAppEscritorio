/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.conexion.Conexion;
import dao.dto.PedidoPanaderiaDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloMesa;

/**
 *
 * @author ayala
 */
public class GenerarPedidoDao {
    
    private static String LISTAR_MESAS = "select numeromesa from mesa m"; 
    
    public ArrayList<ModeloMesa> llenarComboMesa () throws Exception{
                List<ModeloMesa> listaMesas = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_MESAS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModeloMesa mesa = new ModeloMesa();

                mesa.setNumeroMesa(rs.getInt("numeromesa"));
                

                listaMesas.add(mesa);
            }

            rs.close();
            ps.close();

        } finally {
            conn.close();
        }
        return  (ArrayList<ModeloMesa>) listaMesas;
        
        
    }
    
}
