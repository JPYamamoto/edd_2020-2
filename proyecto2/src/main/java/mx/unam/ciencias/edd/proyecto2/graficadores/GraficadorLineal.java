package mx.unam.ciencias.edd.proyecto2.graficadores;

import java.util.Iterator;

/**
 * Clase abstracta de la que heredan las clases concretas de graficadores que
 * corresponden a estructuras de datos lineales.
 */
public abstract class GraficadorLineal<T> implements GraficadorEstructura<T> {

    protected final int ALTURA_NODO = 40;
    protected final int ANCHO_CONEXION = 50;
    protected final int TAMANO_FUENTE = 20;
    protected final int BORDE = 10;

    /**
     * Obtén la cadena de texto de la gráfica SVG que corresponde a la
     * estructura de datos.
     * @return el SVG de la estructura de datos.
     */
    public String graficar() {
        int anchoNodo = calculaAnchoNodos();
        String svg = "";
        Iterator<T> iterador = getIterator();
        int anchoSVG = anchoNodo + BORDE;

        // Agregamos el primer elemento si este existe.
        if (iterador.hasNext())
            svg += graficaNodo(iterador.next(), BORDE, BORDE, anchoNodo, ALTURA_NODO);

        while (iterador.hasNext()) {
            // Grafica el SVG que corresponde a la conexión, y aumenta el
            // tamaño del SVG.
            svg += graficaConexion(anchoSVG, BORDE + (ALTURA_NODO / 4), ANCHO_CONEXION, ALTURA_NODO / 2);
            anchoSVG += ANCHO_CONEXION;
            // Grafica el SVG que corresponde al nodo, y aumenta el tamaño del SVG.
            svg += graficaNodo(iterador.next(), anchoSVG, BORDE, anchoNodo, ALTURA_NODO);
            anchoSVG += anchoNodo;
        }

        // Agrega la declaración XML, la etiqueta de apertura SVG con las
        // medidas del gráfico, el contenido del SVG y la etiqueta de cierre
        // del SVG. Regresa el resultado.
        return GraficadorSVG.declaracionXML() +
                GraficadorSVG.comienzaSVG(anchoSVG + BORDE, ALTURA_NODO + 2 * BORDE) +
                svg +
                GraficadorSVG.terminaSVG();
    }

    /**
     * Método que nos sirve para calcular la medida del ancho de los nodos.
     * Esto con la finalidad de que todos los tengan la misma medida. La medida
     * se basa en la longitud mayor de la representación en cadena de los
     * elementos.
     */
    protected int calculaAnchoNodos() {
        Iterator<T> iterador = getIterator();
        int max = 0;

        // El primer elemento es el máximo hasta el momento. El ancho es 0
        // cuando no hay nodos.
        if (iterador.hasNext())
            max = iterador.next().toString().length();
        else
            return 0;

        // Encontramos el valor máximo.
        while (iterador.hasNext()) {
            T actual = iterador.next();
            max = max - actual.toString().length() <= 0 ? actual.toString().length() : max;
        }

        // Regresamos el producto de la longitud del elemento máximo y el
        // tamaño de cada carácter de la fuente. Además, agregamos un borde de
        // ambos lados.
        return (max * TAMANO_FUENTE) + 2 * BORDE;
    }

    /**
     * Método que genera la cadena de texto que representa un nodo con el
     * elemento recibido. Utiliza las medidas recibidas.
     */
    protected String graficaNodo(T elemento, int origenX, int origenY, int medidaX, int medidaY) {
        // Un nodo es simplemente un rectángulo que contiene el elemento.
        return GraficadorSVG.graficaRectanguloTexto(origenX, origenY, medidaX,
                medidaY, "black", "white", TAMANO_FUENTE, "black", elemento.toString());
    }

    /**
     * Genera la cadena de texto con el SVG que representa la unión entre dos
     * nodos de la estructura de datos.
     */
    protected String graficaConexion(int origenX, int origenY, int medidaX, int medidaY) {
        // Una flecha se comprende por 2 terceras partes de la línea, y la
        // cabeza ocupa una tercera parte de la longitud.
        int seccion = medidaX / 3;

        // Una flecha con una línea y un triángulo en el extremo derecho.
        String conexion;
        conexion = GraficadorSVG.graficaLinea(origenX, (medidaY / 2) + origenY, medidaX - seccion, 0, "black");
        // Utilizamos -seccion para invertir la flecha sobre su eje x.
        conexion += GraficadorSVG.graficaTriangulo(origenX + medidaX, origenY, -seccion, medidaY, "black");

        return conexion;
    }

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
