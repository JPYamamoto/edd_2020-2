package mx.unam.ciencias.edd.proyecto2.graficadores;

import java.util.Iterator;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase concreta para graficar la estructura de datos Lista.
 */
public class GraficadorLista<T extends Comparable<T>> extends GraficadorLineal<T> {

    // La lista a graficar.
    Lista<T> lista;

    /**
     * Constructor que recibe la lista que será graficada. Puesto que recibimos
     * la lista a graficar en el constructor, se necesita una instancia por
     * cada Lista que se desea graficar.
     * @param coleccion la lista a graficar.
     */
    public GraficadorLista(Lista<T> coleccion) {
        lista = coleccion;
    }

    /**
     * Regresa un iterador para iterar la estructura de datos. Necesitamos
     * implementarlo pues lo requiere la clase abstracta.
     */
    protected Iterator<T> getIterator() {
        return lista.iterator();
    }
}

