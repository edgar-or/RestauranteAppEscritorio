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
public class AreaProduccionModelo {

    private int idProduccion;
    private String nombre;
    private String descripcion;
    private String especializacion;
    private int idEmpleado;
    private static ArrayList<ModeloEmpleado> arrayEmpleado = new ArrayList<>();
    

    public static ArrayList<ModeloEmpleado> getArrayEmpleado() {
        return arrayEmpleado;
    }

    public static void setArrayEmpleado(ArrayList<ModeloEmpleado> arrayEmpleado) {
        AreaProduccionModelo.arrayEmpleado = arrayEmpleado;
    }

    public AreaProduccionModelo() {
    }

    public int getIdProduccion() {
        return idProduccion;
    }

    public void setIdProduccion(int idProduccion) {
        this.idProduccion = idProduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

}
