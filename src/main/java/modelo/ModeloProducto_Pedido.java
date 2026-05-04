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
    private String idProducto;
    private String idPedido;

    public ModeloProducto_Pedido(String idProducto, String idPedido) {
        this.idProducto = idProducto;
        this.idPedido = idPedido;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }
    
    
    
}
