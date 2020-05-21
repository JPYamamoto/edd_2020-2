package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
            cola = new Cola<>();

            if (raiz != null)
                cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            Vertice vertice = cola.saca();

            if (vertice.izquierdo != null)
                cola.mete(vertice.izquierdo);

            if (vertice.derecho != null)
                cola.mete(vertice.derecho);

            return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento no válido.");

        Vertice nuevoVertice = nuevoVertice(elemento);
        elementos++;

        if (raiz == null) {
            raiz = nuevoVertice;
            return;
        }

        Vertice vertice = vertice(primerVerticeConHoyo());
        nuevoVertice.padre = vertice;

        if (vertice.izquierdo == null)
            vertice.izquierdo = nuevoVertice;
        else
            vertice.derecho = nuevoVertice;
    }

    /**
     * Obtiene el primer vertice que tiene un "hoyo". Es decir, el primer
     * vértice que no tiene hijo izquierdo o hijo derecho, por lo que debe ser
     * padre del siguiente vértice a agregar en un árbol binario completo.
     * La complejidad en tiempo de este algoritmo es O(log_2(n)) pues utiliza
     * el número de elementos para determinar el padre del que será el
     * siguiente elemento en ser añadido.
     * No utiliza multiplicaciones, divisiones ni métodos de la clase Math,
     * únicamente como ejercicio.
     * @return el primer vértice con hoyo.
     */
    private VerticeArbolBinario<T> primerVerticeConHoyo() {
        Pila<Boolean> recorrido = new Pila<>();
        Vertice vertice, temp;
        vertice = temp = raiz;
        int aux = elementos;

        while (aux > 1) {
            // n & 1 es equivalente a n % 2.
            recorrido.mete((aux & 1) == 0);
            // Desplazar una posición a la derecha es equivalente a dividir
            // entre 2 en binario.
            aux >>= 1;
        }

        while (temp != null) {
            vertice = temp;
            temp = recorrido.saca() ? temp.izquierdo : temp.derecho;
        }

        return vertice;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = vertice(busca(elemento));

        if (vertice == null)
            return;

        elementos--;

        if (elementos == 0) {
            raiz = null;
            return;
        }

        Vertice ultimoVertice = vertice(ultimoVerticeAgregado());
        vertice.elemento = ultimoVertice.elemento;

        if (ultimoVertice.padre.izquierdo == ultimoVertice)
            ultimoVertice.padre.izquierdo = null;
        else
            ultimoVertice.padre.derecho = null;
    }

    /**
     * Obtiene el último vértice que ha sido agregado al arbol binario
     * completo, utilizando la técnica BFS para visitar todos los vértices y
     * luego, regresar el que se encuentra al final de la cola.
     * Por lo anterior, la complejidad en tiempo es O(n).
     * @return el último vértice agregado.
     */
    private VerticeArbolBinario<T> ultimoVerticeAgregado() {
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        Vertice ultimoVertice = raiz;
        Vertice verticeActual;

        while (!cola.esVacia()) {
            verticeActual = cola.saca();
            ultimoVertice = verticeActual;

            if (verticeActual.izquierdo != null)
                cola.mete(verticeActual.izquierdo);

            if (verticeActual.derecho != null)
                cola.mete(verticeActual.derecho);
        }

        return ultimoVertice;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        if (elementos == 0)
            return -1;

        return (int) Math.floor(Math.log(elementos) / Math.log(2));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
            return;

        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);

        Vertice vertice;
        while (!cola.esVacia()) {
            vertice = cola.saca();
            accion.actua(vertice);

            if (vertice.izquierdo != null)
                cola.mete(vertice.izquierdo);

            if (vertice.derecho != null)
                cola.mete(vertice.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
