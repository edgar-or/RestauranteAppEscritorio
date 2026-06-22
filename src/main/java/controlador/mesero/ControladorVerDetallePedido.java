/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import dao.GenerarPedidoDao;
import dao.conexion.Conexion;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloEmpleado;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import vista.VistaPrincipalMesero;
import vista.VistaVerDetallePedido;

/**
 *
 * @author estud
 */
public class ControladorVerDetallePedido {

    private double PROPINA = 0.10;

    private VistaVerDetallePedido vista;
    private GenerarPedidoDao dao;
    private int idPedido;
    private VistaPrincipalMesero principal;
    private ModeloEmpleado empleado;
    private double total = 0.0;
    private String mesero = "";
    private int numeroMesa = -1;

    public ControladorVerDetallePedido(VistaVerDetallePedido vista, int idPedido,
            VistaPrincipalMesero principal, ModeloEmpleado empleado) {
        this.vista = vista;
        this.dao = new GenerarPedidoDao();
        this.idPedido = idPedido;
        this.principal = principal;
        this.empleado = empleado;

        configurarTabla();
        cargarDatosPedido();
        registrarEventos();
        iniciarAutoRefresh();
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID Prod", "Producto", "Cant.", "Subtotal", "Nota", "Estado Orden"}, 0);
        vista.tablaPedido.setModel(modelo); // Asegúrate que en VistaVerDetallePedido la tabla se llame 'tabla'
    }

    private void cargarDatosPedido() {
        try {
            // 1. Cargar cabecera
            Object[] cabecera = dao.obtenerPedidoCabecera(idPedido);
            if (cabecera != null) {
                // Posiciones según el array devuelto por tu DAO:
                // [0]id, [1]fecha, [2]mesa, [3]mesero, [4]total, [5]estado

                String mesaTxt = (String) cabecera[2];
                vista.lblMesa.setText(mesaTxt);
                // Extrae el número de mesa de "Mesa N" para poder editar el pedido
                try {
                    this.numeroMesa = Integer.parseInt(mesaTxt.replaceAll("[^0-9]", ""));
                } catch (NumberFormatException ignore) {
                    this.numeroMesa = -1;
                }
                this.mesero = (String) cabecera[3];
                vista.lblMesero.setText(this.mesero);

                this.total = (double) cabecera[4];
                double propina = this.total * PROPINA;
                double totalConPropina = this.total + propina;

                vista.lblSubtotal.setText(String.format("$%.2f", this.total));
                vista.lblPropina.setText(String.format("$%.2f", propina));
                vista.lblTotal.setText(String.format("$%.2f", totalConPropina));

                // --- AGREGA ESTAS DOS LÍNEAS ---
                vista.lblFecha.setText(cabecera[1].toString());
                vista.lblEstado.setText((boolean) cabecera[5] ? "PAGADO" : "PENDIENTE");
                // -------------------------------
            }

            // 2. Cargar detalles
            List<Object[]> items = dao.obtenerDetallesPedido(idPedido);
            DefaultTableModel modelo = (DefaultTableModel) vista.tablaPedido.getModel();
            modelo.setRowCount(0);
            for (Object[] item : items) {
                modelo.addRow(item);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar datos: " + e.getMessage());
        }
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void registrarEventos() {
        vista.btnCerrar.addActionListener(e -> vista.dispose());
        vista.btnImprmirRecibo.addActionListener(e -> imprimirFactura());
        vista.btngenerarPago.addActionListener(e -> abrirGenerarPago());
        vista.btnActualizar.addActionListener(e -> recargarManual());
        vista.btnEditar.addActionListener(e -> editarPedido());
    }

    private void editarPedido() {
        if ("PAGADO".equalsIgnoreCase(vista.lblEstado.getText())) {
            JOptionPane.showMessageDialog(vista,
                    "Este pedido ya está pagado, no se pueden agregar productos.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ControladorAgregarPedido cap = new ControladorAgregarPedido(principal, empleado);
        if (numeroMesa > 0) {
            cap.seleccionarMesaPorNumero(numeroMesa);
        }
        cap.iniciar();
    }

    public void recargar() {
        cargarDatosPedido();
    }

    private void recargarManual() {
        cargarDatosPedido();
        JOptionPane.showMessageDialog(vista,
                "Datos del pedido actualizados.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirGenerarPago() {
        if ("PAGADO".equalsIgnoreCase(vista.lblEstado.getText())) {
            JOptionPane.showMessageDialog(vista,
                    "Este pedido ya está pagado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ControladorGenerarPago cp = new ControladorGenerarPago(idPedido, total, mesero, this, numeroMesa);
        cp.iniciar();
    }

    private void imprimirFactura() {
        try (Connection cn = Conexion.getConnection()) {
            if (cn == null) {
                JOptionPane.showMessageDialog(vista,
                        "No se pudo establecer conexión con la base de datos.",
                        "Error de conexión", JOptionPane.ERROR_MESSAGE);
                return;
            }

            InputStream archivo = getClass().getResourceAsStream("/R3_TicketPedido.jasper");
            if (archivo == null) {
                JOptionPane.showMessageDialog(vista,
                        "No se encontró el reporte: R3_TicketPedido.jasper",
                        "Reporte no encontrado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // El reporte R3 espera el parámetro 'idpedido'
            Map<String, Object> params = new HashMap<>();
            params.put("idpedido", idPedido);

            JasperPrint jp = JasperFillManager.fillReport(archivo, params, cn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setVisible(true);
        } catch (Exception ex) {
            StringBuilder sb = new StringBuilder("Error al imprimir factura:\n");
            Throwable t = ex;
            while (t != null) {
                sb.append("\n• ").append(t.getClass().getSimpleName())
                        .append(": ").append(t.getMessage());
                t = t.getCause();
            }
            JOptionPane.showMessageDialog(vista, sb.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void iniciarAutoRefresh() {

        Timer timer = new Timer(6000, e -> {
            cargarDatosPedido();
        });

        timer.start();
    }

}
