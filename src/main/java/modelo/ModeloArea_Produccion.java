/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloArea_Produccion {
    
    private String idProduccion;
    private String nombre;
    private String descripcion;
    private String especializacion;
    private String idEmpleado;

    public ModeloArea_Produccion(String idProduccion, String nombre, String descripcion, String especializacion, String idEmpleado) {
        this.idProduccion = idProduccion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.especializacion = especializacion;
        this.idEmpleado = idEmpleado;
    }

    public String getIdProduccion() {
        return idProduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdProduccion(String idProduccion) {
        this.idProduccion = idProduccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    
}
