/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import javax.swing.JFrame;
import vista.VistaGenerarPago;
import vista.VistaVerDetallePedido;

/**
 *
 * @author estud
 */
public class ControladorGenerarPago {
    private VistaGenerarPago vistaGenerarPago;
    private VistaVerDetallePedido vistaVerDetallePedido;

    public ControladorGenerarPago( VistaVerDetallePedido vistaVerDetallePedido) {
        this.vistaGenerarPago =new VistaGenerarPago();
        this.vistaVerDetallePedido = vistaVerDetallePedido;
        eventos();
    }
    public void iniciar() {
        vistaGenerarPago.setLocationRelativeTo(null);
        vistaGenerarPago.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaGenerarPago.setVisible(true);
    }

    private void eventos() {
        vistaGenerarPago.btnCerrar.addActionListener(e -> {
            vistaGenerarPago.dispose();

        });

    }
    
}
