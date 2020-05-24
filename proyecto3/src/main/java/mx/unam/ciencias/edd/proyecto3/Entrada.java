package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;

public class Entrada {

    private Entrada() {  }

    public static String contenidosArchivo(String nombreArchivo) throws IOException {
        if (nombreArchivo == null)
            throw new IllegalArgumentException("El nombre del archivo no puede ser vacío.");

        return contenidosArchivo(new FileInputStream(nombreArchivo));
    }

    private static String contenidosArchivo(InputStream archivo) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(archivo));


        String contenido = "";
        String linea;
        while ((linea = entrada.readLine()) != null)
            contenido += linea + " ";

        entrada.close();

        return contenido;
    }

    public static String leeRecurso(String nombre) throws IOException {
        return Entrada.contenidosArchivo(
                Entrada.class.getClassLoader().getResourceAsStream(nombre));
    }

    public static Diccionario<String, String> getAssets() throws IOException {
        Diccionario<String, String> archivos = new Diccionario<>();

        archivos.agrega("home.svg", leeRecurso("assets/home.svg"));
        archivos.agrega("styles.css", leeRecurso("assets/styles.css"));

        return archivos;
    }
}
