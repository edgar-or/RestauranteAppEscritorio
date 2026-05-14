/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import controlador.LoginControlador;
import controlador.mesero.ControladorAgregarPedido;
import dao.GenerarPedidoDao;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import modelo.ModeloEmpleado;
import modelo.ModeloMesa;
import vista.VistaAgregarPedido;
import vista.VistaLogin;
import vista.VistaPrincipalMesero;
import vista.VistaVerDetallePedido;


/**
 *
 * @author ayala
 */
public class ControladorMesero {

    private VistaPrincipalMesero vistaPricipal;
    private ControladorAgregarPedido controlAgregarPedido;
    private ControladorTodosPedidos controlTodosPedidos;

    private ModeloEmpleado empleadoModelo;

    public ControladorMesero(VistaPrincipalMesero vistaPrincipal, ModeloEmpleado empleado) {
        this.vistaPricipal = vistaPrincipal;
        this.controlAgregarPedido = new ControladorAgregarPedido(vistaPrincipal, empleado);
        this.controlTodosPedidos = new ControladorTodosPedidos(vistaPrincipal);

        this.empleadoModelo = empleado;

        this.controlAgregarPedido = new ControladorAgregarPedido(vistaPrincipal, empleado);
        vistaPrincipal.btnAgregarPedido.addActionListener(e -> {
            controlAgregarPedido.iniciar();
        });

        vistaPrincipal.btnVerPedido.addActionListener(e -> {
            controlTodosPedidos.iniciar();
        });
        eventos();
    }

    public void iniciar() {
        vistaPricipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaPricipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistaPricipal.setVisible(true);

    }

    private void eventos() {

        vistaPricipal.btnCerrarSesion.addActionListener(e -> {
            vistaPricipal.dispose();

            VistaLogin login = new VistaLogin();
            LoginControlador ctrl = new LoginControlador();
            ctrl.iniciar();
        });

    }
}
