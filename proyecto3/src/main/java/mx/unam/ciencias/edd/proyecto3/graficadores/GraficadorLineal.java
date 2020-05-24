package mx.unam.ciencias.edd.proyecto3.graficadores;

import java.util.Iterator;

/**
 * Clase abstracta de la que heredan las clases concretas de graficadores que
 * corresponden a estructuras de datos lineales.
 */
public abstract class GraficadorLineal<T> extends GraficadorEstructura<T> {

    // Altura del contenedor de un vértice.
    protected int ALTURA_VERTICE;
    // Ancho del SVG que representa la conexión.
    protected int ANCHO_CONEXION;
    // EL tamaño de la fuente.
    protected int TAMANO_FUENTE;
    // El tamaño del borde.
    protected int BORDE;

    // El iterable a recorrer para agregar a la estructura de datos lineal.
    protected Iterable<T> iterable;

    /**
     * El constructor de la clase abstracta. Simplemente asignamos el iterable
     * a su variable de clase e inicializamos algunas constantes. Asumimos que
     * cada clase concreta va a elegir el tipo más adecuado para recibir en su
     * constructor, y que sea lineal.
     * @param iterable el iterable a graficar.
     */
    public GraficadorLineal(Iterable<T> iterable) {
        this.iterable = iterable;

        ALTURA_VERTICE = 40;
        ANCHO_CONEXION = 50;
        TAMANO_FUENTE = 20;
        BORDE = 10;
    }

    /**
     * Obtén la cadena de texto de la gráfica SVG que corresponde a la
     * estructura de datos.
     * @return el SVG de la estructura de datos.
     */
    public String graficarEstructura() {
        int anchoVertice = calculaAnchoVertices();
        String svg = "";
        Iterator<T> iterador = iterable.iterator();
        int anchoSVG = anchoVertice + BORDE;

        // Agregamos el primer elemento si este existe.
        if (iterador.hasNext())
            svg += graficaVertice(iterador.next(), BORDE, BORDE, anchoVertice, ALTURA_VERTICE);

        while (iterador.hasNext()) {
            // Grafica el SVG que corresponde a la conexión, y aumenta el
            // tamaño del SVG.
            svg += graficaConexion(anchoSVG, BORDE + (ALTURA_VERTICE / 4), ANCHO_CONEXION, ALTURA_VERTICE / 2);
            anchoSVG += ANCHO_CONEXION;
            // Grafica el SVG que corresponde al vértice, y aumenta el tamaño del SVG.
            svg += graficaVertice(iterador.next(), anchoSVG, BORDE, anchoVertice, ALTURA_VERTICE);
            anchoSVG += anchoVertice;
        }

        // Agrega la declaración XML, la etiqueta de apertura SVG con las
        // medidas del gráfico, el contenido del SVG y la etiqueta de cierre
        // del SVG. Regresa el resultado.
        return GraficadorSVG.declaracionXML() +
                GraficadorSVG.comienzaSVG(anchoSVG + BORDE, ALTURA_VERTICE + 2 * BORDE) +
                svg +
                GraficadorSVG.terminaSVG();
    }

    /**
     * Método que nos sirve para calcular la medida del ancho de los vértices.
     * Esto con la finalidad de que todos los tengan la misma medida. La medida
     * se basa en el máximo de dígitos que un elemento puede tener.
     * elementos.
     */
    protected int calculaAnchoVertices() {
        int max = 0;

        for (T vertice : iterable)
            max = max <= vertice.toString().length() ? vertice.toString().length() : max;

        // Regresamos el producto de la longitud del elemento máximo y el
        // tamaño de cada carácter de la fuente. Además, agregamos un borde de
        // ambos lados.
        return (max * TAMANO_FUENTE) + 2 * BORDE;
    }

    /**
     * Método que genera la cadena de texto que representa un vértice con el
     * elemento recibido. Utiliza las medidas recibidas.
     */
    protected String graficaVertice(T elemento, int origenX, int origenY, int medidaX, int medidaY) {
        // Un vértice es simplemente un rectángulo que contiene el elemento.
        return GraficadorSVG.graficaRectanguloTexto(origenX, origenY, medidaX,
                medidaY, "black", "white", TAMANO_FUENTE, "black", elemento.toString());
    }

    /**
     * Genera la cadena de texto con el SVG que representa la unión entre dos
     * vértices de la estructura de datos.
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

    protected boolean esVacia() {
        return !iterable.iterator().hasNext();
    }
}
