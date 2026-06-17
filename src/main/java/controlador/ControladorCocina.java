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

    VistaCocina visCocina;
    VistaLogin visLogin;
    LoginControlador loginContro;

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
            List<ModeloProducto_Pedido> lista = dao.listar();

            DefaultTableModel modelo = (DefaultTableModel) visCocina.tablaCocina.getModel();

            modelo.setRowCount(0); // limpia filas

            for (ModeloProducto_Pedido orden : lista) {

                modelo.addRow(new Object[]{
                    orden.getCantidad(),
                    orden.getProducto().getNombre(),
                    orden.getNota(),
                    orden.isEstadoOrden()
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
        visCocina.btnCerrarsesion.addActionListener(e -> {
            visCocina.dispose();

            VistaLogin login = new VistaLogin();
            LoginControlador ctrl = new LoginControlador();
            ctrl.iniciar();
        });

    }

}
