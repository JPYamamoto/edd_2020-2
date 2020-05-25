package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Dispersor;
import mx.unam.ciencias.edd.FabricaDispersores;
import mx.unam.ciencias.edd.AlgoritmoDispersor;
import mx.unam.ciencias.edd.Lista;

import java.util.Iterator;

public abstract class GraficadorCategorias<T, R extends Number> extends GraficadorEstructura<T> {

    protected class ValorGraficable implements Comparable<ValorGraficable> {
        private T etiqueta;
        private Double valor;
        private String color;

        public ValorGraficable(T etiqueta, Double valor, String color) {
            this.etiqueta = etiqueta;
            this.valor = valor;
            this.color = color;
        }

        public T getEtiqueta() {
            return etiqueta;
        }

        public Double getValor() {
            return valor;
        }

        public String getColor() {
            return color;
        }

        public String toString() {
            return String.format("%s - %.2f%%", etiqueta.toString(), getValor() * 100);
        }

        public int compareTo(ValorGraficable x) {
            return Double.compare(x.valor, valor);
        }
    }

    protected int TAMANO_FUENTE;
    protected int ESPACIO;

    protected Diccionario<T, R> valores;
    protected Dispersor<String> dispersor;

    public GraficadorCategorias(Diccionario<T, R> valores) {
        this.valores = valores;
        this.dispersor = FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING);

        TAMANO_FUENTE = 20;
        ESPACIO = 5;
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

    protected Lista<ValorGraficable> generaGraficables() {
        Double total = calculaTotal();
        Lista<ValorGraficable> lista = new Lista<>();
        Iterator<T> iterador = valores.iteradorLlaves();
        while (iterador.hasNext()) {
            T etiqueta = iterador.next();
            ValorGraficable entrada = new ValorGraficable(etiqueta,
                    valores.get(etiqueta).doubleValue() / total,
                    String.format("#%06X", dispersor.dispersa(etiqueta.toString()) & 0xFFFFFF));

            lista.agrega(entrada);
        }

        return Lista.mergeSort(lista);
    }

    protected abstract String graficarEstructura();

    public String graficarEtiquetas() {
        Lista<ValorGraficable> lista = generaGraficables();

        String svgEtiquetas = "";

        int desplazamientoY = 0;
        int desplazamientoX = 0;

        for (ValorGraficable graficable : lista) {
            svgEtiquetas += dibujaEtiqueta(desplazamientoY, graficable);
            int longitud = graficable.toString().length() * TAMANO_FUENTE;
            desplazamientoX = longitud > desplazamientoX ? longitud : desplazamientoX;
            desplazamientoY += TAMANO_FUENTE + ESPACIO;
        }

        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(desplazamientoX, desplazamientoY) +
               svgEtiquetas +
               GraficadorSVG.terminaSVG();
    }

    protected String dibujaEtiqueta(int desplazamientoY, ValorGraficable graficable) {
        String svg = GraficadorSVG.graficaRectangulo(0, desplazamientoY,
                                                     TAMANO_FUENTE, TAMANO_FUENTE,
                                                     1, "#000000",
                                                     graficable.getColor());

        svg += GraficadorSVG.graficaTextoEsquina(TAMANO_FUENTE + ESPACIO, desplazamientoY,
                TAMANO_FUENTE, graficable.getColor(), graficable.toString());

        return svg;
    }
}
