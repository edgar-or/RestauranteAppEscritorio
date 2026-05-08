/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.conexion.Conexion;
import dao.dto.EmpleadoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ayala
 */
public class EmpleadoDao {

    public boolean insertar(EmpleadoDTO dto) throws Exception {

        Connection conn = Conexion.getConnection();

        try {
            conn.setAutoCommit(false);

            //Generar usuario y contraseña automáticamente
            String usuarioGen = generarUsuario(dto.getEmpleado().getNombre(), dto.getEmpleado().getApellido());

            String passGen = generarContrasena(dto.getEmpleado().getApellido());

            //Insertar usuario
            PreparedStatement psUser = conn.prepareStatement(
                    "INSERT INTO usuario(usuario, contrasena) VALUES (?, ?) RETURNING idusuario"
            );

            psUser.setString(1, usuarioGen);
            psUser.setString(2, passGen);

            ResultSet rs = psUser.executeQuery();

            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt("idusuario");
            }

            rs.close();
            psUser.close();

            //Insertar empleado
            PreparedStatement psEmp = conn.prepareStatement(
                    "INSERT INTO empleado(dui, nombre, apellido, idusuario, idrol) VALUES (?, ?, ?, ?, ?)"
            );

            psEmp.setString(1, dto.getEmpleado().getDui());
            psEmp.setString(2, dto.getEmpleado().getNombre());
            psEmp.setString(3, dto.getEmpleado().getApellido());
            psEmp.setInt(4, idUsuario);
            psEmp.setInt(5, dto.getRol().getIdRol());

            psEmp.executeUpdate();
            psEmp.close();

            conn.commit();


            return true;

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    private String generarUsuario(String nombre, String apellido) {
        String inicial = nombre.substring(0, 1).toLowerCase();
        String ape = apellido.toLowerCase().replaceAll(" ", "");
        int numero = (int) (Math.random() * 900 + 100);

        return inicial + ape + numero;
    }

    private String generarContrasena(String apellido) {
        return apellido.toLowerCase().replaceAll(" ", "") + "123";
    }
    
    public List<EmpleadoDTO> listar() throws Exception {

    List<EmpleadoDTO> lista = new ArrayList<>();
    Connection conn = Conexion.getConnection();

    String sql =
        "SELECT e.idempleado, e.dui, e.nombre, e.apellido, " +
        "u.idusuario, u.usuario, u.contrasena, " +
        "r.idrol, r.rol " +
        "FROM empleado e " +
        "INNER JOIN usuario u ON e.idusuario = u.idusuario " +
        "INNER JOIN rol r ON e.idrol = r.idrol";

    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            EmpleadoDTO dto = new EmpleadoDTO();

            //Empleado
            dto.getEmpleado().setIdEmpleado(rs.getString("idempleado"));
            dto.getEmpleado().setDui(rs.getString("dui"));
            dto.getEmpleado().setNombre(rs.getString("nombre"));
            dto.getEmpleado().setApellido(rs.getString("apellido"));

            //Usuario
            dto.getUsuario().setUsuario(rs.getString("usuario"));
            dto.getUsuario().setPassword(rs.getString("contrasena"));

            //Rol
            dto.getRol().setIdRol(rs.getInt("idrol"));
            dto.getRol().setRol(rs.getString("rol"));

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
