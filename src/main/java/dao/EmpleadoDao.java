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
import java.util.HashSet;
import java.util.List;
import modelo.AreaProduccionModelo;
import modelo.ModeloEmpleado;
import modelo.RolModelo;
import modelo.UsuarioModelo;

/**
 *
 * @author ayala
 */
public class EmpleadoDao {

    private static String INSERT_USUARIO = "INSERT INTO usuario(usuario, contrasena) VALUES (?, ?) RETURNING idusuario";
    private static String INSERT_EMPLEADO = "INSERT INTO empleado(dui, nombre, apellido, idusuario, idrol, idproduccion) VALUES (?, ?, ?, ?, ?,?)";
    private static String LISTAR_EMPLEADOS
            = "SELECT e.idempleado, e.dui, e.nombre, e.apellido, u.idusuario, u.usuario, u.contrasena, r.idrol, r.rol FROM empleado e"
            + " INNER JOIN usuario u ON e.idusuario = u.idusuario INNER JOIN rol r ON e.idrol = r.idrol";

    private static final String UPDATE_EMPLEADO
            = "UPDATE empleado SET dui = ?, nombre = ?, apellido = ?, idrol = ?, idproduccion = ? "
            + "WHERE idempleado = ?";

    private static final String DELETE_EMPLEADO
            = "DELETE FROM empleado WHERE idempleado = ?";

    private static final String BUSCAR
            = "SELECT e.idempleado, e.dui, e.nombre, e.apellido, "
            + "u.idusuario, u.usuario, u.contrasena, r.idrol, r.rol "
            + "FROM empleado e "
            + "INNER JOIN usuario u ON e.idusuario = u.idusuario "
            + "INNER JOIN rol r ON e.idrol = r.idrol "
            + "WHERE LOWER(e.nombre) LIKE LOWER(?) OR LOWER(e.apellido) LIKE LOWER(?) "
            + "OR e.dui LIKE ? ORDER BY e.idempleado";

    private static final String LISTAR_AREAS = "select * from area_produccion ap";

    public boolean insertar(ModeloEmpleado emp) throws Exception {

        Connection conn = Conexion.getConnection();

        try {
            conn.setAutoCommit(false);

            //Generar usuario y contraseña automáticamente
            String usuarioGen = generarUsuario(emp.getNombre(), emp.getApellido());

            String passGen = generarContrasena(emp.getApellido());

            //Insertar usuario
            PreparedStatement psUser = conn.prepareStatement(
                    INSERT_USUARIO
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
                    INSERT_EMPLEADO
            );

            psEmp.setString(1, emp.getDui());
            psEmp.setString(2, emp.getNombre());
            psEmp.setString(3, emp.getApellido());
            psEmp.setInt(4, idUsuario);
            psEmp.setInt(5, emp.getRol().getIdRol());
            psEmp.setInt(6, emp.getArea().getIdProduccion()); 

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

    public List<ModeloEmpleado> listar() throws Exception {

        List<ModeloEmpleado> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_EMPLEADOS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ModeloEmpleado empleado = new ModeloEmpleado();

                empleado.setUsuario(new UsuarioModelo());
                empleado.setRol(new RolModelo());

                //Empleado
                empleado.setIdEmpleado(rs.getInt("idempleado"));
                empleado.setDui(rs.getString("dui"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));

                //Usuario
                empleado.getUsuario().setUsuario(rs.getString("usuario"));
                empleado.getUsuario().setPassword(rs.getString("contrasena"));

                //Rol
                empleado.getRol().setIdRol(rs.getInt("idrol"));
                empleado.getRol().setRol(rs.getString("rol"));

                lista.add(empleado);
            }

            rs.close();
            ps.close();

        } finally {
            conn.close();
        }

        return lista;
    }

    public boolean actualizar(ModeloEmpleado emp) throws Exception {
        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_EMPLEADO)) {
            ps.setString(1, emp.getDui());
            ps.setString(2, emp.getNombre());
            ps.setString(3, emp.getApellido());
            ps.setInt(4, emp.getRol().getIdRol());
            ps.setInt(5, emp.getArea().getIdProduccion());
            ps.setInt(6, emp.getIdEmpleado());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idEmpleado) throws Exception {
        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_EMPLEADO)) {
            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ModeloEmpleado> buscar(String texto) throws Exception {
        List<ModeloEmpleado> lista = new ArrayList<>();
        try (Connection conn = Conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR)) {
            String filtro = "%" + texto + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    private ModeloEmpleado mapear(ResultSet rs) throws Exception {
        ModeloEmpleado empleado = new ModeloEmpleado();
        UsuarioModelo usu = new UsuarioModelo();
        RolModelo rol = new RolModelo();
        empleado.setIdEmpleado(rs.getInt("idempleado"));
        empleado.setDui(rs.getString("dui"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellido(rs.getString("apellido"));
        usu.setId_usuario(rs.getInt("idusuario"));

        usu.setUsuario(rs.getString("usuario"));
        usu.setPassword(rs.getString("contrasena"));

        rol.setIdRol(rs.getInt("idrol"));
        rol.setRol(rs.getString("rol"));

        empleado.setUsuario(usu);
        empleado.setRol(rol);

        return empleado;
    }

    public ArrayList<AreaProduccionModelo> listarAreas() throws Exception {

        ArrayList<AreaProduccionModelo> lista = new ArrayList<>();

        Connection conn = Conexion.getConnection();

        try {

            PreparedStatement ps = conn.prepareStatement(LISTAR_AREAS);

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

}
