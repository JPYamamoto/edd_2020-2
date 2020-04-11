package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;

public class GraficadorGrafica<T> implements GraficadorEstructura<T> {

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

    protected int BORDE_SVG;
    protected int BORDE_VERTICE;
    protected int TAMANO_FUENTE;

    protected Grafica<T> grafica;

    public GraficadorGrafica(Grafica<T> grafica) {
        this.grafica = grafica;
        BORDE_SVG = 10;
        BORDE_VERTICE= 10;
        TAMANO_FUENTE = 20;
    }

    public String graficar() {
        int radioVertice = calculaRadioVertices();
        double angulo = (double) 360 / grafica.getElementos();
        double radioCircunferencia = Math.abs((6 * radioVertice) / (2 * Math.sin(angulo / 2)));
        double anguloSVG = 0;

        Lista<VerticeGrafica<T>> vertices = new Lista<>();
        grafica.paraCadaVertice((vertice) -> vertices.agrega(vertice));
        Lista<Coord> verticesGraficados = new Lista<>();

        String aristasSVG = "";
        String verticesSVG = "";
        for (VerticeGrafica<T> vertice : vertices) {
            int componenteX = (int) Math.round(radioCircunferencia * Math.cos(Math.toRadians(anguloSVG)));
            int componenteY = (int) Math.round(radioCircunferencia * Math.sin(Math.toRadians(anguloSVG)));
            componenteX += BORDE_SVG + radioVertice + radioCircunferencia;
            componenteY += BORDE_SVG + radioVertice + radioCircunferencia;

            verticesSVG += graficaNodo(vertice.get(), componenteX, componenteY, radioVertice);
            Coord coord = new Coord(componenteX, componenteY, vertice.get());

            for (VerticeGrafica<T> vecino : vertice.vecinos()) {
                Coord coordVecino = getCoordenada(vecino, verticesGraficados);
                if (coordVecino != null)
                    aristasSVG += graficaConexion(componenteX, componenteY, coordVecino.x, coordVecino.y);
            }

            verticesGraficados.agrega(coord);
            anguloSVG += angulo;
        }

        int medida = (int) Math.round((radioCircunferencia + BORDE_SVG + radioVertice) * 2);
        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(medida, medida) +
               aristasSVG +
               verticesSVG +
               GraficadorSVG.terminaSVG();
    }

    private Coord getCoordenada(VerticeGrafica<T> vertice, Lista<Coord> coordenadas) {
        for (Coord coord : coordenadas)
            if (coord.elemento.equals(vertice.get()))
                return coord;

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

        return maximo * TAMANO_FUENTE + BORDE_VERTICE;
    }

    /**
     * Método que genera la cadena de texto que representa un nodo con el
     * elemento recibido. Utiliza las medidas recibidas.
     */
    protected String graficaNodo(T elemento, int origenX, int origenY, int radio) {
        // Un nodo es simplemente un rectángulo que contiene el elemento.
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
