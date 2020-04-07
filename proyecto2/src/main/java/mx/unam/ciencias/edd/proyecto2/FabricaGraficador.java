package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorArbolAVL;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorArbolBinarioCompleto;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorArbolBinarioOrdenado;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorArbolRojinegro;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorCola;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorPila;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorLista;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorEstructura;

/**
 * Clase que funciona como intermediaria entre la lógica para graficar cada una
 * de las estructuras de datos, y la información que va a ser graficada.
 */
public class FabricaGraficador {

    /**
     * Constructor privado para asegurar que la clase se utiliza únicamente de
     * forma estática.
     */
    private FabricaGraficador() {}

    /**
     * Método que recibe una colección con los datos que contendrá la
     * estructura de datos a graficar y la enumeración que nos indica el tipo a
     * generar. Regresará un objeto que genera la gráfica en SVG.
     * @param <T> el tipo de dato que contendrá la estructura de datos.
     * @param datos los datos que contendrá la estructura de datos.
     * @param estructura la estructura de datos a representar.
     * @return el graficador que genera el SVG de la estructura de datos.
     */
    public static <T extends Comparable<T>> GraficadorEstructura<T>
    getGraficadorEstructura(Coleccion<T> datos, Estructura estructura) {
        // No podemos graficar una estructura inválida. Eso debe verificarse
        // antes de llamar este método.
        if (estructura == Estructura.INVALIDO)
            throw new IllegalArgumentException("La estructura de datos es inválida.");

        switch(estructura) {
            case ARBOL_AVL:
                return new GraficadorArbolAVL<T>(new ArbolAVL<>(datos));
            case ARBOL_BINARIO_COMPLETO:
                return new GraficadorArbolBinarioCompleto<T>(new ArbolBinarioCompleto<>(datos));
            case ARBOL_BINARIO_ORDENADO:
                return new GraficadorArbolBinarioOrdenado<T>(new ArbolBinarioOrdenado<>(datos));
            case ARBOL_ROJINEGRO:
                return new GraficadorArbolRojinegro<T>(new ArbolRojinegro<>(datos));
            case COLA:
                return new GraficadorCola<T>(toLista(datos));
            case PILA:
                return new GraficadorPila<T>(toLista(datos));
            case LISTA:
                return new GraficadorLista<T>(toLista(datos));
            // El siguiente caso nunca se alcanzará.
            default:
                return null;
        }
    }

    /**
     * Método que nos permite convertir una colección a una lista. Solo tiene
     * utilidad para preparar la entrada de los graficadores de las estructuras
     * lineales (Cola, Pila, Lista). Las demás no lo necesitan, pues ya
     * implementan un constructor que convierte una colección a la
     * representación interna correspondiente a su estructura de datos.
     */
    private static <T extends Comparable<T>> Lista<T> toLista(Coleccion<T> coleccion) {
        if (coleccion instanceof Lista)
            return (Lista<T>)coleccion;

        Lista<T> lista = new Lista<>();

        for (T elemento : coleccion)
            lista.agrega(elemento);

        return lista;
    }
}
