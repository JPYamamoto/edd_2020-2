package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.Coleccion;

/**
 * Clase para graficar la estructura de datos Montículos Mínimos.
 */
public class GraficadorMonticuloMinimo<T extends Comparable<T>> extends GraficadorEstructura<T> {

    // El montículo a graficar, convertido a Arbol Binario Completo porque su
    // estructura es la misma al momento de visualizarlo.
    ArbolBinarioCompleto<T> arbol;

    /**
     * El constructor del graficador, en donde recibimos una colección de
     * elementos comparables, y utilizamos el algoritmo heapSort para generar
     * el montículo mínimo. Una vez que tenemos el montículo, lo convertimos a
     * un Arbol Binario Completo pues lo podemos graficar de la misma manera.
     * @param coleccion la colección con los datos que contendrá el montículo.
     */
    public GraficadorMonticuloMinimo(Coleccion<T> datos) {
        arbol = new ArbolBinarioCompleto<T>(MonticuloMinimo.heapSort(datos));
    }

    /**
     * Obtén la cadena de texto de la gráfica SVG que corresponde a la
     * estructura de datos.
     * Podemos utilizar el graficador del árbol binario completo, pues el SVG
     * es el mismo.
     * @return el SVG de la estructura de datos.
     */
    public String graficarEstructura() {
        GraficadorArbolBinarioCompleto<T> graficador = new GraficadorArbolBinarioCompleto<T>(arbol);
        return graficador.graficar();
    }

    protected boolean esVacia() {
        return arbol.esVacia();
    }
}
