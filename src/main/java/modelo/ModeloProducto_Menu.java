/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloProducto_Menu {
    private String idProducto;
    private String idMenu;

    public ModeloProducto_Menu(String idProducto, String idMenu) {
        this.idProducto = idProducto;
        this.idMenu = idMenu;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getIdMenu() {
        return idMenu;
    }
    
    
    
}
