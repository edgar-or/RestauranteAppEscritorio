/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author renec
 */
public class ModeloRecibo {
    private String idRecibo;
    private LocalDate fecha;
    private LocalDate hora;
    private double total;
    private String metodoPago;
    private String nombre;
    private String apellido;
    private double propina;
    
    
    private ModeloPedido pedido;

    public ModeloRecibo(String idRecibo, LocalDate fecha, LocalDate hora, double total, String metodoPago, String nombre, String apellido, double propina, ModeloPedido pedido) {
        this.idRecibo = idRecibo;
        this.fecha = fecha;
        this.hora = hora;
        this.total = total;
        this.metodoPago = metodoPago;
        this.nombre = nombre;
        this.apellido = apellido;
        this.propina = propina;
        this.pedido = pedido;
    }



    public String getIdRecibo() {
        return idRecibo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalDate getHora() {
        return hora;
    }

    public double getTotal() {
        return total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public double getPropina() {
        return propina;
    }


    public void setIdRecibo(String idRecibo) {
        this.idRecibo = idRecibo;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalDate hora) {
        this.hora = hora;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPropina(double propina) {
        this.propina = propina;
    }

    public ModeloPedido getPedido() {
        return pedido;
    }

    public void setPedido(ModeloPedido pedido) {
        this.pedido = pedido;
    }


    
    
}
