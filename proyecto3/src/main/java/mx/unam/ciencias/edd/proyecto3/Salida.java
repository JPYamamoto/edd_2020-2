package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.FabricaDispersores;
import mx.unam.ciencias.edd.AlgoritmoDispersor;
import mx.unam.ciencias.edd.Diccionario;

public class Salida {

    private String directorio;
    private Diccionario<String, String> archivos;

    public Salida(String directorio) {
        this.directorio = directorio;
    }

    public static String nombreArchivo(String nombre) {
        int dispersion = FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING)
                                           .dispersa(nombre);
        return String.valueOf(dispersion);
    }
}
