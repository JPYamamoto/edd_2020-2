package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase abstracta de la que heredan los graficadores de las estructuras de
 * datos que heredan de ArbolBinario. Solo es necesario implementar los métodos
 * con las partes específicas a cada estructura de árbol.
 */
public abstract class GraficadorArbol<T extends Comparable<T>> implements GraficadorEstructura<T> {

    protected int TAMANO_FUENTE = 20;
    protected int BORDE_SVG = 10;
    protected int BORDE_VERTICE = 10;
    protected int CAMBIO_X_CONEXION = 30;
    protected int CAMBIO_Y_CONEXION = 50;

    private class Coord {
        public int x;
        public int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // El arbol a graficar.
    ArbolBinario<T> arbol;

    /**
     * El constructor del graficador. Simplemente asignamos el árbol que se
     * construye a partir de los datos en la colección recibida, a una variable
     * de clase que nos permitirá acceder al árbol cuando sea necesario.
     * @param coleccion la colección con los datos que contendrá el árbol.
     */
    public GraficadorArbol(ArbolBinario<T> arbol) {
        this.arbol = arbol;
    }

    public String graficar() {
        Pila<VerticeArbolBinario<T>> pilaVertices = new Pila<>();
        Pila<Integer> pilaNivel = new Pila<>();
        Pila<Coord> pilaCoords = new Pila<>();

        VerticeArbolBinario<T> vertice = arbol.esVacia() ? null : arbol.raiz();
        meteRamaIzquierda(pilaVertices, vertice, pilaNivel, 0);

        String vertices = "";
        String aristas = "";

        int radio = calculaRadioVertices();
        int alturaSVG = arbol.altura() * (CAMBIO_Y_CONEXION + (radio * 2)) + (radio * 2) + (BORDE_SVG * 2);
        int anchoSVG = BORDE_SVG;

        int cambioAltura = radio * 2 + CAMBIO_Y_CONEXION;
        int cambioAncho = radio * 2 + CAMBIO_X_CONEXION;

        while (!pilaVertices.esVacia()) {
            vertice = pilaVertices.saca();
            int nivel = pilaNivel.saca();
            int coordX = anchoSVG + radio;
            int coordY = BORDE_SVG + nivel * cambioAltura + radio;

            vertices += graficaVertice(vertice, coordX, coordY, radio);
            anchoSVG += cambioAncho;

            if (vertice.hayIzquierdo()) {
                Coord hijoI = pilaCoords.saca();
                aristas += graficaConexion(hijoI.x, hijoI.y, coordX, coordY);
            }

            if (esDerecho(vertice)) {
                Coord hijoI = pilaCoords.saca();
                aristas += graficaConexion(hijoI.x, hijoI.y, coordX, coordY);
            }

            if (vertice.hayDerecho()) {
                meteRamaIzquierda(pilaVertices, vertice.derecho(), pilaNivel, nivel + 1);
                pilaCoords.mete(new Coord(coordX, coordY));
            }

            if (esIzquierdo(vertice))
                pilaCoords.mete(new Coord(coordX, coordY));
        }

        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(anchoSVG + BORDE_SVG, alturaSVG) +
               aristas +
               vertices +
               GraficadorSVG.terminaSVG();
    }

    protected final boolean esIzquierdo(VerticeArbolBinario<T> vertice) {
        if (vertice == arbol.raiz())
            return false;

        if (!vertice.padre().hayIzquierdo())
            return false;

        return vertice.padre().izquierdo() == vertice;
    }

    protected final boolean esDerecho(VerticeArbolBinario<T> vertice) {
        if (vertice == arbol.raiz())
            return false;

        if (!vertice.padre().hayDerecho())
            return false;

        return vertice.padre().derecho() == vertice;
    }

    private void meteRamaIzquierda(Pila<VerticeArbolBinario<T>> pilaVertices,
            VerticeArbolBinario<T> vertice, Pila<Integer> pilaNivel, int nivel) {

        if (vertice == null)
            return;

        VerticeArbolBinario<T> verticeAux = vertice;
        int nivelAux = nivel;

        pilaVertices.mete(verticeAux);
        pilaNivel.mete(nivelAux);

        while (verticeAux.hayIzquierdo()) {
            verticeAux = verticeAux.izquierdo();
            pilaVertices.mete(verticeAux);
            pilaNivel.mete(++nivelAux);
        }
    }

    protected int calculaRadioVertices() {
        int medidaTexto = maximoEnSubarbol(arbol.raiz()).toString().length() * TAMANO_FUENTE;
        int radio = (int) Math.ceil(medidaTexto / 2);
        return radio + BORDE_VERTICE;
    }

    protected String graficaVertice(VerticeArbolBinario<T> vertice, int centroX,
                                    int centroY, int radio) {
        return GraficadorSVG.graficaCirculoTexto(centroX, centroY, radio,
                "black", "white", TAMANO_FUENTE, "black", vertice.get().toString());
    }

    protected String graficaConexion(int centroX1, int centroY1, int centroX2, int centroY2) {
        return GraficadorSVG.graficaLinea(centroX1, centroY1,
                centroX2 - centroX1, centroY2 - centroY1, "black");
    }

    protected abstract T maximoEnSubarbol(VerticeArbolBinario<T> vertice);
}
