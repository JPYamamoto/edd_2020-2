package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;

import java.util.NoSuchElementException;
import java.io.IOException;

/**
 * Proyecto 3
 */
public class Proyecto3 {

    /**
     * Imprime cómo se debe usar el programa.
     */
    public static void uso() {
        System.out.println("Uso: java -jar proyecto3.jar -o directorio archivo1 archivo2 ...");
    }

    /**
     * Obtén la instancia que usamos para guardar los archivos del programa.
     * @param argumentos los argumentos que recibe el programa.
     * @return la instancia de salida.
     */
    private static Salida obtenerSalida(Argumentos argumentos) {
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

        return new Salida(directorioSalida);
    }

    /**
     * Método que utilizamos para guardar los archivos auxiliares de los
     * reportes.
     * @param salida la instancia de salida con la que guardamos archivos.
     */
    private static void guardaAssets(Salida salida) {
        try {
            salida.escribeArchivos(GeneradorHTML.getAssets());
        } catch (IOException ioe) {
            System.out.println("Error al guardar archivos auxiliares de reportes.");
            System.exit(1);
        }
    }

    /**
     * Guardamos cada uno de los reportes.
     * @param reportes reportes a guardar.
     * @param salida la salida con la que guardamos archivos.
     */
    private static void guardaReportes(Lista<ReporteArchivo> reportes, Salida salida) {
        for (ReporteArchivo reporte : reportes)
            try {
                salida.escribeArchivos(reporte.getArchivos());
            } catch (IOException ioe) {
                System.out.println("Error al guardar reporte "+ reporte.getRuta());
                System.exit(1);
            }
    }

    /**
     * Guardamos el reporte global.
     * @param reportes reportes que usamos para generar el reporte global.
     * @param salida la salida con la que guardamos archivos.
     */
    private static void guardaReporteGlobal(Lista<ReporteArchivo> reportes, Salida salida) {
        ReporteGlobal reporteGlobal = new ReporteGlobal(reportes);
        try {
            salida.escribeArchivos(reporteGlobal.getArchivos());
        } catch (IOException ioe) {
            System.out.println("Error al guardar reporte global en index.html.");
            System.exit(1);
        }
    }

    /* Punto de entrada del programa. */
    public static void main(String[] args) {
        Argumentos argumentos = new Argumentos(args);
        Salida salida = obtenerSalida(argumentos);
        Lista<String> archivosEntrada = argumentos.getArchivosEntrada();

        // No recibimos archivos a leer.
        if (archivosEntrada.esVacia()) {
            System.out.println("No se recibieron archivos de entrada.");
            uso();
            System.exit(1);
        }

        // Generamos cada reporte.
        Lista<ReporteArchivo> reportes = new Lista<>();
        for (String nombreArchivo : archivosEntrada) {
            try {
                reportes.agrega(GeneradorReporteArchivo.nuevoReporte(nombreArchivo));
            } catch (IOException ioe) {
                System.out.println("Error al leer el archivo " + nombreArchivo);
                System.exit(1);
            }

        }

        // Guardamos los reportes.
        guardaReportes(reportes, salida);
        // Guardamos el reporte global.
        guardaReporteGlobal(reportes, salida);
        // Guardamos los archivos auxiliares.
        guardaAssets(salida);
    }
}
