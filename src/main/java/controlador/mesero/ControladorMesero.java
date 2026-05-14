/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

<<<<<<< HEAD:src/main/java/controlador/mesero/ControladorMesero.java
import controlador.LoginControlador;
import controlador.mesero.ControladorAgregarPedido;
=======
import dao.GenerarPedidoDao;
>>>>>>> adabbeaeb84176d6c423824d43c09b6d0904257f:src/main/java/controlador/ControladorMesero.java
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import modelo.ModeloEmpleado;
import modelo.ModeloMesa;
import vista.VistaAgregarPedido;
import vista.VistaLogin;
import vista.VistaPrincipalMesero;
import vista.VistaVerPedido;

/**
 *
 * @author ayala
 */
public class ControladorMesero {
<<<<<<< HEAD:src/main/java/controlador/mesero/ControladorMesero.java

    private VistaPrincipalMesero vistaPricipal;
    private ControladorAgregarPedido controlAgregarPedido;
    private ControladorTodosPedidos controlTodosPedidos;
=======
>>>>>>> adabbeaeb84176d6c423824d43c09b6d0904257f:src/main/java/controlador/ControladorMesero.java

    private VistaPrincipalMesero vistaPricipal;
    private ControladorAgregarPedido controlAgregarPedido;
    private ModeloEmpleado empleadoModelo;

    public ControladorMesero(VistaPrincipalMesero vistaPrincipal, ModeloEmpleado empleado) {
        this.vistaPricipal = vistaPrincipal;
<<<<<<< HEAD:src/main/java/controlador/mesero/ControladorMesero.java
        this.controlAgregarPedido = new ControladorAgregarPedido(vistaPrincipal);
        this.controlTodosPedidos = new ControladorTodosPedidos(vistaPrincipal);
        
=======
        this.empleadoModelo = empleado;

        this.controlAgregarPedido = new ControladorAgregarPedido(vistaPrincipal, empleado );
>>>>>>> adabbeaeb84176d6c423824d43c09b6d0904257f:src/main/java/controlador/ControladorMesero.java
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

<<<<<<< HEAD:src/main/java/controlador/mesero/ControladorMesero.java
=======
        
        
>>>>>>> adabbeaeb84176d6c423824d43c09b6d0904257f:src/main/java/controlador/ControladorMesero.java
    }

    private void eventos() {

        vistaPricipal.btnCerrarSesion.addActionListener(e -> {
            vistaPricipal.dispose();

            VistaLogin login = new VistaLogin();
            LoginControlador ctrl = new LoginControlador();
            ctrl.iniciar();
<<<<<<< HEAD:src/main/java/controlador/mesero/ControladorMesero.java
        });
    }

=======
        });
        
        vistaPricipal.btnVerPedido.addActionListener(e->{
            VistaVerPedido vista = new VistaVerPedido();
            new ControladorVerPedido(vista);
        
        });
    }

    
     
    
>>>>>>> adabbeaeb84176d6c423824d43c09b6d0904257f:src/main/java/controlador/ControladorMesero.java
}
