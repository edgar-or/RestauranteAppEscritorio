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
    private String idTelefono;
    private int telefono;
    private String idEmpleado;

    public ModeloTelefono(String idTelefono, int telefono, String idEmpleado) {
        this.idTelefono = idTelefono;
        this.telefono = telefono;
        this.idEmpleado = idEmpleado;
    }
    

    public String getIdTelefono() {
        return idTelefono;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdTelefono(String idTelefono) {
        this.idTelefono = idTelefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
}
