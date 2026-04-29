/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaBar;

/**
 *
 * @author ayala
 */
public class ControladorBar {
    VistaBar visBar; 

    public ControladorBar(VistaBar visBar) {
        this.visBar = visBar;
        
        
        
    }
    
    public void iniciar(){
        visBar.setVisible(true);
    }
    
    
    
}
