package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.proyecto2.graficadores.GraficadorSVG;

/**
 * Proyecto 2.
 */
public class Proyecto2 {

    /** Punto de entrada del programa. */
    public static void main(String[] args) {
        GraficadorSVG<Integer> graficador = Entrada.procesaEntrada(args);
        System.out.println(graficador.graficar());
    }
}
