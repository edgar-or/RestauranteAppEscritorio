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
 
    // Se agrega idproducto a todas las consultas para poder insertarlo en producto_pedido
    private static final String LISTAR_BEBIDAS =
        "SELECT idproducto, nombre, descripcion, precio FROM producto WHERE idproduccion = 3";
 
    private static final String LISTAR_POSTRES =
        "SELECT idproducto, nombre, descripcion, precio FROM producto WHERE idproduccion = 2";
 
    private static final String LISTAR_PLATILLOS =
        "SELECT idproducto, nombre, descripcion, precio FROM producto WHERE idproduccion = 1";
 
    // Entradas: idproduccion = 4. Si en tu BD es diferente, cambia el número.
    private static final String LISTAR_ENTRADAS =
        "SELECT idproducto, nombre, descripcion, precio FROM producto WHERE idproduccion = 4";
 
    private static final String BUSCAR_POR_NOMBRE =
        "SELECT idproducto, nombre, descripcion, precio FROM producto " +
        "WHERE idproduccion = ? AND LOWER(nombre) LIKE LOWER(?)";
 
    // ----------------------------------------------------------------
    // Listar todas las categorías
    // ----------------------------------------------------------------
 
    public List<ModeloProducto> listarBebidas() throws Exception {
        return listarPorProduccion(3);
    }
 
    public List<ModeloProducto> listarPostres() throws Exception {
        return listarPorProduccion(2);
    }
 
    public List<ModeloProducto> listarPlatillos() throws Exception {
        return listarPorProduccion(1);
    }
 
    public List<ModeloProducto> listarEntradas() throws Exception {
        return listarPorProduccion(4);
    }
 
    // ----------------------------------------------------------------
    // Buscar por nombre dentro de una categoría
    // ----------------------------------------------------------------
 
    public List<ModeloProducto> buscarPorNombre(int idProduccion, String texto) throws Exception {
        List<ModeloProducto> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();
 
        try {
            PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_NOMBRE);
            ps.setInt(1, idProduccion);
            ps.setString(2, "%" + texto + "%");
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
                lista.add(mapear(rs));
            }
 
            rs.close();
            ps.close();
        } finally {
            conn.close();
        }
 
        return lista;
    }
 
    // ----------------------------------------------------------------
    // Helpers privados
    // ----------------------------------------------------------------
 
    public List<ModeloProducto> listarPorProduccion(int idProduccion) throws Exception {
        List<ModeloProducto> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();
 
        String sql = "SELECT idproducto, nombre, descripcion, precio " +
                     "FROM producto WHERE idproduccion = ?";
 
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduccion);
            ResultSet rs = ps.executeQuery();
 
            while (rs.next()) {
                lista.add(mapear(rs));
            }
 
            rs.close();
            ps.close();
        } finally {
            conn.close();
        }
 
        return lista;
    }
 
    private ModeloProducto mapear(ResultSet rs) throws Exception {
        ModeloProducto p = new ModeloProducto();
        p.setIdProducto(rs.getString("idproducto"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getDouble("precio"));
        return p;
    }
}
 