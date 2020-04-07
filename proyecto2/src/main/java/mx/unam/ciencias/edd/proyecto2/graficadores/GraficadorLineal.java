package mx.unam.ciencias.edd.proyecto2.graficadores;

import java.util.Iterator;

/**
 * Clase abstracta de la que heredan las clases concretas de graficadores que
 * corresponden a estructuras de datos lineales.
 */
public abstract class GraficadorLineal<T extends Comparable<T>> implements GraficadorSVG<T> {

    private final int ALTURA_NODO = 30;

    /**
     * Obtén la cadena de texto de la gráfica SVG que corresponde a la
     * estructura de datos.
     * @return el SVG de la estructura de datos.
     */
    public String graficar() {
        int anchoNodo = calculaAnchoNodos();
        String svg = "";
        Iterator<T> iterador = getIterator();

        if (iterador.hasNext())
            svg += generaNodo(iterador.next(), anchoNodo, ALTURA_NODO);

        while (iterador.hasNext()) {
            svg += generaConexion();
            svg += generaNodo(iterador.next(), anchoNodo, ALTURA_NODO);
        }

        return svg;
    }

    /**
     * Método que nos sirve para calcular la medida del ancho de los nodos.
     * Esto con la finalidad de que todos los tengan la misma medida. La medida
     * se calculará con base en la longitud del elemento más grande.
     */
    protected int calculaAnchoNodos() {
        Iterator<T> iterador = getIterator();
        T max = null;

        if (iterador.hasNext())
            max = iterador.next();
        else
            return 0;

        while (iterador.hasNext()) {
            T actual = iterador.next();
            max = max.compareTo(actual) <= 0 ? actual : max;
        }

        return (max.toString().length() * 20) + 50;
    }

    /**
     * Método que genera la cadena de texto que representa un nodo con el
     * elemento recibido. Utiliza las medidas recibidas.
     */
    protected abstract String generaNodo(T elemento, int medidaX, int medidaY);

    /**
     * Método abstracto que nos sirve internamente para calcular la medida del
     * ancho de los nodos. Esto con la finalidad de que todos los tengan la
     * misma medida. La medida se calculará con base en la longitud del
     * elemento más grande.
     */
    protected abstract String generaConexion();

    /**
     * Método abstracto que nos sirve internamente para obtener un iterador de
     * la estructura de datos a graficar. Esto es necesario pues no conocemos
     * la implementación concreta de los graficadores y podrían no contener
     * una estructura de datos iterable.
     * Por ejemplo, si la implementación concreta de GraficadorPila contiene
     * una pila y esta no es iterable, nos veríamos en la imposibilidad de
     * iterarlo. Este método nos garantiza que hay una forma de iterar.
     * @return el iterador para iterar el iterable.
     */
    protected abstract Iterator<T> getIterator();
}
