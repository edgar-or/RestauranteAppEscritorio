/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import dao.ProductosParaPedidosDao;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloProducto;
import vista.VistaProductos;
/**
 *
 * @author ayala
 */
public class ControladorAgregarProductos {
 
    private final VistaProductos visProduct;
    private final ControladorAgregarPedido controlPedido;
    private final ProductosParaPedidosDao dao;
 
    // idProduccion por categoría (según tu BD)
    private static final int ID_PLATILLOS = 1;
    private static final int ID_POSTRES   = 2;
    private static final int ID_BEBIDAS   = 3;
    private static final int ID_ENTRADAS  = 4;
 
    public ControladorAgregarProductos(VistaProductos visProduct, ControladorAgregarPedido controlPedido) {
        this.visProduct    = visProduct;
        this.controlPedido = controlPedido;
        this.dao           = new ProductosParaPedidosDao();
 
        cargarTablas();
        registrarEventos();
    }
 
    private void cargarTablas() {
        cargar(visProduct.tablaBebidas,  ID_BEBIDAS);
        cargar(visProduct.tablaPlatillos, ID_PLATILLOS);
        cargar(visProduct.tablaPostres,  ID_POSTRES);
        cargar(visProduct.tablaEntradas, ID_ENTRADAS);
    }
 
    private void cargar(javax.swing.JTable tabla, int idProduccion) {
        try {
            llenarTabla(tabla, dao.listarPorProduccion(idProduccion));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void llenarTabla(javax.swing.JTable tabla, List<ModeloProducto> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (ModeloProducto p : lista) {
            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio()
            });
        }
    }
 
    private void registrarEventos() {
 
        // --- BEBIDAS ---
        visProduct.btnBuscarB.addActionListener(e ->
            buscar(ID_BEBIDAS, visProduct.txtbuscarB.getText().trim(),
                   visProduct.tablaBebidas));
 
        visProduct.btnBuscarB.addActionListener(e -> agregar(visProduct.tablaBebidas));
 
        // --- PLATILLOS ---
        visProduct.btnBuscarPlatillos.addActionListener(e ->
            buscar(ID_PLATILLOS, visProduct.txtBuscarPlatillos.getText().trim(),
                   visProduct.tablaPlatillos));
 
        visProduct.btnAgregarPlatillos.addActionListener(e -> agregar(visProduct.tablaPlatillos));
 
        // --- POSTRES ---
        visProduct.btnBuscarPostre.addActionListener(e ->
            buscar(ID_POSTRES, visProduct.txtBuscarPostre.getText().trim(),
                   visProduct.tablaPostres));
 
        visProduct.btnAgregarPostres.addActionListener(e -> agregar(visProduct.tablaPostres));
 
        // --- ENTRADAS ---
        visProduct.btnBuscarEntrdas.addActionListener(e ->
            buscar(ID_ENTRADAS, visProduct.txtBuscarEntradas.getText().trim(),
                   visProduct.tablaEntradas));
 
        visProduct.btnAgregarEntrdas.addActionListener(e -> agregar(visProduct.tablaEntradas));
 
        // --- CERRAR ---
        visProduct.btnCerrar.addActionListener(e -> visProduct.dispose());
    }
 
    private void buscar(int idProduccion, String texto, javax.swing.JTable tabla) {
        try {
            List<ModeloProducto> lista = texto.isEmpty()
                ? dao.listarPorProduccion(idProduccion)
                : dao.buscarPorNombre(idProduccion, texto);
            llenarTabla(tabla, lista);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(visProduct,
                "Error al buscar: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void agregar(javax.swing.JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(visProduct,
                "Selecciona un producto de la tabla.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int    idProducto = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
        String nombre     = modelo.getValueAt(fila, 1).toString();
        double precio     = Double.parseDouble(modelo.getValueAt(fila, 3).toString());
 
        // Pasar el producto seleccionado al controlador principal
        controlPedido.setProductoSeleccionado(idProducto, nombre, precio);
 
        JOptionPane.showMessageDialog(visProduct,
            "\"" + nombre + "\" seleccionado.\nAgrega la cantidad y notas en la ventana principal.",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        // Cierra la ventana de búsqueda para volver a la principal
        visProduct.dispose(); 
    }
}