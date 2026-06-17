/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.MenuDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AreaProduccionModelo;
import modelo.ModeloProducto;
import modelo.ModeloProducto_Pedido;
import vista.VistaGestionMenu;

/**
 *
 * @author ayala
 */
public class ControladorGestionMenu {

    private VistaGestionMenu vista;

    private MenuDao menuDao = new MenuDao();

    public ControladorGestionMenu() throws Exception {

        iniciarVista();

        eventos();
        configurarColumnas();
        listarMenu();
        cargarComboAreas();

    }

    private void eventos() {

        vista.btnCerrar.addActionListener(e -> {
            vista.dispose();
        });

        vista.btnGuardar.addActionListener(e -> {
            guardar();
        });

        vista.tablaMenu.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });

        vista.btnNuevo.addActionListener(e -> {
            limpiarCampos();
        });

        vista.btnLimpiar.addActionListener(e -> {
            limpiarCampos();
        });

        vista.btnActualizar.addActionListener(e -> {
            actualizar();
        });
        
        vista.btnEliminar.addActionListener(e-> {eliminar();});
        
        vista.btnBuscar.addActionListener( e-> { buscar();});

    }

    public void iniciarVista() {
        vista = new VistaGestionMenu();

        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }

    private void configurarColumnas() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMenu.getModel();
        modelo.addColumn("ID");
        modelo.addColumn("Producto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Area de produccion");
        modelo.addColumn("Precio");
    }

    private void listarMenu() throws Exception {

        ArrayList<ModeloProducto> productos = (ArrayList<ModeloProducto>) menuDao.listarMenu();

        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMenu.getModel();

        modelo.setRowCount(0);

        for (ModeloProducto p : productos) {

            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getAreaProduccion().getNombre(),
                p.getPrecio()
            });

        }

    }

    private void cargarComboAreas() throws Exception {
        ArrayList<AreaProduccionModelo> areas = menuDao.listarcategorias();

        DefaultComboBoxModel<AreaProduccionModelo> modelo = new DefaultComboBoxModel<>();
        for (AreaProduccionModelo area : areas) {
            modelo.addElement(area);
        }

        vista.comboFiltroCategoria.setModel(modelo);
    }

    private void guardar() {
        try {
            String nombre = vista.txtNombre.getText().trim();
            String descripcion = vista.txtDescripcion.getText().trim();
            String precioTexto = vista.txtPrecio.getText().trim();

            if (nombre.isEmpty() || precioTexto.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Nombre y precio son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(precioTexto);

            AreaProduccionModelo areaSeleccionada = (AreaProduccionModelo) vista.comboFiltroCategoria.getSelectedItem();
            if (areaSeleccionada == null) {
                JOptionPane.showMessageDialog(vista, "Selecciona una categoría.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ModeloProducto producto = new ModeloProducto();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setAreaProduccion(areaSeleccionada);

            menuDao.insertar(producto);

            JOptionPane.showMessageDialog(vista, "Producto guardado correctamente.");
            limpiarCampos();
            listarMenu();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vista.txtId.setText("");
        vista.comboFiltroCategoria.setSelectedIndex(0);
        vista.txtNombre.setText("");
        vista.txtDescripcion.setText("");
        vista.txtPrecio.setText("");
    }

    private void seleccionarFila() {
        int fila = vista.tablaMenu.getSelectedRow();
        if (fila == -1) {
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMenu.getModel();

        Object id = modelo.getValueAt(fila, 0);
        String nombre = (String) modelo.getValueAt(fila, 1);
        String descripcion = (String) modelo.getValueAt(fila, 2);
        String nombreCategoria = (String) modelo.getValueAt(fila, 3);
        Object precio = modelo.getValueAt(fila, 4);

        vista.txtId.setText(String.valueOf(id));
        vista.txtNombre.setText(nombre);
        vista.txtDescripcion.setText(descripcion);
        vista.txtPrecio.setText(String.valueOf(precio));

        seleccionarCategoriaEnCombo(nombreCategoria);
    }

    private void seleccionarCategoriaEnCombo(String nombreCategoria) {
        for (int i = 0; i < vista.comboFiltroCategoria.getItemCount(); i++) {
            AreaProduccionModelo area = (AreaProduccionModelo) vista.comboFiltroCategoria.getItemAt(i);
            if (area.getNombre().equalsIgnoreCase(nombreCategoria)) {
                vista.comboFiltroCategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    private void actualizar() {
        try {
            String id = vista.txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Selecciona un producto de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nombre = vista.txtNombre.getText().trim();
            String descripcion = vista.txtDescripcion.getText().trim();
            String precioTexto = vista.txtPrecio.getText().trim();

            if (nombre.isEmpty() || precioTexto.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Nombre y precio son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(precioTexto);

            AreaProduccionModelo areaSeleccionada = (AreaProduccionModelo) vista.comboFiltroCategoria.getSelectedItem();
            if (areaSeleccionada == null) {
                JOptionPane.showMessageDialog(vista, "Selecciona una categoría.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ModeloProducto producto = new ModeloProducto();
            producto.setIdProducto(id);
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setAreaProduccion(areaSeleccionada);

            menuDao.modificar(producto);

            JOptionPane.showMessageDialog(vista, "Producto actualizado correctamente.");
            limpiarCampos();
            listarMenu();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        try {
            String idTexto = vista.txtId.getText().trim();
            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Selecciona un producto de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Está seguro de eliminar este producto?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            int id = Integer.parseInt(idTexto);
            menuDao.eliminar(id);

            JOptionPane.showMessageDialog(vista, "Producto eliminado correctamente.");
            limpiarCampos();
            listarMenu();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void buscar() {
    try {
        String busca = vista.txtBuscar.getText().trim();
        
        ArrayList<ModeloProducto> productos;
        if (busca.isEmpty()) {
            productos = (ArrayList<ModeloProducto>) menuDao.listarMenu();
        } else {
            productos = menuDao.buscarPorNombre(busca);
        }
        
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMenu.getModel();
        modelo.setRowCount(0);
        
        for (ModeloProducto p : productos) {
            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getAreaProduccion().getNombre(),
                p.getPrecio()
            });
        }
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(vista, "Error al buscar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
