/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloProducto;

/**
 *
 * @author ayala
 */
public class ProductosParaPedidosDao {
    
    private static String LISTAR_BEBIDAS = "select p.nombre as nombre , p.descripcion as descripcion , p.precio  as precio from producto p where p.idproduccion = 3"; 
    
    public List<ModeloProducto> listarBebidas() throws Exception {

        List<ModeloProducto> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_BEBIDAS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModeloProducto bebida = new ModeloProducto();

                bebida.setNombre(rs.getString("nombre"));
                bebida.setDescripcion(rs.getString("descripcion"));
                bebida.setPrecio(rs.getDouble("precio"));

                lista.add(bebida);
            }

            rs.close();
            ps.close();

        } finally {
            conn.close();
        }

        return lista;
    }
    
    
    
}
