/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.PedidoBarDao;
import dao.PedidoPanaderiaDao;
import dao.dto.PedidoBarDto;
import dao.dto.PedidoPanaderiaDto;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import vista.VistaLogin;
import vista.VistaPanaderia;



/**
 *
 * @author ayala
 */
public class ControladorPanaderia {
    VistaPanaderia visPanaderia; 

    public ControladorPanaderia(VistaPanaderia visPanaderia) {
        this.visPanaderia = visPanaderia;
        cargarTabla();
        eventos();
    }
    
    public void iniciar(){
        visPanaderia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visPanaderia.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visPanaderia.setVisible(true);
    }
    
       public void cargarTabla() {

    try {
        PedidoPanaderiaDao dao = new PedidoPanaderiaDao();
        List<PedidoPanaderiaDto> lista = dao.listar();

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Estado");

        for (PedidoPanaderiaDto p : lista) {
            modelo.addRow(new Object[]{
                p.getNombreProducto(),
                p.getCantidad(),
                p.getDescripcion(),
                p.isEstado()
            });
        }

        visPanaderia.tablaPanaderia.setModel(modelo);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void eventos() {
         visPanaderia.btnCerrarsesion.addActionListener(e->{
        visPanaderia.dispose();
        
        VistaLogin login= new VistaLogin();
        LoginControlador ctrl= new LoginControlador();
        ctrl.iniciar();
        });
    }
    
}
