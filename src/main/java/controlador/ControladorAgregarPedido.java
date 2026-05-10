/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.GenerarPedidoDao;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.ModeloMesa;
import vista.VistaAgregarPedido;
import vista.VistaLogin;
import vista.VistaPrincipalMesero;

/**
 *
 * @author estud
 */
public class ControladorAgregarPedido {

    private VistaAgregarPedido vistaAgregar;
    private VistaPrincipalMesero principal;
    private GenerarPedidoDao genPedido;

    public ControladorAgregarPedido(VistaPrincipalMesero principal) {
        this.genPedido = new GenerarPedidoDao();

        this.principal = principal;
        this.vistaAgregar = new VistaAgregarPedido();
        eventos();
    }

    public void iniciar() {
        vistaAgregar.setVisible(true);
        llenarComboMesas(); 
    }

    private void eventos() {
        vistaAgregar.btnCerrar.addActionListener(e -> {
            vistaAgregar.dispose();

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

}
