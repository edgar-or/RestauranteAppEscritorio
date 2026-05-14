/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.GenerarPedidoDao;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.ModeloMesa;
import vista.VistaAgregarPedido;
import vista.VistaLogin;
import vista.VistaPrincipalMesero;
import vista.VistaProductos;

/**
 *
 * @author estud
 */
public class ControladorAgregarPedido {

    private VistaAgregarPedido vistaAgregar;
    private VistaPrincipalMesero principal;
    private VistaProductos visProducto;

    private GenerarPedidoDao genPedido;

    public ControladorAgregarPedido(VistaPrincipalMesero principal) {
        this.genPedido = new GenerarPedidoDao();

        this.principal = principal;
        this.vistaAgregar = new VistaAgregarPedido();
        eventos();
    }

    

                


    public void iniciar() {
        vistaAgregar.setVisible(true);
        llenarComboMesas(); 

    }

    private void eventos() {

        
        vistaAgregar.btnProductos.addActionListener(e -> {

            // Evita que se abran múltiples ventanas
            if (visProducto == null || !visProducto.isDisplayable()) {
                visProducto = new VistaProductos();
            }

            visProducto.setLocationRelativeTo(null);
            visProducto.setVisible(true);
        });

        vistaAgregar.btnCerrar.addActionListener(e->{
        vistaAgregar.dispose();
    

        
        });

    }
    

    public void llenarComboMesas() {

        try {

            ArrayList<ModeloMesa> listaMesas = genPedido.llenarComboMesa();
            vistaAgregar.comboMesa.removeAllItems();

            for (ModeloMesa mesa : listaMesas) {

                vistaAgregar.comboMesa.addItem(mesa);
               
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
