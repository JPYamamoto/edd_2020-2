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
        return String.format("<div class=\"palabra\">" +
                "<p class=\"font-lg font-medium\">%s</p>" +
                "<p class=\"font-sm font-gray\">%d ocurrencias - %.3f%%</p>" +
                "</div>",
                palabra.getPalabra(), palabra.getOcurrencias(),
                ((double) palabra.getOcurrencias()) / total * 100.0);
    }

    /**
     * Genera el HTML que utilizamos para mostrar una fila en la tabla de
     * archivos.
     * @param url la ruta donde se encuentra el reporte del archivo.
     * @param nombre el nombre del archivo a mostrar.
     * @param total el total de palabras que tiene el archivo.
     * @param palabras el número de palabras distintas en el archivo.
     * @return el HTML que corresponde al archivo.
     */
    public static String marcadoArchivo(String url, String nombre, int total, int palabras) {
        return String.format("<tr>" +
                "<td><a href=\"./%s\"><img class=\"link-img\" src=\"./link.svg\"></a></td>" +
                "<td><a href=\"./%s\" class=\"link-file\">%s</a></td>" +
                "<td>%d</td>" +
                "<td>%d</td>" +
                "</tr>",
                url, url, nombre, total, palabras);
    }

    /**
     * Genera el HTML a partir del archivo plantilla para reportes individuales
     * utilizando los valores del diccionario recibido.
     * @param datos los datos a sustituir en el archivo plantilla.
     * @return el HTML del reporte generado.
     * @throws IOException si ocurre un error al leer un archivo.
     */
    public static String generaReporteIndividual(Diccionario<String, String> datos) throws IOException {
        return plantillaArchivo("html/base.html", datos);
    }

    /**
     * Genera el HTML a partir del archivo plantilla para reportes globales
     * utilizando los valores del diccionario recibido.
     * @param datos los datos a sustituir en el archivo plantilla.
     * @return el HTML del reporte generado.
     * @throws IOException si ocurre un error al leer un archivo.
     */
    public static String generaReporteGlobal(Diccionario<String, String> datos) throws IOException {
        return plantillaArchivo("html/base_index.html", datos);
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

        archivos.agrega("home.svg", Entrada.leeRecurso("svg/home.svg"));
        archivos.agrega("link.svg", Entrada.leeRecurso("svg/link.svg"));
        archivos.agrega("styles.css", Entrada.leeRecurso("css/styles.css"));

        return archivos;
    }

    /**
     * Genera el HTML a partir del archivo recibido, que debe estar en los
     * recursos del proyecto, utilizando los valores del diccionario recibido.
     * @param archivo el nombre del archivo a usar como plantilla.
     * @param datos los datos a sustituir en el archivo plantilla.
     * @return el HTML del reporte generado.
     * @throws IOException si ocurre un error al leer un archivo.
     */
    private static String plantillaArchivo(String archivo, Diccionario<String, String> datos) throws IOException {
        String base = Entrada.leeRecurso(archivo);

        String[] secciones = base.split("((?=\\{\\{)|(?<=\\}\\}))");

        for (int i = 0; i < secciones.length; i++)
            if (secciones[i].matches("\\{\\{ \\w+ \\}\\}")) {
                String llave = secciones[i].substring(3, secciones[i].length() - 3);
                secciones[i] = datos.get(llave);
            }

        return String.join("", secciones);
    }
}
