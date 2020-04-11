package mx.unam.ciencias.edd.proyecto2.graficadores;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase concreta para graficar la estructura de datos Cola.
 */
public class GraficadorPila<T> extends GraficadorLineal<T> {

    /**
     * Constructor que recibe la lista con los datos que contiene la Pila que
     * queremos graficar.
     * @param coleccion la lista con la información a graficar.
     */
    public GraficadorPila(Lista<T> coleccion) {
        super(coleccion);
    }

    /**
     * Sobreescribimos este método a pesar de corresponder a una estructura de
     * datos lineal, porque deseamos tener una representación con los nodos uno
     * sobre el otro, con el primero en entrar a la pila hasta abajo.
     */
    public String graficar() {
        Lista<T> coleccion = (Lista<T>) iterable;
        int anchoNodo = calculaAnchoNodos();
        String svg = "";
        int alturaSVG = BORDE + ALTURA_NODO * coleccion.getElementos();

        for (T elemento : iterable) {
            // Ajusta la posición en el eje Y y grafica el nodo.
            alturaSVG -= ALTURA_NODO;
            svg += graficaNodo(elemento, BORDE, alturaSVG, anchoNodo, ALTURA_NODO);
        }

        // Agrega la declaración XML, la etiqueta de apertura SVG con las
        // medidas del gráfico, el contenido del SVG y la etiqueta de cierre
        // del SVG. Regresa el resultado.
        return GraficadorSVG.declaracionXML() +
                GraficadorSVG.comienzaSVG(anchoNodo + 2 * BORDE,
                    coleccion.getLongitud() * ALTURA_NODO + 2 * BORDE) +
                svg +
                GraficadorSVG.terminaSVG();
    }
}

