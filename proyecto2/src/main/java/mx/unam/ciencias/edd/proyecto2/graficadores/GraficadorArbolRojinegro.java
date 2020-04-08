package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;

public class GraficadorArbolRojinegro<T extends Comparable<T>> extends GraficadorArbolBinarioOrdenado<T> {

    public GraficadorArbolRojinegro(ArbolRojinegro<T> arbol) {
        super(arbol);
    }

    protected String graficaVertice(VerticeArbolBinario<T> vertice, int centroX,
                                    int centroY, int radio) {
        Color color = ((ArbolRojinegro<T>) arbol).getColor(vertice);
        return GraficadorSVG.graficaCirculoTexto(centroX, centroY, radio,
                "black", color == Color.ROJO ? "red" : "black", TAMANO_FUENTE,
                "white", vertice.get().toString());
    }
}
