/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author renec
 */
public class ModeloMesa {
    private String idMesa;
    private int  numeroMesa;
    private boolean estado;
    private int capacidad;
    private static ArrayList<ModeloPedido> arrayPedido   = new ArrayList<>();

    public static ArrayList<ModeloPedido> getArrayPedido() {
        return arrayPedido;
    }

    public static void setArrayPedido(ArrayList<ModeloPedido> arrayPedido) {
        ModeloMesa.arrayPedido = arrayPedido;
    }

    public ModeloMesa() {
    }
    
    

    public ModeloMesa(String idMesa, int numeroMesa, boolean estado, int capacidad) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        this.capacidad = capacidad;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public boolean isEstado() {
        return estado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "Mesa: " + numeroMesa;
    }
    
    
    
}
