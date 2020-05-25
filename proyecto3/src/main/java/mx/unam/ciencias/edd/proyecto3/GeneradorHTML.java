package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;

import java.io.IOException;

/**
 * Clase que se encarga de generar código HTML.
 */
public class GeneradorHTML {

    /**
     * Constructor privado para evitar instanciación y utilizar los métodos
     * públicos solo de manera estática.
     */
    private GeneradorHTML() {  }

    /**
     * Genera el HTML que utilizamos para mostrar una palabra en el listado de
     * palabras.
     * @param palabra la palabra a mostrar.
     * @param total el total de palabras, para obtener el porcentaje.
     * @return el HTML que corresponde a la palabra.
     */
    public static String marcadoPalabra(Palabra palabra, int total) {
        return String.format("<div " +
                "class=\"m-4 border-solid border-2 border-gray-600 p-4 rounded-md flex-grow\">" +
                "<p class=\"text-lg font-medium\">%s</p>" +
                "<p class=\"text-sm text-gray-500\">%d ocurrencias - %.3f%%</p>" +
                "</div>",
                palabra.getPalabra(), palabra.getOcurrencias(),
                ((double) palabra.getOcurrencias()) / total * 100.0);
    }

    /**
     * Genera el HTML a partir del archivo plantilla para reportes individuales
     * utilizando los valores del diccionario recibido.
     * @param datos los datos a sustituir en el archivo plantilla.
     * @return el HTML del reporte generado.
     * @throws IOException si ocurre un error al leer un archivo.
     */
    public static String generaReporteIndividual(Diccionario<String, String> datos) throws IOException {
        String base = Entrada.leeRecurso("base.html");

        String[] secciones = base.split("((?=\\{\\{)|(?<=\\}\\}))");

        for (int i = 0; i < secciones.length; i++)
            if (secciones[i].matches("\\{\\{ \\w+ \\}\\}")) {
                String llave = secciones[i].substring(3, secciones[i].length() - 3);
                secciones[i] = datos.get(llave);
            }

        return String.join("", secciones);
    }
}
