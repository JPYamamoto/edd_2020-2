package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;

public class Argumentos {

    String[] argumentos;
    final static String BANDERA_GUARDA = "-o";
    final static String BANDERA_REVERSA = "-r";

    public Argumentos(String[] args) {
        argumentos = args;
    }

    public boolean banderaReversa() {
        for (String argumento: argumentos)
            if (argumento.equals(BANDERA_REVERSA))
                return true;

        return false;
    }

    // Leer los argumentos y asignar las variables correspondientes.
    public String banderaGuarda() {
        for (int i = 0; i < argumentos.length; i++)
            if (argumentos[i].equals(BANDERA_GUARDA))
                // Verifico que haya un argumento después de la bandera -o. Ese
                // argumento lo considero como el archivo en donde escribir la
                // salida. Aumenta i, para no volver a evaluar al archivo
                // como bandera.
                if (argumentos.length > i+1)
                    return argumentos[i+1];
                else
                    throw new IllegalArgumentException("Bandera -o debe ir acompañada de un archivo.");

        return null;
    }

    public Lista<String> fuentesEntrada() {
        Lista<String> archivosEntrada = new Lista<String>();

        for (int i = 0; i < argumentos.length; i++)
            if (argumentos[i].equals(BANDERA_GUARDA))
                i++;
            else if (!argumentos[i].equals(BANDERA_REVERSA))
                archivosEntrada.agrega(argumentos[i]);

        return archivosEntrada;
    }
}
