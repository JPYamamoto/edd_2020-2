package mx.unam.ciencias.edd.proyecto2.graficadores;

/**
 * Interfaz que deben implementar las clases que se encargan de graficar las
 * estructuras de datos. Lo ideal es que cada estructura de datos tenga una
 * clase correspondiente que implemente esta interfaz para generar la
 * representación adecuada de la estructura.
 */
public interface GraficadorEstructura<T> {

    /**
     * Método que regresa la gráfica SVG de una estructura de datos.
     * @return la cadena de texto con el SVG de la estructura.
     */
    public String graficar();
}
