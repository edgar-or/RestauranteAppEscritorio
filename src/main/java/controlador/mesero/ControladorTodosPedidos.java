/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import controlador.mesero.ControladorVerDetallePedido;
import dao.GenerarPedidoDao;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloEmpleado;
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
    private ModeloEmpleado empleado;
    private GenerarPedidoDao dao;

    public ControladorTodosPedidos(VistaPrincipalMesero vistaPrincipal, ModeloEmpleado empleado) {
        this.vistaTodosPedidos = new VistaTodosPedidos();
        this.vistaPrincipal = vistaPrincipal;
        this.empleado = empleado;
        this.dao = new GenerarPedidoDao();
        eventos();
        iniciarAutoRefresh();
    }

    public void iniciar() {
        configurarTabla(); 
        cargarDatosEnTabla(); 

        vistaTodosPedidos.pack();
        vistaTodosPedidos.setLocationRelativeTo(null);
        vistaTodosPedidos.setVisible(true);
    }

    private void eventos() {
        vistaTodosPedidos.btnCerrar.addActionListener(e -> vistaTodosPedidos.dispose());

        vistaTodosPedidos.btnVerDetalle.addActionListener(e -> abrirDetalle());
    }

   private void abrirDetalle() {
    int fila = vistaTodosPedidos.tablaPedidos.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vistaTodosPedidos, "Seleccione un pedido");
        return;
    }

    int idPedido = (int) vistaTodosPedidos.tablaPedidos.getValueAt(fila, 0);

    VistaVerDetallePedido vistaDetalle = new VistaVerDetallePedido();
    ControladorVerDetallePedido ctrl = new ControladorVerDetallePedido(vistaDetalle, idPedido, vistaPrincipal, empleado);
    ctrl.iniciar();
}

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "Fecha", "Mesa", "Total", "Estado"}, 0);
        vistaTodosPedidos.tablaPedidos.setModel(modelo);
    }

    private void cargarDatosEnTabla() {
        try {
            List<Object[]> pedidos = dao.listarTodosLosPedidos(); 
            DefaultTableModel modelo = (DefaultTableModel) vistaTodosPedidos.tablaPedidos.getModel();
            modelo.setRowCount(0); 

            for (Object[] fila : pedidos) {
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaTodosPedidos, "Error al cargar pedidos: " + e.getMessage());
        }
    }
    
    
    private void iniciarAutoRefresh() {

        Timer timer = new Timer(6000, e -> {
            cargarDatosEnTabla();
        });

        timer.start();
    }
}