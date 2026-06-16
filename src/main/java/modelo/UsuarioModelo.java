/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ayala
 */
public class UsuarioModelo {

    private int id_usuario;
    private String usuario;
    private String password;

    public String validarCredenciales() {
        if (this.usuario == null || this.password == null) {
            return "ERROR";
        } else if (usuario.equals("admin") && password.equals("12345")) {
            return "ADMIN";

        } else if (usuario.equals("panaderia") && password.endsWith("1234")) {
            return "UserPanaderia";
        } else if (usuario.equals("cocina") && password.endsWith("1234")) {
            return "UserCocina";
        } else if (usuario.equals("bar") && password.endsWith("1234")) {
            return "UserBar";
        } else if (usuario.equals("mesero") && password.endsWith("1234")) {
            return "UserMesero";
        }
        return "ERROR";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

}
