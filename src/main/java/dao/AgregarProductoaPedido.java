package dao;

import dao.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * DAO para insertar productos en un pedido existente.
 */
public class AgregarProductoaPedido {

    private static final String INSERT_PRODUCTO_PEDIDO =
        "INSERT INTO public.producto_pedido (idproducto, idpedido, cantidad, sub_total, nota) " +
        "VALUES (?, ?, ?, ?, ?)";

    /**
     * Agrega un producto al pedido indicado.
     *
     * @param idProducto  id del producto seleccionado
     * @param idPedido    id del pedido activo
     * @param cantidad    cantidad solicitada
     * @param subTotal    precio * cantidad
     * @param nota        nota opcional (puede ser null)
     * @return true si se insertó correctamente
     */
    public boolean agregarProducto(int idProducto, int idPedido,
                                   int cantidad, double subTotal,
                                   String nota) throws Exception {

        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_PRODUCTO_PEDIDO);
            ps.setInt(1, idProducto);
            ps.setInt(2, idPedido);
            ps.setInt(3, cantidad);
            ps.setDouble(4, subTotal);
            ps.setString(5, nota);

            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;

        } finally {
            conn.close();
        }
    }
}