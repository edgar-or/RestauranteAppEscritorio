/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.UsuarioDao;
import dao.dto.LoginResultadoDTO;
import javax.swing.JOptionPane;
import modelo.AreaProduccionModelo;
import modelo.RolModelo;
import modelo.UsuarioModelo;
import vista.VistaBar;
import vista.VistaCocina;
import vista.VistaLogin;
import vista.VistaPanaderia;
//import vista.VistaPrincipalAdministrador;
import vista.VistaPrincipalMesero;

/**
 *
 * @author ayala
 */
public class LoginControlador {

    private final VistaLogin loginVista;
   // private VistaPrincipalAdministrador vista;
    private UsuarioModelo loginModelo;
    private ControladorCocina controladorCocina;
    private ControladorBar controladorBar;
    private VistaBar visBar;
    private VistaCocina visCocina;
    private VistaPanaderia visPan;
    private ControladorPanaderia controladorPan;
    private ControladorMesero controladorMesero;
    private VistaPrincipalMesero visMesero;

    private ControladorPrincipal controladorPrincipal;

    public LoginControlador() {

        this.loginVista = new VistaLogin();
        this.loginModelo = new UsuarioModelo();
        //this.vista = null;
        ;

        //Para boton Enter
        this.loginVista.getRootPane().setDefaultButton(this.loginVista.btnLogin);

        this.loginVista.btnLogin.addActionListener(e -> login());

    }

    private void login() {
        UsuarioDao dao = new UsuarioDao();

        String usuario = loginVista.txtUsuario.getText();
        String contra = loginVista.txtcontrasenia.getText();

        LoginResultadoDTO res = dao.validar(usuario, contra);

        if (res != null) {
            RolModelo rol = res.getRol();
            AreaProduccionModelo area = res.getArea();

            System.out.println("ROL -> '" + rol.getRol() + "'");
            if (rol.getIdRol()==1) {
                //vista = new VistaPrincipalAdministrador();
                //controladorPrincipal = new ControladorPrincipal(vista);
                controladorPrincipal.iniciar();
                cerrar();

            } else if (rol.getRol().equalsIgnoreCase("empleado")) {

                if (area.getNombre().equalsIgnoreCase("mesero")) {
                    visMesero = new VistaPrincipalMesero();
                    controladorMesero = new ControladorMesero(visMesero);
                    controladorMesero.iniciar();
                    cerrar();
                } else if (area.getNombre().equalsIgnoreCase("cocina")) {
                    visCocina = new VistaCocina();
                    controladorCocina = new ControladorCocina(visCocina);
                    controladorCocina.iniciar();
                    cerrar();
                } else if (area.getNombre().equalsIgnoreCase("Bar")) {
                    visBar = new VistaBar();
                    controladorBar = new ControladorBar(visBar);
                    controladorBar.iniciar();
                    controladorBar.cargarTabla();
                    cerrar();
                } else if (area.getNombre().equalsIgnoreCase("panaderia")) {
                    visPan = new VistaPanaderia();
                    controladorPan = new ControladorPanaderia(visPan);
                    controladorPan.iniciar();
                    cerrar();
                }

            }

        } else {
            mostrarError("Usuario o contraseña incorrectos");
        }

    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(loginVista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void cerrar() {
        loginVista.dispose();
    }

    public void iniciar() {

        loginVista.setLocationRelativeTo(null);
        loginVista.getRootPane().setDefaultButton(loginVista.btnLogin);
        loginVista.setVisible(true);
    }

    private void cerrarSesion() {
        //vista.dispose();
        //vista = null;

        loginVista.txtUsuario.setText("");
        loginVista.txtcontrasenia.setText("");
        loginVista.txtUsuario.requestFocus();
    }

}
