/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloCorreo {
    private String idCorreo;
    private String correo;
    private String idEmpleado;

    public ModeloCorreo(String idCorreo, String correo, String idEmpleado) {
        this.idCorreo = idCorreo;
        this.correo = correo;
        this.idEmpleado = idEmpleado;
    }

    public String getIdCorreo() {
        return idCorreo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdCorreo(String idCorreo) {
        this.idCorreo = idCorreo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    
    
    
}
