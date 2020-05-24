package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.proyecto3.html.GeneradorHTML;

import java.util.NoSuchElementException;
import java.io.IOException;

/**
 * Proyecto 3
 */
public class Proyecto3 {

    public static void uso() {
        System.out.println("Uso: java -jar proyecto3.jar -o directorio archivo1 archivo2 ...");
    }

    /* Punto de entrada del programa. */
    public static void main(String[] args) {
        Argumentos argumentos = new Argumentos(args);

        String directorioSalida = null;
        try {
            directorioSalida = argumentos.getDirectorioSalida();
        } catch(IllegalArgumentException iae) {
            System.out.println("La bandera -o debe ir acompañada de un directorio.");
            uso();
            System.exit(1);
        } catch(NoSuchElementException nsee) {
            System.out.println("No se recibió la bandera -o.");
            uso();
            System.exit(1);
        }

        Lista<String> archivosEntrada = argumentos.getArchivosEntrada();
        if (archivosEntrada.esVacia()) {
            System.out.println("No se recibieron archivos de entrada.");
            uso();
            System.exit(1);
        }

        Diccionario<String, Diccionario<String, Integer>> infoArchivos = new Diccionario<>();
        Diccionario<String, String> archivosSalida = new Diccionario<>();

        for (String nombreArchivo : archivosEntrada) {
            Diccionario<String, Integer> conteo = new Diccionario<>();
            Reporte archivo;

            try {
                archivo = FabricaReporte.nuevoReporte(nombreArchivo);
            } catch (IOException ioe) {
                System.out.println("Error al leer el archivo " + nombreArchivo);
                System.exit(1);
            }

            String htmlArchivo = "";
            try {
                htmlArchivo = GeneradorHTML.generaReporteIndividual(datos, archivosSalida);
            } catch(IOException ioe) {
                System.out.println("Error al generar el reporte.");
                System.exit(1);
            }
            System.out.println(htmlArchivo);
        }

        //Reporte.generaGlobal(infoArchivos);
    }
}
