/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import dao.GenerarPedidoDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import vista.VistaProductos; // Importamos la vista del menú de productos
import vista.VistaVerDetallePedido;

/**
 *
 * @author estud
 */
public class ControladorVerDetallePedido {

    private final VistaVerDetallePedido vista;
    private final GenerarPedidoDao dao;
    private final int idPedido;

    // Constructor único y limpio
    public ControladorVerDetallePedido(VistaVerDetallePedido vista, int idPedido) {
        this.vista = vista;
        this.dao = new GenerarPedidoDao();
        this.idPedido = idPedido;

        configurarTabla();
        cargarDatosPedido();
        registrarEventos();
        iniciarAutoRefresh();
    }

    /**
     * Establece la estructura de columnas inicial de la JTable.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID Prod", "Producto", "Cant.", "Subtotal", "Nota"}, 0);
        vista.tablaPedido.setModel(modelo);
    }

    /**
     * Consulta el DAO para rellenar la cabecera y las filas de la tabla de
     * forma dinámica.
     */
    public void cargarDatosPedido() {
        try {
            // 1. Cargar datos de la cabecera del Pedido
            Object[] cabecera = dao.obtenerPedidoCabecera(idPedido);
            if (cabecera != null) {
                // Posiciones del array según tu consulta: [0]id, [1]fecha, [2]mesa, [3]mesero, [4]total, [5]estado
                if (cabecera != null) {

                    vista.lblMesa.setText((String) cabecera[2]);
                    vista.lblMesero.setText((String) cabecera[3]);
                    vista.lblFecha.setText(cabecera[1].toString());
                    vista.lblEstado.setText((boolean) cabecera[5] ? "PAGADO" : "PENDIENTE");

                    double subtotal = (double) cabecera[4];
                    double propina = subtotal * 0.10;
                    double totalFinal = subtotal + propina;

                    vista.lblSubtotal.setText(String.format("$%.2f", subtotal));
                    vista.lblPropina.setText(String.format("$%.2f", propina));
                    vista.lblTotal.setText(String.format("$%.2f", totalFinal));
                };
            }

            // 2. Cargar las líneas de producto en la JTable
            List<Object[]> items = dao.obtenerDetallesPedido(idPedido);
            DefaultTableModel modelo = (DefaultTableModel) vista.tablaPedido.getModel();
            modelo.setRowCount(0); // Limpieza previa de la tabla

            for (Object[] item : items) {
                modelo.addRow(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error crítico al refrescar los datos del pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Centra la ventana en el monitor y la vuelve visible al usuario.
     */
    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    /**
     * Centraliza la lógica de escucha para todos los botones públicos de la
     * vista.
     */
    private void registrarEventos() {

        // BOTÓN EDITAR: Abre el menú de productos inyectando el ID de este pedido automáticamente
        vista.btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Instanciamos la vista de los productos (el catálogo/menú)
                VistaProductos vistaProd = new VistaProductos();

                // 2. Instanciamos su controlador pasándole el ID de este pedido de forma automática
                ControladorAgregarProductos ctrlProductos = new ControladorAgregarProductos(vistaProd, idPedido);

                // 3. Posicionamos la ventana de productos sobre esta para una mejor experiencia visual
                vistaProd.setLocationRelativeTo(vista);
                vistaProd.setVisible(true);

                // 4. TRUCO SWMNG: Escuchamos el cierre de VistaProductos para actualizar la tabla inmediatamente
                vistaProd.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        // Cuando el mesero termine de añadir productos y cierre la pestaña, recargamos los datos
                        cargarDatosPedido();
                    }
                });
            }
        });

        // BOTÓN CERRAR: Libera la memoria de esta ventana sin cerrar el hilo principal de la app
        vista.btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });

// Dentro de registrarEventos() en ControladorVerDetallePedido.java
        vista.btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // 1. Validar selección
                int filaSeleccionada = vista.tablaPedido.getSelectedRow();
                if (filaSeleccionada < 0) {
                    JOptionPane.showMessageDialog(vista,
                            "Por favor, selecciona el producto de la tabla que deseas eliminar.",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idProducto = Integer.parseInt(vista.tablaPedido.getValueAt(filaSeleccionada, 0).toString());
                String nombreProducto = vista.tablaPedido.getValueAt(filaSeleccionada, 1).toString();

                // 2. Contamos cuántos productos hay en la tabla actualmente
                int cantidadProductosActuales = vista.tablaPedido.getRowCount();

                if (cantidadProductosActuales == 1) {
                    // CASO A: Es el último producto. Si se elimina, el pedido quedará vacío.
                    int confirmacionTotal = JOptionPane.showConfirmDialog(
                            vista,
                            "\"" + nombreProducto + "\" es el último producto. Si lo eliminas, el Pedido #" + idPedido + " quedará vacío y se borrará por completo.\n\n¿Deseas eliminar todo el pedido?",
                            "Eliminar Pedido Completo",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirmacionTotal == JOptionPane.YES_OPTION) {
                        try {
                            // Borramos el pedido completo de la BD
                            dao.eliminarPedidoCompleto(idPedido);

                            JOptionPane.showMessageDialog(vista, "Pedido #" + idPedido + " eliminado por completo ya que no contenía productos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            vista.dispose(); // Cerramos la ventana de detalles porque el pedido ya no existe

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(vista, "Error al eliminar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } else {
                    // CASO B: Hay más productos en la tabla. Hacemos el borrado individual normal.
                    int confirmacionItem = JOptionPane.showConfirmDialog(
                            vista,
                            "¿Está seguro de que desea quitar \"" + nombreProducto + "\" del Pedido #" + idPedido + "?",
                            "Quitar Producto",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirmacionItem == JOptionPane.YES_OPTION) {
                        try {
                            // Calculamos el nuevo total restando el subtotal de la fila seleccionada
                            double nuevoTotal = 0.0;
                            for (int i = 0; i < cantidadProductosActuales; i++) {
                                if (i != filaSeleccionada) {
                                    nuevoTotal += Double.parseDouble(vista.tablaPedido.getValueAt(i, 3).toString());
                                }
                            }

                            // Invocamos tu método del DAO para eliminar el producto suelto y actualizar el total
                            dao.eliminarProductoDePedido(idPedido, idProducto, nuevoTotal);

                            JOptionPane.showMessageDialog(vista, "\"" + nombreProducto + "\" fue removido con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                            // Refrescamos la tabla y las etiquetas de la cabecera automáticamente
                            cargarDatosPedido();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(vista, "Error al quitar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        // BOTÓN GENERAR PAGO (Estructura lista para conectar con tu módulo de caja/facturas)
        vista.btngenerarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vista, "Redireccionando al módulo de facturación para el Pedido #" + idPedido, "Módulo de Caja", JOptionPane.INFORMATION_MESSAGE);
                // Aquí instanciarías tu VistaFactura o VistaCobro pasándole el objeto o ID
            }
        });

        // BOTÓN IMPRIMIR RECIBO (Estructura lista para conectar con JasperReports o tickets de texto plano)
        vista.btnImprmirRecibo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vista, "Enviando comandos de impresión a la ticketera para el Pedido #" + idPedido, "Impresión", JOptionPane.INFORMATION_MESSAGE);
                // Aquí mandas a llamar tus métodos de impresión térmica
            }
        });
        vista.btnActualizar.addActionListener(e -> {
            cargarDatosPedido();

        });
    }

    private void iniciarAutoRefresh() {

        Timer timer = new Timer(3000, e -> {
            cargarDatosPedido();
        });

        timer.start();
    }
}
