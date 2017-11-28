/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.logica;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author HP
 */
public class Algoritmo {

    private Stack<Integer> pila;
    private List<Integer> listaResultadoDijkstra;
    private int[][] matrizFloydRecorrido;
    private int[][] matrizFloydDistancia;
    private List<Integer> listaCaminoFloyd;
    private int peso;
    private int saltos;
    private boolean evaluar[];
    private int[] pesoPrim;
    private int[] nodos;
    private final int infinito;
    public Algoritmo() {
        pila = new Stack<Integer>();
        listaResultadoDijkstra = new ArrayList<Integer>();
        peso = 0;
        matrizFloydRecorrido = new int[15][15];
        matrizFloydDistancia = new int[15][15];
        listaCaminoFloyd = new ArrayList<Integer>();
        evaluar = new boolean[15];
        pesoPrim = new int[15];
        nodos = new int[15];
        infinito= 10000;
        saltos = 0;
    }

    public void limpiar() {
        pila = new Stack<Integer>();
        listaResultadoDijkstra = new ArrayList<Integer>();
        peso = 0;
        matrizFloydRecorrido = new int[15][15];
        matrizFloydDistancia = new int[15][15];
        listaCaminoFloyd = new ArrayList<Integer>();
        evaluar = new boolean[15];
        pesoPrim = new int[15];
        nodos = new int[15];
        saltos = 0;
    }
    public int[] getNodosArbolMinimo(){return nodos;}
    public void dijkstraCorto(int[][] matrizAdyacencia, int ini, int fin, int xPeso, int xSaltos) {
        try {
            //Corto por pesos.
            boolean bandera = false;
            bandera = pila.contains(ini);
            pila.push(ini);
            if (!bandera) {
                if (ini == fin) {
                    if (xPeso < peso) {
                        listaResultadoDijkstra.clear();
                        Stack<Integer> temp = new Stack<Integer>();
                        saltos = xSaltos;
                        peso = xPeso;
                        while (!pila.empty()) {
                            Integer numAux = pila.pop();
                            temp.push(numAux);
                        }
                        while (!temp.empty()) {
                            Integer numAux = temp.pop();
                            pila.push(numAux);
                            listaResultadoDijkstra.add(numAux);
                        }
                    }
                    else if(xPeso == peso && xSaltos < saltos){ //Si son caminos con el mismo peso pero el nuevo es de menos pasos.
                        listaResultadoDijkstra.clear();
                        Stack<Integer> temp = new Stack<Integer>();
                        saltos = xSaltos;
                        peso = xPeso;
                        while (!pila.empty()) {
                            Integer numAux = pila.pop();
                            temp.push(numAux);
                        }
                        while (!temp.empty()) {
                            Integer numAux = temp.pop();
                            pila.push(numAux);
                            listaResultadoDijkstra.add(numAux);
                        }
                    }
                } else {
                    for (int i = 0; i < matrizAdyacencia.length; i++) {
                        if (matrizAdyacencia[ini][i] > 0) {
                            dijkstraCorto(matrizAdyacencia, i, fin, xPeso + matrizAdyacencia[ini][i], xSaltos+1);
                        }
                    }
                }
            }
            pila.pop();
        } catch (EmptyStackException ex) {

        }
    }

    public List<Integer> getListaResultadoDijkstra() {
        return listaResultadoDijkstra;
    }

    public int[][] getMatrizFloydDistancia() {
        return matrizFloydDistancia;
    }

    public int[][] getMatrizFloydRecorrido() {
        return matrizFloydRecorrido;
    }

    public List<Integer> getCaminoFloyd() {
        return listaCaminoFloyd;
    }
    public void limpiarCaminoFloyd(){
        listaCaminoFloyd = new ArrayList<>();
    }
    public void floyd(int[][] matrizAdyacencia, int cantidadNodos) {
        //matriz de recorrido
        for (int i = 0; i < cantidadNodos; i++) {
            for (int j = 0; j < cantidadNodos; j++) {
                matrizFloydRecorrido[i][j] = j;
            }
        }

        /* Matriz de solucion(distancia) se iguala a matriz de adyacencia. */
        for (int i = 0; i < cantidadNodos; i++) {
            for (int j = 0; j < cantidadNodos; j++) {
                if (matrizAdyacencia[i][j] == 0) {
                    matrizFloydDistancia[i][j] = infinito;
                } else {
                    matrizFloydDistancia[i][j] = matrizAdyacencia[i][j];
                }
            }
        }

        //k representa los nodos intermedios.
        for (int k = 0; k < cantidadNodos; k++) {
            //i representa el nodo de origen
            for (int i = 0; i < cantidadNodos; i++) {
                //j representa el nodo de destino.
                for (int j = 0; j < cantidadNodos; j++) {
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

    public void obtenerCaminoFloyd(int ini, int fin) {
        //Si en la matriz de recorrido, si al consultar el camino entre ini y fin el resultado es fin, es porque hay destino directo.
        listaCaminoFloyd.add(ini);
        if (matrizFloydRecorrido[ini][fin] == fin) {
            listaCaminoFloyd.add(fin);
        } else {
		//Si no hay, es porque hay un nodo intermedio entre ini y fin.
            //Se debe seguir el recorrido a partir de ahi.
            obtenerCaminoFloyd(matrizFloydRecorrido[ini][fin], fin);
        }
    }

    public void dijkstraLargo(int[][] matrizAdyacencia, int ini, int fin, int xSaltos, int xPeso) {
        try {
            //Largo por pasos.
            boolean bandera = false;
            bandera = pila.contains(ini);
            pila.push(ini);
            if (!bandera) {
                if (ini == fin) {
                    if (xSaltos > saltos) {
                        listaResultadoDijkstra.clear();
                        Stack<Integer> temp = new Stack<Integer>();
                        saltos = xSaltos;
                        peso = xPeso;
                        while (!pila.empty()) {
                            Integer numAux = pila.pop();
                            temp.push(numAux);
                        }
                        while (!temp.empty()) {
                            Integer numAux = temp.pop();
                            pila.push(numAux);
                            listaResultadoDijkstra.add(numAux);
                        }
                    }
                    else if (xSaltos == saltos && xPeso > peso) { //Dos caminos con la misma cantidad de saltos, escoge el más pesado.
                        listaResultadoDijkstra.clear();
                        Stack<Integer> temp = new Stack<Integer>();
                        saltos = xSaltos;
                        peso = xPeso;
                        while (!pila.empty()) {
                            Integer numAux = pila.pop();
                            temp.push(numAux);
                        }
                        while (!temp.empty()) {
                            Integer numAux = temp.pop();
                            pila.push(numAux);
                            listaResultadoDijkstra.add(numAux);
                        }
                    }
                } else {
                    for (int i = 0; i < matrizAdyacencia.length; i++) {
                        if (matrizAdyacencia[ini][i] > 0) {
                            dijkstraLargo(matrizAdyacencia, i, fin, xSaltos + 1, xPeso + matrizAdyacencia[ini][fin]);
                        }
                    }
                }
            }
            pila.pop();
        } catch (EmptyStackException ex) {

        }
    }

    private Integer minimoPeso(int cantidadNodos)//buscamos el nodo con el mínimo peso
    {
        Integer min = Integer.MAX_VALUE; //peso minimo.
        Integer minimo = null; //nodo de destino con el minimo peso.

        for (int v = 0; v < cantidadNodos; v++) {
            if (evaluar[v] == false && pesoPrim[v] < min) { //Si no se ha evaluado la linea y el peso evaluado es menor al mínimo.
                min = pesoPrim[v]; //cambia el minimo.
                minimo = v; //cambia el nodo con el minimo.
            }
        }
        return minimo; //devuelve el nodo a seguir evaluando(el del mínimo peso).
    }

    public void arbolMinimo(int[][] mat, int cantidadNodos) {
        
        //asignar valores por defecto.
        for (int i = 0; i < cantidadNodos; i++) {
            //Limpia cada linea de pesos y la bandera de evaluar a cada nodo.
            pesoPrim[i] = Integer.MAX_VALUE;
            evaluar[i] = false;
            nodos[i] = 0;
        }
        //Siempre empezamos con A
        pesoPrim[0] = 0; //Esto permite que minimoPeso() entre en el primero. Con esto se le indica que debe empezar con el A.
        nodos[0] = -1;//Esto permite que convertir al ´nodo desde el cual se entra en el primero (padre). No lo toma en cuenta.

        for (int i = 0; i < (cantidadNodos - 1); i++)//cantidadNodos-1 porque SON cantidadNodos-1 CAMINOS.
        {
            //Evalua el peso mínimo dentro de cada fila.
            Integer u = minimoPeso(cantidadNodos); //Devuelve el nodo (linea) con el mínimo.
            evaluar[u] = true; // Marca esta linea como evaluada.

            for (int v = 0; v < cantidadNodos; v++) {
                if (mat[u][v] != 0 && evaluar[v] == false && mat[u][v] < pesoPrim[v]) {
                    nodos[v] = u;
                    pesoPrim[v] = mat[u][v];
                }
            }
        }
      
    }

    void ImprimeArbol(int[][] matrizAdyacencia, int cantidadNodos) {
        System.out.println("Nodos   Peso\n");
        for (int i = 1; i < cantidadNodos; i++) {
            System.out.println(nodos[i] + " - " + i + "    " + matrizAdyacencia[i][nodos[i]]);
        }
    }
}
