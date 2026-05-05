package dao;

import dao.dto.PedidoBarDto;
import dao.conexion.Conexion;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PedidoBarDao {

    private static final String SELECT_PEDIDOS_BAR =
        "SELECT p.nombre AS nombreProducto, p.descripcion, ped.estado " +
        "FROM producto p " +
        "INNER JOIN producto_pedido pp ON p.idProducto = pp.idProducto " +
        "INNER JOIN pedido ped ON pp.idPedido = ped.idPedido " +
        "INNER JOIN empleado e ON ped.idEmpleado = e.idEmpleado " +
        "INNER JOIN area_produccion ap ON e.idproduccion = ap.idproduccion " +
        "WHERE ped.estado = false AND ap.nombre = 'Bar'";

    public List<PedidoBarDto> listar() throws Exception {

        List<PedidoBarDto> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(SELECT_PEDIDOS_BAR);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PedidoBarDto dto = new PedidoBarDto();

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