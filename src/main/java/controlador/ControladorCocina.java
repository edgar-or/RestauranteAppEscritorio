/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaCocina;

/**
 *
 * @author ayala
 */
public class ControladorCocina {
    VistaCocina visCocina; 

    public ControladorCocina(VistaCocina visCocina) {
        this.visCocina = visCocina;
    }
    
    public void iniciar(){
        visCocina.setVisible(true);
    }
    
}
