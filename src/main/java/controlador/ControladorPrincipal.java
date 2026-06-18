/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

//import vista.VistaPrincipalAdministrador;
import javax.swing.JFrame;
import vista.VistaPrincipal;
import vista.VistaReportee;
import vista.VistaReportee;

/**
 *
 * @author estud
 */
public class ControladorPrincipal {

    VistaPrincipal vista;
    LoginControlador logingControlador;
    ControladorEmpleado cntrlEmpleado;
    ControladorGestionMenu gestionMenu;
    ControladorMesa contrlMesa;

    public ControladorPrincipal(VistaPrincipal vista) {
        this.vista = vista;

        this.cntrlEmpleado = new ControladorEmpleado(vista);

        vista.btnEmpleados.addActionListener(e -> {
            try {
                cntrlEmpleado.abrirVistaEmpleados();
            } catch (Exception ex) {
                System.getLogger(ControladorPrincipal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        });

        vista.btnCerrarSesion.addActionListener(e -> {
            cerrarSesion();
        });

        vista.btnMenu.addActionListener(e -> {
            try {
                gestionMenu = new ControladorGestionMenu();
            } catch (Exception ex) {
                System.getLogger(ControladorPrincipal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        vista.btnMesas.addActionListener(e -> {
            try {
                contrlMesa = new ControladorMesa();
            } catch (Exception ex) {
                System.getLogger(ControladorPrincipal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
        
        vista.btnReportes.addActionListener(e -> {
            try {
                VistaReportee vistaReporte = new VistaReportee();
                ControladorReporte cntrlReporte = new ControladorReporte(vistaReporte);
                cntrlReporte.iniciar();
            } catch (Exception ex) {
                System.getLogger(ControladorPrincipal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        vista.btnReportes.addActionListener(e -> {
            try {
                VistaReportee vistaReporte = new VistaReportee();
                ControladorReporte cntrlReporte = new ControladorReporte(vistaReporte);
                cntrlReporte.iniciar();
            } catch (Exception ex) {
                System.getLogger(ControladorPrincipal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });


    }

    public void iniciar() {
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.setVisible(true);
    }

    private void cerrarSesion() {

        vista.dispose();
        LoginControlador log = new LoginControlador();

        log.iniciar();

    }

}
