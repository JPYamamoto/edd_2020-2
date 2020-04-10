package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.VerticeArbolBinario;
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

    /**
     * Podemos obtener el máximo en el subárbol de manera optimizada, pues
     * sabemos que el árbol está ordenado. En el peor de los casos su
     * complejidad es O(n), pero en los árboles autobalanceados es O(log n)
     * La optimización consiste en buscar el último vértice a la derecha, pues
     * en los árboles ordenados, ese siempre es el máximo. Esto no garantiza
     * que su representación en cadena de texto será la más larga, pero como
     * sabemos que en nuestro proyecto solo aceptamos enteros, podemos afirmar
     * que la optimización funciona. En otro caso, será mejor borrar este
     * método.
     */
    protected int longitudMaxima(VerticeArbolBinario<T> vertice) {
        VerticeArbolBinario<T> verticeAux = vertice;

        while (verticeAux.hayDerecho())
            verticeAux = verticeAux.derecho();

        return verticeAux.get().toString().length();
    }
}
