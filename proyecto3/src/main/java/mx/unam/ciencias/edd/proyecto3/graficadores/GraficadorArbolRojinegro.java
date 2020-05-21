package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase concreta para graficar la estructura de datos Árbol Binario Ordenado.
 */
public class GraficadorArbolRojinegro<T extends Comparable<T>> extends GraficadorArbolBinarioOrdenado<T> {

    /**
     * Constructor del graficador, que recibe un árbol rojinegro.
     * @param arbol el árbol a graficar.
     */
    public GraficadorArbolRojinegro(ArbolRojinegro<T> arbol) {
        super(arbol);
    }

    /**
     * Graficamos el vértice recibido, en la posición descrita por los
     * argumentos. Además, le agregamos su color.
     * @param vertice el vértice a graficar.
     * @param centroX la coordenada en el eje X del centro del vértice.
     * @param centroY la coordenada en el eje Y del centro del vértice.
     * @param radio la medida del radio del vértice.
     * @return el texto con el SVG que representa al vértice rojinegro.
     */
    protected String graficaVertice(VerticeArbolBinario<T> vertice, int centroX,
                                    int centroY, int radio) {
        // Es seguro hacer la audición, pues sabemos que el método solo se
        // invoca cuando el árbol es rojinegro.
        Color color = ((ArbolRojinegro<T>) arbol).getColor(vertice);
        return GraficadorSVG.graficaCirculoTexto(centroX, centroY, radio,
                "black", color == Color.ROJO ? "red" : "black", TAMANO_FUENTE,
                "white", vertice.get().toString());
    }
}
