package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.ArbolBinarioOrdenado;

/**
 * Clase concreta para graficar la estructura de datos Árbol Binario Ordenado.
 * Tenemos que acotar el genérico a Comparable para poder utilizar el tipo
 * adecuado en el constructor. Una alternativa sería recibir un ArbolBinario
 * sin la necesidad de acotar el genérico, pero no podríamos estar
 * completamente seguros de que el árbol que recibimos está ordenado.
 */
public class GraficadorArbolBinarioOrdenado<T extends Comparable<T>> extends GraficadorArbol<T> {

    /**
     * Constructor del graficador, que recibe un árbol binario ordenado.
     * @param arbol el árbol a graficar.
     */
    public GraficadorArbolBinarioOrdenado(ArbolBinarioOrdenado<T> arbol) {
        super(arbol);
    }
}
