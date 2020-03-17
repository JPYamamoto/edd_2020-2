package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;

public class Proyecto1 {

    /* Recibe una lista de archivos y regresa una lista con el BufferedReader
     * correspondiente a cada archivo.
     * Cuando no hay archivos, regresa un BufferedReader que se crea a partir
     * de la entrada estándar.
     */
    public static Lista<BufferedReader> getEntradas(Lista<String> archivos) {
        Lista<BufferedReader> entradas = new Lista<BufferedReader>();

        if (archivos.esVacia()) {
            entradas.agregaFinal(new BufferedReader(
                                    new InputStreamReader(System.in)));
            return entradas;
        }

        for (String archivo : archivos)
            try {
                BufferedReader entrada = new BufferedReader(
                                            new InputStreamReader(
                                                new FileInputStream(archivo)));
                entradas.agregaFinal(entrada);
            } catch (IOException e) {
                System.out.printf("\nHubo un error al intentar leer el " +
                                  "archivo %s\n", archivo);
                System.exit(1);
            }

        return entradas;
    }

    /* Regresa una lista con las líneas de todas las entradas que recibe. */
    public static Lista<Linea> leeEntradas(Lista<BufferedReader> entradas) {
        Lista<Linea> lineas = new Lista<Linea>();

        String linea;
        for (BufferedReader entrada : entradas)
            try {
                while ((linea = entrada.readLine()) != null)
                    lineas.agregaFinal(new Linea(linea));
            } catch(IOException e) {
                System.out.println(e);
                System.out.println("\nHubo un error al leer la entrada.");
                cierraEntradas(entradas);
                System.exit(1);
            }

        return lineas;
    }

    /* Cierra todos los BufferedReader de entradas. */
    public static void cierraEntradas(Lista<BufferedReader> entradas) {
        for (BufferedReader entrada : entradas)
            try {
                entrada.close();
            } catch(IOException e) {
                System.out.println("\nHubo un error al cerrar los archivos.");
                System.exit(1);
            }
    }


    /* Guarda en un archivo, las lineas de la lista recibida. */
    public static void guarda(String archivo, Lista<Linea> lineas) {
        try {
            BufferedWriter out = new BufferedWriter(
                                    new OutputStreamWriter(
                                        new FileOutputStream(archivo)));

            for (Linea linea : lineas)
                out.write(linea.toString() + "\n");

            out.close();
        } catch (IOException e) {
            System.out.printf("\nHubo un error al intentar guardar el archivo" +
                              " %s\n", archivo);
            System.exit(1);
        }
    }

    /* Punto de entrada del programa. */
    public static void main(String[] args) {
        // Indica si recibimos la bandera -r.
        boolean reversa = false;
        // El archivo en donde escribir la salida al recibir la bandera -o.
        String archivoSalida = null;
        // Los archivos a usar como entrada, recibidos en los argumentos.
        Lista<String> archivosEntrada = new Lista<String>();

        // Leer los argumentos y asignar las variables correspondientes.
        for (int i = 0; i < args.length; i++)
            if (args[i].equals("-r"))
                reversa = true;
            else if (args[i].equals("-o"))
                // Verifico que haya un argumento después de la bandera -o. Ese
                // argumento lo considero como el archivo en donde escribir la
                // salida. Aumenta i, para no volver a evaluar al archivo
                // como bandera.
                if (args.length > i+1) {
                    archivoSalida = args[(i++)+1];
                } else {
                    System.out.println("\nLa bandera -o debe estar seguida de" +
                                       " un archivo en el cual escribir.\n" +
                                       "Uso: java -jar proyecto2.jar -o " +
                                       "<archivo>");
                    System.exit(1);
                }
            else
                archivosEntrada.agregaFinal(args[i]);

        // Entradas de donde leer.
        Lista<BufferedReader> entradas = getEntradas(archivosEntrada);
        // Lista con las lineas de todas las entradas, para ser ordenadas.
        Lista<Linea> lineas = leeEntradas(entradas);
        // Cierra las entradas.
        cierraEntradas(entradas);

        // Ordenamiento de las líneas. Si reversa es true, ordena en reversa.
        Lista<Linea> ordenadas = reversa ?
            OrdenadorLexicografico.ordenaReversa(lineas) :
            OrdenadorLexicografico.ordena(lineas);

        // Imprime las líneas ya ordenadas o guarda las líneas ordenadas en la
        // salida, si el usuario ingresó un archivo de salida con la bandera -o
        if (archivoSalida == null)
            for (Linea linea : ordenadas)
                System.out.println(linea.toString());
        else
            guarda(archivoSalida, ordenadas);
    }
}
