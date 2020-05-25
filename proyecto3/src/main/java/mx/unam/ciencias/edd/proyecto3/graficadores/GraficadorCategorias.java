package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Dispersor;
import mx.unam.ciencias.edd.FabricaDispersores;
import mx.unam.ciencias.edd.AlgoritmoDispersor;
import mx.unam.ciencias.edd.Lista;

import java.util.Iterator;

/**
 * Clase abstracta que extienden las clases que representan valores por
 * categorías, como lo son las gráficas de barras y de pastel.
 * @param <T> El tipo por el cuál categorizamos los elementos.
 * @param <R> El valor numérico asociado al elemento.
 */
public abstract class GraficadorCategorias<T, R extends Number> extends GraficadorEstructura<T> {

    /**
     * Clase interna que utilizamos para asociar a cada llave con un valor
     * númerico, y un color.
     */
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

    // Tamaño de la fuente que se utilizará en las etiquetas.
    protected int TAMANO_FUENTE;
    // El espacio entre etiquetas.
    protected int ESPACIO;

    // Los valores a graficar.
    protected Diccionario<T, R> valores;
    // El dispersor que utilizamos para generar los colores.
    protected Dispersor<String> dispersor;

    /**
     * El constructor de nuestro graficador. Inicializamos nuestras variables.
     * @param valores los valores a graficar.
     */
    public GraficadorCategorias(Diccionario<T, R> valores) {
        this.valores = valores;
        this.dispersor = FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING);

        TAMANO_FUENTE = 20;
        ESPACIO = 5;
    }

    /**
     * Método abstracto que implementarán las clases concretas para graficar
     * cada estructura.
     */
    protected abstract String graficarEstructura();

    /**
     * Método que genera el SVG de las etiquetas de la gráfica.
     * @return el SVG de las etiquetas.
     */
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

    /**
     * Método que nos genera una etiqueta correspondiente a un valor
     * graficable, con su color y representación en cadena correspondiente.
     * @param desplazamientoY la distancia desde el borde superior hacia abajo.
     * @param graficable el valor cuya etiqueta queremos graficar.
     * @return el svg correspondiente a la etiqueta.
     */
    protected String dibujaEtiqueta(int desplazamientoY, ValorGraficable graficable) {
        String svg = GraficadorSVG.graficaRectangulo(0, desplazamientoY,
                                                     TAMANO_FUENTE, TAMANO_FUENTE,
                                                     1, "#000000",
                                                     graficable.getColor());

        svg += GraficadorSVG.graficaTextoEsquina(TAMANO_FUENTE + ESPACIO, desplazamientoY,
                TAMANO_FUENTE, graficable.getColor(), graficable.toString());

        return svg;
    }

    /**
     * Crea una lista de elementos graficables que podemos usar para generar el
     * gráfico ordenado.
     * @return una lista con los elementos a graficar.
     */
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

    /**
     * La suma de todos los valores de los elementos a graficar, para poder
     * obtener el porcentaje correspondiente a cada uno.
     * @return el total de los valores numéricos.
     */
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
