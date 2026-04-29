/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.dto;

import modelo.RolModelo;
import modelo.UsuarioModelo;

/**
 *
 * @author ayala
 */
public class LoginResultadoDTO {
     private UsuarioModelo usuario;
    private RolModelo rol;

    public UsuarioModelo getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModelo usuario) {
        this.usuario = usuario;
    }

    public RolModelo getRol() {
        return rol;
    }

    public void setRol(RolModelo rol) {
        this.rol = rol;
    }
    
}
