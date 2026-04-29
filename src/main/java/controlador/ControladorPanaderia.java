/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaPanaderia;

/**
 *
 * @author ayala
 */
public class ControladorPanaderia {
    VistaPanaderia visPanaderia; 

    public ControladorPanaderia(VistaPanaderia visPanaderia) {
        this.visPanaderia = visPanaderia;
    }
    
    public void iniciar(){
        visPanaderia.setVisible(true);
    }
    
}
