/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.CocinaDao;
import dao.PedidoBarDao;
import dao.PedidoPanaderiaDao;
import dao.dto.PedidoBarDto;
import dao.dto.PedidoPanaderiaDto;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloProducto_Pedido;
import vista.VistaLogin;
import vista.VistaPanaderia;



/**
 *
 * @author ayala
 */
public class ControladorPanaderia {
    VistaPanaderia visPanaderia; 
    private List<ModeloProducto_Pedido> listaPedidos;


    public ControladorPanaderia(VistaPanaderia visPanaderia) {
        this.visPanaderia = visPanaderia;
        configurarTabla();
        cargarTabla();
        iniciarAutoRefresh();
        eventos();
    }
    
    public void iniciar(){
        visPanaderia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visPanaderia.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visPanaderia.setVisible(true);
    }
    
      private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Cantidad");
        modelo.addColumn("Producto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");

        visPanaderia.tablaPanaderia.setModel(modelo);
    }
    
       public void cargarTabla() {

    try {
         CocinaDao dao = new CocinaDao();
            // List<ModeloProducto_Pedido> lista = dao.listar();
            listaPedidos = dao.listar();

            DefaultTableModel modelo = (DefaultTableModel) visPanaderia.tablaPanaderia.getModel();

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

        Timer timer = new Timer(6000, e -> { 
            cargarTabla();
        });

        timer.start();
    }

    private void eventos() {
         visPanaderia.btnCerrarsesion.addActionListener(e->{
        visPanaderia.dispose();
        
        VistaLogin login= new VistaLogin();
        LoginControlador ctrl= new LoginControlador();
        ctrl.iniciar();
        });
         
         visPanaderia.btnTerminado.addActionListener(e -> {
            int fila = visPanaderia.tablaPanaderia.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(visPanaderia, "Seleccione un Pan");
                return;
            }
            try {
                ModeloProducto_Pedido pedido = listaPedidos.get(fila);

                PedidoPanaderiaDao dao = new PedidoPanaderiaDao(); 
                dao.actualizarEstado(pedido.getIdPedido(),pedido.getProducto().getIdProducto(), true);

                cargarTabla();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
         
         visPanaderia.btnNoTerminado.addActionListener(e -> {
            int fila = visPanaderia.tablaPanaderia.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(
                        visPanaderia, "Seleccione un platillo");
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
