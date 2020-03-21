package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;

public class EntradaSalida {

    private EntradaSalida() {  }

    /* Recibe una lista de archivos y regresa una lista con el BufferedReader
     * correspondiente a cada archivo.
     * Cuando no hay archivos, regresa un BufferedReader que se crea a partir
     * de la entrada estándar.
     */
    public static Lista<BufferedReader> getEntradas(Lista<String> archivos) throws IOException {
        Lista<BufferedReader> entradas = new Lista<BufferedReader>();

        if (archivos.esVacia()) {
            entradas.agregaFinal(new BufferedReader(
                                    new InputStreamReader(System.in)));
            return entradas;
        }

        for (String archivo : archivos) {
            try {
                BufferedReader entrada = new BufferedReader(
                                            new InputStreamReader(
                                                new FileInputStream(archivo)));
                entradas.agregaFinal(entrada);
            } catch (IOException ioe) {
                cierraEntradas(entradas);
                throw new IOException("Error al abrir el flujo para leer las entradas.");
            }
        }

        return entradas;
    }

    /* Regresa una lista con las líneas de todas las entradas que recibe. */
    public static Lista<Linea> leeEntradas(Lista<BufferedReader> entradas) throws IOException {
        Lista<Linea> lineas = new Lista<Linea>();

        String linea;
        for (BufferedReader entrada : entradas)
            while ((linea = entrada.readLine()) != null)
                lineas.agregaFinal(new Linea(linea));

        return lineas;
    }

    /* Cierra todos los BufferedReader de entradas. */
    public static void cierraEntradas(Lista<BufferedReader> entradas) {
        if (entradas == null)
            return;

        for (BufferedReader entrada : entradas)
            try {
                entrada.close();
            } catch(IOException ioe) { /* Ya no hay nada que hacer. */ }
    }


    /* Guarda en un archivo, las lineas de la lista recibida. */
    public static void guarda(String archivo, Lista<Linea> lineas) throws IOException {
        BufferedWriter out = new BufferedWriter(
                                new OutputStreamWriter(
                                    new FileOutputStream(archivo)));

        for (Linea linea : lineas)
            out.write(linea.toString() + "\n");

        out.close();
    }
}
