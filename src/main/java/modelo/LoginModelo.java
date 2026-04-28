/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ayala
 */
public class LoginModelo {
   

    private String usuario;
    private String password;

    public String validarCredenciales() {
        if (this.usuario == null || this.password == null) {
            return "ERROR";
        } else if (usuario.equals("admin") && password.equals("12345")) {
            return "ADMIN";

        }else if(usuario.equals("admin2") && password.endsWith("1234")){
        return "USER";
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

}

    
    

