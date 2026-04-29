/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.UsuarioDao;
import dao.dto.LoginResultadoDTO;
import javax.swing.JOptionPane;
import modelo.RolModelo;
import modelo.UsuarioModelo;
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
    private VistaPrincipalAdministrador vista;
    private UsuarioModelo loginModelo; 

    private ControladorPrincipal controladorPrincipal;

    public LoginControlador() {

        this.loginVista = new VistaLogin();
        this.loginModelo = new UsuarioModelo();
        this.vista = null;
        ;

        //Para boton Enter
        this.loginVista.getRootPane().setDefaultButton(this.loginVista.btnLogin);

        this.loginVista.btnLogin.addActionListener(e -> login());
       

    }

    private void login() {
        UsuarioDao dao = new UsuarioDao();

        String usuario = loginVista.txtUsuario.getText();
        String contra = loginVista.txtContraseña.getText();

        LoginResultadoDTO res = dao.validar(usuario, contra);

        if (res != null) {
            RolModelo rol = res.getRol();

            if (rol.getRol().equalsIgnoreCase("administrador")) {
                vista = new VistaPrincipalAdministrador();
                controladorPrincipal = new ControladorPrincipal(vista);
                controladorPrincipal.iniciar();
                loginVista.dispose();
                
            } else if (rol.getRol().equalsIgnoreCase("mesero")) {

            }
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
