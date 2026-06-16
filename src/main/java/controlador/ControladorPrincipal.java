/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

//import vista.VistaPrincipalAdministrador;

import javax.swing.JFrame;
import vista.VistaPrincipal;


/**
 *
 * @author estud
 */
public class ControladorPrincipal {
  VistaPrincipal vista;
 LoginControlador logingControlador;
ControladorEmpleado cntrlEmpleado;  

    public ControladorPrincipal(VistaPrincipal vista) {
        this.vista = vista;
        
        this.cntrlEmpleado = new ControladorEmpleado(vista); 
        
        vista.btnEmpleados.addActionListener(e-> {
            try {
                cntrlEmpleado.abrirVistaEmpleados();
            } catch (Exception ex) {
                System.getLogger(ControladorPrincipal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
        });
        
        
    }


    
    public void iniciar(){
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.setVisible(true);
    }
 
    
 
 
}
