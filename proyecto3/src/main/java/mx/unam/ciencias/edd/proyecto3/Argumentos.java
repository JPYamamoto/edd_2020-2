package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;

import java.util.NoSuchElementException;

/**
 * Clase que utilizamos para encapsular los métodos correspondientes al
 * procesamiento de los argumentos que recibe el programa.
 */
public class Argumentos {

    // Los argumentos que recibe el programa.
    private String[] argumentos;
    // La bandera que recibimos antes del nombre del directorio.
    final static String BANDERA_DIRECTORIO = "-o";

    /**
     * Constructor que recibe los parámetros del programa.
     * @param args los argumentos del programa.
     */
    public Argumentos(String[] args) {
        argumentos = args;
    }

    /**
     * Leer los argumentos e identificar el directorio donde se escribe la
     * salida.
     * @return el nombre del directorio de salida.
     */
    public String getDirectorioSalida() {
        for (int i = 0; i < argumentos.length; i++)
            if (argumentos[i].equals(BANDERA_DIRECTORIO))
                // Verifico que haya un elemento después de la bandera, y lo
                // regreso. De otra manera, lanzamos excepción.
                if (argumentos.length > i+1)
                    return argumentos[i+1];
                else
                    throw new IllegalArgumentException("Bandera -o debe ir acompañada de un directorio.");

        // Excepción por no recibir la bandera.
        throw new NoSuchElementException("No se recibió la bandera -o.");
    }

    /**
     * Identifica los archivos que leemos como entrada.
     * @return una lista con los nombres de los archivos de entrada.
     */
    public Lista<String> getArchivosEntrada() {
        Lista<String> archivosEntrada = new Lista<String>();

        for (int i = 0; i < argumentos.length; i++)
            if (argumentos[i].equals(BANDERA_DIRECTORIO))
                i++;
            else
                archivosEntrada.agrega(argumentos[i]);

        return archivosEntrada;
    }
}
