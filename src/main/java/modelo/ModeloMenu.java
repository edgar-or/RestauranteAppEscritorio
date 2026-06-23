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
public class ModeloMenu {

    private String idMenu;
    private String descrpcion;
    
    private static ArrayList<ModeloProductoMenu> arrayProducto_Menu = new ArrayList<>();

    public static ArrayList<ModeloProductoMenu> getArrayProducto_Menu() {
        return arrayProducto_Menu;
    }

    public static void setArrayProducto_Menu(ArrayList<ModeloProductoMenu> arrayProducto_Menu) {
        ModeloMenu.arrayProducto_Menu = arrayProducto_Menu;
    }

    public ModeloMenu(String idMenu, String descrpcion) {
        this.idMenu = idMenu;
        this.descrpcion = descrpcion;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public String getDescrpcion() {
        return descrpcion;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    public void setDescrpcion(String descrpcion) {
        this.descrpcion = descrpcion;
    }

}
