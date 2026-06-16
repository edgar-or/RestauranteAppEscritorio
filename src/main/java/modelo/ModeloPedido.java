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
    
    private ModeloMesa mesa;
    private ModeloEmpleado empleado;
    
    private static ArrayList<ModeloRecibo> arrayRecibo = new ArrayList<>();
    private static ArrayList<ModeloProducto_Pedido> arrayProducto_Pedido = new ArrayList<>();



    public static ArrayList<ModeloProducto_Pedido> getArrayProducto_Pedido() {
        return arrayProducto_Pedido;
    }

    public static void setArrayProducto_Pedido(ArrayList<ModeloProducto_Pedido> arrayProducto_Pedido) {
        ModeloPedido.arrayProducto_Pedido = arrayProducto_Pedido;
    }

    public ModeloPedido(String idPedido, LocalDate fecha, double total, boolean estado, ModeloMesa mesa, ModeloEmpleado empleado) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.mesa = mesa;
        this.empleado = empleado;
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

    public ModeloMesa getMesa() {
        return mesa;
    }

    public void setMesa(ModeloMesa mesa) {
        this.mesa = mesa;
    }

    public ModeloEmpleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(ModeloEmpleado empleado) {
        this.empleado = empleado;
    }



}
