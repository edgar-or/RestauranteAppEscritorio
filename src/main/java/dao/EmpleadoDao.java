package dao;

import dao.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.AreaProduccionModelo;
import modelo.ModeloEmpleado;
import modelo.RolModelo;
import modelo.UsuarioModelo;

public class EmpleadoDao {

    private static final String INSERT_USUARIO = 
        "insert into usuario (usuario, contrasena) values (?, ?) returning idusuario";

    private static final String INSERT_EMPLEADO = 
        "insert into empleado (dui, nombre, apellido, idusuario, idrol, idproduccion) values (?, ?, ?, ?, ?, ?)";

    private static final String LISTAR_EMPLEADOS = 
        "select e.idempleado, e.dui, e.nombre, e.apellido, u.idusuario, u.usuario, u.contrasena, r.idrol, r.rol " +
        "from empleado e " +
        "inner join usuario u on e.idusuario = u.idusuario " +
        "inner join rol r on e.idrol = r.idrol " +
        "order by e.idempleado";

    private static final String ACTUALIZAR_EMPLEADO = 
        "update empleado set dui = ?, nombre = ?, apellido = ?, idrol = ?, idproduccion = ? where idempleado = ?";

    private static final String ELIMINAR_EMPLEADO = 
        "delete from empleado where idempleado = ?";

    private static final String BUSCAR_EMPLEADO = 
        "select e.idempleado, e.dui, e.nombre, e.apellido, u.idusuario, u.usuario, u.contrasena, r.idrol, r.rol " +
        "from empleado e " +
        "inner join usuario u on e.idusuario = u.idusuario " +
        "inner join rol r on e.idrol = r.idrol " +
        "where lower(e.nombre) like lower(?) or lower(e.apellido) like lower(?) or e.dui like ? " +
        "order by e.idempleado";

    private static final String LISTAR_AREAS = "select idproduccion, nombre from area_produccion";

    public boolean insertar(ModeloEmpleado emp) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            conn.setAutoCommit(false);

            String usuarioGen = generarUsuario(emp.getNombre(), emp.getApellido());
            String passGen = generarContrasena(emp.getApellido());

            PreparedStatement psUsuario = conn.prepareStatement(INSERT_USUARIO);
            psUsuario.setString(1, usuarioGen);
            psUsuario.setString(2, passGen);

            ResultSet rs = psUsuario.executeQuery();
            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt("idusuario");
            }
            rs.close();
            psUsuario.close();

            PreparedStatement psEmpleado = conn.prepareStatement(INSERT_EMPLEADO);
            psEmpleado.setString(1, emp.getDui());
            psEmpleado.setString(2, emp.getNombre());
            psEmpleado.setString(3, emp.getApellido());
            psEmpleado.setInt(4, idUsuario);
            psEmpleado.setInt(5, emp.getRol().getIdRol());
            psEmpleado.setInt(6, emp.getArea().getIdProduccion());

            psEmpleado.executeUpdate();
            psEmpleado.close();

            conn.commit();
            return true;

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public ArrayList<ModeloEmpleado> listar() throws Exception {
        ArrayList<ModeloEmpleado> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_EMPLEADOS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModeloEmpleado empleado = new ModeloEmpleado();
                empleado.setUsuario(new UsuarioModelo());
                empleado.setRol(new RolModelo());

                empleado.setIdEmpleado(rs.getInt("idempleado"));
                empleado.setDui(rs.getString("dui"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));

                empleado.getUsuario().setId_usuario(rs.getInt("idusuario"));
                empleado.getUsuario().setUsuario(rs.getString("usuario"));
                empleado.getUsuario().setPassword(rs.getString("contrasena"));

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
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(ACTUALIZAR_EMPLEADO);
            ps.setString(1, emp.getDui());
            ps.setString(2, emp.getNombre());
            ps.setString(3, emp.getApellido());
            ps.setInt(4, emp.getRol().getIdRol());
            ps.setInt(5, emp.getArea().getIdProduccion());
            ps.setInt(6, emp.getIdEmpleado());

            boolean actualizado = ps.executeUpdate() > 0;
            ps.close();
            return actualizado;
        } finally {
            conn.close();
        }
    }

    public boolean eliminar(int idEmpleado) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(ELIMINAR_EMPLEADO);
            ps.setInt(1, idEmpleado);

            boolean eliminado = ps.executeUpdate() > 0;
            ps.close();
            return eliminado;
        } finally {
            conn.close();
        }
    }

    public ArrayList<ModeloEmpleado> buscar(String texto) throws Exception {
        ArrayList<ModeloEmpleado> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(BUSCAR_EMPLEADO);
            String filtro = "%" + texto + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModeloEmpleado empleado = new ModeloEmpleado();
                empleado.setUsuario(new UsuarioModelo());
                empleado.setRol(new RolModelo());

                empleado.setIdEmpleado(rs.getInt("idempleado"));
                empleado.setDui(rs.getString("dui"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));

                empleado.getUsuario().setId_usuario(rs.getInt("idusuario"));
                empleado.getUsuario().setUsuario(rs.getString("usuario"));
                empleado.getUsuario().setPassword(rs.getString("contrasena"));

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

    private String generarUsuario(String nombre, String apellido) {
        String inicial = nombre.substring(0, 1).toLowerCase();
        String ape = apellido.toLowerCase().replaceAll(" ", "");
        int numero = (int) (Math.random() * 900 + 100);
        return inicial + ape + numero;
    }

    private String generarContrasena(String apellido) {
        return apellido.toLowerCase().replaceAll(" ", "") + "123";
    }
}