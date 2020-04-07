package mx.unam.ciencias.edd.proyecto2.graficadores;

import java.util.Iterator;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase concreta para graficar la estructura de datos Lista.
 */
public class GraficadorLista<T extends Comparable<T>> extends GraficadorLineal<T> {

    // La lista a graficar.
    Lista<T> lista;

    /**
     * Constructor que recibe la lista que será graficada. Puesto que recibimos
     * la lista a graficar en el constructor, se necesita una instancia por
     * cada Lista que se desea graficar.
     * @param coleccion la lista a graficar.
     */
    public GraficadorLista(Lista<T> coleccion) {
        lista = coleccion;
    }

    /**
     * Sobreescribimos el método para generar la cadena de texto con el SVG que
     * representa la unión entre dos nodos de la estructura de datos, para que
     * utilice una doble flecha que representa la doble ligadura de la lista.
     */
    @Override
    protected String graficaConexion(int origenX, int origenY, int medidaX, int medidaY) {
        // Una doble flecha se comprende por 1 cuarta parte que ocupa la cabeza
        // izquierda de la flecha, 2 cuartas partes de la línea, y la cabeza
        // derecha ocupa otra cuarta parte de la longitud.
        int seccion = medidaX / 4;

        String conexion;
        conexion = GraficadorSVG.graficaTriangulo(origenX, origenY, seccion, medidaY, "black");
        conexion += GraficadorSVG.graficaLinea(origenX + seccion, (medidaY / 2) + origenY, medidaX - 2 * seccion, 0, "black");
        // Utilizamos -seccion para invertir la flecha sobre su eje x.
        conexion += GraficadorSVG.graficaTriangulo(origenX + medidaX, origenY, -seccion, medidaY, "black");

        return conexion;
    }

    /**
     * Regresa un iterador para iterar la estructura de datos. Necesitamos
     * implementarlo pues lo requiere la clase abstracta.
     */
    protected Iterator<T> getIterator() {
        return lista.iterator();
    }
}

