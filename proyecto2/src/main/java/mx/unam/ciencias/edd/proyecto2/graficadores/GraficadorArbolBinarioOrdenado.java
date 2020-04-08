package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;

public class GraficadorArbolBinarioOrdenado<T extends Comparable<T>> extends GraficadorArbol<T> {

    public GraficadorArbolBinarioOrdenado(ArbolBinarioOrdenado<T> arbol) {
        super(arbol);
    }

    protected ArbolBinarioOrdenado<T> creaArbol(Coleccion<T> coleccion) {
        return new ArbolBinarioOrdenado<T>(coleccion);
    }

    protected T maximoEnSubarbol(VerticeArbolBinario<T> vertice) {
        VerticeArbolBinario<T> verticeMaximo = vertice;

        while (vertice.hayDerecho()) 
            verticeMaximo = vertice.derecho();

        return verticeMaximo.get();
    }
}
