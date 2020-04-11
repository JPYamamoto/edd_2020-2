package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.VerticeGrafica;

public class GraficadorGrafica<T> implements GraficadorEstructura<T> {

    private class Coord {
        public int x;
        public int y;
        public VerticeGrafica<T> vertice;

        public Coord(int x, int y, VerticeGrafica<T> vertice) {
            this.x = x;
            this.y = y;
            this.vertice = vertice;
        }
    }

    protected int BORDE_SVG;
    protected int BORDE_VERTICE;
    protected int TAMANO_FUENTE;

    protected Grafica<T> grafica;

    public GraficadorGrafica(Grafica<T> grafica) {
        this.grafica = grafica;
    }

    public String graficar() {
        int radioVertice = calculaRadioVertices();
        int radioCircunferencia = (int) Math.ceil((6 * radioVertice) / (2 * Math.sin(180 / grafica.getElementos())));

        return null;
    }

    /**
     * Método que nos sirve para calcular la medida del ancho de los nodos.
     * Esto con la finalidad de que todos los tengan la misma medida. La medida
     * se basa en la longitud mayor de la representación en cadena de los
     * elementos.
     */
    protected int calculaRadioVertices() {
        int maximo = 0;

        for (T vertice : grafica)
            maximo = maximo <= vertice.toString().length() ? vertice.toString().length() : maximo;

        return maximo + BORDE_VERTICE;
    }

    /**
     * Método que genera la cadena de texto que representa un nodo con el
     * elemento recibido. Utiliza las medidas recibidas.
     */
    protected String graficaNodo(T elemento, int origenX, int origenY, int medidaX, int medidaY) {
        // Un nodo es simplemente un rectángulo que contiene el elemento.
        return GraficadorSVG.graficaCirculoTexto(origenX, origenY, medidaX,
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
}
