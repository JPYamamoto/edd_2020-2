package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorEstructura;

/**
 * Proyecto 2.
 */
public class Proyecto2 {

    /** Punto de entrada del programa. */
    public static void main(String[] args) {
        // Procesamos la entrada y obtenemos el graficador adecuado.
        GraficadorEstructura<Integer> graficador = Entrada.procesaEntrada(args);
        // Graficamos la estructura de datos y la imprimimos.
        System.out.println(graficador.graficar());
    }
}
