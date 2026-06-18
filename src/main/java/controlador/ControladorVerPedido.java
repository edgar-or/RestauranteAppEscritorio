/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaVerDetallePedido;



/**
 *
 * @author renec
 */
public class ControladorVerPedido {
    private VistaVerDetallePedido vistaVerPedidos;

    public ControladorVerPedido(VistaVerDetallePedido vistaVerDetallePedidos) {
        this.vistaVerPedidos = vistaVerDetallePedidos;
        iniciarVista();
        onEvento();
        
        
      
    }

    private void iniciarVista() {
        vistaVerPedidos.setLocationRelativeTo(null);
        vistaVerPedidos.setVisible(true);
    }

    private void onEvento() {
        vistaVerPedidos.btnCerrar.addActionListener(e->{
        vistaVerPedidos.dispose();
        });
    }
    

   
    
    
    
}
