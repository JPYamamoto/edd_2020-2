package mx.unam.ciencias.edd.proyecto3;

import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class EntradaSalida {

    private EntradaSalida() {  }

    public static String contenidosArchivo(String nombreArchivo) throws IOException {
        if (nombreArchivo == null)
            throw new IllegalArgumentException("El nombre del archivo no puede ser vacío.");

        BufferedReader entrada = new BufferedReader(
                                    new InputStreamReader(
                                        new FileInputStream(nombreArchivo)));


        String contenido = "";
        String linea;
        while ((linea = entrada.readLine()) != null)
            contenido += linea + " ";

        entrada.close();

        return contenido;
    }
}
