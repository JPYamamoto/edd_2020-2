package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase concreta para graficar gráficas de pastel.
 */
public class GraficadorPastel<T, R extends Number> extends GraficadorCategorias<T, R> {

    /**
     * Constructor del graficador en el que llamamos el constructor del padre e
     * inicializamos unas variables que usamos en otros métodos.
     * @param valores
     */
    public GraficadorPastel(Diccionario<T, R> valores) {
        super(valores);
    }

    /**
     * Genera el svg de la gráfica de pastel.
     */
    public String graficarEstructura() {
        Lista<ValorGraficable> lista = generaGraficables();

        String resultado = "";
        Double acumulado = 0.0;

        // Graficamos cada una de las rebanadas.
        for (ValorGraficable graficable : lista) {
            Double angulo = graficable.getValor();
            resultado += dibujaRebanada(acumulado, angulo, graficable.getColor());
            acumulado += angulo;
        }

        if (lista.getLongitud() == 2)
            for (ValorGraficable graficable : lista)
                if (graficable.getValor() == 1)
                    resultado = GraficadorSVG.graficaCirculo(0, 0, 1, "#FFFFFF", graficable.getColor());

        // Regresamos el svg.
        return GraficadorSVG.declaracionXML() +
               "<svg xmlns='http://www.w3.org/2000/svg' viewBox='-1 -1 2 2'><g>" +
               resultado +
               "</g></svg>";
    }

    /**
     * Método que genera el svg de una rebanada de la gráfica.
     * @param origen el valor en el que comienza la rebanada (entre 0 y 1).
     * @param valor el valor numérico de la rebanada (entre 0 y 1).
     * @param color el color de la rebanada.
     * @return el svg correspondiente a la rebanada.
     */
    protected String dibujaRebanada(Double origen, Double valor, String color) {
        Double origenX = Math.cos(2 * Math.PI * origen);
        Double origenY = Math.sin(2 * Math.PI * origen);
        Double finalX = Math.cos(2 * Math.PI * (valor + origen));
        Double finalY = Math.sin(2 * Math.PI * (valor + origen));
        boolean arcoLargo = valor > 0.5;

        return GraficadorSVG.graficaCaminoArco(origenX, origenY,
                                               finalX, finalY,
                                               arcoLargo, color);
    }
}
