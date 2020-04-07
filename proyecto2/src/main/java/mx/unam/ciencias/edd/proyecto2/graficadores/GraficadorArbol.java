package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Coleccion;

/**
 * Clase abstracta de la que heredan los graficadores de las estructuras de
 * datos que heredan de ArbolBinario. Solo es necesario implementar los métodos
 * con las partes específicas a cada estructura de árbol.
 */
public abstract class GraficadorArbol<T extends Comparable<T>> implements GraficadorEstructura<T> {

    // El arbol a graficar.
    ArbolBinario<T> arbol;

    /**
     * El constructor del graficador. Simplemente asignamos el árbol que se
     * construye a partir de los datos en la colección recibida, a una variable
     * de clase que nos permitirá acceder al árbol cuando sea necesario.
     * @param coleccion la colección con los datos que contendrá el árbol.
     */
    public GraficadorArbol(Coleccion<T> coleccion) {
        arbol = creaArbol(coleccion);
    }

    /**
     * Método que sirve internamente para regresar una instancia de la clase
     * concreta del árbol y a la que corresponde el graficador.
     */
    protected abstract ArbolBinario<T> creaArbol(Coleccion<T> coleccion);
}
