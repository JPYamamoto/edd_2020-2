package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.ArbolAVL;

public class GraficadorArbolAVL<T extends Comparable<T>> extends GraficadorArbol<T> {

    public GraficadorArbolAVL(ArbolAVL<T> arbol) {
        super(arbol);
    }

    protected ArbolAVL<T> creaArbol(Coleccion<T> coleccion) {
        return new ArbolAVL<T>(coleccion);
    }

    public String graficar() {
        return null;
    }
}
