package mx.unam.ciencias.edd.proyecto3;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class Entrada {

    private Entrada() {  }

    public static String contenidosArchivo(String nombreArchivo) throws IOException {
        if (nombreArchivo == null)
            throw new IllegalArgumentException("El nombre del archivo no puede ser vacío.");

        return contenidosArchivo(new FileInputStream(nombreArchivo));
    }

    public static String contenidosArchivo(InputStream archivo) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(archivo));


        String contenido = "";
        String linea;
        while ((linea = entrada.readLine()) != null)
            contenido += linea + " ";

        entrada.close();

        return contenido;
    }
}
