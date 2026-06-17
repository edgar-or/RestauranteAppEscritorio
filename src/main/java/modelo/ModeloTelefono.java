/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloTelefono {
    private int idTelefono;
    private String telefono;
    
    private ModeloEmpleado empleado;

    public ModeloTelefono(int idTelefono, String telefono, String idEmpleado) {
        this.idTelefono = idTelefono;
        this.telefono = telefono;
    }

    public ModeloTelefono() {
    }

    
    public ModeloEmpleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(ModeloEmpleado empleado) {
        this.empleado = empleado;
    }
    

    public int getIdTelefono() {
        return idTelefono;
    }

    public String getTelefono() {
        return telefono;
    }


    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    
}
