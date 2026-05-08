/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.dto;

import modelo.ModeloEmpleado;
import modelo.RolModelo;
import modelo.UsuarioModelo;

/**
 *
 * @author ayala
 */
    
    



public class EmpleadoDTO {

    private ModeloEmpleado empleado = new ModeloEmpleado();;
    private UsuarioModelo usuario = new UsuarioModelo();
    private RolModelo rol = new RolModelo();

        public EmpleadoDTO() {
        }

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
    
    

  
}
    

