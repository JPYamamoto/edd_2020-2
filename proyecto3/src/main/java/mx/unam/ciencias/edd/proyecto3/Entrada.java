package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Clase que utilizamos para encapsular los métodos correspondientes al
 * procesamiento de la entrada del programa.
 */
public class Entrada {

    /**
     * Constructor privado para evitar instanciación y utilizar los métodos
     * públicos solo de manera estática.
     */
    private Entrada() {  }

    /**
     * Lee los contenidos del archivo con el nombre recibido.
     * @param nombreArchivo el nombre del archivo.
     * @return el contenido del archivo.
     * @throws IOException si hubo un error al leer el archivo.
     */
    public static String contenidosArchivo(String nombreArchivo) throws IOException {
        if (nombreArchivo == null)
            throw new IllegalArgumentException("El nombre del archivo no puede ser vacío.");

        return contenidosArchivo(new FileInputStream(nombreArchivo));
    }

    /**
     * Lee los contenidos de un {@link InputStream}.
     * @param archivo el {@link InputStream} del archivo cuyo contenido leemos.
     * @return el contenido del archivo.
     * @throws IOException si hubo un error al leer el archivo.
     */
    private static String contenidosArchivo(InputStream archivo) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(archivo));


        String contenido = "";
        String linea;
        while ((linea = entrada.readLine()) != null)
            contenido += linea + " ";

        entrada.close();

        return contenido;
    }

    /**
     * Los archivos que usamos como recursos y deben ser exportados al generar
     * el reporte.
     * @return un diccionario con los nombres de los archivos como llaves y el
     * contenido como valor.
     * @throws IOException si hubo un error al leer el archivo.
     */
    public static Diccionario<String, String> getAssets() throws IOException {
        Diccionario<String, String> archivos = new Diccionario<>();

        archivos.agrega("home.svg", leeRecurso("assets/home.svg"));
        archivos.agrega("styles.css", leeRecurso("assets/styles.css"));
        archivos.agrega("link.svg", leeRecurso("assets/link.svg"));

        return archivos;
    }

    /**
     * Leemos un archivo de los recursos del proyecto.
     * @param nombre el nombre del archivo.
     * @return el contenido del recurso que leemos.
     * @throws IOException si hubo un error al leer el archivo.
     */
    public static String leeRecurso(String nombre) throws IOException {
        return Entrada.contenidosArchivo(
                Entrada.class.getClassLoader().getResourceAsStream(nombre));
    }
}
