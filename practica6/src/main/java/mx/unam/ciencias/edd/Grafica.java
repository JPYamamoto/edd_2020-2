package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            color = Color.NINGUNO;
            vecinos = new Lista<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento es <code>null</code> o ya
     *         había sido agregado a la gráfica.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("El elemento es nulo.");

        if (contiene(elemento))
            throw new IllegalArgumentException("El elemento ya se encuentra en los vértices.");

        Vertice vertice = new Vertice(elemento);
        vertices.agrega(vertice);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        if (a.equals(b))
            throw new IllegalArgumentException("Los elementos son iguales.");

        Vertice verticeA = (Vertice) vertice(a);
        Vertice verticeB = (Vertice) vertice(b);

        if (sonVecinosVertices(verticeA, verticeB))
            throw new IllegalArgumentException("Los elementos ya están conectados.");

        verticeA.vecinos.agrega(verticeB);
        verticeB.vecinos.agrega(verticeA);
        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        Vertice verticeA = (Vertice) vertice(a);
        Vertice verticeB = (Vertice) vertice(b);

        if (!sonVecinosVertices(verticeA, verticeB))
            throw new IllegalArgumentException("Los elementos no están conectados.");

        desconectaVertices(verticeA, verticeB);
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (Vertice vertice : vertices)
            if (vertice.elemento.equals(elemento))
                return true;

        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = (Vertice) vertice(elemento);

        for (Vertice vecino : vertice.vecinos)
            desconectaVertices(vertice, vecino);

        vertices.elimina(vertice);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        Vertice verticeA = (Vertice) vertice(a);
        Vertice verticeB = (Vertice) vertice(b);

        return sonVecinosVertices(verticeA, verticeB);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        for (Vertice vertice : vertices)
            if (vertice.elemento.equals(elemento))
                return vertice;

        throw new NoSuchElementException("El elemento no se encuentra en la gráfica.");
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice.getClass() != Vertice.class)
            throw new IllegalArgumentException("El vértice no es instancia de Vertice.");

        Vertice verticeAux = (Vertice) vertice;
        verticeAux.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        paraCadaVertice((v) -> setColor(v, Color.ROJO));

        Cola<Vertice> cola = new Cola<>();
        Vertice vertice = vertices.getPrimero();

        vertice.color = Color.NEGRO;
        cola.mete(vertice);

        while (!cola.esVacia()) {
            vertice = cola.saca();

            for (Vertice vecino : vertice.vecinos)
                if (vecino.color == Color.ROJO) {
                    vecino.color = Color.NEGRO;
                    cola.mete(vecino);
                }
        }

        boolean resultado = true;

        for (Vertice v : vertices)
            if (v.color == Color.ROJO) {
                resultado = false;
                break;
            }

        paraCadaVertice((v) -> setColor(v, Color.NINGUNO));

        return resultado;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice vertice : vertices)
            accion.actua(vertice);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        recorre(elemento, accion, new Cola<Vertice>());
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        recorre(elemento, accion, new Pila<Vertice>());
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        String texto = "{";

        for (Vertice vertice : vertices)
            texto += String.format("%s, ", vertice.elemento.toString());

        texto += "}, {";

        Lista<T> verticesPasados = new Lista<>();
        for (Vertice vertice : vertices) {
            for (Vertice vecino : vertice.vecinos)
                if (!verticesPasados.contiene(vecino.elemento))
                    texto += String.format("(%s, %s), ",
                        vertice.elemento.toString(), vecino.elemento.toString());

            verticesPasados.agrega(vertice.elemento);
        }

        texto += "}";
        return texto;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;

        // Si el número de aristas o de vértices es distinto, no son iguales y
        // terminamos.
        if (aristas != grafica.aristas ||
                vertices.getLongitud() != grafica.vertices.getLongitud())
            return false;

        for (Vertice vertice : vertices) {
            // Verificamos que los vértices en ambas gráficas sean los mismos.
            if (!grafica.contiene(vertice.elemento))
                return false;

            Vertice vertice2 = (Vertice) grafica.vertice(vertice.elemento);

            // Si el número de vecinos es distinto, terminamos. Al verificar
            // esto en O(1) podemos evitar el siguiente ciclo anidado.
            if (vertice.vecinos.getLongitud() != vertice2.vecinos.getLongitud())
                return false;

            // Vemos que los vértices sean los mismos.
            for (Vertice vecino1 : vertice.vecinos) {
                boolean contiene = false;

                // No podemos utilizar contiene(), pues las referencias a los
                // vértices pueden ser distintas, pero el elemento que
                // contienen igual. Una alternativa sería primero utilizar
                // vertice(vecino.elemento) y luego usar contiene, pero sería
                // recorrer dos listas.
                for (Vertice vecino2 : vertice2.vecinos)
                    if (vecino1.elemento.equals(vecino2.elemento)) {
                        contiene = true;
                        break;
                    }

                if (!contiene)
                    return false;
            }
        }

        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Desconectamos dos vértices que sabemos que existen por sus referencias.
     * Nos sirve para cuando necesitamos desconectar dos vértices cuyas
     * referencias tenemos, y evitamos volver a buscar los vértices invocando
     * directamente desconecta().
     * @param verticeA uno de los vértices a desconectar.
     * @param verticeB el otro de los vértices a desconectar.
     */
    private void desconectaVertices(Vertice verticeA, Vertice verticeB) {
        verticeA.vecinos.elimina(verticeB);
        verticeB.vecinos.elimina(verticeA);
        aristas--;
    }

    /**
    * Método que podemos utilizar cuando necesitamos saber si dos vértices
    * (que sabemos existen) son vecinos. Es conveniente cuando necesitamos
    * manipular los vértices, y saber si son vecinos, pues así evitamos
    * tener que buscar varias veces el vértice correspondiente al elemento.
    */
    private boolean sonVecinosVertices(Vertice verticeA, Vertice verticeB) {
        return verticeA.vecinos.contiene(verticeB) && verticeB.vecinos.contiene(verticeA);
    }

    /**
     * Realiza un recorrido a través de los vértices de la gráfica. Dependiendo
     * de la estructura recibida (Pila o Cola) será el tipo de recorrido (DFS o
     * BFS).
     * @param elemento el elemento por cuyo vértice comenzar el recorrido.
     * @param accion la acción a realizar en cada vértice.
     * @param estructura la estructura con la que realizamos el recorrido.
     */
    private void recorre(T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Vertice> estructura) {
        Vertice vertice = (Vertice) vertice(elemento);

        paraCadaVertice((v) -> setColor(v, Color.ROJO));

        vertice.color = Color.NEGRO;
        estructura.mete(vertice);

        while (!estructura.esVacia()) {
            vertice = estructura.saca();
            accion.actua(vertice);

            for (Vertice vecino : vertice.vecinos)
                if (vecino.color == Color.ROJO) {
                    vecino.color = Color.NEGRO;
                    estructura.mete(vecino);
                }
        }

        paraCadaVertice((v) -> setColor(v, Color.NINGUNO));
    }
}
