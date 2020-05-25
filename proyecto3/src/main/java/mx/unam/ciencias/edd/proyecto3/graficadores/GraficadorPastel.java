package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;

public class GraficadorPastel<T, R extends Number> extends GraficadorCategorias<T, R> {

    public GraficadorPastel(Diccionario<T, R> valores) {
        super(valores);
    }

    public String graficarEstructura() {
        Lista<ValorGraficable> lista = generaGraficables();

        String resultado = "";
        Double acumulado = 0.0;

        for (ValorGraficable graficable : lista) {
            Double angulo = graficable.getValor();
            resultado += dibujaRebanada(graficable, acumulado, angulo);
            acumulado += angulo;
        }

        return GraficadorSVG.declaracionXML() +
               "<svg xmlns='http://www.w3.org/2000/svg' viewBox='-1 -1 2 2'><g>" +
               resultado +
               "</g></svg>";
    }

    protected String dibujaRebanada(ValorGraficable graficable, Double desplazamiento, Double valor) {
        Double origenX = Math.cos(2 * Math.PI * desplazamiento);
        Double origenY = Math.sin(2 * Math.PI * desplazamiento);
        Double finalX = Math.cos(2 * Math.PI * (valor + desplazamiento));
        Double finalY = Math.sin(2 * Math.PI * (valor + desplazamiento));
        boolean arcoLargo = valor > 0.5;

        return GraficadorSVG.graficaCaminoArco(origenX, origenY,
                                               finalX, finalY,
                                               arcoLargo, graficable.getColor());
    }
}
