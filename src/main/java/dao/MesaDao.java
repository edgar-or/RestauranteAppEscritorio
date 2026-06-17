/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ayala
 */


import dao.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.ModeloMesa;

public class MesaDao {

    private static final String LISTAR_MESAS = "select idmesa, numeromesa, estado, capacidad from mesa order by idmesa";

    private static final String INSERTAR_MESA = "insert into mesa (numeromesa, estado, capacidad) values (?, ?, ?)";

    private static final String MODIFICAR_MESA = "update mesa set numeromesa = ?, estado = ?, capacidad = ? where idmesa = ?";

    private static final String ELIMINAR_MESA = "delete from mesa where idmesa = ?";
    
    private static final String BUSCAR_MESA = "select idmesa, numeromesa, estado, capacidad from mesa where numeromesa = ? order by idmesa";

    public ArrayList<ModeloMesa> listarMesas() throws Exception {
        ArrayList<ModeloMesa> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_MESAS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModeloMesa mesa = new ModeloMesa();
                mesa.setIdMesa(rs.getString("idmesa"));
                mesa.setNumeroMesa(rs.getInt("numeromesa"));
                mesa.setEstado(rs.getBoolean("estado"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                lista.add(mesa);
            }
            rs.close();
            ps.close();
        } finally {
            conn.close();
        }
        return lista;
    }

    public void insertar(ModeloMesa mesa) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(INSERTAR_MESA);
            ps.setInt(1, mesa.getNumeroMesa());
            ps.setBoolean(2, true); // siempre disponible al crearla
            ps.setInt(3, mesa.getCapacidad());

            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }

    public void modificar(ModeloMesa mesa) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(MODIFICAR_MESA);
            ps.setInt(1, mesa.getNumeroMesa());
            ps.setBoolean(2, mesa.isEstado());
            ps.setInt(3, mesa.getCapacidad());
            ps.setInt(4, Integer.parseInt( mesa.getIdMesa()));

            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }

    public void eliminar(String idMesa) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(ELIMINAR_MESA);
            ps.setInt(1, Integer.parseInt(idMesa));

            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }
    
    public ModeloMesa buscarPorNumero(int numeroMesa) throws Exception {
    ModeloMesa mesa = null;
    Connection conn = Conexion.getConnection();
    try {
        PreparedStatement ps = conn.prepareStatement(BUSCAR_MESA);
        ps.setInt(1, numeroMesa);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            mesa = new ModeloMesa();
            mesa.setIdMesa(rs.getString("idmesa"));
            mesa.setNumeroMesa(rs.getInt("numeromesa"));
            mesa.setEstado(rs.getBoolean("estado"));
            mesa.setCapacidad(rs.getInt("capacidad"));
        }
        rs.close();
        ps.close();
    } finally {
        conn.close();
    }
    return mesa;
}
}
    

