package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;

/**
 * Clase concreta para graficar la estructura de datos Árbol Binario Completo.
 */
public class GraficadorArbolBinarioCompleto<T> extends GraficadorArbol<T> {

    /**
     * Constructor del graficador, que recibe un árbol binario completo.
     * @param arbol el árbol a graficar.
     */
    public GraficadorArbolBinarioCompleto(ArbolBinarioCompleto<T> arbol) {
        super(arbol);
    }
}
