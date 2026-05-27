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
  
    private ModeloEmpleado empleado;
    

    public ModeloCorreo(String idCorreo, String correo, String idEmpleado) {
        this.idCorreo = idCorreo;
        this.correo = correo;
    }

    public String getIdCorreo() {
        return idCorreo;
    }

    public String getCorreo() {
        return correo;
    }



    public void setIdCorreo(String idCorreo) {
        this.idCorreo = idCorreo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public ModeloEmpleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(ModeloEmpleado empleado) {
        this.empleado = empleado;
    }


    
    
    
    
}
