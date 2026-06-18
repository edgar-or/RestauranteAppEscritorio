/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.CocinaDao;
import dao.PedidoBarDao;
import dao.dto.PedidoBarDto;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloProducto_Pedido;
import vista.VistaBar;
import vista.VistaLogin;

/**
 *
 * @author ayala
 */
public class ControladorBar {
    private VistaBar visBar; 
    private List<ModeloProducto_Pedido> listaPedidos;


    public ControladorBar(VistaBar visBar) {
        this.visBar = visBar;
        configurarTabla();
        cargarTabla();
        iniciarAutoRefresh();
        evento();
        
    }
    
private void iniciarAutoRefresh() {
    Timer timer = new Timer(6000, e -> {
            cargarTabla();
        
    });
    timer.start();
}
    
    public void iniciar(){
        visBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visBar.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visBar.setVisible(true);
    }
    
    private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Cantidad");
        modelo.addColumn("Producto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");

        visBar.tablaBar.setModel(modelo);
    }
    
    public void cargarTabla() {

    try {
        PedidoBarDao dao = new PedidoBarDao();
        //List<ModeloProducto_Pedido> lista = dao.listar();
            listaPedidos = dao.listar();

        DefaultTableModel modelo = (DefaultTableModel) visBar.tablaBar.getModel();

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
    
    

    private void evento() {
         visBar.btnCerrarsesion.addActionListener(e->{
        visBar.dispose();
        
        VistaLogin login= new VistaLogin();
        LoginControlador ctrl= new LoginControlador();
        ctrl.iniciar();
        });
         
         visBar.btnTerminado.addActionListener(e -> {
            int fila = visBar.tablaBar.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(visBar, "Seleccione una Bebida");
                return;
            }
            try {
                ModeloProducto_Pedido pedido = listaPedidos.get(fila);

                PedidoBarDao dao = new PedidoBarDao();
                dao.actualizarEstado(pedido.getIdPedido(),
                        pedido.getProducto().getIdProducto(), true);

                cargarTabla();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
         
          visBar.btnNoTermnadao.addActionListener(e -> {
            int fila = visBar.tablaBar.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(
                        visBar, "Seleccione un platillo");
                return;
            }

            try {

                ModeloProducto_Pedido pedido = listaPedidos.get(fila);

                PedidoBarDao dao = new PedidoBarDao();

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