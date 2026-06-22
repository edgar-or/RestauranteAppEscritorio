/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.mesero;

import dao.GenerarPedidoDao;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.ModeloPedido;
import vista.VistaGenerarPago;

/**
 *
 * @author estud
 */
public class ControladorGenerarPago {

    private double PROPINA = 0.10;

    private VistaGenerarPago vistaGenerarPago;
    private ControladorVerDetallePedido detalleControlador;
    private GenerarPedidoDao dao;
    private int idPedido;
    private double subtotal;  
    private double propina;    
    private double totalPagar; 
    private String mesero;
    private int numeroMesa; 
    
    public ControladorGenerarPago(int idPedido, double total, String mesero, ControladorVerDetallePedido detalleControlador,int numeroMesa) {
        this.vistaGenerarPago = new VistaGenerarPago();
        this.detalleControlador = detalleControlador;
        this.dao = new GenerarPedidoDao();
        this.idPedido = idPedido;
        this.mesero = mesero;
        this.subtotal = total;
        this.propina = total * PROPINA;
        this.totalPagar = subtotal + propina;

        prepararVista();
        eventos();
    }

  
    
    
    
    

    public void iniciar() {
        vistaGenerarPago.setLocationRelativeTo(null);
        vistaGenerarPago.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vistaGenerarPago.setVisible(true);
    }

    private void prepararVista() {
        if (vistaGenerarPago.comboMetodoPago.getItemCount() == 0) {
            vistaGenerarPago.comboMetodoPago.addItem("Efectivo");
            vistaGenerarPago.comboMetodoPago.addItem("Tarjeta");
            vistaGenerarPago.comboMetodoPago.addItem("Transferencia");
        }
        vistaGenerarPago.txtMonto.setText(String.format("%.2f", totalPagar));
        vistaGenerarPago.txtnombre.setText(mesero);
    }

    private void eventos() {
        vistaGenerarPago.btnCerrar.addActionListener(e -> vistaGenerarPago.dispose());
        vistaGenerarPago.btnPagar.addActionListener(e -> pagar());
    }

    private void pagar() {
        Object metodo = vistaGenerarPago.comboMetodoPago.getSelectedItem();
        if (metodo == null) {
            JOptionPane.showMessageDialog(vistaGenerarPago,
                    "Selecciona un método de pago.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double monto;
        try {
            monto = Double.parseDouble(vistaGenerarPago.txtMonto.getText().trim().replace("$", "").replace(",", "."));
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vistaGenerarPago,
                    "Ingresa un monto válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (monto < totalPagar) {
            JOptionPane.showMessageDialog(vistaGenerarPago,
                    String.format("El monto recibido ($%.2f) es menor al total a pagar ($%.2f).", monto, totalPagar),
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if (dao.existeReciboPedido(idPedido)) {
                dao.marcarPedidoPagado(idPedido);
                JOptionPane.showMessageDialog(vistaGenerarPago,
                        "Este pedido ya fue cobrado (ya tiene recibo).",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                if (detalleControlador != null) {
                    detalleControlador.recargar();
                }
                vistaGenerarPago.dispose();
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String nombreCompleto = vistaGenerarPago.txtnombre.getText().trim();
        String nombre = nombreCompleto;
        String apellido = "";
        int sp = nombreCompleto.indexOf(' ');
        if (sp > 0) {
            nombre = nombreCompleto.substring(0, sp);
            apellido = nombreCompleto.substring(sp + 1).trim();
        }

        try {
            boolean ok = dao.registrarPago(idPedido, totalPagar, metodo.toString(), nombre, apellido, propina);
            if (ok) {
                double cambio = monto - totalPagar;
                JOptionPane.showMessageDialog(vistaGenerarPago,
                        String.format("Pago realizado con %s.%nPedido #%d marcado como PAGADO.%n"
                                + "Subtotal: $%.2f%nPropina (10%%): $%.2f%nTotal: $%.2f%nCambio: $%.2f",
                                metodo, idPedido, subtotal, propina, totalPagar, cambio),
                        "Pago exitoso", JOptionPane.INFORMATION_MESSAGE);
                int idMesa = dao.obtenerIdMesa(idPedido); 
                
                dao.actualizarEstadoMesaTrue(idMesa); 
                
                System.out.println("mesa numero "+idMesa);
                
                if (detalleControlador != null) {
                    detalleControlador.recargar();
                }
                vistaGenerarPago.dispose();
            } else {
                JOptionPane.showMessageDialog(vistaGenerarPago,
                        "No se pudo actualizar el pedido. Verifica que exista.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vistaGenerarPago,
                    "Error al procesar el pago:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}