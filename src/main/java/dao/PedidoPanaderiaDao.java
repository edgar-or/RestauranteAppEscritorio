/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.conexion.Conexion;
import dao.dto.PedidoBarDto;
import dao.dto.PedidoPanaderiaDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.AreaProduccionModelo;
import modelo.ModeloProducto;
import modelo.ModeloProducto_Pedido;

/**
 *
 * @author Emilio
 */
public class PedidoPanaderiaDao {

    
    private static final String SELECT_PEDIDOS_PANADERIA =
        "SELECT pp.cantidad as cantidad ,  p.nombre AS nombreProducto, pp.nota as nota, pp.estado_orden as estado  FROM producto p INNER JOIN producto_pedido pp ON p.idProducto = pp.idProducto INNER JOIN pedido ped ON pp.idPedido = ped.idPedido INNER JOIN empleado e ON ped.idEmpleado = e.idEmpleado INNER JOIN area_produccion ap ON p.idproduccion = ap.idproduccion WHERE pp.estado_orden  = false AND ap.nombre   = 'Panaderia'";

    public List<ModeloProducto_Pedido> listar() throws Exception {

        List<ModeloProducto_Pedido> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(SELECT_PEDIDOS_PANADERIA);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ModeloProducto_Pedido orden = new ModeloProducto_Pedido(); 
                ModeloProducto product = new ModeloProducto(); 
                
               product.setNombre(rs.getString("nombreProducto"));
              orden.setCantidad(rs.getInt("cantidad") );
              orden.setNota(rs.getString("nota"));
              orden.setEstadoOrden(rs.getBoolean("estado"));
              
              orden.setProducto(product);

                lista.add(orden);
            }

            rs.close();
            ps.close();

        } finally {
            conn.close();
        }

        return lista;
    }
}
