/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

/**
 *
 * @author wili
 */
import java.util.HashMap;


public class AppContext {

    private static AppContext INSTANCE = null;
    
    /**
     * Mapa para almacenar todos los objetos necesarios
     */
    private static HashMap<String, Object> context = new HashMap<>();
     
    /**
     * Constructor privado para evitar que se instancien objetos
     */
    private AppContext() {
    }

    /**
     * Metodo para crear una instancia
     */
    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (AppContext.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new AppContext();
                }
            }
        }
    }

    /**
     * Metodo para obtener una instancia
     * @return Instacia del contexto de la aplicación
     */
    public static AppContext getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    
    /**
     * El método "clone" es sobreescrito por el siguiente que arroja una excepción:
     * @return Error en caso de intentar clonar
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Obtiene el objeto con el nombre indicado
     * @param parameter Nombre del objeto
     * @return Objeto buscado, null en caso de que no exista
     */
    public Object get(String parameter){
        
        Object object = context.get(parameter);
        if(object == null && parameter.contains("Service"))
        {
            synchronized (AppContext.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                object = context.get(parameter);
            } 
        }        
        return object;
    }

    /**
     * Ingresa un objeto al contexto de la aplicación
     * @param nombre Nombre del objeto
     * @param valor Valor del objeto
     */
    public void set(String nombre, Object valor) {
        context.put(nombre, valor);
    }

    /**
     * Elimina un objeto del contexto de la aplicación
     * @param parameter Nombre del objeto
     */
    public void delete(String parameter) {
        context.put(parameter, null);
    }

}
