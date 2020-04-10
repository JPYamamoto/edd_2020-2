package mx.unam.ciencias.edd.proyecto2.graficadores;

import java.util.Iterator;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase concreta para graficar la estructura de datos Cola.
 */
public class GraficadorPila<T> extends GraficadorLineal<T> {

    /**
     * Utilizamos una lista para representar internamente los datos de la
     * estructura, puesto que podemos iterar una Lista sin modificarla. Lo
     * anterior no se puede con nuestra implementación de Pila.
     */
    Lista<T> coleccion;

    /**
     * Constructor que recibe la lista con los datos que contiene la Pila que
     * queremos graficar.
     * @param coleccion la lista con la información a graficar.
     */
    public GraficadorPila(Lista<T> coleccion) {
        this.coleccion = coleccion;
    }

    /**
     * Sobreescribimos este método a pesar de corresponder a una estructura de
     * datos lineal, porque deseamos tener una representación con los nodos uno
     * sobre el otro, con el primero en entrar a la pila hasta abajo.
     */
    public String graficar() {
        int anchoNodo = calculaAnchoNodos();
        String svg = "";
        int alturaSVG = BORDE + ALTURA_NODO * coleccion.getLongitud();

        for (T elemento : coleccion) {
            // Ajusta la posición en el eje Y y grafica el nodo.
            alturaSVG -= ALTURA_NODO;
            svg += graficaNodo(elemento, BORDE, alturaSVG, anchoNodo, ALTURA_NODO);
        }

        // Agrega la declaración XML, la etiqueta de apertura SVG con las
        // medidas del gráfico, el contenido del SVG y la etiqueta de cierre
        // del SVG. Regresa el resultado.
        return GraficadorSVG.declaracionXML() +
                GraficadorSVG.comienzaSVG(anchoNodo + 2 * BORDE, coleccion.getLongitud() * ALTURA_NODO + 2 * BORDE) +
                svg +
                GraficadorSVG.terminaSVG();
    }

    /**
     * Regresa un iterador para iterar la estructura de datos. Necesitamos
     * implementarlo pues lo requiere la clase abstracta.
     * No es necesario para esta estructura en específico, pero igual lo
     * implementamos.
     */
    protected Iterator<T> getIterator() {
        return coleccion.iterator();
    }
}

