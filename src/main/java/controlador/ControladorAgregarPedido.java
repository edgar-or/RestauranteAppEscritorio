/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import java.awt.event.ActionListener;
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

    public ControladorAgregarPedido(VistaPrincipalMesero principal) {
        this.principal = principal;
        this.vistaAgregar = new VistaAgregarPedido();
        eventos();
    }

    

     public void iniciar(){
         vistaAgregar.setLocationRelativeTo(null);
        vistaAgregar.setVisible(true);
                

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
    

}
