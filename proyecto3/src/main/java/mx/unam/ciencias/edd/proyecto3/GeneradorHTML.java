package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;

import java.io.IOException;

public class GeneradorHTML {

    private GeneradorHTML() {  }

    public static String marcadoPalabra(Palabra palabra, int total) {
        return String.format("<div " +
                "class=\"m-4 border-solid border-2 border-gray-600 p-4 rounded-md flex-grow\">" +
                "<p class=\"text-lg font-medium\">%s</p>" +
                "<p class=\"text-sm text-gray-500\">%d ocurrencias - %.3f%%</p>" +
                "</div>",
                palabra.getPalabra(), palabra.getOcurrencias(),
                ((double) palabra.getOcurrencias()) / total * 100.0);
    }

    public static String generaReporteIndividual(Diccionario<String, String> datos) throws IOException {
        String base = Entrada.leeRecurso("base.html");

        String[] secciones = base.split("((?=\\{\\{)|(?<=\\}\\}))");

        for (int i = 0; i < secciones.length; i++) {
            if (secciones[i].matches("\\{\\{ \\w+ \\}\\}")) {
                String llave = secciones[i].substring(3, secciones[i].length() - 3);
                secciones[i] = datos.get(llave);
            }
        }

        return String.join("", secciones);
    }
}
