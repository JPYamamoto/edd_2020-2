package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Dispersor;
import mx.unam.ciencias.edd.FabricaDispersores;
import mx.unam.ciencias.edd.AlgoritmoDispersor;
import mx.unam.ciencias.edd.Diccionario;

import java.util.Iterator;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;

/**
 * Clase que se encarga con todo lo relacionado a la salida, es decir, la
 * escritura de archivos.
 */
public class Salida {

    // El directorio donde se guardan los archivos.
    private String directorio;
    // El dispersor que usamos para construir el nombre de los archivos.
    private static Dispersor<String> dispersor;

    /**
     * El constructor de la clase que usamos para inicializar variables.
     * @param directorio
     */
    public Salida(String directorio) {
        this.directorio = directorio;
    }

    /**
     * Método que utilizamos para obtener el nombre que tendrá un archivo al
     * ser guardado. El identificador siempre debe ser distinto de null para
     * evitar repetir nombres (no es perfecto pues las funciones de dispersión
     * tienen colisiones, pero funciona en general suficientemente bien).
     * El identificador debe ser null cuando no nos importa que sea único, por
     * ejemplo, con el index.html.
     * @param identificador un identificador que usamos para dar nombres de
     * archivos distintos.
     * @param nombre el nombre del archivo que queremos guardar.
     * @param extension la extensión que tendrá el archivo.
     * @return el nombre del archivo que será guardado.
     */
    public static String nombreArchivo(String identificador, String nombre, String extension) {
        if (identificador == null)
            return String.format("%s.%s", nombre, extension);

        return String.format("%s_%d.%s", nombre, dispersor.dispersa(identificador), extension);
    }

    /**
     * Método que usamos para escribir varios archivos en nuestro sistema.
     * @param archivos un diccionario donde las llaves son los nombres de cada
     * archivo y el valor su contenido.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public void escribeArchivos(Diccionario<String, String> archivos) throws IOException {
        Iterator<String> iteradorArchivos = archivos.iteradorLlaves();

        while (iteradorArchivos.hasNext()) {
            String archivo = iteradorArchivos.next();
            escribeArchivo(archivo, archivos.get(archivo));
        }
    }

    /**
     * Método que usamos para escribir un archivo.
     * @param nombre el nombre del archivo.
     * @param contenido el contenido del archivo.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    private void escribeArchivo(String nombre, String contenido) throws IOException {
        verificaDirectorio(directorio);

        BufferedWriter out = new BufferedWriter(
                                new OutputStreamWriter(
                                    new FileOutputStream(directorio + "/" + nombre)));

        out.write(contenido);
        out.close();
    }

    /**
     * Método que verifica que el directorio donde escribimos exista, y si no,
     * lo creamos.
     * @param nombreDirectorio el nombre del directorio.
     */
    private void verificaDirectorio(String nombreDirectorio) {
        File directorio = new File(nombreDirectorio);
        if (!directorio.isDirectory())
            directorio.mkdir();
    }

    // Inicializamos el dispersor que usamos. Es estático para evitar que
    // cambie y tengamos inconsistencias en los nombres de los archivos.
    static {
        dispersor = FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING);
    }
}
