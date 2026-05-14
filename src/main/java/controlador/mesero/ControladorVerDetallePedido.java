/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import javax.swing.JFrame;
import vista.VistaPrincipalMesero;
import vista.VistaTodosPedidos;
import vista.VistaVerDetallePedido;

/**
 *
 * @author estud
 */
public class ControladorVerDetallePedido {
    private VistaVerDetallePedido vistaVerDetallePedido;
    private VistaPrincipalMesero vistaPrincipal;
    private VistaTodosPedidos vistaTodosPedidos;
    private ControladorGenerarPago controladorGenerarPago;

    public ControladorVerDetallePedido( VistaTodosPedidos vistaTodosPedidos) {
        this.vistaVerDetallePedido = new VistaVerDetallePedido();
        this.vistaPrincipal = vistaPrincipal;
        this.vistaTodosPedidos =new VistaTodosPedidos();
        this.controladorGenerarPago= new ControladorGenerarPago(vistaVerDetallePedido);
        
        vistaVerDetallePedido.btngenerarPago.addActionListener(e -> {
            controladorGenerarPago.iniciar();
        });
        
        eventos();
    }

   
    public void iniciar() {
        vistaVerDetallePedido.setLocationRelativeTo(null);
        vistaVerDetallePedido.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistaVerDetallePedido.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaVerDetallePedido.setVisible(true);
    }

    private void eventos() {
        vistaVerDetallePedido.btnCerrar.addActionListener(e -> {
            vistaVerDetallePedido.dispose();

        });

    }
    
    
}
