/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import modelo.AreaProduccionModelo;
import modelo.ModeloEmpleado;
import modelo.RolModelo;
import modelo.UsuarioModelo;

/**
 *
 * @author ayala
 */
public class UsuarioDao {

    public ModeloEmpleado validar(String usuario, String password) {
        UsuarioModelo u = null;
        ModeloEmpleado emp = null;

        String consulta = "SELECT u.*, r.idRol, r.rol AS nombreRol, ap.nombre as nombreArea, e.idempleado as idEmpleado"
                + " FROM usuario u"
                + " LEFT JOIN empleado e ON u.idUsuario = e.idUsuario"
                + " LEFT JOIN rol r ON e.idRol = r.idRol"
                + " LEFT join area_produccion ap ON e.idproduccion = ap.idproduccion"
                + " WHERE u.usuario = ? AND u.contrasena = ?";

        try {
            Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                emp = new ModeloEmpleado(); 

                u = new UsuarioModelo();
                u.setUsuario(rs.getString("usuario"));
                u.setPassword(rs.getString("contrasena"));

                RolModelo rol = new RolModelo();
                rol.setIdRol(rs.getInt("idRol"));
                rol.setRol(rs.getString("nombreRol"));

                AreaProduccionModelo area = new AreaProduccionModelo();
                area.setNombre(rs.getString("nombreArea"));

                emp.setIdEmpleado(rs.getInt("idEmpleado"));
                emp.setRol(rol);
                emp.setUsuario(u);
                emp.setArea(area);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emp;
    }

}
