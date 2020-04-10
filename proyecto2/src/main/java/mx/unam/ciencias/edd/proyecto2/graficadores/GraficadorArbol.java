package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase abstracta de la que heredan los graficadores de las estructuras de
 * datos que heredan de ArbolBinario. De esta manera podemos sobreescribir solo
 * las partes específicas a cada estructura de árbol.
 */
public abstract class GraficadorArbol<T> implements GraficadorEstructura<T> {

    // Las siguientes variables las utilizamos como constantes, más no son
    // finales pues una subclase debe poder modificar su valor, aunque solo
    // debe hacerlo en el constructor.

    // Tamaño de la fuente que se utilizará en el contenido de los vértices.
    protected int TAMANO_FUENTE;
    // Tamaño del borde alrededor de la gráfica SVG.
    protected int BORDE_SVG;
    // Tamaño del borde entre el contenido y la orilla del vértice.
    protected int BORDE_VERTICE;
    // El desplazamiento en el eje X de un vértice con respecto a su padre.
    protected int CAMBIO_X_CONEXION;
    // El desplazamiento en el eje Y de un vértice con respecto a su padre.
    protected int CAMBIO_Y_CONEXION;

    // El arbol a graficar.
    ArbolBinario<T> arbol;

    /**
     * Clase interna privada que utilizamos únicamente por conveniencia al
     * momento de guardar las coordenadas de un vértice, para poder crear la
     * arista con sus vecinos.
     */
    private class Coord {
        public int x;
        public int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * El constructor del graficador. Asignamos el árbol recibido a una variable
     * de clase que nos permitirá acceder al árbol cuando sea necesario. Cada
     * subclase debe implementar esto con el tipo correspondiente. Además,
     * inicializamos los valores de algunas constantes.
     * @param coleccion la colección con los datos que contendrá el árbol.
     */
    public GraficadorArbol(ArbolBinario<T> arbol) {
        this.arbol = arbol;

        TAMANO_FUENTE = 20;
        BORDE_SVG = 10;
        BORDE_VERTICE = 10;
        CAMBIO_X_CONEXION = 30;
        CAMBIO_Y_CONEXION = 50;
    }

    /**
     * Obtén la cadena de texto de la gráfica SVG que corresponde a la
     * estructura de datos.
     * La complejidad en tiempo, considerando la implementación actual de los
     * métodos auxiliares, es O(n). La complejidad en espacio también es O(n)
     * pues solo simulamos la pila de ejecución, y guardamos algunos otros
     * valores que solo incrementan la constante oculta.
     * @return el SVG de la estructura de datos.
     */
    public String graficar() {
        // Utilizamos 3 pilas para simular los parámetros que podríamos pasar
        // en un método recursivo. Es la única forma de hacerlo iterativo.
        // En este caso es conveniente, pues en un método recursivo no podemos
        // regresar varios valores. Al hacerlo iterativo, podemos modificar
        // todas las variables locales que necesitemos.
        Pila<VerticeArbolBinario<T>> pilaVertices = new Pila<>();
        Pila<Integer> pilaNivel = new Pila<>();
        Pila<Coord> pilaCoords = new Pila<>();

        // Utilizamos el recorrido DFS in order.
        VerticeArbolBinario<T> vertice = arbol.esVacia() ? null : arbol.raiz();
        meteRamaIzquierda(pilaVertices, vertice, pilaNivel, 0);

        // Generamos una cadena de vértices y otra de aristas para poder poner
        // una debajo de la otra en el resultado. De manera que no hayan
        // aristas que se sobrepongan a los vértices.
        String vertices = "";
        String aristas = "";

        // Algunas medidas que necesitamos.
        int radio = calculaRadioVertices();
        int alturaSVG = arbol.altura() * (CAMBIO_Y_CONEXION + (radio * 2)) + (radio * 2) + (BORDE_SVG * 2);
        int anchoSVG = BORDE_SVG;

        // El desplazamiento después de la inserción de cada vértice.
        int cambioAltura = radio * 2 + CAMBIO_Y_CONEXION;
        int cambioAncho = radio * 2 + CAMBIO_X_CONEXION;

        while (!pilaVertices.esVacia()) {
            // Usamos las pilas porque realizamos el algoritmo de manera
            // iterativa.
            vertice = pilaVertices.saca();
            int nivel = pilaNivel.saca();

            // Obtenemos las coordenadas X y Y que corresponden al nuevo
            // vértice para graficarlo.
            int coordX = anchoSVG + radio;
            int coordY = BORDE_SVG + nivel * cambioAltura + radio;

            // Graficamos el vértice y modificamos el desplazamiento en el eje
            // X que tendrá el siguiente vértice.
            vertices += graficaVertice(vertice, coordX, coordY, radio);
            anchoSVG += cambioAncho;

            // Parte del algoritmo de recorrido in order.
            if (vertice.hayDerecho())
                meteRamaIzquierda(pilaVertices, vertice.derecho(), pilaNivel, nivel + 1);

            // Generamos las aristas que corresponden al vértice actual, con
            // los vértices vecinos ya existentes.
            aristas += conectaVertices(pilaCoords, vertice, coordX, coordY);
        }

        // Agrega la declaración XML, la etiqueta de apertura SVG con las
        // medidas del gráfico, las aristas del árbol, los vértices sobre
        // ellas y la etiqueta de cierre.
        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(anchoSVG - CAMBIO_X_CONEXION + BORDE_SVG, alturaSVG) +
               aristas +
               vertices +
               GraficadorSVG.terminaSVG();
    }

    /**
     * Método que conecta un vértice con sus hijos o padre según corresponda.
     * Las conexiones se realizan de izquierda a derecha (in order) cuando los
     * dos vértices a unir ya han sido graficados.
     */
    private String conectaVertices(Pila<Coord> pila, VerticeArbolBinario<T> vertice,
                                   int coordX, int coordY) {
        String aristas = "";

        // Cuando hay un hijo izquierdo, dado que es in order sabemos que lo
        // graficamos inmediatamente antes, por lo tanto, los unimos.
        if (vertice.hayIzquierdo()) {
            Coord hijoI = pila.saca();
            aristas += graficaConexion(hijoI.x, hijoI.y, coordX, coordY);
        }

        // Cuando un vértice es hijo derecho de otro, sabemos que graficamos al
        // padre inmediatamente antes (solo siendo precedido probablemente por
        // el hijo izquierdo propio cuyo caso ya cubrimos), por lo que los
        // unimos.
        if (esDerecho(vertice)) {
            Coord hijoI = pila.saca();
            aristas += graficaConexion(hijoI.x, hijoI.y, coordX, coordY);
        }

        // Los siguientes casos deben suceder hasta que ya se graficaron las
        // aristas que corresponden al vértice actual.

        // Si el vértice tiene hijo derecho, ese se encarga de realizar la
        // gráfica de la arista, por lo que guardamos nuestras coordenadas.
        if (vertice.hayDerecho())
            pila.mete(new Coord(coordX, coordY));

        // Si el vértice es izquierdo de otro, este debe ser graficado
        // inmediatamente después, por lo que guardamos nuestras coordenadas
        // hasta arriba en la pila.
        if (esIzquierdo(vertice))
            pila.mete(new Coord(coordX, coordY));

        return aristas;
    }

    /**
     * Nos dice si un vértice es izquierdo de otro.
     * @param vertice el vértice que queremos saber si es izquierdo.
     * @return true si vertice es hijo izquierdo de su padre, false en otro
     * caso.
     */
    protected final boolean esIzquierdo(VerticeArbolBinario<T> vertice) {
        if (vertice == arbol.raiz())
            return false;

        if (!vertice.padre().hayIzquierdo())
            return false;

        return vertice.padre().izquierdo() == vertice;
    }

    /**
     * Nos dice si un vértice es derecho de otro.
     * @param vertice el vértice que queremos saber si es derecho.
     * @return true si vertice es hijo derecho de su padre, false en otro
     * caso.
     */
    protected final boolean esDerecho(VerticeArbolBinario<T> vertice) {
        if (vertice == arbol.raiz())
            return false;

        if (!vertice.padre().hayDerecho())
            return false;

        return vertice.padre().derecho() == vertice;
    }

    /**
     * Guardamos un vértice en una pila y toda su rama izquierda. Además,
     * guardamos el nivel en el que cada vértice se ubica. Esto nos sirve para
     * simular los múltiples parámetros que podemos pasar a un método recursivo
     * si utilizamos la pila de ejecución, en vez de un método iterativo.
     */
    private void meteRamaIzquierda(Pila<VerticeArbolBinario<T>> pilaVertices,
            VerticeArbolBinario<T> vertice, Pila<Integer> pilaNivel, int nivel) {

        if (vertice == null)
            return;

        VerticeArbolBinario<T> verticeAux = vertice;
        int nivelAux = nivel;

        pilaVertices.mete(verticeAux);
        pilaNivel.mete(nivelAux);

        // Cada vez que guardamos un vértice en la pila, guardamos también su
        // nivel.
        while (verticeAux.hayIzquierdo()) {
            verticeAux = verticeAux.izquierdo();
            pilaVertices.mete(verticeAux);
            pilaNivel.mete(++nivelAux);
        }
    }

    /**
     * Genera la cadena de texto que representa en SVG el vértice recibido,
     * utilizando los parámetros recibidos.
     * @param vertice el vértice a graficar.
     * @param centroX la coordenada X del centro del vértice.
     * @param centroY la coordenada Y del centro del vértice.
     * @param radio el radio del vértice.
     * @return la cadena de texto del SVG que representa al vértice.
     */
    protected String graficaVertice(VerticeArbolBinario<T> vertice, int centroX,
                                    int centroY, int radio) {
        return GraficadorSVG.graficaCirculoTexto(centroX, centroY, radio,
                "black", "white", TAMANO_FUENTE, "black", vertice.get().toString());
    }

    /**
     * Genera la cadena de texto con el SVG que representa la unión entre dos
     * vértices del árbol.
     * @param centroX1 la coordenada X del centro del vértice 1.
     * @param centroY1 la coordenada Y del centro del vértice 1.
     * @param centroX2 la coordenada X del centro del vértice 2.
     * @param centroY2 la coordenada Y del centro del vértice 2.
     * @return la cadena de texto con el SVG de la arista entre ambos vértices.
     */
    protected String graficaConexion(int centroX1, int centroY1, int centroX2, int centroY2) {
        return GraficadorSVG.graficaLinea(centroX1, centroY1,
                centroX2 - centroX1, centroY2 - centroY1, "black");
    }

    /**
     * Calcula la medida del radio de cada vértice, a partir del elemento cuya
     * representación en cadena sea más larga.
     * @return el radio de los vértices.
     */
    protected int calculaRadioVertices() {
        int medidaTexto = longitudMaxima(arbol.raiz()) * TAMANO_FUENTE;
        int radio = (int) Math.ceil(medidaTexto / 2);
        return radio + BORDE_VERTICE;
    }

    /**
     * Nos indica el elemento con la representación en cadena más larga en el
     * subárbol con el vértice recibido como la raíz.
     * No es la forma más óptima de obtener la longitud máxima con los enteros,
     * pues sabemos que el entero más grande siempre es el que tiene la
     * representación en cadena más larga, y no necesitamos convertirlo con
     * toString(), pero lo hacemos con la idea de que nuestro graficador sea
     * genérico.
     * @param vertice el vértice que representa la raíz de un subárbol.
     * @return el elemento mayor en el subárbol.
     */
    protected int longitudMaxima(VerticeArbolBinario<T> vertice) {
        int maximo = vertice.get().toString().length();

        if (vertice.hayIzquierdo()) {
            int maximoIzquierdo = longitudMaxima(vertice.izquierdo());
            maximo = maximo - maximoIzquierdo <= 0 ? maximoIzquierdo : maximo;
        }

        if (vertice.hayDerecho()) {
            int maximoDerecho = longitudMaxima(vertice.derecho());
            maximo = maximo - maximoDerecho <= 0 ? maximoDerecho : maximo;
        }

        return maximo;
    }
}
