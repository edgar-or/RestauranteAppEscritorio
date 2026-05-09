/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.CocinaDao;
import dao.PedidoBarDao;
import dao.dto.PedidoBarDto;
import dao.dto.PedidoCocinaDto;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import vista.VistaCocina;
import vista.VistaLogin;

/**
 *
 * @author ayala
 */
public class ControladorCocina {

    VistaCocina visCocina;
    VistaLogin visLogin;
    LoginControlador loginContro;
    public ControladorCocina(VistaCocina visCocina) {
        this.visCocina = visCocina;
        cargarTabla();
        eventos();
    }

    public void iniciar() {
        visCocina.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visCocina.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visCocina.setVisible(true);
    }
    
   

    public void cargarTabla() {

        try {
            CocinaDao dao = new CocinaDao();
            List<PedidoCocinaDto> lista = dao.listar();

            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("Producto");
            modelo.addColumn("Descripción");
            modelo.addColumn("Estado");

            for (PedidoCocinaDto p : lista) {
                modelo.addRow(new Object[]{
                    p.getNombreProducto(),
                    p.getDescripcion(),
                    p.isEstado()
                });
            }

            visCocina.tablaCocina.setModel(modelo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void eventos() {
        visCocina.btnCerrarsesion.addActionListener(e->{
        visCocina.dispose();
        
        VistaLogin login= new VistaLogin();
        LoginControlador ctrl= new LoginControlador();
        ctrl.iniciar();
        });
        
    }
    
}
