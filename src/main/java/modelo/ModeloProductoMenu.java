/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloProductoMenu {
    

    
    private ModeloProducto producto;
    private ModeloMenu menu;

    public ModeloProductoMenu(ModeloProducto producto, ModeloMenu menu) {
        this.producto = producto;
        this.menu = menu;
    }

    public ModeloProducto getProducto() {
        return producto;
    }

    public void setProducto(ModeloProducto producto) {
        this.producto = producto;
    }

    public ModeloMenu getMenu() {
        return menu;
    }

    public void setMenu(ModeloMenu menu) {
        this.menu = menu;
    }


    
    
}
