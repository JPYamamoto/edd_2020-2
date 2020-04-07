package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;

public class GraficadorArbolBinarioCompleto<T extends Comparable<T>> extends GraficadorArbol<T> {

    public GraficadorArbolBinarioCompleto(ArbolBinarioCompleto<T> arbol) {
        super(arbol);
    }

    protected ArbolBinarioCompleto<T> creaArbol(Coleccion<T> coleccion) {
        return new ArbolBinarioCompleto<T>(coleccion);
    }

    public String graficar() {
        return null;
    }
}
