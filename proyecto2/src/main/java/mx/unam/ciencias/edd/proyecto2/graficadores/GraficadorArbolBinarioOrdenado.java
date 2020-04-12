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
     * en los árboles ordenados, ese siempre es el máximo; y el primer vértice
     * a la izquierda, pues es el mínimo. Luego, alguno de estos dos debe ser
     * el que tenga la cadena de texto más larga, pues en este proyecto estamos
     * trabajando con enteros. Esto no va a funcionar con todos los tipos de
     * datos, por ser un árbol genérico. En esos casos, podemos borrar este
     * método y continuar utilizando el que está en GraficadorArbol.
     */
    protected int longitudMaxima(VerticeArbolBinario<T> vertice) {
        VerticeArbolBinario<T> verticeAuxDerecho = vertice;
        VerticeArbolBinario<T> verticeAuxIzquierdo = vertice;

        while (verticeAuxDerecho.hayDerecho())
            verticeAuxDerecho = verticeAuxDerecho.derecho();

        while (verticeAuxIzquierdo.hayIzquierdo())
            verticeAuxIzquierdo = verticeAuxIzquierdo.izquierdo();

        int longitudIzquierdo = verticeAuxIzquierdo.toString().length();
        int longitudDerecho = verticeAuxDerecho.toString().length();
        return Math.max(longitudIzquierdo, longitudDerecho);
    }
}
