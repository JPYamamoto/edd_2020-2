package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Diccionario;

public class GraficadorPastel<T, R extends Number> extends GraficadorEstructura<T> {

    protected int BORDE_SVG;
    protected int TAMANO_FUENTE;

    protected Diccionario<T, R> valores;

    public GraficadorPastel(Diccionario<T, R> valores) {
        this.valores = valores;

        BORDE_SVG = 10;
        TAMANO_FUENTE = 20;
    }

    public String graficarEstructura() {
        String resultado = "";
        Double total = calculaTotal();
        Double acumulado = 0.0;

        for (Number valor : valores) {
            Double angulo = valor.doubleValue() / total;
            resultado += dibujaRebanada(acumulado, angulo);
            acumulado += angulo;
        }

        return GraficadorSVG.declaracionXML() +
               "<svg xmlns='http://www.w3.org/2000/svg' viewBox='-1 -1 2 2'><g>" +
               resultado +
               "</g></svg>";
    }

    protected String dibujaRebanada(Double desplazamiento, Double valor) {
        Double origenX = Math.cos(2 * Math.PI * desplazamiento);
        Double origenY = Math.sin(2 * Math.PI * desplazamiento);
        Double finalX = Math.cos(2 * Math.PI * (valor + desplazamiento));
        Double finalY = Math.sin(2 * Math.PI * (valor + desplazamiento));
        boolean arcoLargo = valor > 0.5;
        String color = String.format("#%06X", (int)(Math.random() * 0x1000000));

        return GraficadorSVG.graficaCaminoArco(origenX, origenY,
                                               finalX, finalY,
                                               arcoLargo, color);
    }

    protected Double calculaTotal() {
        Double total = 0.0;

        for (Number valor : valores)
            total += valor.doubleValue();

        return total;
    }

    protected boolean esVacia() {
        return valores.esVacia();
    }
}
