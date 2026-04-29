/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.conexion.Conexion;
import dao.dto.LoginResultadoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.RolModelo;
import modelo.UsuarioModelo;

/**
 *
 * @author ayala
 */
public class UsuarioDao {

    public LoginResultadoDTO validar(String usuario, String password) {
        UsuarioModelo u = null;
        LoginResultadoDTO resultado = null;
        String consulta = "SELECT u.*, r.idRol, r.rol AS nombreRol"
                + " FROM usuario u"
                + " INNER JOIN empleado e ON u.idUsuario = e.idUsuario"
                + " INNER JOIN rol r ON e.idRol = r.idRol"
                + " WHERE u.usuario = ? AND u.contrasena = ?";

        System.out.println(consulta);

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                resultado = new LoginResultadoDTO();

                u = new UsuarioModelo();
                u.setUsuario(rs.getString("usuario"));
                u.setPassword(rs.getString("contrasena"));

                RolModelo rol = new RolModelo();
                rol.setIdRol(rs.getInt("idRol"));
                rol.setRol(rs.getString("nombreRol"));

                resultado.setUsuario(u);
                resultado.setRol(rol);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

}
