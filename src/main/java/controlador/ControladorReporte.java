
package controlador;

import dao.conexion.Conexion;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import vista.VistaReportee;

/**
 * 
 *
 * @author mendo
 */
public class ControladorReporte {

    private  VistaReportee vista;

    public ControladorReporte(VistaReportee vista) {
        this.vista = vista;
        registrarEventos();
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void registrarEventos() {
        vista.btnR1.addActionListener(evt -> abrirReporte("R1_IngresosPorArea.jasper"));
        vista.btnR2.addActionListener(evt -> abrirReporte("R2_ListadoProductos.jasper"));
        vista.btnR4.addActionListener(evt -> abrirReporte("R4_ConsolidadoSemestral.jasper"));
    }

    private void abrirReporte(String nombreReporte) {
        try {
            Connection cn = Conexion.getConnection();
            if (cn == null) {
                JOptionPane.showMessageDialog(vista,
                        "No se pudo establecer conexión con la base de datos.",
                        "Error de conexión", JOptionPane.ERROR_MESSAGE);
                return;
            }

            InputStream archivo = getClass().getResourceAsStream("/" + nombreReporte);
            if (archivo == null) {
                JOptionPane.showMessageDialog(vista,
                        "No se encontró el reporte: " + nombreReporte,
                        "Reporte no encontrado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JasperPrint jp = JasperFillManager.fillReport(archivo, new HashMap<>(), cn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Error al abrir reporte:\n");
            Throwable t = e;
            while (t != null) {
                sb.append("\n• ").append(t.getClass().getSimpleName())
                  .append(": ").append(t.getMessage());
                t = t.getCause();
            }
            JOptionPane.showMessageDialog(vista, sb.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
