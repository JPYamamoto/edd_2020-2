package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorEstructura;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorArbolAVL;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorArbolBinarioCompleto;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorArbolBinarioOrdenado;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorArbolRojinegro;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorCola;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorPila;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorLista;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorGrafica;
import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorMonticuloMinimo;

/**
 * Proyecto 2.
 */
public class Proyecto2 {

    /** Punto de entrada del programa. */
    public static void main(String[] args) {
        // El programa debe recibir a lo más 1 argumento.
        if (args.length > 1) {
            System.out.println("\nArgumentos incorrectos.\n" +
                               "Uso: java -jar proyecto2.jar <archivo>");
            System.exit(1);
        }

        // Abrimos la entrada.
        String archivo = null;
        if (args.length == 1)
            archivo = args[0];

        BufferedReader flujoEntrada = Entrada.abrirEntrada(archivo);
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
            case GRAFICA:
                graficador = new GraficadorGrafica<>(construyeGrafica(datos));
                break;
            case MONTICULO_MINIMO:
                graficador = new GraficadorMonticuloMinimo<>(datos);
                break;
            // El siguiente caso nunca se alcanzará.
            default:
                graficador = null;
        }

        // Graficamos e imprimimos.
        System.out.println(graficador.graficar());
    }

    /**
     * Genera la gráfica constituida por los vértices recibidos y con aristas
     * entre cada par de elementos. Si el número de elementos no es par,
     * imprime un error y termina la ejecución del programa.
     * @param datos los vértices a graficar con aristas entre cada par.
     * @return la gráfica que se forma a partir de los datos recibidos.
     */
    private static <T> Grafica<T> construyeGrafica(Lista<T> datos) {
        // Si el número de elementos no es par, terminamos.
        if (datos.getElementos() % 2 != 0) {
            System.out.println("El número de elementos para crear la gráfica debe ser par.");
            System.exit(1);
        }

        Grafica<T> grafica = new Grafica<>();

        // Agregamos los vértices a la gráfica.
        for (T vertice : datos)
            if (!grafica.contiene(vertice))
                grafica.agrega(vertice);

        // Creamos las aristas de la gráfica.
        int i = 0;
        T verticeAnterior = null;
        for (T vertice : datos)
            if (i++ % 2 == 0)
                verticeAnterior = vertice;
            else if (verticeAnterior.equals(vertice))
                continue;
            else if (!grafica.sonVecinos(verticeAnterior, vertice))
                grafica.conecta(verticeAnterior, vertice);

        return grafica;
    }
}
