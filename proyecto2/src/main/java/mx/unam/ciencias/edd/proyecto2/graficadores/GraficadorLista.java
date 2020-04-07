package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;

public class GraficadorLista<T extends Comparable<T>> extends GraficadorLineal<T> {

    Lista<T> lista;

    public GraficadorLista(Lista<T> coleccion) {
        lista = coleccion;
    }

    protected Iterator<T> getIterador() {
        return lista.iterator();
    }
}

