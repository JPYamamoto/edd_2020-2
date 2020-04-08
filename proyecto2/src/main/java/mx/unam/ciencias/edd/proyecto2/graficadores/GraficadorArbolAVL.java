package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;

public class GraficadorArbolAVL<T extends Comparable<T>> extends GraficadorArbolBinarioOrdenado<T> {

    private final int TAMANO_FUENTE_BALANCE = 10;
    private final int DESPLAZAMIENTO_ETIQUETA = 10;

    public GraficadorArbolAVL(ArbolAVL<T> arbol) {
        super(arbol);
        BORDE_SVG = 20;
    }

    protected String graficaVertice(VerticeArbolBinario<T> vertice, int centroX,
                                    int centroY, int radio) {
        String textoVertice = vertice.toString();
        int ultimoEspacio = textoVertice.lastIndexOf(' ');
        String balance = String.format("(%s)", textoVertice.substring(ultimoEspacio + 1));
        int centroTextoX = centroX;

        if (esDerecho(vertice))
            centroTextoX += (int) Math.ceil(balance.length() / 2);
        else if (esIzquierdo(vertice))
            centroTextoX += - (int) Math.ceil(balance.length() / 2);

        String resultado = "";

        resultado += GraficadorSVG.graficaTexto(centroTextoX,
                        centroY - radio - DESPLAZAMIENTO_ETIQUETA,
                        TAMANO_FUENTE_BALANCE, "black", balance);

        resultado += GraficadorSVG.graficaCirculoTexto(centroX, centroY, radio,
                "black", "white", TAMANO_FUENTE, "black", vertice.get().toString());

        return resultado;
    }
}
