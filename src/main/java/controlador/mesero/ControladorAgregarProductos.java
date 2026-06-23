/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import dao.ProductosParaPedidosDao;
import dao.GenerarPedidoDao;
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

    private  VistaProductos visProduct;
    private  ControladorAgregarPedido controlPedido;
    private  ProductosParaPedidosDao dao;

    private int idPedidoExistente = -1;

    private static final int ID_PLATILLOS = 1;
    private static final int ID_POSTRES = 2;
    private static final int ID_BEBIDAS = 3;
    private static final int ID_ENTRADAS = 4;

    public ControladorAgregarProductos(VistaProductos visProduct, ControladorAgregarPedido controlPedido) {
        this.visProduct = visProduct;
        this.controlPedido = controlPedido;
        this.dao = new ProductosParaPedidosDao();

        configurarTablas(); 
        cargarTablas();
        registrarEventos();
    }

    
    public ControladorAgregarProductos(VistaProductos visProduct, int idPedidoExistente) {
        this.visProduct = visProduct;
        this.controlPedido = null;
        this.idPedidoExistente = idPedidoExistente;
        this.dao = new ProductosParaPedidosDao();

        configurarTablas(); 
        cargarTablas();
        registrarEventos();
    }

    private void configurarTablas() {
        configurarModeloYColumnas(visProduct.tablaBebidas);
        configurarModeloYColumnas(visProduct.tablaPlatillos);
        configurarModeloYColumnas(visProduct.tablaPostres);
        configurarModeloYColumnas(visProduct.tablaEntradas);
    }

    private void configurarModeloYColumnas(javax.swing.JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Descripción", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false; 
            }
        };
        tabla.setModel(modelo);

        var col = tabla.getColumnModel().getColumn(0);
        col.setMinWidth(0);
        col.setMaxWidth(0);
        col.setWidth(0);
        col.setPreferredWidth(0);
    }

    private void cargarTablas() {
        cargar(visProduct.tablaBebidas, ID_BEBIDAS);
        cargar(visProduct.tablaPlatillos, ID_PLATILLOS);
        cargar(visProduct.tablaPostres, ID_POSTRES);
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

        visProduct.btnBuscar.addActionListener(e
                -> buscar(ID_BEBIDAS, visProduct.txtbuscar.getText().trim(),
                        visProduct.tablaBebidas));

        visProduct.btnAgregarBebidas.addActionListener(e -> agregar(visProduct.tablaBebidas));

        visProduct.btnBuscarPlatillos.addActionListener(e
                -> buscar(ID_PLATILLOS, visProduct.txtBuscarPlatillos.getText().trim(),
                        visProduct.tablaPlatillos));

        visProduct.btnAgregarPlatillos.addActionListener(e -> agregar(visProduct.tablaPlatillos));

        visProduct.btnBuscarPostre.addActionListener(e
                -> buscar(ID_POSTRES, visProduct.txtBuscarPostre.getText().trim(),
                        visProduct.tablaPostres));

        visProduct.btnAgregarPostres.addActionListener(e -> agregar(visProduct.tablaPostres));

        visProduct.btnBuscarEntrdas.addActionListener(e
                -> buscar(ID_ENTRADAS, visProduct.txtBuscarEntradas.getText().trim(),
                        visProduct.tablaEntradas));

        visProduct.btnAgregarEntrdas.addActionListener(e -> agregar(visProduct.tablaEntradas));

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
            JOptionPane.showMessageDialog(
                    visProduct,
                    "Selecciona un producto de la tabla.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        Object idObj = modelo.getValueAt(fila, 0);

        int idProducto = 0;

        if (idObj != null && !idObj.toString().trim().isEmpty()) {
            idProducto = Integer.parseInt(idObj.toString().trim());
        }

        String nombre = modelo.getValueAt(fila, 1).toString();
        double precio = Double.parseDouble(modelo.getValueAt(fila, 3).toString());

        if (this.controlPedido != null) {

            controlPedido.setProductoSeleccionado(
                    idProducto,
                    nombre,
                    precio);

            JOptionPane.showMessageDialog(
                    visProduct,
                    "\"" + nombre + "\" seleccionado.\nAgrega la cantidad y notas en la ventana principal.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } else if (this.idPedidoExistente > 0) {

            try {

                String cantStr = JOptionPane.showInputDialog(
                        visProduct,
                        "Ingrese la cantidad para " + nombre + ":",
                        "1");

                if (cantStr == null || cantStr.trim().isEmpty()) {
                    return;
                }

                int cantidad = Integer.parseInt(cantStr);

                String nota = JOptionPane.showInputDialog(
                        visProduct,
                        "Nota u observación (opcional):",
                        "");

                if (nota == null) {
                    nota = "";
                }

                double subtotal = cantidad * precio;

                GenerarPedidoDao detalleDao = new GenerarPedidoDao();

                detalleDao.insertarLineaDetalle(
                        idPedidoExistente,
                        idProducto,
                        cantidad,
                        subtotal,
                        nota);

                detalleDao.actualizarTotalPedido(idPedidoExistente);

                JOptionPane.showMessageDialog(
                        visProduct,
                        "Producto añadido correctamente al Pedido #"
                        + idPedidoExistente,
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        visProduct,
                        "Cantidad no válida.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                return;

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        visProduct,
                        "Error al guardar en la base de datos: "
                        + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                ex.printStackTrace();
                return;
            }
        }

        visProduct.dispose();
    }
}
