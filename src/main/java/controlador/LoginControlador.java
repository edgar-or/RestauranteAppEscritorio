/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;
import modelo.LoginModelo;
import vista.VistaBar;
import vista.VistaCocina;
import vista.VistaLogin;
import vista.VistaPanaderia;
import vista.VistaPrincipalAdministrador;
import vista.VistaPrincipalMesero;

/**
 *
 * @author ayala
 */
public class LoginControlador {

    private final VistaLogin loginVista;
    private final LoginModelo loginModelo;
    private VistaPrincipalAdministrador vista;
    private VistaPrincipalMesero vistaMesero;
    private VistaCocina vistaCocina;
    private VistaBar vistaBar;
    private VistaPanaderia vistaPanaderia;

    public LoginControlador() {
        
        this.loginVista = new VistaLogin();
        this.loginModelo =  new LoginModelo();
        this.vista = null;
        this.vistaMesero = new VistaPrincipalMesero();

        //Para boton Enter
        this.loginVista.getRootPane().setDefaultButton(this.loginVista.btnLogin);

        this.loginVista.btnLogin.addActionListener(e -> login());

    }

    private void login() {

        String usuario = loginVista.txtUsuario.getText();
        String password = new String(loginVista.txtContraseña.getText());

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarError("El usuario y la contraseña no pueden estar vacíos.");
            return;
        }

        loginModelo.setUsuario(usuario);
        loginModelo.setPassword(password);

        String tipo = loginModelo.validarCredenciales();
        if (tipo.equals("ADMIN")) {

            vista = new VistaPrincipalAdministrador();

            ControladorPrincipal controladorPricipal = new ControladorPrincipal(vista);
            controladorPricipal.iniciar();

            // Registrar listener DESPUÉS de crear la vista
            vista.btnCerrarSesion.addActionListener(e -> cerrarSesion());

            cerrar();
//        } else if (tipo.equals("USER")) {
//            VistaPrincipalMaestros visMaestros = new VistaPrincipalMaestros();
//            ControladorPrinciplaMaestros controladorMaestros = new ControladorPrinciplaMaestros(visMaestros);
//            controladorMaestros.iniciar();
//
//            visMaestros.btnCerrarsesion.addActionListener(e -> {
//                visMaestros.dispose();
//                iniciar();
//
//            });
//            cerrar();
//
//        } else {
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
        vista.dispose();
        vista = null;
        iniciar();

        loginVista.txtUsuario.setText("");
        loginVista.txtContraseña.setText("");
        loginVista.txtUsuario.requestFocus();
    }

}
