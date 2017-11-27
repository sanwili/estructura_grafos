/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.logica;

import com.sun.xml.internal.fastinfoset.algorithm.IntegerEncodingAlgorithm;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class Algoritmo {
    List<Integer> pila;
    List<Integer> listaResultadoDijkstra;
    int[][] matrizFloydRecorrido;
    int[][] matrizFloydDistancia;
    List<Integer> listaCaminoFloyd;
    int peso;
    
    boolean evaluar[];
    int[] pesoPrim;
    int[] nodos;
    
    public Algoritmo(){
        pila = new ArrayList<Integer>();
        listaResultadoDijkstra = new ArrayList<Integer>();
        peso = 0;
        matrizFloydRecorrido = new int[15][15];
        matrizFloydDistancia = new int[15][15];
        listaCaminoFloyd = new ArrayList<Integer>();
        evaluar = new boolean[15];
        pesoPrim = new int[15];
        nodos = new int[15];
    }
    public void limpiar(){
        pila = new ArrayList<Integer>();
        listaResultadoDijkstra = new ArrayList<Integer>();
        peso = 0;
        matrizFloydRecorrido = new int[15][15];
        matrizFloydDistancia = new int[15][15];
    }
    public void dijkstraCorto(int[][] matrizAdyacencia, int ini, int fin, int x){
        //Corto por pesos.
        boolean bandera = false;
        bandera = pila.contains(ini);
        if(!bandera){
            if(ini == fin){
                if(x < peso){
                    List<Integer> temp = new ArrayList<Integer>();
                    peso = x;
                    while(!pila.isEmpty()){
                        Integer numAux = pila.get(pila.size()-1);
                        temp.add(numAux);
                        pila.remove(numAux);
                    }
                    while(!pila.isEmpty()){
                        Integer numAux = temp.get(temp.size()-1);
                        pila.add(numAux);
                        temp.remove(numAux);
                        listaResultadoDijkstra.add(numAux);
                    }
                }
            }
            else{
                for (int i = 0; i < matrizAdyacencia.length; i++) {
                    if (matrizAdyacencia[ini][i] > 0) {
                        dijkstraCorto(matrizAdyacencia, i, fin, x + matrizAdyacencia[ini][i]);
                    }
                }
            }
        }
        pila.remove(pila.size()-1);
    }
    public void floyd(int[][] matrizAdyacencia){
        //matriz de recorrido
	for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
            	matrizFloydRecorrido[i][j] = j;

	/* Matriz de solucion(distancia) se iguala a matriz de adyacencia. */
	for (int i = 0; i < 15; i++)
		for (int j = 0; j < 15; j++)
			matrizFloydDistancia[i][j] = matrizAdyacencia[i][j];

	//k representa los nodos intermedios.
	for (int k = 0; k < 15; k++) {
		//i representa el nodo de origen
		for (int i = 0; i < 15; i++) {
			//j representa el nodo de destino.
			for (int j = 0; j < 15; j++) {
				//Si el camino entre el los nodos de origen, intermedio y destino es más corto
				//que el camino entre el nodo de origen y destino
				if (matrizFloydDistancia[i][k] + matrizFloydDistancia[k][j] < matrizFloydDistancia[i][j]) {
					//se actualiza el valor.
					matrizFloydDistancia[i][j] = matrizFloydDistancia[i][k] + matrizFloydDistancia[k][j];
					//Se actualiza la matriz de recorridos.
					matrizFloydRecorrido[i][j] = k;
				}
			}
		}
	}
    }
    public void obtenerCamino(int ini, int fin) {
        //Si en la matriz de recorrido, si al consultar el camino entre ini y fin el resultado es fin, es porque hay destino directo.
	listaCaminoFloyd.add(ini);
	if (matrizFloydRecorrido[ini][fin] == fin) {
            listaCaminoFloyd.add(fin);
	}
	else {
		//Si no hay, es porque hay un nodo intermedio entre ini y fin.
		//Se debe seguir el recorrido a partir de ahi.
		obtenerCamino(matrizFloydRecorrido[ini][fin], fin);
	}
}
    public void dijkstraLargo(int[][] matrizAdyacencia, int ini, int fin, int x){
        //Largo por pasos.
        boolean bandera = false;
        bandera = pila.contains(ini);
        if(!bandera){
            if(ini == fin){
                if(x > peso){
                    List<Integer> temp = new ArrayList<Integer>();
                    peso = x;
                    while(!pila.isEmpty()){
                        Integer numAux = pila.get(pila.size()-1);
                        temp.add(numAux);
                        pila.remove((Object)numAux);
                    }
                    while(!pila.isEmpty()){
                        Integer numAux = temp.get(temp.size()-1);
                        pila.add(numAux);
                        temp.remove((Object)numAux);
                        listaResultadoDijkstra.add(numAux);
                    }
                }
            }
            else{
                for (int i = 0; i < matrizAdyacencia.length; i++) {
                    if (matrizAdyacencia[ini][i] > 0) {
                        dijkstraLargo(matrizAdyacencia, i, fin, x + 1);
                    }
                }
            }
        }
        pila.remove(pila.size()-1);
    }
    public void arbolAbarcadorMinimo(){
        
    }
    
    private int minimoPeso()//buscamos el nodo con el mínimo peso
    {
            int min = Integer.MAX_VALUE; //peso minimo.
            int minimo = -1; //nodo de destino con el minimo peso.

            for (int v = 0; v < 15; v++){
                    if (evaluar[v] == false && pesoPrim[v] < min){ //Si no se ha evaluado la linea y el peso evaluado es menor al mínimo.
                            min = pesoPrim[v]; //cambia el minimo.
                            minimo = v; //cambia el nodo con el minimo.
                    }
            }
            return minimo; //devuelve el nodo a seguir evaluando(el del mínimo peso).
    }
    
    void arbolMinimo(int[][] mat)
    {
	//asignar valores por defecto.
	for (int i = 0; i < 15; i++)
	{
		//Limpia cada linea de pesos y la bandera de evaluar a cada nodo.
		pesoPrim[i] = Integer.MAX_VALUE; 
		evaluar[i] = false;
	}
	//Siempre empezamos con A
	pesoPrim[0] = 0; //Esto permite que minimoPeso() entre en el primero. Con esto se le indica que debe empezar con el A.
	nodos[0] = -1;//Esto permite que convertir al ´nodo desde el cual se entra en el primero (padre). No lo toma en cuenta.

	for (int i = 0; i < 14; i++)//cantidadNodos-1 porque SON cantidadNodos-1 CAMINOS.
	{
		//Evalua el peso mínimo dentro de cada fila.
		int u = minimoPeso(); //Devuelve el nodo (linea) con el mínimo.
		evaluar[u] = true; // Marca esta linea como evaluada.
		
		for (int v = 0; v < 15; v++) {
			if (mat[u][v] != 0 && evaluar[v] == false && mat[u][v] <  pesoPrim[v]) {
				//mat[u][v] el nodo obtenido al posible destino
				//evaluar[v] no se haya evaluado 
				//mat[u][v] <  peso[v]
					//si mat[u][v] (nuevo camino encontrado) es menor al camino que ya estaba al nodo 'v' previamente (peso[v])
				//Si hay camino y no se ha evaluado el nodo de salida y el peso es menor.
				//si el peso encontrado es menor al peso que ya había en el camino .
				
				nodos[v] = u; 
				//asigna el peso obtenido a la matriz de caminos.
				pesoPrim[v] = mat[u][v];
			}
		}
	}
    }
}
