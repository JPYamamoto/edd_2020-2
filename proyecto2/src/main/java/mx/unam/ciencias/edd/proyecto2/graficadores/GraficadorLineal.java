package mx.unam.ciencias.edd.proyecto2.graficadores;

import java.util.Iterator;

/**
 * Clase abstracta de la que heredan las clases concretas de graficadores que
 * corresponden a estructuras de datos lineales.
 */
public abstract class GraficadorLineal<T extends Comparable<T>> implements GraficadorSVG<T> {

    /**
     * Método abstracto que nos sirve internamente para obtener el iterador de
     * la estructura de datos a graficar. Esto es necesario pues no conocemos
     * la implementación concreta de los graficadores y podrían o no contener
     * una estructura de datos iterable.
     * Por ejemplo, si la implementación concreta de GraficadorPila contiene
     * una pila y esta no es iterable, nos veríamos en la imposibilidad de
     * iterarlo. Este método nos garantiza que existe un iterador.
     * @return el iterador para iterar el iterable.
     */
    protected abstract Iterator<T> getIterador();

    /**
     * Obtén la cadena de texto de la gráfica SVG que corresponde a la
     * estructura de datos.
     * @return el SVG de la estructura de datos.
     */
    public String graficar() {
        return null;
    }
}
