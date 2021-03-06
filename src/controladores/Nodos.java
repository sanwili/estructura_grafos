/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author wili
 */
public class Nodos {
    private Node nodo;
    private List<Node> ListaConexiones= new ArrayList<>(); 
    private boolean lazo;

    public Nodos() {
        this.nodo=null;
        this.ListaConexiones = new ArrayList<>();
        this.lazo=false;
    }

    public Nodos(Node nodo,boolean lazo) {
        this.nodo = nodo;
        this.ListaConexiones =  new ArrayList<Node>();
        this.lazo=lazo;
    }

    public Node getNodo() {
        return nodo;
    }

    public void setNodo(Node nodo) {
        this.nodo = nodo;
    }

    public List<Node> getListaConexiones() {
        return ListaConexiones;
    }

    public void setListaConexiones(Node Conexion) {
        this.ListaConexiones.add(Conexion);
    }

    public boolean isLazo() {
        return lazo;
    }

    public void setLazo(boolean lazo) {
        this.lazo = lazo;
    }
    
}
