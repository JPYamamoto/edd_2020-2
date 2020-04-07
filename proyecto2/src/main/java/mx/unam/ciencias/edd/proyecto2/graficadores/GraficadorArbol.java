package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Coleccion;

public abstract class GraficadorArbol<T extends Comparable<T>> implements GraficadorSVG<T> {

    ArbolBinario<T> arbol;

    public GraficadorArbol(Coleccion<T> coleccion) {
        arbol = creaArbol(coleccion);
    }

    public GraficadorArbol(ArbolBinario<T> arbol) {
        this.arbol = arbol;
    }

    protected abstract ArbolBinario<T> creaArbol(Coleccion<T> coleccion);
}
