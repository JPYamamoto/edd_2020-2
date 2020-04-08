package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;

public class GraficadorArbolBinarioOrdenado<T extends Comparable<T>> extends GraficadorArbol<T> {

    public GraficadorArbolBinarioOrdenado(ArbolBinarioOrdenado<T> arbol) {
        super(arbol);
    }

    protected T maximoEnSubarbol(VerticeArbolBinario<T> vertice) {
        VerticeArbolBinario<T> verticeAux = vertice;

        while (verticeAux.hayDerecho()) 
            verticeAux = verticeAux.derecho();

        return verticeAux.get();
    }
}
