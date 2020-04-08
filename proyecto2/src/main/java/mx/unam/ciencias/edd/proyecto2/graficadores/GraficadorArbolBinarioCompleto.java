package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase concreta para graficar la estructura de datos Árbol Binario Completo.
 */
public class GraficadorArbolBinarioCompleto<T extends Comparable<T>> extends GraficadorArbol<T> {

    /**
     * Constructor del graficador, que recibe un árbol binario completo.
     * @param arbol el árbol a graficar.
     */
    public GraficadorArbolBinarioCompleto(ArbolBinarioCompleto<T> arbol) {
        super(arbol);
    }

    /**
     * Recorremos todo el árbol buscando el elemento máximo en el subárbol con
     * el vértice recibido como raíz.
     */
    protected T maximoEnSubarbol(VerticeArbolBinario<T> vertice) {
        T maximo = vertice.get();

        if (vertice.hayIzquierdo()) {
            T maximoIzquierdo = maximoEnSubarbol(vertice.izquierdo());
            maximo = maximo.compareTo(maximoIzquierdo) <= 0 ? maximoIzquierdo : maximo;
        }

        if (vertice.hayDerecho()) {
            T maximoDerecho = maximoEnSubarbol(vertice.derecho());
            maximo = maximo.compareTo(maximoDerecho) <= 0 ? maximoDerecho : maximo;
        }

        return maximo;
    }

}
