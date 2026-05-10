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
<<<<<<< HEAD
    private VistaProductos visProducto;
=======
    private GenerarPedidoDao genPedido;
>>>>>>> 875fe59f69adddb4a823db55dc798a4795a4e3e5

    public ControladorAgregarPedido(VistaPrincipalMesero principal) {
        this.genPedido = new GenerarPedidoDao();

        this.principal = principal;
        this.vistaAgregar = new VistaAgregarPedido();
        eventos();
    }

<<<<<<< HEAD
    

     public void iniciar(){
         vistaAgregar.setLocationRelativeTo(null);
        vistaAgregar.setVisible(true);
                

=======
    public void iniciar() {
        vistaAgregar.setVisible(true);
        llenarComboMesas(); 
>>>>>>> 875fe59f69adddb4a823db55dc798a4795a4e3e5
    }

    private void eventos() {
<<<<<<< HEAD
        
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
    
=======
        vistaAgregar.btnCerrar.addActionListener(e -> {
            vistaAgregar.dispose();

>>>>>>> 875fe59f69adddb4a823db55dc798a4795a4e3e5
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
