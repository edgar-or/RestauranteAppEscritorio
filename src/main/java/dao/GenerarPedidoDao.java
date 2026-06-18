/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import modelo.ModeloMesa;

/**
 *
 * @author ayala
 */
public class GenerarPedidoDao {

    private static final String LISTAR_MESAS
            = "SELECT idmesa, numeromesa FROM mesa";

    private static final String INSERTAR_PEDIDO
            = "INSERT INTO public.pedido (fecha, total, estado, idmesa, idempleado) "
            + "VALUES(CURRENT_DATE, ?, false, ?, ?) RETURNING idpedido";

    private static final String INSERTAR_DETALLE
            = "INSERT INTO public.producto_pedido (idproducto, idpedido, cantidad, sub_total, nota) "
            + "VALUES (?, ?, ?, ?, ?) "
            + "ON CONFLICT (idproducto, idpedido) DO UPDATE SET "
            + "cantidad = producto_pedido.cantidad + EXCLUDED.cantidad, "
            + "sub_total = producto_pedido.sub_total + EXCLUDED.sub_total";

    public ArrayList<ModeloMesa> llenarComboMesa() throws Exception {
        ArrayList<ModeloMesa> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_MESAS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModeloMesa m = new ModeloMesa();
                m.setIdMesa(rs.getString("idmesa"));

                m.setNumeroMesa(rs.getInt("numeromesa"));

                lista.add(m);
            }
            rs.close();
            ps.close();
        } finally {
            conn.close();
        }
        return lista;
    }

    public int guardarPedidoCompleto(int idMesa, int idEmpleado, double total, List<int[]> detalles, List<String> notas) throws Exception {
        Connection conn = Conexion.getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement psPedido = conn.prepareStatement(INSERTAR_PEDIDO);
            psPedido.setDouble(1, total);
            psPedido.setInt(2, idMesa);
            psPedido.setInt(3, idEmpleado);

            ResultSet rs = psPedido.executeQuery();
            if (!rs.next()) {
                conn.rollback();
                return -1;
            }
            int idPedido = rs.getInt("idpedido");
            rs.close();
            psPedido.close();

            PreparedStatement psDetalle = conn.prepareStatement(INSERTAR_DETALLE);
            for (int i = 0; i < detalles.size(); i++) {
                int[] d = detalles.get(i);
                psDetalle.setInt(1, d[0]);
                psDetalle.setInt(2, idPedido);
                psDetalle.setInt(3, d[1]);
                psDetalle.setDouble(4, d[2] / 100.0);
                psDetalle.setString(5, notas.get(i));
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();
            psDetalle.close();

            conn.commit();
            return idPedido;

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public boolean eliminarPedidoCompleto(int idPedido) throws Exception {
        String sqlDetalles = "DELETE FROM public.producto_pedido WHERE idpedido = ?";
        String sqlPedido = "DELETE FROM public.pedido WHERE idpedido = ?";

        Connection conn = Conexion.getConnection();
        conn.setAutoCommit(false);

        try {
            // 1. Limpiamos cualquier rastro en la tabla detalle
            try (PreparedStatement psDetalles = conn.prepareStatement(sqlDetalles)) {
                psDetalles.setInt(1, idPedido);
                psDetalles.executeUpdate();
            }

            // 2. Eliminamos definitivamente la cabecera del pedido
            int filasAfectadas = 0;
            try (PreparedStatement psPedido = conn.prepareStatement(sqlPedido)) {
                psPedido.setInt(1, idPedido);
                filasAfectadas = psPedido.executeUpdate();
            }

            conn.commit();
            return filasAfectadas > 0;

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public boolean registrarPedido(int idMesa, int idEmpleado) throws Exception {
        Connection conn = Conexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(INSERTAR_PEDIDO)) {
            ps.setDouble(1, 0.0);
            ps.setInt(2, idMesa);
            ps.setInt(3, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } finally {
            conn.close();
        }
    }

    public int obtenerIdPedidoActivo(int idMesa) throws Exception {
        String sql = "SELECT idpedido FROM public.pedido WHERE idmesa = ? AND estado = false LIMIT 1";
        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idpedido");
                }
            }
        }
        return -1;
    }

    public void insertarLineaDetalle(int idPedido, int idProducto, int cantidad, double subtotal, String nota) throws Exception {

        String sql = "INSERT INTO producto_pedido "
                + "(idproducto,idpedido,cantidad,sub_total,nota) "
                + "VALUES (?,?,?,?,?)";

        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idPedido);
            ps.setInt(3, cantidad);
            ps.setDouble(4, subtotal);
            ps.setString(5, nota);

            ps.executeUpdate();
        }
    }

    public List<Object[]> obtenerDetallesPedido(int idPedido) throws Exception {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT pp.idproducto, p.nombre, pp.cantidad, pp.sub_total, pp.nota "
                + "FROM public.producto_pedido pp "
                + "JOIN public.producto p ON pp.idproducto = p.idproducto "
                + "WHERE pp.idpedido = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("idproducto"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getDouble("sub_total"),
                        rs.getString("nota")
                    });
                }
            }
        }
        return lista;
    }

    public boolean guardarItemsEnPedidoExistente(int idPedido, double totalFinal, List<int[]> detallesNuevos, List<String> notasNuevas) throws Exception {
        Connection conn = Conexion.getConnection();
        conn.setAutoCommit(false);
        try {
            if (!detallesNuevos.isEmpty()) {
                try (PreparedStatement psDetalle = conn.prepareStatement(INSERTAR_DETALLE)) {
                    for (int i = 0; i < detallesNuevos.size(); i++) {
                        int[] d = detallesNuevos.get(i);
                        psDetalle.setInt(1, d[0]);
                        psDetalle.setInt(2, idPedido);
                        psDetalle.setInt(3, d[1]);
                        psDetalle.setDouble(4, d[2] / 100.0);
                        psDetalle.setString(5, notasNuevas.get(i));
                        psDetalle.addBatch();
                    }
                    psDetalle.executeBatch();
                }
            }

            String sqlUpdate = "UPDATE public.pedido SET total = ? WHERE idpedido = ?";
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setDouble(1, totalFinal);
                psUpdate.setInt(2, idPedido);
                psUpdate.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void eliminarProductoDePedido(int idPedido, int idProducto, double nuevoTotal) throws Exception {
        Connection conn = Conexion.getConnection();
        conn.setAutoCommit(false);
        String sqlDel = "DELETE FROM public.producto_pedido WHERE idpedido = ? AND idproducto = ?";
        String sqlUpd = "UPDATE public.pedido SET total = ? WHERE idpedido = ?";
        try {
            try (PreparedStatement psDel = conn.prepareStatement(sqlDel)) {
                psDel.setInt(1, idPedido);
                psDel.setInt(2, idProducto);
                psDel.executeUpdate();
            }
            try (PreparedStatement psUpd = conn.prepareStatement(sqlUpd)) {
                psUpd.setDouble(1, nuevoTotal);
                psUpd.setInt(2, idPedido);
                psUpd.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public List<Object[]> listarTodosLosPedidos() throws Exception {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT p.idpedido, p.fecha, m.numeromesa, p.total, p.estado "
                + "FROM public.pedido p "
                + "JOIN public.mesa m ON p.idmesa = m.idmesa "
                + "ORDER BY p.idpedido DESC";

        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("idpedido"),
                    rs.getDate("fecha"),
                    "Mesa " + rs.getInt("numeromesa"),
                    String.format("$%.2f", rs.getDouble("total")),
                    rs.getBoolean("estado") ? "PAGADO" : "PENDIENTE"
                });
            }
        }
        return lista;
    }

    public Object[] obtenerPedidoCabecera(int idPedido) throws Exception {
        String sql = "SELECT p.idpedido, p.fecha, m.numeromesa, e.nombre AS mesero, p.total, p.estado "
                + "FROM public.pedido p "
                + "JOIN public.mesa m ON p.idmesa = m.idmesa "
                + "JOIN public.empleado e ON p.idempleado = e.idempleado "
                + "WHERE p.idpedido = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{
                        rs.getInt("idpedido"),
                        rs.getDate("fecha"),
                        "Mesa " + rs.getInt("numeromesa"),
                        rs.getString("mesero"),
                        rs.getDouble("total"),
                        rs.getBoolean("estado")
                    };
                }
            }
        }
        return null;
    }

    public boolean marcarPedidoPagado(int idPedido) throws Exception {
        String sql = "UPDATE public.pedido SET estado = true WHERE idpedido = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean existeReciboPedido(int idPedido) throws Exception {
        String sql = "SELECT 1 FROM public.recibo WHERE idpedido = ? LIMIT 1";
        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean registrarPago(int idPedido, double total, String metodoPago,
            String nombre, String apellido, double propina) throws Exception {
        String sqlRecibo = "INSERT INTO public.recibo "
                + "(fecha, hora, total, metodopago, nombre, apellido, propina, idpedido) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlPedido = "UPDATE public.pedido SET estado = true WHERE idpedido = ?";

        Connection conn = Conexion.getConnection();
        conn.setAutoCommit(false);
        try {
            try (PreparedStatement ps = conn.prepareStatement(sqlRecibo)) {
                long ahora = System.currentTimeMillis();
                ps.setDate(1, new java.sql.Date(ahora));
                ps.setTime(2, new java.sql.Time(ahora));
                ps.setDouble(3, total);
                ps.setString(4, metodoPago);
                ps.setString(5, nombre);
                ps.setString(6, apellido);
                ps.setDouble(7, propina);
                ps.setInt(8, idPedido);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(sqlPedido)) {
                ps.setInt(1, idPedido);
                ps.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void actualizarTotalPedido(int idPedido) throws Exception {

        String sql = """
        UPDATE pedido
        SET total = (
            SELECT COALESCE(SUM(sub_total),0)
            FROM producto_pedido
            WHERE idpedido = ?
        )
        WHERE idpedido = ? """;

        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idPedido);

            ps.executeUpdate();
        }
    }

}
