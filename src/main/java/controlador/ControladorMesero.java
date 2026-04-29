/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaPrincipalMesero;

/**
 *
 * @author ayala
 */
public class ControladorMesero {
    VistaPrincipalMesero visMesero; 

    public ControladorMesero(VistaPrincipalMesero visMesero) {
        this.visMesero = visMesero;
    }
    
    public void iniciar(){
        visMesero.setVisible(true);
    }
    
}
