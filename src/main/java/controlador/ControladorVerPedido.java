/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaVerPedido;

/**
 *
 * @author renec
 */
public class ControladorVerPedido {
    private VistaVerPedido visVerPedidos;

    public ControladorVerPedido(VistaVerPedido visVerPedidos) {
        this.visVerPedidos = visVerPedidos;
        iniciarVista();
        onEvento();
        
        
      
    }

    private void iniciarVista() {
        visVerPedidos.setLocationRelativeTo(null);
        visVerPedidos.setVisible(true);
    }

    private void onEvento() {
        visVerPedidos.btnCerrar.addActionListener(e->{
        visVerPedidos.dispose();
        });
    }
    

   
    
    
    
}
