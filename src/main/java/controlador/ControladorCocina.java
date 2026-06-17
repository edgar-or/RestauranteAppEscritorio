/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.CocinaDao;
import dao.PedidoBarDao;
import dao.dto.PedidoBarDto;
import dao.dto.PedidoCocinaDto;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloProducto_Pedido;
import vista.VistaCocina;
import vista.VistaLogin;

/**
 *
 * @author ayala
 */
public class ControladorCocina {

    private VistaCocina visCocina;
    private VistaLogin visLogin;
    private LoginControlador loginContro;
    private List<ModeloProducto_Pedido> listaPedidos;

    public ControladorCocina(VistaCocina visCocina) {
        this.visCocina = visCocina;
        configurarTabla();
        cargarTabla();
        iniciarAutoRefresh();
        eventos();
    }

    public void iniciar() {
        visCocina.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visCocina.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visCocina.setVisible(true);
    }

    private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Cantidad");
        modelo.addColumn("Producto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");

        visCocina.tablaCocina.setModel(modelo);
    }

    public void cargarTabla() {

        try {

            CocinaDao dao = new CocinaDao();
            // List<ModeloProducto_Pedido> lista = dao.listar();
            listaPedidos = dao.listar();

            DefaultTableModel modelo = (DefaultTableModel) visCocina.tablaCocina.getModel();

            modelo.setRowCount(0); // limpia filas

            for (ModeloProducto_Pedido orden : listaPedidos) {

                modelo.addRow(new Object[]{
                    orden.getCantidad(),
                    orden.getProducto().getNombre(),
                    orden.getNota(),
                    orden.isEstadoOrden() ? "Terminado" : "Pendiente"
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void iniciarAutoRefresh() {

        Timer timer = new Timer(5000, e -> { // 3000 ms = 3 segundos
            cargarTabla();
        });

        timer.start();
    }

    private void eventos() {
        visCocina.btnCerrarsesion.addActionListener(e -> {
            visCocina.dispose();

            VistaLogin login = new VistaLogin();
            LoginControlador ctrl = new LoginControlador();
            ctrl.iniciar();
        });

        visCocina.btnTerminado.addActionListener(e -> {
            int fila = visCocina.tablaCocina.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(visCocina, "Seleccione un Platillo");
                return;
            }
            try {
                ModeloProducto_Pedido pedido = listaPedidos.get(fila);

                CocinaDao dao = new CocinaDao();
                dao.actualizarEstado(pedido.getIdPedido(),
                        pedido.getProducto().getIdProducto(), true);

                cargarTabla();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        visCocina.btnNoTerminado.addActionListener(e -> {
            int fila = visCocina.tablaCocina.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(
                        visCocina, "Seleccione un platillo");
                return;
            }

            try {

                ModeloProducto_Pedido pedido = listaPedidos.get(fila);

                CocinaDao dao = new CocinaDao();

                dao.actualizarEstado(pedido.getIdPedido(),
                        pedido.getProducto().getIdProducto(),
                        false);

                cargarTabla();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }

}
