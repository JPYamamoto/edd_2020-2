package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase concreta para graficar la estructura de datos Cola.
 */
public class GraficadorCola<T> extends GraficadorLineal<T> {

    /**
     * Constructor que recibe la lista con los datos que contiene la Cola que
     * queremos graficar.
     * @param coleccion la lista con la información a graficar.
     */
    public GraficadorCola(Lista<T> coleccion) {
        super(coleccion);
    }
}

