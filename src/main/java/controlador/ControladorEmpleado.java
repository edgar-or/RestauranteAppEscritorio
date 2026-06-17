package controlador;

import dao.EmpleadoDao;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AreaProduccionModelo;
import modelo.ModeloEmpleado;
import modelo.RolModelo;
import vista.VistaGestionEmpleados;
import vista.VistaPrincipal;
/**
 *
 * @author ayala
 */
public class ControladorEmpleado {

    private VistaPrincipal vis;
    private VistaGestionEmpleados visEmpleados;
    private EmpleadoDao empleadoDao = new EmpleadoDao();
    private ControladorTelefonoEmpleado controlTel; 
    private ControladorCorreo controlCorreo;

    public ControladorEmpleado(VistaPrincipal vis) {
        this.vis = vis;
    }

    public void abrirVistaEmpleados() throws Exception {
        visEmpleados = new VistaGestionEmpleados();
        eventos();
        cargarComboArea();
        listarEmpleados();
        visEmpleados.setVisible(true);
        visEmpleados.setLocationRelativeTo(null);
    }

    private void eventos() {
        visEmpleados.btnGuardar.addActionListener(e -> guardarEmpleado());
        visEmpleados.btnActualizar.addActionListener(e -> actualizarEmpleado());
        visEmpleados.btnNuevo.addActionListener(e -> limpiarCampos());
        visEmpleados.btnLimpiar.addActionListener(e -> limpiarCampos());
        visEmpleados.btnVerEmpleados.addActionListener(e -> listarEmpleados());
        visEmpleados.btnCerrar.addActionListener(e -> visEmpleados.dispose());
        visEmpleados.btnBuscar.addActionListener(e -> buscar());

        visEmpleados.tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });
        
        
    visEmpleados.btnTelefono.addActionListener(e -> abrirVistaTelefonos());
        visEmpleados.btnCorreo.addActionListener(e -> abrirVistaCorreos());

        
        
    }

    private void cargarComboArea() {
        try {
            ArrayList<AreaProduccionModelo> areas = empleadoDao.listarAreas();

            visEmpleados.comboCargo.removeAllItems();
            for (AreaProduccionModelo area : areas) {
                visEmpleados.comboCargo.addItem(area);
            }
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void listarEmpleados() {
        try {
            ArrayList<ModeloEmpleado> empleados = empleadoDao.listar();
            cargarTabla(empleados);
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void cargarTabla(ArrayList<ModeloEmpleado> empleados) {
        DefaultTableModel modelo = (DefaultTableModel) visEmpleados.tablaEmpleados.getModel();
        modelo.setRowCount(0);

        for (ModeloEmpleado empleado : empleados) {
            modelo.addRow(new Object[]{
                empleado.getIdEmpleado(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getDui(),
                empleado.getUsuario().getUsuario(),
                empleado.getUsuario().getPassword()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = visEmpleados.tablaEmpleados.getSelectedRow();
        if (fila == -1) {
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) visEmpleados.tablaEmpleados.getModel();

        Object id = modelo.getValueAt(fila, 0);
        Object nombre = modelo.getValueAt(fila, 1);
        Object apellido = modelo.getValueAt(fila, 2);
        Object dui = modelo.getValueAt(fila, 3);

        visEmpleados.txtId.setText(String.valueOf(id));
        visEmpleados.txtNombre.setText(String.valueOf(nombre));
        visEmpleados.txtApellido.setText(String.valueOf(apellido));
        visEmpleados.txtDpi.setText(String.valueOf(dui));
    }

    private void guardarEmpleado() {
        try {
            String nombre = visEmpleados.txtNombre.getText().trim();
            String apellido = visEmpleados.txtApellido.getText().trim();
            String dui = visEmpleados.txtDpi.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || dui.isEmpty()) {
                JOptionPane.showMessageDialog(visEmpleados, "Nombre, apellido y DUI son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            AreaProduccionModelo area = (AreaProduccionModelo) visEmpleados.comboCargo.getSelectedItem();
            if (area == null) {
                JOptionPane.showMessageDialog(visEmpleados, "Selecciona un cargo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            RolModelo rol = new RolModelo();
            rol.setIdRol(2);

            ModeloEmpleado emp = new ModeloEmpleado();
            emp.setNombre(nombre);
            emp.setApellido(apellido);
            emp.setDui(dui);
            emp.setArea(area);
            emp.setRol(rol);

            boolean guardado = empleadoDao.insertar(emp);

            if (guardado) {
                JOptionPane.showMessageDialog(visEmpleados, "Empleado guardado correctamente.");
                limpiarCampos();
                listarEmpleados();
            }

        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void actualizarEmpleado() {
        try {
            String idTexto = visEmpleados.txtId.getText().trim();
            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(visEmpleados, "Selecciona un empleado de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nombre = visEmpleados.txtNombre.getText().trim();
            String apellido = visEmpleados.txtApellido.getText().trim();
            String dui = visEmpleados.txtDpi.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || dui.isEmpty()) {
                JOptionPane.showMessageDialog(visEmpleados, "Nombre, apellido y DUI son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            AreaProduccionModelo area = (AreaProduccionModelo) visEmpleados.comboCargo.getSelectedItem();
            if (area == null) {
                JOptionPane.showMessageDialog(visEmpleados, "Selecciona un cargo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idEmpleado = Integer.parseInt(idTexto);

            RolModelo rol = new RolModelo();
            rol.setIdRol(2);

            ModeloEmpleado emp = new ModeloEmpleado();
            emp.setIdEmpleado(idEmpleado);
            emp.setNombre(nombre);
            emp.setApellido(apellido);
            emp.setDui(dui);
            emp.setArea(area);
            emp.setRol(rol);

            boolean actualizado = empleadoDao.actualizar(emp);

            if (actualizado) {
                JOptionPane.showMessageDialog(visEmpleados, "Empleado actualizado correctamente.");
                limpiarCampos();
                listarEmpleados();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(visEmpleados, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void buscar() {
        try {
            String textoBusqueda = visEmpleados.txtBuscar.getText().trim();

            ArrayList<ModeloEmpleado> empleados;
            if (textoBusqueda.isEmpty()) {
                empleados = empleadoDao.listar();
            } else {
                empleados = empleadoDao.buscar(textoBusqueda);
            }

            cargarTabla(empleados);

        } catch (Exception e) {
            mostrarError(e);
        }
    }

    private void limpiarCampos() {
        visEmpleados.txtId.setText("");
        visEmpleados.txtNombre.setText("");
        visEmpleados.txtApellido.setText("");
        visEmpleados.txtDpi.setText("");
        visEmpleados.tablaEmpleados.clearSelection();
    }
    
    private void abrirVistaTelefonos() {
    int fila = visEmpleados.tablaEmpleados.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(visEmpleados, "Selecciona un empleado de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    DefaultTableModel modelo = (DefaultTableModel) visEmpleados.tablaEmpleados.getModel();

    int idEmpleado = (int) modelo.getValueAt(fila, 0);
    String nombre = (String) modelo.getValueAt(fila, 1);
    String apellido = (String) modelo.getValueAt(fila, 2);

    ModeloEmpleado empleadoSeleccionado = new ModeloEmpleado();
    empleadoSeleccionado.setIdEmpleado(idEmpleado);
    empleadoSeleccionado.setNombre(nombre);
    empleadoSeleccionado.setApellido(apellido);

    controlTel = new ControladorTelefonoEmpleado(empleadoSeleccionado);
}
    
    private void abrirVistaCorreos() {
    int fila = visEmpleados.tablaEmpleados.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(visEmpleados, "Selecciona un empleado de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    DefaultTableModel modelo = (DefaultTableModel) visEmpleados.tablaEmpleados.getModel();

    int idEmpleado = (int) modelo.getValueAt(fila, 0);
    String nombre = (String) modelo.getValueAt(fila, 1);
    String apellido = (String) modelo.getValueAt(fila, 2);

    ModeloEmpleado empleadoSeleccionado = new ModeloEmpleado();
    empleadoSeleccionado.setIdEmpleado(idEmpleado);
    empleadoSeleccionado.setNombre(nombre);
    empleadoSeleccionado.setApellido(apellido);

    controlCorreo = new ControladorCorreo(empleadoSeleccionado);
}

    private void mostrarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(visEmpleados, "Error: " + e.getMessage());
    }
}