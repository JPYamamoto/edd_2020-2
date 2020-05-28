package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase concreta para graficar una gráfica de barras. Hay que tomar en cuenta
 * que la gráfica es de barras horizontales. Me pareció que se veían mejor en
 * la pantalla y eran más fáciles de graficar :P
 */
public class GraficadorBarras<T, R extends Number> extends GraficadorCategorias<T, R> {

    // La altura de la barra horizontal.
    protected int ALTURA_BARRA;
    // Ancho del eje.
    protected int ANCHO_EJE;
    // El coeficiente por el que multiplicamos el tamaño de la barra.
    protected int COEFICIENTE;
    // Borde SVG.
    protected int BORDE_SVG;

    /**
     * Constructor del graficador en el que llamamos el constructor del padre e
     * inicializamos unas variables que usamos en otros métodos.
     * @param valores los valores que graficamos.
     */
    public GraficadorBarras(Diccionario<T, R> valores) {
        super(valores);

        this.ALTURA_BARRA = 10;
        this.COEFICIENTE = 500;
        this.ANCHO_EJE = 1;
        this.BORDE_SVG = 5;
    }

    /**
     * Genera el svg de la gráfica de barras.
     * @return el svg de la gráfica de barras.
     */
    public String graficarEstructura() {
        Lista<ValorGraficable> lista = generaGraficables();

        String svgGrafica = "";

        int desplazamientoY = BORDE_SVG;
        int desplazamientoX = 0;
        ValorGraficable maximo = null;

        for (ValorGraficable graficable : lista) {
            // Obtenemos la longitud de la barra que estamos graficando.
            int longitud = (int) Math.ceil(graficable.getValor() * COEFICIENTE);
            svgGrafica += dibujaBarra(desplazamientoY, longitud, graficable.getColor());

            if (maximo == null || maximo.getValor() < graficable.getValor()) {
                maximo = graficable;
                desplazamientoX = longitud;
            }

            desplazamientoY += ALTURA_BARRA;
        }

        desplazamientoY += ALTURA_BARRA;
        svgGrafica += GraficadorSVG.graficaLinea(BORDE_SVG, desplazamientoY,
                                    desplazamientoX, 0, ANCHO_EJE, "#000000");
        svgGrafica += GraficadorSVG.graficaLinea(desplazamientoX + BORDE_SVG,
                                    desplazamientoY - 2, 0, 4, ANCHO_EJE, "#000000");
        svgGrafica += GraficadorSVG.graficaLinea(BORDE_SVG, desplazamientoY - 2,
                                    0, 4, ANCHO_EJE, "#000000");
        svgGrafica += GraficadorSVG.graficaTextoEsquina(BORDE_SVG,
                                    desplazamientoY + 2, 5, "#000000", "0");
        svgGrafica += GraficadorSVG.graficaTextoEsquina(desplazamientoX - 10,
                                    desplazamientoY + 2, 5, "#000000",
                                    String.format("%.2f%%", maximo.getValor() * 100));
        desplazamientoY += ALTURA_BARRA;

        // Generamos el svg.
        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(desplazamientoX + (2 * BORDE_SVG), desplazamientoY + BORDE_SVG) +
               svgGrafica +
               GraficadorSVG.terminaSVG();
    }

    /**
     * Auxiliar para construir cada una de las barras de la gráfica.
     * @param desplazamiento el espacio del borde superior hacia abajo antes de
     * la barra.
     * @param longitud el tamaño de la barra.
     * @param color el color de la barra.
     * @return el svg de una barra.
     */
    protected String dibujaBarra(int desplazamiento, int longitud, String color) {
        return GraficadorSVG.graficaRectangulo(BORDE_SVG, desplazamiento,
                                               longitud, ALTURA_BARRA, 0,
                                               color, color);
    }
}
