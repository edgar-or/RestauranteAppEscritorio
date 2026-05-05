/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.PedidoBarDao;
import dao.dto.PedidoBarDto;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import vista.VistaBar;

/**
 *
 * @author ayala
 */
public class ControladorBar {
    VistaBar visBar; 

    public ControladorBar(VistaBar visBar) {
        this.visBar = visBar;
        cargarTabla();
        
    }
    
    public void iniciar(){
        visBar.setVisible(true);
    }
    
    public void cargarTabla() {

    try {
        PedidoBarDao dao = new PedidoBarDao();
        List<PedidoBarDto> lista = dao.listar();

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Producto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");

        for (PedidoBarDto p : lista) {
            modelo.addRow(new Object[]{
                p.getNombreProducto(),
                p.getDescripcion(),
                p.isEstado()
            });
        }

        visBar.tablaBar.setModel(modelo);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    
    
}
