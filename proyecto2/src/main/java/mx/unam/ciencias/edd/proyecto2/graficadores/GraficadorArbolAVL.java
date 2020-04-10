package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase concreta para graficar la estructura de datos Árbol AVL.
 */
public class GraficadorArbolAVL<T extends Comparable<T>> extends GraficadorArbolBinarioOrdenado<T> {

    // Tamaño que tendrá la fuente del balance de cada vértice.
    private final int TAMANO_FUENTE_BALANCE = 15;
    // Un desplazamiento que tendrá el texto del balance, por comodidad visual.
    private final int DESPLAZAMIENTO_ETIQUETA = 10;

    /**
     * Constructor del graficador, que recibe un árbol AVL.
     * Además, modificamos el tamaño del borde del gráfico, pues necesitamos
     * hacer espacio por las etiquetas de balance.
     * @param arbol el árbol a graficar.
     */
    public GraficadorArbolAVL(ArbolAVL<T> arbol) {
        super(arbol);
        BORDE_SVG = 25;
    }

    /**
     * Genera la cadena de texto que representa el SVG con el vértice recibido
     * y un texto que indica el balance. No tenemos un método en árbol AVL que
     * nos regrese el balance, por lo que recurrimos a analizar la
     * representación en cadena del vértice.
     */
    protected String graficaVertice(VerticeArbolBinario<T> vertice, int centroX,
                                    int centroY, int radio) {
        String textoVertice = vertice.toString();
        int ultimoEspacio = textoVertice.lastIndexOf(' ');
        String balance = String.format("(%s)", textoVertice.substring(ultimoEspacio + 1));
        int centroTextoX = centroX;

        // Si el vértice es la raíz, colocamos el balance justo encima.
        // Si el vértice es izquierdo, colocamos el balance ligeramente a la
        // izquierda.
        // Si el vértice es derecho, colocamos el balance ligeramente a la
        // derecha.
        if (esDerecho(vertice))
            centroTextoX += (int) Math.ceil(balance.length() / 2) * TAMANO_FUENTE_BALANCE;
        else if (esIzquierdo(vertice))
            centroTextoX += - (int) Math.ceil(balance.length() / 2) * TAMANO_FUENTE_BALANCE;

        String resultado = "";

        resultado += GraficadorSVG.graficaTexto(centroTextoX,
                        centroY - radio - DESPLAZAMIENTO_ETIQUETA,
                        TAMANO_FUENTE_BALANCE, "black", balance);

        resultado += GraficadorSVG.graficaCirculoTexto(centroX, centroY, radio,
                "black", "white", TAMANO_FUENTE, "black", vertice.get().toString());

        return resultado;
    }
}
