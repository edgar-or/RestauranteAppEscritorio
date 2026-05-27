/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author renec
 */
public class ModeloEmpleado {
    private String idEmpleado;
    private String dui;
    private String nombre;
    private String apellido;
    private UsuarioModelo usuario;
    private RolModelo rol;
    private AreaProduccionModelo area; 
    private static ArrayList<ModeloTelefono> arrayTelefono = new ArrayList<>();
    private static ArrayList<ModeloCorreo>  arrayCorreo = new ArrayList<>();
    private static ArrayList<ModeloPedido>  arrayPedido = new ArrayList<>();

    public static ArrayList<ModeloTelefono> getArrayTelefono() {
        return arrayTelefono;
    }

    public AreaProduccionModelo getArea() {
        return area;
    }

    public void setArea(AreaProduccionModelo area) {
        this.area = area;
    }
    
    
    
    

    public static void setArrayTelefono(ArrayList<ModeloTelefono> arrayTelefono) {
        ModeloEmpleado.arrayTelefono = arrayTelefono;
    }

    public static ArrayList<ModeloCorreo> getArrayCorreo() {
        return arrayCorreo;
    }

    public static void setArrayCorreo(ArrayList<ModeloCorreo> arrayCorreo) {
        ModeloEmpleado.arrayCorreo = arrayCorreo;
    }

    public static ArrayList<ModeloPedido> getArrayPedido() {
        return arrayPedido;
    }

    public static void setArrayPedido(ArrayList<ModeloPedido> arrayPedido) {
        ModeloEmpleado.arrayPedido = arrayPedido;
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
}
