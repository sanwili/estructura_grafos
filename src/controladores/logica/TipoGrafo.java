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
public enum TipoGrafo {
    NINGUNO(0),
    DIRIGIDO(1),
    NO_DIRIGIDO(2);
    private final int num;
    TipoGrafo(int num){
        this.num = num;
    }
}
