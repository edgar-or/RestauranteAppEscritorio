/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.dto;

import modelo.AreaProduccionModelo;
import modelo.ModeloEmpleado;
import modelo.RolModelo;
import modelo.UsuarioModelo;

/**
 *
 * @author ayala
 */
public class LoginResultadoDTO {
     private UsuarioModelo usuario;
    private RolModelo rol;
    private AreaProduccionModelo area; 
    private ModeloEmpleado empleado; 

    public ModeloEmpleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(ModeloEmpleado empleado) {
        this.empleado = empleado;
    }

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

    public AreaProduccionModelo getArea() {
        return area;
    }

    public void setArea(AreaProduccionModelo area) {
        this.area = area;
    }
    
    
}
