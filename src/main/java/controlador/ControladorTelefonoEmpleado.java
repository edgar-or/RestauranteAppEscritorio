package controlador;

import dao.TelefonoEmpleadoDao;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloEmpleado;
import modelo.ModeloTelefono;
import vista.VistaTelefono;
/**
 *
 * @author ayala
 */
public class ControladorTelefonoEmpleado {

    private VistaTelefono visTel;
    private ModeloEmpleado empleado;
    private TelefonoEmpleadoDao telefonoDao = new TelefonoEmpleadoDao();

    public ControladorTelefonoEmpleado(ModeloEmpleado empleado) {
        this.empleado = empleado;
        iniciar();
    }

    private void iniciar() {
        visTel = new VistaTelefono();

        configurarColumnas();
        eventos();
        mostrarDatosEmpleado();
        listarTelefonos();

        visTel.setVisible(true);
        visTel.setLocationRelativeTo(null);
    }

    private void configurarColumnas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Teléfono");
        visTel.tablaTel.setModel(modelo);
    }

    private void mostrarDatosEmpleado() {
        visTel.labelEmp.setText("Telefonos de: "+empleado.getNombre() + " " + empleado.getApellido());
    }

    private void eventos() {
        visTel.btnCerrar.addActionListener(e -> visTel.dispose());
        visTel.btnRegistrar.addActionListener(e -> registrarTelefono());
        visTel.btnEliminar.addActionListener(e -> eliminarTelefono());
    }

    private void listarTelefonos() {
        try {
            ArrayList<ModeloTelefono> telefonos = telefonoDao.listar(empleado.getIdEmpleado());
            cargarTabla(telefonos);
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void cargarTabla(ArrayList<ModeloTelefono> telefonos) {
        DefaultTableModel modelo = (DefaultTableModel) visTel.tablaTel.getModel();
        modelo.setRowCount(0);

        for (ModeloTelefono t : telefonos) {
            modelo.addRow(new Object[]{
                t.getIdTelefono(),
                t.getTelefono()
            });
        }
    }

    private void registrarTelefono() {
        try {
            String numero = visTel.txtTelefono.getText().trim();

            if (numero.isEmpty()) {
                JOptionPane.showMessageDialog(visTel, "Ingresa un número de teléfono.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ModeloTelefono telefono = new ModeloTelefono();
            telefono.setTelefono(numero);
            ModeloEmpleado emp = new ModeloEmpleado(); 
            emp.setIdEmpleado(empleado.getIdEmpleado());
            
            telefono.setEmpleado(emp);

            telefonoDao.insertar(telefono);

            JOptionPane.showMessageDialog(visTel, "Teléfono registrado correctamente.");
            visTel.txtTelefono.setText("");
            listarTelefonos();

        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void eliminarTelefono() {
        try {
            int fila = visTel.tablaTel.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(visTel, "Selecciona un teléfono de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                visTel,
                "¿Está seguro de eliminar este teléfono?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) visTel.tablaTel.getModel();
            int idTelefono = (int) modelo.getValueAt(fila, 0);

            telefonoDao.eliminar(idTelefono);

            JOptionPane.showMessageDialog(visTel, "Teléfono eliminado correctamente.");
            listarTelefonos();

        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void mostrarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(visTel, "Error: " + e.getMessage());
    }
}
    
    
    
    
    
    

