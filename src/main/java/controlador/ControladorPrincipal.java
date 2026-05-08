/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

//import vista.VistaPrincipalAdministrador;

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
            cntrlEmpleado.abrirVistaEmpleados();
            
        });
        
        
    }


    
    public void iniciar(){
        vista.setVisible(true);
    }
 
    
 
 
}
