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
import vista.VistaProductos; 
import vista.VistaVerDetallePedido;


public class ControladorVerDetallePedido {

    private final VistaVerDetallePedido vista;
    private final GenerarPedidoDao dao;
    private final int idPedido;

    public ControladorVerDetallePedido(VistaVerDetallePedido vista, int idPedido) {
        this.vista = vista;
        this.dao = new GenerarPedidoDao();
        this.idPedido = idPedido;

        configurarTabla();
        cargarDatosPedido();
        registrarEventos();
        iniciarAutoRefresh();
    }

    
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID Prod", "Producto", "Cant.", "Subtotal", "Nota"}, 0);
        vista.tablaPedido.setModel(modelo);
    }

    
    public void cargarDatosPedido() {
        try {
            Object[] cabecera = dao.obtenerPedidoCabecera(idPedido);
            if (cabecera != null) {
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

   
    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void registrarEventos() {

        vista.btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VistaProductos vistaProd = new VistaProductos();

                ControladorAgregarProductos ctrlProductos = new ControladorAgregarProductos(vistaProd, idPedido);

                vistaProd.setLocationRelativeTo(vista);
                vistaProd.setVisible(true);

                vistaProd.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        cargarDatosPedido();
                    }
                });
            }
        });

        vista.btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });

        vista.btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int filaSeleccionada = vista.tablaPedido.getSelectedRow();
                if (filaSeleccionada < 0) {
                    JOptionPane.showMessageDialog(vista,
                            "Por favor, selecciona el producto de la tabla que deseas eliminar.",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idProducto = Integer.parseInt(vista.tablaPedido.getValueAt(filaSeleccionada, 0).toString());
                String nombreProducto = vista.tablaPedido.getValueAt(filaSeleccionada, 1).toString();

                int cantidadProductosActuales = vista.tablaPedido.getRowCount();

                if (cantidadProductosActuales == 1) {
                    int confirmacionTotal = JOptionPane.showConfirmDialog(
                            vista,
                            "\"" + nombreProducto + "\" es el último producto. Si lo eliminas, el Pedido #" + idPedido + " quedará vacío y se borrará por completo.\n\n¿Deseas eliminar todo el pedido?",
                            "Eliminar Pedido Completo",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirmacionTotal == JOptionPane.YES_OPTION) {
                        try {
                            dao.eliminarPedidoCompleto(idPedido);

                            JOptionPane.showMessageDialog(vista, "Pedido #" + idPedido + " eliminado por completo ya que no contenía productos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            vista.dispose(); 

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(vista, "Error al eliminar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } else {
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
        vista.btngenerarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vista, "Redireccionando al módulo de facturación para el Pedido #" + idPedido, "Módulo de Caja", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        vista.btnImprmirRecibo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vista, "Enviando comandos de impresión a la ticketera para el Pedido #" + idPedido, "Impresión", JOptionPane.INFORMATION_MESSAGE);
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
