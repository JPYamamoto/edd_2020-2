package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;

import java.util.NoSuchElementException;

public class Argumentos {

    String[] argumentos;
    final static String BANDERA_DIRECTORIO = "-o";

    public Argumentos(String[] args) {
        argumentos = args;
    }

    // Leer los argumentos y asignar las variables correspondientes.
    public String getDirectorioSalida() {
        for (int i = 0; i < argumentos.length; i++)
            if (argumentos[i].equals(BANDERA_DIRECTORIO))
                // Verifico que haya un argumento después de la bandera -o. Ese
                // argumento lo considero como el archivo en donde escribir la
                // salida. Aumenta i, para no volver a evaluar al archivo
                // como bandera.
                if (argumentos.length > i+1)
                    return argumentos[i+1];
                else
                    throw new IllegalArgumentException("Bandera -o debe ir acompañada de un directorio.");

        throw new NoSuchElementException("No se recibió la bandera -o.");
    }

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
