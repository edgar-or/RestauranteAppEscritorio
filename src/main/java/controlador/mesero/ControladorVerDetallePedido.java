/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import dao.GenerarPedidoDao;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.VistaPrincipalMesero;
import vista.VistaTodosPedidos;
import vista.VistaVerDetallePedido;

/**
 *
 * @author estud
 */
public class ControladorVerDetallePedido {
    private final VistaVerDetallePedido vista;
    private final GenerarPedidoDao dao;
    private final int idPedido;

    // Este es el único constructor que necesitas
    public ControladorVerDetallePedido(VistaVerDetallePedido vista, int idPedido) {
        this.vista = vista;
        this.dao = new GenerarPedidoDao();
        this.idPedido = idPedido;

        configurarTabla();
        cargarDatosPedido();
        registrarEventos();
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID Prod", "Producto", "Cant.", "Subtotal", "Nota"}, 0);
        vista.tablaPedido.setModel(modelo); // Asegúrate que en VistaVerDetallePedido la tabla se llame 'tabla'
    }

    private void cargarDatosPedido() {
try {
            // 1. Cargar cabecera
            Object[] cabecera = dao.obtenerPedidoCabecera(idPedido);
            if (cabecera != null) {
                // Posiciones según el array devuelto por tu DAO:
                // [0]id, [1]fecha, [2]mesa, [3]mesero, [4]total, [5]estado
                
                vista.lblMesa.setText((String) cabecera[2]);
                vista.lblMesero.setText((String) cabecera[3]);
                vista.lblTotal.setText(String.format("$%.2f", (double) cabecera[4]));
                
                // --- AGREGA ESTAS DOS LÍNEAS ---
                vista.lblFecha.setText(cabecera[1].toString()); 
                vista.lblEstado.setText((boolean) cabecera[5] ? "PAGADO" : "PENDIENTE");
                // -------------------------------
            }

            // 2. Cargar detalles
            List<Object[]> items = dao.obtenerDetallesPedido(idPedido);
            DefaultTableModel modelo = (DefaultTableModel) vista.tablaPedido.getModel();
            modelo.setRowCount(0);
            for (Object[] item : items) {
                modelo.addRow(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar datos: " + e.getMessage());
        }
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void registrarEventos() {
        vista.btnCerrar.addActionListener(e -> vista.dispose());
    }
}