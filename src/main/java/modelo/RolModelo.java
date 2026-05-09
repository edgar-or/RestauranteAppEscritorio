/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author ayala
 */
public class RolModelo {
    private int idRol; 
    private String rol; 
    private static ArrayList<ModeloEmpleado> arrayEmpleado = new ArrayList<>();
    private static ArrayList<ModeloProducto> arrayProducto = new ArrayList<>();

    public static ArrayList<ModeloEmpleado> getArrayEmpleado() {
        return arrayEmpleado;
    }

    public static void setArrayEmpleado(ArrayList<ModeloEmpleado> arrayEmpleado) {
        RolModelo.arrayEmpleado = arrayEmpleado;
    }

    public static ArrayList<ModeloProducto> getArrayProducto() {
        return arrayProducto;
    }

    public static void setArrayProducto(ArrayList<ModeloProducto> arrayProducto) {
        RolModelo.arrayProducto = arrayProducto;
    }
    
    public RolModelo() {
    }

    public RolModelo(int idRol, String rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
}
