package mx.unam.ciencias.edd.proyecto3.graficadores;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;

/**
 * Graficador para generar el SVG de la estructura de datos Gráfica.
 */
public class GraficadorGrafica<T> extends GraficadorEstructura<T> {

    /**
     * Clase interna privada que utilizamos para mantener las coordenadas en
     * donde se ubica cada vértice, y poder graficar las aristas.
     */
    private class Coord {
        public int x;
        public int y;
        public int posicion;
        public T elemento;

        public Coord(int x, int y, int posicion, T elemento) {
            this.x = x;
            this.y = y;
            this.posicion = posicion;
            this.elemento = elemento;
        }
    }

    // Tamaño del espacio entre el márgen del SVG y la gráfica.
    protected int BORDE_SVG;
    // Espacio entre el contenido del vértice y su orilla.
    protected int BORDE_VERTICE;
    // El tamaño de la fuente del contenido de los vértices.
    protected int TAMANO_FUENTE;
    // Ancho de la arista.
    protected int ANCHO_ARISTA;

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
        ANCHO_ARISTA = 3;
    }

    /**
     * Genera la cadena de texto del SVG que representa a la estructura de
     * datos.
     * @return el SVG de la estructura de datos.
     */
    public String graficarEstructura() {
        // El radio de los vértices.
        int radioVertice = calculaRadioVertices();

        // El ángulo entre cada uno de los vértices.
        double angulo = (double) 360 / grafica.getElementos();
        /**
        * A falta de una mejor idea para distribuir los vértices de manera
        * moderadamente estética, decidí graficarlos en una disposición
        * circular. Podemos colocar cada uno de los vértices sobre una
        * circunferencia a una distancia igual.
        * Queremos que el espacio entre cualesquiera dos centros de vértices
        * sea constante. Va a estar dado por el tamaño del radio de un vértice
        * multiplicado por una constante. Luego, podemos utilizar la siguiente
        * fórmula para obtener el tamaño del radio:
        * radio = cuerda / (2 * sin(angulo / 2))
        */
        double radioCircunferencia = Math.abs((3 * radioVertice) / (2 * Math.sin(Math.toRadians(angulo / 2))));
        int radioGrafica = (int) Math.round(radioCircunferencia + BORDE_SVG + radioVertice);
        // El ángulo al que va a estar cada uno de los vértices.
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
        int posicion = 0;

        for (VerticeGrafica<T> vertice : vertices) {
            // Conseguimos los componentes X y Y de cada vértice sobre la
            // circunferencia, para poder graficarlos en sus coordenadas.
            int componenteX = (int) Math.round(radioCircunferencia * Math.cos(Math.toRadians(anguloSVG)));
            int componenteY = (int) Math.round(radioCircunferencia * Math.sin(Math.toRadians(anguloSVG)));
            componenteX += radioGrafica;
            componenteY += radioGrafica;

            // Graficamos cada uno de los vértices y guardamos sus coordenadas.
            verticesSVG += graficaVertice(vertice.get(), componenteX, componenteY, radioVertice);
            Coord coord = new Coord(componenteX, componenteY, posicion++, vertice.get());

            // Recorremos su lista de vecinos, y si el vecino ya ha sido
            // graficado, obtenemos sus coordenadas y graficamos una arista.
            for (VerticeGrafica<T> vecino : vertice.vecinos()) {
                Coord coordVecino = getCoordenada(vecino, verticesGraficados);
                if (coordVecino != null)
                    aristasSVG += graficaConexion(coord, coordVecino, radioGrafica);
            }

            // Agregamos el vértice a los vértices graficados.
            verticesGraficados.agrega(coord);
            // Modificamos el ángulo.
            anguloSVG += angulo;
        }

        // Obtenemos la medida de cada lado del SVG y lo graficamos junto con
        // sus etiquetas de apertura y cerradura.
        int medida = radioGrafica * 2;
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
     * se basa en el máximo de dígitos que un vértice puede tener.
     */
    protected int calculaRadioVertices() {
        int maximo = 0;

        for (T vertice : grafica)
            maximo = maximo <= vertice.toString().length() ? vertice.toString().length() : maximo;

        return ((maximo * (2 * TAMANO_FUENTE / 3)) / 2) + BORDE_VERTICE;
    }

    /**
     * Método que genera la cadena de texto que representa un vertice con el
     * elemento recibido. Utiliza las medidas recibidas.
     */
    protected String graficaVertice(T elemento, int origenX, int origenY, int radio) {
        // Un vértice es un círculo que contiene el elemento.
        return GraficadorSVG.graficaCirculoTexto(origenX, origenY, radio,
                "black", "white", TAMANO_FUENTE, "black", elemento.toString());
    }

    /**
     * Genera la cadena de texto con el SVG que representa la unión entre dos
     * vértices de la estructura de datos.
     * Si los vértices se encuentran contiguos, solo graficamos una línea recta
     * entre ellos. Si no, graficamos una curva, para que se vean más
     * claramente las aristas.
     */
    protected String graficaConexion(Coord vertice1, Coord vertice2, int radioGrafica) {
        if (Math.abs(vertice1.posicion - vertice2.posicion) == 1)
            return GraficadorSVG.graficaLinea(vertice1.x, vertice1.y,
                    vertice2.x - vertice1.x, vertice2.y - vertice1.y,
                    ANCHO_ARISTA, "black");

        return GraficadorSVG.graficaCurvaBezier(vertice1.x, vertice1.y,
                            vertice2.x - vertice1.x, vertice2.y - vertice1.y,
                            radioGrafica - vertice1.x, radioGrafica - vertice1.y,
                            "black");
    }

    protected boolean esVacia() {
        return grafica.esVacia();
    }
}
