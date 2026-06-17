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
import java.util.HashSet;
import java.util.List;
import modelo.AreaProduccionModelo;
import modelo.ModeloProducto;
import modelo.ModeloProducto_Pedido;

/**
 *
 * @author ayala
 */
public class MenuDao {

    private static final String LISTAR_MENU = "select p.idproducto , p.nombre, p.descripcion , ap.nombre as nombreProduccion, p.precio   from producto p inner join area_produccion ap on p.idproduccion = ap.idproduccion order by p.nombre";

    private static final String LISTAR_CATEGORIA = "select * from area_produccion ap where ap.nombre  = 'Bar' or ap.nombre = 'Cocina' or ap.nombre = 'Panaderia'";

    private static final String INSERTAR_PRODUCTO = "insert into producto (nombre, descripcion, precio, idproduccion) values (?, ?, ?, ?)";

    private static final String MODIFICAR_PRODUCTO = "update producto set nombre = ?, descripcion = ?, precio = ?, idproduccion = ? where idproducto = ?";
    

    private static final String ELIMINAR_PRODUCTO = "delete from producto where idproducto = ?";
    
    private static final String BUSCAR_PRODUCTO = "select p.idproducto, p.nombre, p.descripcion, ap.nombre as nombreProduccion, p.precio from producto p inner join area_produccion ap on p.idproduccion = ap.idproduccion where p.nombre ilike ? order by p.nombre";

    public List<ModeloProducto> listarMenu() throws Exception {

        List<ModeloProducto> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_MENU);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ModeloProducto product = new ModeloProducto();
                AreaProduccionModelo area = new AreaProduccionModelo();

                area.setNombre(rs.getString("nombreProduccion"));

                product.setIdProducto(String.valueOf(rs.getInt("idproducto")));
                product.setNombre(rs.getString("nombre"));
                product.setDescripcion(rs.getString("descripcion"));
                product.setAreaProduccion(area);
                product.setPrecio(rs.getDouble("precio"));

                lista.add(product);
            }

            rs.close();
            ps.close();

        } finally {
            conn.close();
        }

        return lista;
    }

    public ArrayList<AreaProduccionModelo> listarcategorias() throws Exception {

        ArrayList<AreaProduccionModelo> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_CATEGORIA);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                AreaProduccionModelo area = new AreaProduccionModelo();

                area.setIdProduccion(rs.getInt("idproduccion"));
                area.setNombre(rs.getString("nombre"));

                lista.add(area);
            }

            rs.close();
            ps.close();

        } finally {
            conn.close();
        }

        return lista;
    }

    public void insertar(ModeloProducto producto) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(INSERTAR_PRODUCTO);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getAreaProduccion().getIdProduccion());

            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }

    public void modificar(ModeloProducto producto) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(MODIFICAR_PRODUCTO);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getAreaProduccion().getIdProduccion());
            ps.setInt(5, Integer.parseInt(producto.getIdProducto()));

            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }

    public void eliminar(int idProducto) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(ELIMINAR_PRODUCTO);
            ps.setInt(1, idProducto);

            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }
    
    public ArrayList<ModeloProducto> buscarPorNombre(String nombre) throws Exception {
    ArrayList<ModeloProducto> lista = new ArrayList<>();
    Connection conn = Conexion.getConnection();
    try {
        PreparedStatement ps = conn.prepareStatement(BUSCAR_PRODUCTO);
        ps.setString(1, "%" + nombre + "%");
        ResultSet rs = ps.executeQuery();
        
            while (rs.next()) {
            ModeloProducto producto = new ModeloProducto();
            producto.setIdProducto(String.valueOf(rs.getInt("idproducto")) );
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setPrecio(rs.getDouble("precio"));
            
            AreaProduccionModelo area = new AreaProduccionModelo();
            area.setNombre(rs.getString("nombreProduccion"));
            producto.setAreaProduccion(area);
            
            lista.add(producto);
        }
        rs.close();
        ps.close();
    } finally {
        conn.close();
    }
    return lista;
}


}
