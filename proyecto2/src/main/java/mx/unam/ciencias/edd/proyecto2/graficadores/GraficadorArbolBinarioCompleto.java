package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.VerticeArbolBinario;

public class GraficadorArbolBinarioCompleto<T extends Comparable<T>> extends GraficadorArbol<T> {

    public GraficadorArbolBinarioCompleto(ArbolBinarioCompleto<T> arbol) {
        super(arbol);
    }

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
