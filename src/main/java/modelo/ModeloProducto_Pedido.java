/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloProducto_Pedido {

    private String idPedido;

    private int cantidad;
    private double subTotal;
    private String nota;
    private boolean estadoOrden;

    private ModeloProducto producto;
    private ModeloPedido pedido;    

    public boolean isEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(boolean estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public ModeloProducto_Pedido() {
    }

    public ModeloProducto_Pedido(String idPedido, ModeloProducto producto, ModeloPedido pedido, int cantidad, double subTotal) {
        this.idPedido = idPedido;
        this.producto = producto;
        this.pedido = pedido;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public ModeloProducto getProducto() {
        return producto;
    }

    public void setProducto(ModeloProducto producto) {
        this.producto = producto;
    }

    public ModeloPedido getPedido() {
        return pedido;
    }

    public void setPedido(ModeloPedido pedido) {
        this.pedido = pedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

}
