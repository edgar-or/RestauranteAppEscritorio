/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import javax.swing.JFrame;
import vista.VistaAgregarPedido;
import vista.VistaLogin;
import vista.VistaPrincipalMesero;

/**
 *
 * @author ayala
 */
public class ControladorMesero {
    private VistaPrincipalMesero vistaPricipal; 
    private ControladorAgregarPedido controlAgregarPedido;

    public ControladorMesero(VistaPrincipalMesero vistaPrincipal) {
        this.vistaPricipal = vistaPrincipal;
        this.controlAgregarPedido = new ControladorAgregarPedido(vistaPrincipal);
        vistaPrincipal.btnAgregarPedido.addActionListener(e ->{
            controlAgregarPedido.iniciar();
        });
        eventos();
    }
    
    public void iniciar(){
        vistaPricipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaPricipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistaPricipal.setVisible(true);
        
        
        
    }

    private void eventos() {
             
        vistaPricipal.btnCerrarSesion.addActionListener(e->{
        vistaPricipal.dispose();
        
        VistaLogin login= new VistaLogin();
        LoginControlador ctrl= new LoginControlador();
        ctrl.iniciar();
        });
    }
    
}
