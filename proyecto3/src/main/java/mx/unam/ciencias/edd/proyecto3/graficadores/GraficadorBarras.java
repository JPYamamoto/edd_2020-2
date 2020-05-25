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
    // El coeficiente por el que multiplicamos el tamaño de la barra.
    protected int COEFICIENTE;

    /**
     * Constructor del graficador en el que llamamos el constructor del padre e
     * inicializamos unas variables que usamos en otros métodos.
     * @param valores los valores que graficamos.
     */
    public GraficadorBarras(Diccionario<T, R> valores) {
        super(valores);

        this.ALTURA_BARRA = 5;
        this.COEFICIENTE = 500;
    }

    /**
     * Genera el svg de la gráfica de barras.
     * @return el svg de la gráfica de barras.
     */
    public String graficarEstructura() {
        Lista<ValorGraficable> lista = generaGraficables();

        String svgGrafica = "";

        int desplazamientoY = 0;
        int desplazamientoX = 0;

        for (ValorGraficable graficable : lista) {
            // Obtenemos la longitud de la barra que estamos graficando.
            int longitud = (int) Math.ceil(graficable.getValor() * COEFICIENTE);
            svgGrafica += dibujaBarra(desplazamientoY, longitud, graficable.getColor());

            desplazamientoX = longitud > desplazamientoX ? longitud : desplazamientoX;
            desplazamientoY += ALTURA_BARRA;
        }

        // Generamos el svg.
        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(desplazamientoX, desplazamientoY) +
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
        return GraficadorSVG.graficaRectangulo(0, desplazamiento,
                                               longitud, ALTURA_BARRA, 0,
                                               color, color);
    }
}
