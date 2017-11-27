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
public class Grafo {
     private TipoGrafo tipo;
     private Nodo[] nodos;
     private int[][] matrizAdyacencia;
     
     public Grafo(TipoGrafo tipo, Nodo vertices){
         this.tipo = tipo;
         this.nodos = new Nodo[15];
         this.matrizAdyacencia = new int[15][15];
     }
     public Grafo(){
         this.tipo = null;
         this.nodos = new Nodo[15];
         this.matrizAdyacencia = new int[15][15];
     }
    /**
     * @return the tipo
     */
    public TipoGrafo getTipo() {return tipo;}
    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoGrafo tipo) {this.tipo = tipo;}
    /**
     * @return the vertices
     */
    public Nodo[] getNodos() {return nodos;}
    /**
     * @return the matrizAdyacencia
     */
    public int[][] getMatrizAdyacencia() {return matrizAdyacencia;}
    /**
     * @return Actualiza la matrizAdyacencia después de un cambio
     */
     public void setMatrizAdyacencia(int[][] matrizAdyacencia) {this.matrizAdyacencia = matrizAdyacencia;}
    /**
      @param matrizAdyacencia the matrizAdyacencia to set
     */
    public void actualizarListaAdyacencia()
        {
            for(int i = 0; i<nodos.length; i++){
                if(nodos[i] != null) {
                    nodos[i].setListaAdyacencia(null);
                    for (int j = 0; j < nodos.length; j++)
                    {
                        if (matrizAdyacencia[i][j] > 0)
                        {
                            nodos[i].insertarListaAdyacencia(j, matrizAdyacencia[i][j]);
                        }

                    }
                }
            }
        }
        /**
        * @param tipo the tipo to set
        */
        public void cambiarMatrizAdyacencia(int f, int c, int valor)
        {
            matrizAdyacencia[f][c] = valor;
        }
        public void conectarNodos(int origen, int destino, int costo)
        {
            if(costo >= 0) { 
                nodos[origen].setGrado(nodos[origen].getGrado()+1);
                nodos[destino].setGrado(nodos[origen].getGrado()+1);
                if(tipo == TipoGrafo.DIRIGIDO) { 
                    matrizAdyacencia[origen][destino] = costo;
                }
                else{
                    if(origen != destino) { 
                        matrizAdyacencia[origen][destino] = costo;
                        matrizAdyacencia[destino][origen] = costo;
                    }
                    else
                    {
                        matrizAdyacencia[origen][destino] = costo;
                    }
                }
            }
            else
            {
                
            }
        }
        public void crearNodo()
        {
            boolean yaCreado = false;
            for(int i = 0; i<15 && !yaCreado; i++){
                if(nodos[i] == null){
                    nodos[i] = new Nodo(i, 0);
                    yaCreado = true;
                }
            }
            if (!yaCreado) { 
                //No se creo
            }
            else {
                actualizarListaAdyacencia();
            }
        }
        public void eliminarNodo(int vertice)
        {
            //Eliminar conexiones desde y hacia él .
            for (int i = 0; i<nodos.length; i++)
            {
                if(nodos[i] != null){
                    if(matrizAdyacencia[i][vertice] != 0)
                    {
                        matrizAdyacencia[i][vertice] = 0;
                    }
                    if (matrizAdyacencia[vertice][i] != 0)
                    {
                        matrizAdyacencia[vertice][i] = 0;
                    }
                }
            }
            nodos[vertice] = null;
            actualizarListaAdyacencia();
        }
}
