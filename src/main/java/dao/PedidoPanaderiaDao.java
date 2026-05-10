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

/**
 *
 * @author Emilio
 */
public class PedidoPanaderiaDao {

//    private static final String SELECT_PEDIDOS_BAR
//            = "select producto.nombre as nombreProducto, producto_pedido.cantidad as cantidad, producto.descripcion as descripcion, producto_pedido.estado as estado "
//            + "from producto "
//            + "inner join producto_pedido on producto_pedido.idproducto  = producto.idproducto "
//            + "inner join area_produccion on area_produccion.idproduccion = producto.idproduccion "
//            +" inner join pedido on pedido.idpedido = producto_pedido.idpedido "
//            + "where area_produccion.nombre = 'Panaderia' and pedido.estado = false ";
    private static String SELECT_PEDIDOS_BAR = "select producto.nombre as nombreProducto, producto.descripcion as descripcion, pedido.estado as estado"
            + " from producto "
            + " inner join producto_pedido on producto_pedido.idproducto  = producto.idproducto "
            + " inner join area_produccion on area_produccion.idproduccion = producto.idproduccion "
            + " inner join pedido on pedido.idpedido = producto_pedido.idpedido "
            + " where area_produccion.nombre = 'Panaderia' and pedido.estado = false ";

    public List<PedidoPanaderiaDto> listar() throws Exception {

        List<PedidoPanaderiaDto> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(SELECT_PEDIDOS_BAR);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PedidoPanaderiaDto dto = new PedidoPanaderiaDto();

                dto.setNombreProducto(rs.getString("nombreProducto"));
                dto.setCantidad(4);
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setEstado(rs.getBoolean("estado"));

                lista.add(dto);
            }

            rs.close();
            ps.close();

        } finally {
            conn.close();
        }

        return lista;
    }
}
