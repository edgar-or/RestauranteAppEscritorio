package controlador;

import dao.CorreoEmpleadoDao;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloCorreo;
import modelo.ModeloEmpleado;
import vista.VistaCorreo;

public class ControladorCorreo {

    private VistaCorreo visCorreo;
    private ModeloEmpleado empleado;
    private CorreoEmpleadoDao correoDao = new CorreoEmpleadoDao();

    public ControladorCorreo(ModeloEmpleado empleado) {
        this.empleado = empleado;
        iniciar();
    }

    private void iniciar() {
        visCorreo = new VistaCorreo();

        configurarColumnas();
        eventos();
        mostrarDatosEmpleado();
        listarCorreos();

        visCorreo.setVisible(true);
        visCorreo.setLocationRelativeTo(null);
    }

    private void configurarColumnas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Correo");
        visCorreo.tablaCorreo.setModel(modelo);
    }

    private void mostrarDatosEmpleado() {
        visCorreo.labelEmp.setText("Correos de : "+empleado.getNombre() + " " + empleado.getApellido());
    }

    private void eventos() {
        visCorreo.btnRegistrar.addActionListener(e -> registrarCorreo());
        visCorreo.btnEliminar.addActionListener(e -> eliminarCorreo());
        visCorreo.btnCerrar.addActionListener(e-> {visCorreo.dispose();});
    }

    private void listarCorreos() {
        try {
            ArrayList<ModeloCorreo> correos = correoDao.listar(empleado.getIdEmpleado());
            cargarTabla(correos);
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void cargarTabla(ArrayList<ModeloCorreo> correos) {
        DefaultTableModel modelo = (DefaultTableModel) visCorreo.tablaCorreo.getModel();
        modelo.setRowCount(0);

        for (ModeloCorreo c : correos) {
            modelo.addRow(new Object[]{
                c.getIdCorreo(),
                c.getCorreo()
            });
        }
    }

    private void registrarCorreo() {
        try {
            String correoTexto = visCorreo.txtCorreo.getText().trim();

            if (correoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(visCorreo, "Ingresa un correo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ModeloCorreo correo = new ModeloCorreo();
            correo.setCorreo(correoTexto);
            correo.setEmpleado(empleado);

            correoDao.insertar(correo);

            JOptionPane.showMessageDialog(visCorreo, "Correo registrado correctamente.");
            visCorreo.txtCorreo.setText("");
            listarCorreos();

        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void eliminarCorreo() {
        try {
            int fila = visCorreo.tablaCorreo.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(visCorreo, "Selecciona un correo de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                visCorreo,
                "¿Está seguro de eliminar este correo?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) visCorreo.tablaCorreo.getModel();
            int idCorreo = (int) modelo.getValueAt(fila, 0);

            correoDao.eliminar(idCorreo);

            JOptionPane.showMessageDialog(visCorreo, "Correo eliminado correctamente.");
            listarCorreos();

        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void mostrarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(visCorreo, "Error: " + e.getMessage());
    }
}