/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import vista.VistaAgregarPedido;
import vista.VistaLogin;
import vista.VistaPrincipalMesero;

/**
 *
 * @author estud
 */
public class ControladorAgregarPedido {

    private VistaAgregarPedido vistaAgregar;
    private VistaPrincipalMesero principal;

    public ControladorAgregarPedido(VistaPrincipalMesero principal) {
        this.principal = principal;
        this.vistaAgregar = new VistaAgregarPedido();
        eventos();
    }

    

     public void iniciar(){
        vistaAgregar.setVisible(true);
    }
    private void eventos() {
        vistaAgregar.btnCerrar.addActionListener(e->{
        vistaAgregar.dispose();
    
        });

    }

}
