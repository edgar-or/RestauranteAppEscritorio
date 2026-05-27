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
    private String idProduccion;
    private AreaProduccionModelo areaProduccion; 
    

    public AreaProduccionModelo getAreaProduccion() {
        return areaProduccion;
    }

    public void setAreaProduccion(AreaProduccionModelo areaProduccion) {
        this.areaProduccion = areaProduccion;
    }
    
    
    private static ArrayList<ModeloProducto_Menu> arrayProducto_Menu = new ArrayList<>();
    private static ArrayList<ModeloProducto_Pedido> arrayProducto_Pedido = new ArrayList<>();

    public static ArrayList<ModeloProducto_Menu> getArrayProducto_Menu() {
        return arrayProducto_Menu;
    }

    public ModeloProducto() {
    }
    
    

    public static void setArrayProducto_Menu(ArrayList<ModeloProducto_Menu> arrayProducto_Menu) {
        ModeloProducto.arrayProducto_Menu = arrayProducto_Menu;
    }

    public static ArrayList<ModeloProducto_Pedido> getArrayProducto_Pedido() {
        return arrayProducto_Pedido;
    }

    public static void setArrayProducto_Pedido(ArrayList<ModeloProducto_Pedido> arrayProducto_Pedido) {
        ModeloProducto.arrayProducto_Pedido = arrayProducto_Pedido;
    }

    public ModeloProducto(String idProducto, String nombre, String descripcion, double precio, String idProduccion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idProduccion = idProduccion;
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

    public String getIdProduccion() {
        return idProduccion;
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

    public void setIdProduccion(String idProduccion) {
        this.idProduccion = idProduccion;
    }

}
