/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloMenu {
    private String idMenu;
    private String descrpcion;

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
