/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import controlador.mesero.ControladorVerDetallePedido;
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
public class ControladorTodosPedidos {

    private VistaTodosPedidos vistaTodosPedidos;
    private VistaPrincipalMesero vistaPrincipal;
    private GenerarPedidoDao dao;

    public ControladorTodosPedidos(VistaPrincipalMesero vistaPrincipal) {
        this.vistaTodosPedidos = new VistaTodosPedidos();
        this.vistaPrincipal = vistaPrincipal;
        this.dao = new GenerarPedidoDao();
        eventos();
    }

    public void iniciar() {
        configurarTabla(); // Prepara columnas
        cargarDatosEnTabla(); // Trae datos de la BD

        vistaTodosPedidos.pack();
        vistaTodosPedidos.setLocationRelativeTo(null);
        vistaTodosPedidos.setVisible(true);
    }

    private void eventos() {
        vistaTodosPedidos.btnCerrar.addActionListener(e -> vistaTodosPedidos.dispose());

        // Acción del botón para abrir el detalle
        vistaTodosPedidos.btnVerDetalle.addActionListener(e -> abrirDetalle());
    }

   private void abrirDetalle() {
    int fila = vistaTodosPedidos.tablaPedidos.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vistaTodosPedidos, "Seleccione un pedido");
        return;
    }

    // Obtenemos el ID de la primera columna
    int idPedido = (int) vistaTodosPedidos.tablaPedidos.getValueAt(fila, 0);

    // Abrimos la vista y le pasamos el ID al nuevo controlador
    VistaVerDetallePedido vistaDetalle = new VistaVerDetallePedido();
    ControladorVerDetallePedido ctrl = new ControladorVerDetallePedido(vistaDetalle, idPedido);
    ctrl.iniciar();
}

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "Fecha", "Mesa", "Total", "Estado"}, 0);
        vistaTodosPedidos.tablaPedidos.setModel(modelo);
    }

    private void cargarDatosEnTabla() {
        try {
            List<Object[]> pedidos = dao.listarTodosLosPedidos(); // Método que creamos arriba
            DefaultTableModel modelo = (DefaultTableModel) vistaTodosPedidos.tablaPedidos.getModel();
            modelo.setRowCount(0); // Limpiar tabla antes de cargar

            for (Object[] fila : pedidos) {
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaTodosPedidos, "Error al cargar pedidos: " + e.getMessage());
        }
    }
}
