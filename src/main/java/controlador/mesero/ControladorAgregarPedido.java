/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import controlador.ControladorAgregarProductos;
import dao.GenerarPedidoDao;
import dao.dto.LoginResultadoDTO;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.ModeloEmpleado;
import modelo.ModeloMesa;
import vista.VistaAgregarPedido;
import vista.VistaLogin;
import vista.VistaPrincipalMesero;
import vista.VistaProductos;

/**
 *
 * @author estud
 */
public class ControladorAgregarPedido {

    private VistaAgregarPedido vistaAgregar;
    private VistaPrincipalMesero principal;
    private VistaProductos visProducto;
    private GenerarPedidoDao genPedido;
    private ModeloEmpleado empleado;

    private ControladorAgregarProductos contrlAgregarProduct;


    public ControladorAgregarPedido(VistaPrincipalMesero principal, ModeloEmpleado empleado) {
        this.genPedido = new GenerarPedidoDao();
        this.empleado = empleado;
        this.visProducto = new VistaProductos();
        this.principal = principal;
        this.vistaAgregar = new VistaAgregarPedido();
        eventos();
        onEvento();
    }

    

                


    public void iniciar() {
        
    
        vistaAgregar.setVisible(true);
                vistaAgregar.setLocationRelativeTo(null);

        llenarComboMesas(); 

    }

    private void eventos() {

        llenarComboMesas(); 


    }


    private void onEvento() {

        
        vistaAgregar.btnProductos.addActionListener(e -> {

            // Evita que se abran múltiples ventanas
            if (visProducto == null || !visProducto.isDisplayable()) {
                visProducto = new VistaProductos();

                ControladorAgregarProductos controlerAgregar = new ControladorAgregarProductos(visProducto);

            }

            visProducto.setLocationRelativeTo(null);
            visProducto.setVisible(true);
        });

        vistaAgregar.btnCerrar.addActionListener(e -> {
            vistaAgregar.dispose();
        });

    

        

        vistaAgregar.btnCerrar.addActionListener(e -> {
            vistaAgregar.dispose();

        });

        vistaAgregar.btnProductos.addActionListener(e -> {

            try {

                registrarPedido();

            } catch (Exception ex) {

                ex.printStackTrace();

            }
        });
        
        //CERRAR VENTANA
        visProducto.btnCerrar.addActionListener(e -> {
            visProducto.dispose();

        });

    }

    public void llenarComboMesas() {

        try {

            ArrayList<ModeloMesa> listaMesas = genPedido.llenarComboMesa();
            vistaAgregar.comboMesa.removeAllItems();

            for (ModeloMesa mesa : listaMesas) {

                vistaAgregar.comboMesa.addItem(mesa);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrarPedido() throws Exception {

        ModeloMesa mesa
                = (ModeloMesa) vistaAgregar.comboMesa.getSelectedItem();

        boolean insertado = genPedido.registrarPedido(Integer.parseInt(mesa.getIdMesa()),empleado.getIdEmpleado());

    }

}
