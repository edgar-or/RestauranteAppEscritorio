package dao;

import dao.dto.PedidoBarDto;
import dao.conexion.Conexion;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.AreaProduccionModelo;
import modelo.ModeloProducto;
import modelo.ModeloProducto_Pedido;

public class PedidoBarDao {

   private static final String SELECT_PEDIDOS_BAR =
        "SELECT pp.cantidad as cantidad ,  p.nombre AS nombreProducto, pp.nota as nota, pp.estado_orden as estado  FROM producto p INNER JOIN producto_pedido pp ON p.idProducto = pp.idProducto INNER JOIN pedido ped ON pp.idPedido = ped.idPedido INNER JOIN empleado e ON ped.idEmpleado = e.idEmpleado INNER JOIN area_produccion ap ON p.idproduccion = ap.idproduccion WHERE pp.estado_orden  = false AND ap.nombre   = 'Bar'";

    public List<ModeloProducto_Pedido> listar() throws Exception {

        List<ModeloProducto_Pedido> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(SELECT_PEDIDOS_BAR);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ModeloProducto_Pedido orden = new ModeloProducto_Pedido(); 
                ModeloProducto product = new ModeloProducto(); 
                AreaProduccionModelo area = new AreaProduccionModelo(); 
                
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