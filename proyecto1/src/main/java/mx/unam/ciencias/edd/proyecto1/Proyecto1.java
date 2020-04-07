package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.IOException;

public class Proyecto1 {

   /**
    * Imprime las líneas ya ordenadas o guarda las líneas ordenadas en la
    * salida, si el usuario ingresó un archivo de salida con la bandera -o
    */
    public static void regresaSalida(Lista<Linea> lineas, String salida) {
        if (salida == null)
            for (Linea linea : lineas)
                System.out.println(linea.toString());
        else
            try {
                EntradaSalida.guarda(salida, lineas);
            } catch(IOException ioe) {
                System.out.printf("\nHubo un error al intentar guardar la salida.");
                System.exit(1);
            }
    }

    /* Ordena las lineas. */
    private static Lista<Linea> ordena(Lista<Linea> lineas, boolean reversa) {
        return reversa ?
                OrdenadorLexicografico.ordenaReversa(lineas) :
                OrdenadorLexicografico.ordena(lineas);
    }

    /* Lee la(s) entrada(s) y regresa una lista con las líneas leídas. */
    private static Lista<Linea> leeLineas(Lista<String> fuentesEntrada) {
        Lista<BufferedReader> entradas = null;
        Lista<Linea> lineas = new Lista<>();

        try {
            entradas = EntradaSalida.getEntradas(fuentesEntrada);
            lineas = EntradaSalida.leeEntradas(entradas);
            EntradaSalida.cierraEntradas(entradas);
        } catch(IOException ioe) {
            EntradaSalida.cierraEntradas(entradas);
            System.out.println("Ocurrió un error al leer la entrada.");
            System.exit(1);
        }

        return lineas;
    }

    /* Punto de entrada del programa. */
    public static void main(String[] args) {
        // Analiza los argumentos recibidos.
        Argumentos argumentos = new Argumentos(args);
        boolean reversa = argumentos.banderaReversa();
        String archivoSalida = null;
        try {
            archivoSalida = argumentos.banderaGuarda();
        } catch(IllegalArgumentException iae) {
            System.out.println("\nLa bandera -o debe estar seguida de un " +
                               "archivo en el cual escribir.\n" +
                               "Uso: java -jar proyecto1.jar -o <archivo>");
            System.exit(1);
        }

        // Lee la entrada.
        Lista<Linea> lineas = leeLineas(argumentos.fuentesEntrada());
        // Ordena las líneas.
        Lista<Linea> ordenadas = ordena(lineas, reversa);
        // Da el resultado en la salida estándar o en un archivo.
        regresaSalida(ordenadas, archivoSalida);
    }
}
