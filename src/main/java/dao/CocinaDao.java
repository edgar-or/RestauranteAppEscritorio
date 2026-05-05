/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.conexion.Conexion;
import dao.dto.PedidoCocinaDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayala
 */
public class CocinaDao {
    
     private static final String SELECT_PEDIDOS_COCINA =
        "SELECT p.nombre AS nombreProducto, p.descripcion, ped.estado " +
        "FROM producto p " +
        "INNER JOIN producto_pedido pp ON p.idProducto = pp.idProducto " +
        "INNER JOIN pedido ped ON pp.idPedido = ped.idPedido " +
        "INNER JOIN empleado e ON ped.idEmpleado = e.idEmpleado " +
        "INNER JOIN area_produccion ap ON e.idproduccion = ap.idproduccion " +
        "WHERE ped.estado = false AND ap.nombre = 'Cocina'";

    public List<PedidoCocinaDto> listar() throws Exception {

        List<PedidoCocinaDto> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(SELECT_PEDIDOS_COCINA);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PedidoCocinaDto dto = new PedidoCocinaDto();

                dto.setNombreProducto(rs.getString("nombreProducto"));
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
