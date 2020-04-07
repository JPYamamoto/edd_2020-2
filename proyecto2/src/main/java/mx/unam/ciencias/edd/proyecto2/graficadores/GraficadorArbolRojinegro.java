package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.ArbolRojinegro;

public class GraficadorArbolRojinegro<T extends Comparable<T>> extends GraficadorArbol<T> {

    public GraficadorArbolRojinegro(ArbolRojinegro<T> arbol) {
        super(arbol);
    }

    protected ArbolRojinegro<T> creaArbol(Coleccion<T> coleccion) {
        return new ArbolRojinegro<T>(coleccion);
    }

    public String graficar() {
        return null;
    }
}
