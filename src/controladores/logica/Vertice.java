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
public class Vertice {
    private int letra;
    private int grado;
    private ListaAdyacencia listaAdyacencia;
    
    public Vertice(int letra, int grado){
        this.letra = letra;
        this.grado = grado;
        listaAdyacencia = null;
    }
    
    public void setLetra(int letra){this.letra = letra;}
    public void setGrado(int grado){this.grado = grado;}
    public void setListaAdyacencia(ListaAdyacencia listaAdyacencia) {this.listaAdyacencia = listaAdyacencia;}
    
    public int getLetra(){return letra;}
    public int getGrado(){return grado;}
    public ListaAdyacencia getListaAdyacencia(){return listaAdyacencia;}
    
    public void insertarListaAdyacencia(int arista, int costo){
            ListaAdyacencia aux = listaAdyacencia;
            if(aux == null){
                aux = new ListaAdyacencia(arista, costo);
                listaAdyacencia = aux;
            }
            else{
                while(aux.getSig() != null){
                    aux = aux.getSig();
                }
                aux.setSig(new ListaAdyacencia(arista, costo));
            }
        }
        public void eliminarListaAdyacencia(int arista){
            ListaAdyacencia aux = listaAdyacencia;
            if(aux.getArista() == arista){
                aux = aux.getSig();
            }
        }
        public void modificarListaAdyacencia(int arista, int costo)
        {
            ListaAdyacencia aux = listaAdyacencia;
            if (aux.getArista() == arista)
            {
                aux.setCosto(costo);
            }
        }
}

