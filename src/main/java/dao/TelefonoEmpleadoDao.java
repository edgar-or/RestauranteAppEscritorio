package dao;

import dao.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.ModeloEmpleado;
import modelo.ModeloTelefono;
/**
 *
 * @author ayala
 */
public class TelefonoEmpleadoDao {

    private static final String INSERTAR_TELEFONO = "insert into telefono (telefono, idempleado) values (?, ?)";


    private static final String LISTAR_TELEFONOS_POR_EMPLEADO =   "select idtelefono, telefono, idempleado from telefono where idempleado = ? order by idtelefono";

    private static final String ELIMINAR_TELEFONO =  "delete from telefono where idtelefono = ?";

    public void insertar(ModeloTelefono telefono) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(INSERTAR_TELEFONO);
            ps.setString(1, telefono.getTelefono());
            ps.setInt(2, telefono.getEmpleado().getIdEmpleado());

            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }



    public ArrayList<ModeloTelefono> listar(int idEmpleado) throws Exception {
        ArrayList<ModeloTelefono> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_TELEFONOS_POR_EMPLEADO);
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModeloTelefono telefono = new ModeloTelefono();
                telefono.setIdTelefono(rs.getInt("idtelefono"));
                telefono.setTelefono(rs.getString("telefono"));
                ModeloEmpleado emp = new ModeloEmpleado();
                emp.setIdEmpleado(rs.getInt("idempleado"));
                telefono.setEmpleado(emp);
                lista.add(telefono);
            }

            rs.close();
            ps.close();
        } finally {
            conn.close();
        }
        return lista;
    }

    public void eliminar(int idTelefono) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(ELIMINAR_TELEFONO);
            ps.setInt(1, idTelefono);

            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }
}