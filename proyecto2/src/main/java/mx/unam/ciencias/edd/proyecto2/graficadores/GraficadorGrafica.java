package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;

/**
 * Graficador para generar el SVG de la estructura de datos Gráfica.
 */
public class GraficadorGrafica<T> implements GraficadorEstructura<T> {

    /**
     * Clase interna privada que utilizamos para mantener las coordenadas en
     * donde se ubica cada vértice, y poder graficar las aristas.
     */
    private class Coord {
        public int x;
        public int y;
        public T elemento;

        public Coord(int x, int y, T elemento) {
            this.x = x;
            this.y = y;
            this.elemento = elemento;
        }
    }

    // Tamaño del espacio entre el márgen del SVG y la gráfica.
    protected int BORDE_SVG;
    // Espacio entre el contenido del vértice y su orilla.
    protected int BORDE_VERTICE;
    // El tamaño de la fuente del contenido de los vértices.
    protected int TAMANO_FUENTE;

    // La gráfica a graficar.
    protected Grafica<T> grafica;

    /**
     * El constructor del graficador. Asignamos la gráfica a la variable de
     * clase y algunas constantes.
     * @param grafica la gráfica que se representará.
     */
    public GraficadorGrafica(Grafica<T> grafica) {
        this.grafica = grafica;

        BORDE_SVG = 10;
        BORDE_VERTICE= 10;
        TAMANO_FUENTE = 20;
    }

    /**
     * Genera la cadena de texto del SVG que representa a la estructura de
     * datos.
     * @return el SVG de la estructura de datos.
     */
    public String graficar() {
        // El radio de los nodos.
        int radioVertice = calculaRadioVertices();

        // El ángulo entre cada uno de los nodos.
        double angulo = (double) 360 / grafica.getElementos();
        /**
        * A falta de una mejor idea para distribuir los nodos de manera
        * moderadamente estética, decidí graficarlos en una disposición
        * circular. Podemos colocar cada uno de los nodos sobre una
        * circunferencia a una distancia igual.
        * Queremos que el espacio entre cualesquiera dos centros de nodos sea
        * constante. Va a estar dado por el tamaño del radio de un vértice
        * multiplicado por una constante. Luego, podemos utilizar la siguiente
        * fórmula para obtener el tamaño del radio:
        * radio = cuerda / (2 * sin(angulo / 2))
        */
        double radioCircunferencia = Math.abs((6 * radioVertice) / (2 * Math.sin(angulo / 2)));
        // El ángulo al que va a estar cada uno de los nodos.
        double anguloSVG = 0;

        // Lista donde guardamos los vértices para poder iterarlos.  Hacemos
        // esto pues el iterador de la gráfica no nos permite acceder a los
        // vecinos del vértice, sin primero llamar el método vertice(), que
        // toma tiempo lineal. Al hacerlo para cada elemento se va a O(n^2).
        Lista<VerticeGrafica<T>> vertices = new Lista<>();
        grafica.paraCadaVertice((vertice) -> vertices.agrega(vertice));

        // Lista que contiene a los vértices que ya han sido graficados, para
        // obtener las coordenadas de estos.
        Lista<Coord> verticesGraficados = new Lista<>();

        String aristasSVG = "";
        String verticesSVG = "";

        for (VerticeGrafica<T> vertice : vertices) {
            // Conseguimos los componentes X y Y de cada vértice sobre la
            // circunferencia, para poder graficarlos en sus coordenadas.
            int componenteX = (int) Math.round(radioCircunferencia * Math.cos(Math.toRadians(anguloSVG)));
            int componenteY = (int) Math.round(radioCircunferencia * Math.sin(Math.toRadians(anguloSVG)));
            componenteX += BORDE_SVG + radioVertice + radioCircunferencia;
            componenteY += BORDE_SVG + radioVertice + radioCircunferencia;

            // Graficamos cada uno de los nodos y guardamos sus coordenadas.
            verticesSVG += graficaNodo(vertice.get(), componenteX, componenteY, radioVertice);
            Coord coord = new Coord(componenteX, componenteY, vertice.get());

            // Recorremos su lista de vecinos, y si el vecino ya ha sido
            // graficado, obtenemos sus coordenadas y graficamos una arista.
            for (VerticeGrafica<T> vecino : vertice.vecinos()) {
                Coord coordVecino = getCoordenada(vecino, verticesGraficados);
                if (coordVecino != null)
                    aristasSVG += graficaConexion(componenteX, componenteY,
                                                  coordVecino.x, coordVecino.y);
            }

            // Agregamos el vértice a los vértices graficados.
            verticesGraficados.agrega(coord);
            // Modificamos el ángulo.
            anguloSVG += angulo;
        }

        // Obtenemos la medida de cada lado del SVG y lo graficamos junto con
        // sus etiquetas de apertura y cerradura.
        int medida = (int) Math.round((radioCircunferencia + BORDE_SVG + radioVertice) * 2);
        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(medida, medida) +
               aristasSVG +
               verticesSVG +
               GraficadorSVG.terminaSVG();
    }

    /**
     * Método privado que nos dice si el vértice ya ha sido graficado, y nos da
     * sus coordenadas. De otra manera, regresa null.
     */
    private Coord getCoordenada(VerticeGrafica<T> vertice, Lista<Coord> coordenadas) {
        for (Coord coord : coordenadas)
            if (coord.elemento.equals(vertice.get()))
                return coord;

        return null;
    }

    /**
     * Método que nos sirve para calcular la medida del ancho de los vértices.
     * Esto con la finalidad de que todos los tengan la misma medida. La medida
     * se basa en la longitud mayor de la representación en cadena de los
     * elementos.
     */
    protected int calculaRadioVertices() {
        int maximo = 0;

        for (T vertice : grafica)
            maximo = maximo <= vertice.toString().length() ? vertice.toString().length() : maximo;

        return maximo * TAMANO_FUENTE + BORDE_VERTICE;
    }

    /**
     * Método que genera la cadena de texto que representa un nodo con el
     * elemento recibido. Utiliza las medidas recibidas.
     */
    protected String graficaNodo(T elemento, int origenX, int origenY, int radio) {
        // Un nodo es un círculo que contiene el elemento.
        return GraficadorSVG.graficaCirculoTexto(origenX, origenY, radio,
                "black", "white", TAMANO_FUENTE, "black", elemento.toString());
    }

    /**
     * Genera la cadena de texto con el SVG que representa la unión entre dos
     * nodos de la estructura de datos.
     */
    protected String graficaConexion(int origenX1, int origenY1, int origenX2, int origenY2) {
        return GraficadorSVG.graficaLinea(origenX1, origenY1,
                origenX2 - origenX1, origenY2 - origenY1, "black");
    }
}
