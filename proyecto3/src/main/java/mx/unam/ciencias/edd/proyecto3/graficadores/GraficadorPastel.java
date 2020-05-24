package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Diccionario;

public class GraficadorPastel<T> extends GraficadorEstructura<T> {

    protected int BORDE_SVG;
    protected int TAMANO_FUENTE;

    protected Diccionario<T, Number> valores;

    public GraficadorPastel(Diccionario<T, Number> valores) {
        this.valores = valores;

        BORDE_SVG = 10;
        TAMANO_FUENTE = 20;
    }

    public String graficarEstructura() {
        return null;
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
