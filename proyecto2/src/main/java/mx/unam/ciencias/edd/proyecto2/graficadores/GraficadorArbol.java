package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase abstracta de la que heredan los graficadores de las estructuras de
 * datos que heredan de ArbolBinario. Solo es necesario implementar los métodos
 * con las partes específicas a cada estructura de árbol.
 */
public abstract class GraficadorArbol<T extends Comparable<T>> implements GraficadorEstructura<T> {

    protected final int TAMANO_FUENTE = 20;
    protected final int BORDE = 10;

    // El arbol a graficar.
    ArbolBinario<T> arbol;

    /**
     * El constructor del graficador. Simplemente asignamos el árbol que se
     * construye a partir de los datos en la colección recibida, a una variable
     * de clase que nos permitirá acceder al árbol cuando sea necesario.
     * @param coleccion la colección con los datos que contendrá el árbol.
     */
    public GraficadorArbol(Coleccion<T> coleccion) {
        arbol = creaArbol(coleccion);
    }

    public String graficar() {
        Pila<VerticeArbolBinario<T>> pila = new Pila<>();
        VerticeArbolBinario<T> vertice = arbol.esVacia() ? null : arbol.raiz();
        meteRamaIzquierda(pila, vertice);

        while (!pila.esVacia()) {
            vertice = pila.saca();
            // Realiza acciones necesarias.

            vertice = vertice.hayDerecho() ? vertice.derecho() : null;
            meteRamaIzquierda(pila, vertice);
        }
    }

    private void meteRamaIzquierda(Pila<VerticeArbolBinario<T>> pila, VerticeArbolBinario<T> vertice) {
        if (vertice == null)
            return;

        VerticeArbolBinario<T> verticeAux = vertice;
        pila.mete(verticeAux);

        while (verticeAux.hayIzquierdo()) {
            verticeAux = vertice.izquierdo();
            pila.mete(verticeAux);
        }
    }

    protected int calculaMedidaVertices() {
        return (maximoEnSubarbol(arbol.raiz()).toString().length() * TAMANO_FUENTE) + 2 * BORDE;
    }

    protected T maximoEnSubarbol(VerticeArbolBinario<T> vertice) {
        T maximo = vertice.get();

        if (vertice.hayIzquierdo()) {
            T maximoIzquierdo = maximoEnSubarbol(vertice.izquierdo());
            maximo = maximo.compareTo(maximoIzquierdo) <= 0 ? maximoIzquierdo : maximo;
        }

        if (vertice.hayDerecho()) {
            T maximoDerecho = maximoEnSubarbol(vertice.derecho());
            maximo = maximo.compareTo(maximoDerecho) <= 0 ? maximoDerecho : maximo;
        }

        return maximo;
    }

    protected String graficaVertice(T elemento, int centroX, int centroY, int radio) {
        return GraficadorSVG.graficaCirculoTexto(centroX, centroY, radio,
                "black", "white", TAMANO_FUENTE, "black", elemento.toString());
    }

    protected String graficaConexion(T elemento, int centroX1, int centroY1, int centroX2, int centroY2) {
        return GraficadorSVG.graficaLinea(centroX1, centroY1,
                centroX2 - centroX1, centroY2 - centroY1, "black");
    }

    /**
     * Método que sirve internamente para regresar una instancia de la clase
     * concreta del árbol y a la que corresponde el graficador.
     */
    protected abstract ArbolBinario<T> creaArbol(Coleccion<T> coleccion);
}
