package controlador;

import dao.EmpleadoDao;
import dao.dto.EmpleadoDTO;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloEmpleado;
import modelo.RolModelo;
import vista.VistaGestionEmpleados;
import vista.VistaPrincipal;

public class ControladorEmpleado {

    private VistaPrincipal vis;
    private VistaGestionEmpleados visEmpleados;

    public ControladorEmpleado(VistaPrincipal vis) {
        this.vis = vis;
    }

    public void abrirVistaEmpleados() {

        this.visEmpleados = new VistaGestionEmpleados();

        //EVENTO GUARDAR
        visEmpleados.btnGuardar.addActionListener(e -> guardarEmpleado());

        //CARGAR TABLA AL ABRIR
        listarEmpleados();

        visEmpleados.setVisible(true);
    }

    //GUARDAR
    private void guardarEmpleado() {

        try {
            EmpleadoDTO dto = new EmpleadoDTO();

            //Empleado
            ModeloEmpleado emp = dto.getEmpleado();
            emp.setNombre(visEmpleados.txtNombre.getText());
            emp.setApellido(visEmpleados.txtApellido.getText());
            emp.setDui(visEmpleados.txtDpi.getText());

            //Rol desde combo
            RolModelo rol = dto.getRol();
            rol.setIdRol(visEmpleados.comboCargo.getSelectedIndex() + 1); 

            EmpleadoDao dao = new EmpleadoDao();

            boolean ok = dao.insertar(dto);

            if (ok) {
                javax.swing.JOptionPane.showMessageDialog(null, "Empleado guardado");

                limpiarCampos();
                listarEmpleados();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //LISTAR EN TABLA
    private void listarEmpleados() {

        try {
            EmpleadoDao dao = new EmpleadoDao();
            List<EmpleadoDTO> lista = dao.listar();

            DefaultTableModel modelo = (DefaultTableModel) visEmpleados.tablaEmpleados.getModel();
            modelo.setRowCount(0);

            for (EmpleadoDTO dto : lista) {

                modelo.addRow(new Object[]{
                    dto.getEmpleado().getIdEmpleado(),
                    dto.getEmpleado().getNombre(),
                    dto.getEmpleado().getApellido(),
                    dto.getEmpleado().getDui(),
                    dto.getUsuario().getUsuario(),
                    dto.getUsuario().getPassword()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //LIMPIAR CAMPOS
    private void limpiarCampos() {
        visEmpleados.txtNombre.setText("");
        visEmpleados.txtApellido.setText("");
        visEmpleados.txtDpi.setText("");
    }
}