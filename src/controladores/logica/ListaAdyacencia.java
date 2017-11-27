/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.logica;

/**
 *
 * @author HP
 */
public class ListaAdyacencia {
     private int arista;
     private int costo;
     private ListaAdyacencia sig;
    public ListaAdyacencia(int arista, int costo){
        this.arista = arista;
        this.costo = costo;
        sig = null;
    }

    /**
     * @return the arista
     */
    public int getArista() {
        return arista;
    }

    /**
     * @return the costo
     */
    public int getCosto() {
        return costo;
    }

    /**
     * @return the sig
     */
    public ListaAdyacencia getSig() {
        return sig;
    }

    /**
     * @param arista the arista to set
     */
    public void setArista(int arista) {
        this.arista = arista;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * @param sig the sig to set
     */
    public void setSig(ListaAdyacencia sig) {
        this.sig = sig;
    }
}
