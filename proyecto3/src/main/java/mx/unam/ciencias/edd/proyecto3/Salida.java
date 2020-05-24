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

public class Salida {

    private String directorio;
    private static Dispersor<String> dispersor;

    public Salida(String directorio) {
        this.directorio = directorio;
    }

    public static String nombreArchivo(String ruta, String nombre, String extension) {
        return String.format("%s_%d.%s", nombre, dispersor.dispersa(ruta), extension);
    }

    public void escribeArchivos(Diccionario<String, String> archivos) throws IOException {
        Iterator<String> iteradorArchivos = archivos.iteradorLlaves();

        while (iteradorArchivos.hasNext()) {
            String archivo = iteradorArchivos.next();
            escribeArchivo(archivo, archivos.get(archivo));
        }
    }

    private void escribeArchivo(String nombre, String contenido) throws IOException {
        verificaDirectorio(directorio);

        BufferedWriter out = new BufferedWriter(
                                new OutputStreamWriter(
                                    new FileOutputStream(directorio + "/" + nombre)));

        out.write(contenido);
        out.close();
    }

    private void verificaDirectorio(String nombreDirectorio) {
        File directorio = new File(nombreDirectorio);
        if (!directorio.isDirectory())
            directorio.mkdir();
    }

    static {
        dispersor = FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING);
    }
}
