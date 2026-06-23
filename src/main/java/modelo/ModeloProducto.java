/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author renec
 */
public class ModeloProducto {

    private String idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    
    private AreaProduccionModelo areaProduccion; 
    
    private ModeloProductoPedido productoPedido;
    private ModeloProductoMenu productoMenu;
    

    public AreaProduccionModelo getAreaProduccion() {
        return areaProduccion;
    }

    public void setAreaProduccion(AreaProduccionModelo areaProduccion) {
        this.areaProduccion = areaProduccion;
    }
    
    
    private static ArrayList<ModeloProductoMenu> arrayProducto_Menu = new ArrayList<>();
    private static ArrayList<ModeloProductoPedido> arrayProducto_Pedido = new ArrayList<>();

    public static ArrayList<ModeloProductoMenu> getArrayProducto_Menu() {
        return arrayProducto_Menu;
    }

    public ModeloProducto() {
    }
    
    

    public static void setArrayProducto_Menu(ArrayList<ModeloProductoMenu> arrayProducto_Menu) {
        ModeloProducto.arrayProducto_Menu = arrayProducto_Menu;
    }

    public static ArrayList<ModeloProductoPedido> getArrayProducto_Pedido() {
        return arrayProducto_Pedido;
    }

    public static void setArrayProducto_Pedido(ArrayList<ModeloProductoPedido> arrayProducto_Pedido) {
        ModeloProducto.arrayProducto_Pedido = arrayProducto_Pedido;
    }

    public ModeloProducto(String idProducto, String nombre, String descripcion, double precio, AreaProduccionModelo areaProduccion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.areaProduccion = areaProduccion;
    }



    public String getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }



    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }



}
