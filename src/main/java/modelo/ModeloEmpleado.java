/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloEmpleado {
    private String idEmpleado;
    private String dui;
    private String nombre;
    private String apellido;
    private String idUsuario;
    private String idRol;

    public ModeloEmpleado(String idEmpleado, String dui, String nombre, String apellido, String idUsuario, String idRol) {
        this.idEmpleado = idEmpleado;
        this.dui = dui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    public ModeloEmpleado() {
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getDui() {
        return dui;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }
    
    
    
    
}
