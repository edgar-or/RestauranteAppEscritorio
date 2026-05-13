/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.ProductosParaPedidosDao;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloProducto;
import vista.VistaProductos;

/**
 *
 * @author ayala
 */
public class ControladorAgregarProductos {
    
    private ModeloProducto modelo; 
    private VistaProductos visProduct; 

        private ProductosParaPedidosDao dao;

    public ControladorAgregarProductos(VistaProductos visProduct) {
        this.visProduct = visProduct;
        this.dao = new ProductosParaPedidosDao();
        
        mostrarTablaBebidas();
    }

    public void mostrarTablaBebidas() {

        try {

            List<ModeloProducto> lista = dao.listarBebidas();

            DefaultTableModel modeloTabla =
                    (DefaultTableModel) visProduct.tablaBebidas.getModel();

            // limpiar tabla
            modeloTabla.setRowCount(0);

            for (ModeloProducto bebida : lista) {

                Object[] fila = {
                    bebida.getNombre(),
                    bebida.getDescripcion(),
                    bebida.getPrecio()
                };

                modeloTabla.addRow(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
}
