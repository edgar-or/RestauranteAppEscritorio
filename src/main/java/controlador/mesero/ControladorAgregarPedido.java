/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import dao.GenerarPedidoDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloEmpleado;
import modelo.ModeloMesa;
import vista.VistaAgregarPedido;
import vista.VistaPrincipalMesero;
import vista.VistaProductos;

/**
 *
 * @author estud
 */
public class ControladorAgregarPedido {
 
    private final VistaAgregarPedido vistaAgregar;
    private final VistaPrincipalMesero principal;
    private final GenerarPedidoDao genPedido;
    private final ModeloEmpleado empleado;
 
    // Guarda únicamente las filas agregadas en la sesión actual
    private final List<int[]>  detallesNuevos = new ArrayList<>();
    private final List<String> notasNuevas    = new ArrayList<>();
    
    private double totalAcumulado = 0.0;
    private int idPedidoActual = -1; // -1 significa pedido nuevo, de lo contrario guarda el ID activo
    private int cantidadItemsExistentes = 0; // Controla cuáles filas pertenecen a la BD

    // Estado del producto seleccionado transitoriamente
    private int idProductoSeleccionado = -1;
    private String nombreProductoSeleccionado = "";
    private double precioProductoSeleccionado = 0.0;
 
    public ControladorAgregarPedido(VistaPrincipalMesero principal, ModeloEmpleado empleado) {
        this.genPedido    = new GenerarPedidoDao();
        this.empleado     = empleado;
        this.principal    = principal;
        this.vistaAgregar = new VistaAgregarPedido();
 
        configurarTabla();
        llenarComboMesas();
        registrarEventos();
        
        // Carga inicial automática de la primera mesa que aparezca seleccionada
        cargarPedidoDeMesaSeleccionada();
    }
 
    public void iniciar() {
        vistaAgregar.setLocationRelativeTo(null);
        vistaAgregar.setVisible(true);
    }
 
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Producto", "Cantidad", "Precio Unit.", "Subtotal", "Nota"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        vistaAgregar.tablaItems.setModel(modelo);
 
        var col = vistaAgregar.tablaItems.getColumnModel().getColumn(0);
        col.setMinWidth(0); col.setMaxWidth(0);
        col.setWidth(0);    col.setPreferredWidth(0);
    }
 
    public void llenarComboMesas() {
        try {
            // Limpieza preventiva antes de consultar la base de datos
            vistaAgregar.comboMesa.removeAllItems();
            ArrayList<ModeloMesa> lista = genPedido.llenarComboMesa();
            for (ModeloMesa m : lista) vistaAgregar.comboMesa.addItem(m);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vistaAgregar, 
                "Error al conectar o extraer los datos de las mesas: " + e.getMessage(), 
                "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void registrarEventos() {
        // Escucha cambios en el JComboBox de mesas
        vistaAgregar.comboMesa.addActionListener(e -> cargarPedidoDeMesaSeleccionada());

        vistaAgregar.btnProductos.addActionListener(e -> abrirVistaProductos());
        vistaAgregar.btnAgregarItem.addActionListener(e -> agregarItemDesdeVista());
        vistaAgregar.btnQuitarItem.addActionListener(e -> quitarItem());
        vistaAgregar.btnGuardar.addActionListener(e -> guardarPedido());
        vistaAgregar.btnCancelar.addActionListener(e -> limpiarTodo());
        vistaAgregar.btnCerrar.addActionListener(e -> vistaAgregar.dispose());
    }
 
    // Consulta la BD y rellena la UI según la mesa seleccionada
    private void cargarPedidoDeMesaSeleccionada() {
        Object item = vistaAgregar.comboMesa.getSelectedItem();
        
        // CONTROL ANTICRASHEO: Filtra nulos y cadenas de texto residuales de NetBeans
        if (item == null || !(item instanceof ModeloMesa)) {
            return;
        }
        
        ModeloMesa mesa = (ModeloMesa) item;

        // Reset completo de la interfaz de forma limpia
        ((DefaultTableModel) vistaAgregar.tablaItems.getModel()).setRowCount(0);
        detallesNuevos.clear();
        notasNuevas.clear();
        totalAcumulado = 0.0;
        idPedidoActual = -1;
        cantidadItemsExistentes = 0;
        vistaAgregar.lblTotal.setText("$0.00");

        try {
            int idMesa = Integer.parseInt(mesa.getIdMesa());
            int idPedido = genPedido.obtenerIdPedidoActivo(idMesa);

            if (idPedido > 0) {
                idPedidoActual = idPedido;
                List<Object[]> items = genPedido.obtenerDetallesPedido(idPedido);
                DefaultTableModel modelo = (DefaultTableModel) vistaAgregar.tablaItems.getModel();
                
                cantidadItemsExistentes = items.size();

                for (Object[] rowItem : items) {
                    int idProd = (int) rowItem[0];
                    String nombre = (String) rowItem[1];
                    int cant = (int) rowItem[2];
                    double subtotal = (double) rowItem[3];
                    String nota = (String) rowItem[4];
                    double precioUnit = (cant > 0) ? (subtotal / cant) : 0;

                    modelo.addRow(new Object[]{
                        idProd,
                        nombre,
                        cant,
                        String.format("$%.2f", precioUnit),
                        String.format("$%.2f", subtotal),
                        nota
                    });
                    totalAcumulado += subtotal;
                }
                vistaAgregar.lblTotal.setText(String.format("$%.2f", totalAcumulado));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirVistaProductos() {
        VistaProductos visProducto = new VistaProductos();
        new ControladorAgregarProductos(visProducto, this);
        visProducto.setLocationRelativeTo(vistaAgregar);
        visProducto.setVisible(true);
    }

    public void setProductoSeleccionado(int idProducto, String nombre, double precio) {
        this.idProductoSeleccionado = idProducto;
        this.nombreProductoSeleccionado = nombre;
        this.precioProductoSeleccionado = precio;
        
        vistaAgregar.txtPrecio.setText(String.format("%.2f", precio));
        vistaAgregar.spinnerCantidad.setValue(1); 
        vistaAgregar.txtDescripcion.setText(""); 
    }
 
    private void agregarItemDesdeVista() {
        if (idProductoSeleccionado == -1) {
            JOptionPane.showMessageDialog(vistaAgregar, 
                "Por favor, selecciona un producto usando el botón 'Productos'.", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad = (Integer) vistaAgregar.spinnerCantidad.getValue();
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(vistaAgregar, 
                "La cantidad debe ser mayor a 0.", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nota = vistaAgregar.txtDescripcion.getText().trim();
        double subtotal = precioProductoSeleccionado * cantidad;

        DefaultTableModel modelo = (DefaultTableModel) vistaAgregar.tablaItems.getModel();
        modelo.addRow(new Object[]{
            idProductoSeleccionado,
            nombreProductoSeleccionado,
            cantidad,
            String.format("$%.2f", precioProductoSeleccionado),
            String.format("$%.2f", subtotal),
            nota
        });

        detallesNuevos.add(new int[]{idProductoSeleccionado, cantidad, (int)(subtotal * 100)});
        notasNuevas.add(nota);

        totalAcumulado += subtotal;
        vistaAgregar.lblTotal.setText(String.format("$%.2f", totalAcumulado));

        idProductoSeleccionado = -1;
        vistaAgregar.txtPrecio.setText("");
        vistaAgregar.spinnerCantidad.setValue(0);
        vistaAgregar.txtDescripcion.setText("");
    }
 
    private void quitarItem() {
        int fila = vistaAgregar.tablaItems.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vistaAgregar,
                "Selecciona un item de la tabla para quitar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        int idProducto = (int) vistaAgregar.tablaItems.getValueAt(fila, 0);
        String subtotalStr = vistaAgregar.tablaItems.getValueAt(fila, 4).toString().replace("$", "").replace(",", ".");
        double subtotalFila = Double.parseDouble(subtotalStr);
        
        totalAcumulado -= subtotalFila;
        if (totalAcumulado < 0) totalAcumulado = 0;

        try {
            if (fila < cantidadItemsExistentes) {
                genPedido.eliminarProductoDePedido(idPedidoActual, idProducto, totalAcumulado);
                cantidadItemsExistentes--;
            } else {
                int idxNuevo = fila - cantidadItemsExistentes;
                detallesNuevos.remove(idxNuevo);
                notasNuevas.remove(idxNuevo);
            }

            ((DefaultTableModel) vistaAgregar.tablaItems.getModel()).removeRow(fila);
            vistaAgregar.lblTotal.setText(String.format("$%.2f", totalAcumulado));
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vistaAgregar, "Error al quitar producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void guardarPedido() {
        Object item = vistaAgregar.comboMesa.getSelectedItem();
        if (item == null || !(item instanceof ModeloMesa)) {
            JOptionPane.showMessageDialog(vistaAgregar, "Selecciona una mesa válida.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ModeloMesa mesa = (ModeloMesa) item;
 
        try {
            if (idPedidoActual == -1) {
                if (detallesNuevos.isEmpty()) {
                    JOptionPane.showMessageDialog(vistaAgregar, "Agrega al menos un producto antes de guardar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int idPedido = genPedido.guardarPedidoCompleto(
                    Integer.parseInt(mesa.getIdMesa()),
                    Integer.parseInt(empleado.getIdEmpleado()),
                    totalAcumulado, detallesNuevos, notasNuevas
                );
 
                if (idPedido > 0) {
                    JOptionPane.showMessageDialog(vistaAgregar, "Pedido #" + idPedido + " generado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarPedidoDeMesaSeleccionada();
                }
            } else {
                boolean exito = genPedido.guardarItemsEnPedidoExistente(idPedidoActual, totalAcumulado, detallesNuevos, notasNuevas);
                if (exito) {
                    JOptionPane.showMessageDialog(vistaAgregar, "Pedido #" + idPedidoActual + " actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarPedidoDeMesaSeleccionada();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vistaAgregar, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void limpiarTodo() {
        cargarPedidoDeMesaSeleccionada();
    }
}