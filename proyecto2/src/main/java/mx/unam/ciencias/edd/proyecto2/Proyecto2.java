package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;

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
 * Proyecto 2.
 */
public class Proyecto2 {

    /** Punto de entrada del programa. */
    public static void main(String[] args) {
        BufferedReader flujoEntrada = Entrada.abrirEntrada(args);
        // Identificamos la estructura de datos que recibimos.
        Estructura estructuraDeDatos = Entrada.identificaEstructura(flujoEntrada);

        // Si la estructura de datos no es válida, terminamos limpiamente.
        if (estructuraDeDatos == null || estructuraDeDatos == Estructura.INVALIDO) {
            Entrada.cierraEntrada(flujoEntrada);
            System.out.println("No se ingresó el nombre de una estructura de " +
                               "datos válida al comienzo de la entrada.");
            System.exit(1);
        }

        // Leemos los datos que va a contener la estructura de datos.
        Lista<Integer> datos = Entrada.leerEntrada(flujoEntrada);
        Entrada.cierraEntrada(flujoEntrada);

        // Obtenemos el graficador correcto, según la estructura recibida.
        GraficadorEstructura<Integer> graficador;

        switch (estructuraDeDatos) {
            case ARBOL_AVL:
                graficador = new GraficadorArbolAVL<>(new ArbolAVL<>(datos));
                break;
            case ARBOL_BINARIO_COMPLETO:
                graficador = new GraficadorArbolBinarioCompleto<>(new ArbolBinarioCompleto<>(datos));
                break;
            case ARBOL_BINARIO_ORDENADO:
                graficador = new GraficadorArbolBinarioOrdenado<>(new ArbolBinarioOrdenado<>(datos));
                break;
            case ARBOL_ROJINEGRO:
                graficador = new GraficadorArbolRojinegro<>(new ArbolRojinegro<>(datos));
                break;
            case COLA:
                graficador = new GraficadorCola<>(datos);
                break;
            case PILA:
                graficador = new GraficadorPila<>(datos);
                break;
            case LISTA:
                graficador = new GraficadorLista<>(datos);
                break;
            // El siguiente caso nunca se alcanzará.
            default:
                graficador = null;
        }

        // Graficamos e imprimimos.
        System.out.println(graficador.graficar());
    }
}
