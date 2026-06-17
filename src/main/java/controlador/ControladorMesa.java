/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.MesaDao;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloMesa;
import vista.VistaGestionMesas;

/**
 *
 * @author ayala
 */
public class ControladorMesa {

    private VistaGestionMesas vista;
    private MesaDao mesaDao;

    public ControladorMesa() throws Exception {

        mesaDao = new MesaDao();

        iniciarVista();
        eventos();
        configurarColumnas();
        listarMesas();

    }

    private void iniciarVista() {

        vista = new VistaGestionMesas();

        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }

    private void eventos() {
        vista.btnCerrar.addActionListener(e -> {
            vista.dispose();
        });

        vista.btnGuardar.addActionListener(e -> {
            guardar();
        });

        vista.btnActualizar.addActionListener(e -> {
            actualizar();
        });

        vista.btnEliminar.addActionListener(e -> {
            eliminar();
        });

        vista.btnLimpiar.addActionListener(e -> {
            limpiarCampos();
        });

        vista.btnNuevo.addActionListener(e -> {
            limpiarCampos();
        });

        vista.btnBuscar.addActionListener(e -> {
            buscar();
        });

        vista.tablaMesas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });

    }

    private void configurarColumnas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Número");
        modelo.addColumn("Estado");
        modelo.addColumn("Capacidad");
        vista.tablaMesas.setModel(modelo);
    }

    private void cargarTabla(ArrayList<ModeloMesa> mesas) {
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMesas.getModel();
        modelo.setRowCount(0);

        for (ModeloMesa m : mesas) {
            modelo.addRow(new Object[]{
                m.getIdMesa(),
                m.getNumeroMesa(),
                m.isEstado() ? "Disponible" : "Ocupada",
                m.getCapacidad()
            });
        }
    }

    private void listarMesas() throws Exception {
        ArrayList<ModeloMesa> mesas = mesaDao.listarMesas();
        cargarTabla(mesas);
    }

    private void seleccionarFila() {
        int fila = vista.tablaMesas.getSelectedRow();
        if (fila == -1) {
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) vista.tablaMesas.getModel();

        Object id = modelo.getValueAt(fila, 0);
        Object numero = modelo.getValueAt(fila, 1);
        Object estado = modelo.getValueAt(fila, 2);

        Object capacidad = modelo.getValueAt(fila, 3);

        vista.txtId.setText(String.valueOf(id));
        vista.txtNumero.setText(String.valueOf(numero));
        vista.spinnerCapacidad.setValue(capacidad);
        vista.checkDisponible.setSelected("Disponible".equals(estado));

    }

    private void guardar() {
        try {
            String numeroTexto = vista.txtNumero.getText().trim();

            if (numeroTexto.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El número de mesa es obligatorio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int numero = Integer.parseInt(numeroTexto);
            int capacidad = (Integer) vista.spinnerCapacidad.getValue();

            ModeloMesa mesa = new ModeloMesa();
            mesa.setNumeroMesa(numero);
            mesa.setCapacidad(capacidad);
            mesa.setEstado(true);

            mesaDao.insertar(mesa);

            JOptionPane.showMessageDialog(vista, "Mesa guardada correctamente.");
            limpiarCampos();
            listarMesas();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El número de mesa debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizar() {
        try {
            String id = vista.txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Selecciona una mesa de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String numeroTexto = vista.txtNumero.getText().trim();
            if (numeroTexto.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El número de mesa es obligatorio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int numero = Integer.parseInt(numeroTexto);
            int capacidad = (Integer) vista.spinnerCapacidad.getValue();
            boolean disponible = vista.checkDisponible.isSelected();

            ModeloMesa mesa = new ModeloMesa();
            mesa.setIdMesa(id);
            mesa.setNumeroMesa(numero);
            mesa.setCapacidad(capacidad);
            mesa.setEstado(disponible);

            mesaDao.modificar(mesa);

            JOptionPane.showMessageDialog(vista, "Mesa actualizada correctamente.");
            limpiarCampos();
            listarMesas();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El número de mesa debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        try {
            String id = vista.txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Selecciona una mesa de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Está seguro de eliminar esta mesa?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            mesaDao.eliminar(id);

            JOptionPane.showMessageDialog(vista, "Mesa eliminada correctamente.");
            limpiarCampos();
            listarMesas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vista.txtId.setText("");
        vista.txtNumero.setText("");
        vista.spinnerCapacidad.setValue(1);
        vista.checkDisponible.setSelected(true);

        vista.tablaMesas.clearSelection();
    }

    private void buscar() {
        try {
            String textoBusqueda = vista.txtBuscar.getText().trim();

            ArrayList<ModeloMesa> mesas = new ArrayList<>();

            if (textoBusqueda.isEmpty()) {
                mesas = mesaDao.listarMesas();
            } else {
                int numero = Integer.parseInt(textoBusqueda);
                ModeloMesa mesa = mesaDao.buscarPorNumero(numero);
                if (mesa != null) {
                    mesas.add(mesa);
                }
            }

            cargarTabla(mesas);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El número de mesa debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
