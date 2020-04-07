package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;

public class GraficadorPila<T extends Comparable<T>> extends GraficadorLineal<T> {

    Lista<T> coleccion;

    public GraficadorPila(Lista<T> coleccion) {
        this.coleccion = coleccion;
    }

    protected Iterator<T> getIterador() {
        return coleccion.iterator();
    }
}

