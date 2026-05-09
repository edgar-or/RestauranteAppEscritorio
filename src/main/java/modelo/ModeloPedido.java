/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author renec
 */
public class ModeloPedido {

    private String idPedido;
    private LocalDate fecha;
    private double total;
    private boolean estado;
    private String idMesa;
    private String idEmpleado;
    private static ArrayList<ModeloProducto_Pedido> arrayProducto_Pedido = new ArrayList<>();

    public ModeloPedido(String idPedido, LocalDate fecha, double total, boolean estado, String idMesa, String idEmpleado) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.idMesa = idMesa;
        this.idEmpleado = idEmpleado;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

}
