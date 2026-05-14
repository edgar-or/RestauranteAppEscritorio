/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import controlador.mesero.ControladorVerDetallePedido;
import javax.swing.JFrame;
import vista.VistaPrincipalMesero;
import vista.VistaTodosPedidos;

/**
 *
 * @author estud
 */
public class ControladorTodosPedidos {

    private VistaTodosPedidos vistaTodosPedidos;
    private VistaPrincipalMesero vistaPrincipal;
    private ControladorVerDetallePedido controladorVerPedido;
            
    public ControladorTodosPedidos(VistaPrincipalMesero vistaPrincipal) {
        this.vistaTodosPedidos = new VistaTodosPedidos();
        this.vistaPrincipal = vistaPrincipal;
        this.controladorVerPedido= new ControladorVerDetallePedido(vistaTodosPedidos);
        vistaTodosPedidos.btnVerDetalle.addActionListener(e -> {
            controladorVerPedido.iniciar();
        });
        
        eventos();
    }

    public void iniciar() {
       vistaTodosPedidos.pack();
        vistaTodosPedidos.setLocationRelativeTo(null);
        vistaTodosPedidos.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistaTodosPedidos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaTodosPedidos.setVisible(true);
    }

    private void eventos() {
        vistaTodosPedidos.btnCerrar.addActionListener(e -> {
            vistaTodosPedidos.dispose();

        });

    }

}
