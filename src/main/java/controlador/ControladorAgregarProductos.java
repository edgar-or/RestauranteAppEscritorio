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
        mostrarTablaPostres();
        mostrarTablaPlatillos();

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
    
    public void mostrarTablaPostres() {

        try {

            List<ModeloProducto> lista = dao.listarPostres();

            DefaultTableModel modeloTabla =
                    (DefaultTableModel) visProduct.tablaPostres.getModel();

            // limpiar tabla
            modeloTabla.setRowCount(0);

            for (ModeloProducto postre : lista) {

                Object[] fila = {
                    postre.getNombre(),
                    postre.getDescripcion(),
                    postre.getPrecio()
                };

                modeloTabla.addRow(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    
    
    }
    public void mostrarTablaPlatillos() {

        try {

            List<ModeloProducto> lista = dao.listarPlatillos();

            DefaultTableModel modeloTabla =
                    (DefaultTableModel) visProduct.tablaPlatillos.getModel();

            // limpiar tabla
            modeloTabla.setRowCount(0);

            for (ModeloProducto platillos : lista) {

                Object[] fila = {
                    platillos.getNombre(),
                    platillos.getDescripcion(),
                    platillos.getPrecio()
                };

                modeloTabla.addRow(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    
    
    }
    
}
