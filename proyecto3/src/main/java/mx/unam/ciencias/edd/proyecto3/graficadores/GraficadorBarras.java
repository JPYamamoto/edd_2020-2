package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;

public class GraficadorBarras<T, R extends Number> extends GraficadorCategorias<T, R> {

    protected int ALTURA_BARRA;
    protected int COEFICIENTE;

    public GraficadorBarras(Diccionario<T, R> valores) {
        super(valores);

        this.ALTURA_BARRA = 5;
        this.COEFICIENTE = 500;
    }

    public String graficarEstructura() {
        Lista<ValorGraficable> lista = generaGraficables();

        String svgGrafica = "";

        int desplazamientoY = 0;
        int desplazamientoX = 0;

        for (ValorGraficable graficable : lista) {
            int longitud = (int) Math.ceil(graficable.getValor() * COEFICIENTE);
            svgGrafica += dibujaBarra(desplazamientoY, longitud, graficable);

            desplazamientoX = longitud > desplazamientoX ? longitud : desplazamientoX;
            desplazamientoY += ALTURA_BARRA;
        }

        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(desplazamientoX, desplazamientoY) +
               svgGrafica +
               GraficadorSVG.terminaSVG();
    }

    protected String dibujaBarra(int desplazamiento, int longitud, ValorGraficable graficable) {
        return GraficadorSVG.graficaRectangulo(0, desplazamiento,
                                               longitud, ALTURA_BARRA, 0,
                                               graficable.getColor(), graficable.getColor());
    }
}
