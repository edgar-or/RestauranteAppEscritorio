package controlador;

import dao.EmpleadoDao;
import dao.dto.EmpleadoDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AreaProduccionModelo;
import modelo.ModeloEmpleado;
import modelo.RolModelo;
import vista.VistaGestionEmpleados;
import vista.VistaPrincipal;

public class ControladorEmpleado {

    private VistaPrincipal vis;
    private VistaGestionEmpleados visEmpleados;
    
    
    private final EmpleadoDao dao = new EmpleadoDao();

    // comboCargo: 0 Mesero, 1 Cocinero, 2 Bartender, 3 Panadero, 5 Administrador
    // rol:  1 = Administrador, 2 = Empleado
    private static final int[] ROL_POR_CARGO = {2, 2, 2, 2, 2, 1};
    // area: 1 Cocina, 2 Panaderia, 3 Bar, 4 Mesero, 5 Administracion
    private static final int[] AREA_POR_CARGO = {4, 1, 3, 2, 5, 5};

    public ControladorEmpleado(VistaPrincipal vis) {
        this.vis = vis;
        

        

    }


  

    public void abrirVistaEmpleados() throws Exception {

        this.visEmpleados = new VistaGestionEmpleados();

       eventos(); 

              cargarComboArea(); 

        //CARGAR TABLA AL ABRIR
        listarEmpleados();
        

        visEmpleados.setVisible(true);
    }
    
    
    
    
       private void eventos() {
        visEmpleados.btnGuardar.addActionListener(e -> guardarEmpleado());
        visEmpleados.btnActualizar.addActionListener(e -> actualizarEmpleado());
        //visEmpleados.btnEliminar.addActionListener(e -> eliminarEmpleado());
        //visEmpleados.btnBuscar.addActionListener(e -> buscarEmpleados());
        visEmpleados.btnNuevo.addActionListener(e -> limpiarCampos());
        visEmpleados.btnLimpiar.addActionListener(e -> limpiarCampos());
        visEmpleados.btnVerEmpleados.addActionListener(e -> listarEmpleados());
        visEmpleados.btnCerrar.addActionListener(e -> visEmpleados.dispose());
        visEmpleados.tablaEmpleados.getSelectionModel()
                .addListSelectionListener(e -> cargarSeleccion());
    }

    //GUARDAR
    private void guardarEmpleado() {

        try {
            ModeloEmpleado emp = new ModeloEmpleado();

            //Empleado
            emp.setNombre(visEmpleados.txtNombre.getText());
            emp.setApellido(visEmpleados.txtApellido.getText());
            emp.setDui(visEmpleados.txtDpi.getText());

            //Rol desde combo
            RolModelo rol = new RolModelo();
            rol.setIdRol(2); 
            
            AreaProduccionModelo area = (AreaProduccionModelo) visEmpleados.comboCargo.getSelectedItem(); 
            
            emp.setArea(area);
            emp.setRol(rol);

            EmpleadoDao dao = new EmpleadoDao();

            boolean ok = dao.insertar(emp);

            if (ok) {
                javax.swing.JOptionPane.showMessageDialog(null, "Empleado guardado");

                limpiarCampos();
                listarEmpleados();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //LISTAR EN TABLA
    private void listarEmpleados() {

        try {
            EmpleadoDao dao = new EmpleadoDao();
            List<ModeloEmpleado> lista = dao.listar();

            DefaultTableModel modelo = (DefaultTableModel) visEmpleados.tablaEmpleados.getModel();
            modelo.setRowCount(0);

            for (ModeloEmpleado empleado : lista) {

                modelo.addRow(new Object[]{
                    empleado.getIdEmpleado(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getDui(),
                    empleado.getUsuario().getUsuario(),
                    empleado.getUsuario().getPassword()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
    //LIMPIAR CAMPOS
    private void limpiarCampos() {
        visEmpleados.txtNombre.setText("");
        visEmpleados.txtApellido.setText("");
        visEmpleados.txtDpi.setText("");
    }
    
    
    private void actualizarEmpleado() {
        try {
            if (visEmpleados.txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(visEmpleados, "Seleccione un empleado de la tabla");
                return;
            }
            
            int idEmpleado = Integer.parseInt(visEmpleados.txtId.getText()); 
            String duiEmpleado =  visEmpleados.txtDpi.getText(); 
            String nombre = visEmpleados.txtNombre.getText().trim(); 
            String apellido = visEmpleados.txtApellido.getText().trim(); 
            AreaProduccionModelo area = (AreaProduccionModelo) visEmpleados.comboCargo.getSelectedItem(); 
           
            ModeloEmpleado emp = new ModeloEmpleado(); 
            
            emp.setIdEmpleado(idEmpleado);
            emp.setDui(duiEmpleado);
            emp.setNombre(nombre);
            emp.setApellido(apellido);
            
            RolModelo rol = new RolModelo(); 
            rol.setIdRol(2);
            
            emp.setArea(area);
            emp.setRol(rol);
            
            
            

            if (dao.actualizar(emp)) {
                JOptionPane.showMessageDialog(visEmpleados, "Empleado actualizado");
                limpiarCampos();
                listarEmpleados();
            }
        } catch (Exception e) {
            mostrarError(e);
        }
    }
    
    private void cargarComboArea() throws Exception{
        ArrayList<AreaProduccionModelo> areas = dao.listarAreas(); 
        
        try{
            
             visEmpleados.comboCargo.removeAllItems();

        for (AreaProduccionModelo area : areas) {
            visEmpleados.comboCargo.addItem(area);
        }
            
          
            
        }catch(Exception e){
            mostrarError(e);
        }
        
    }
    
    
    private void cargarSeleccion() {

    int fila = visEmpleados.tablaEmpleados.getSelectedRow();

    if (fila == -1) {
        return;
    }

    visEmpleados.txtId.setText(
            visEmpleados.tablaEmpleados.getValueAt(fila, 0).toString()
    );

    visEmpleados.txtNombre.setText(
            visEmpleados.tablaEmpleados.getValueAt(fila, 1).toString()
    );

    visEmpleados.txtApellido.setText(
            visEmpleados.tablaEmpleados.getValueAt(fila, 2).toString()
    );

    visEmpleados.txtDpi.setText(
            visEmpleados.tablaEmpleados.getValueAt(fila, 3).toString()
    );
}
    
    
    
    
    
    
    
     private void mostrarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(visEmpleados, "Error: " + e.getMessage());
    }
    
}
