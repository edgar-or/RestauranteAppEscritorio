package dao;

import dao.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.ModeloEmpleado;
import modelo.ModeloCorreo;

/**
 *
 * @author ayala
 */
public class CorreoEmpleadoDao {

    private static final String INSERTAR_CORREO = "insert into correo (correo, idempleado) values (?, ?)";
    private static final String LISTAR_CORREOS_POR_EMPLEADO = "select idcorreo, correo, idempleado from correo where idempleado = ? order by idcorreo";
    private static final String ELIMINAR_CORREO = "delete from correo where idcorreo = ?";

    public void insertar(ModeloCorreo correo) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(INSERTAR_CORREO);
            ps.setString(1, correo.getCorreo());
            ps.setInt(2, correo.getEmpleado().getIdEmpleado());
            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }

    public ArrayList<ModeloCorreo> listar(int idEmpleado) throws Exception {
        ArrayList<ModeloCorreo> lista = new ArrayList<>();
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(LISTAR_CORREOS_POR_EMPLEADO);
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModeloCorreo correo = new ModeloCorreo();
                correo.setIdCorreo(rs.getInt("idcorreo"));
                correo.setCorreo(rs.getString("correo"));
                ModeloEmpleado emp = new ModeloEmpleado();
                emp.setIdEmpleado(rs.getInt("idempleado"));
                correo.setEmpleado(emp);
                lista.add(correo);
            }
            rs.close();
            ps.close();
        } finally {
            conn.close();
        }
        return lista;
    }

    public void eliminar(int idCorreo) throws Exception {
        Connection conn = Conexion.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(ELIMINAR_CORREO);
            ps.setInt(1, idCorreo);
            ps.executeUpdate();
            ps.close();
        } finally {
            conn.close();
        }
    }
}