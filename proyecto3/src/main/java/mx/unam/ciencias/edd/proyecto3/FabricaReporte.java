package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Iterator;

public class FabricaReporte {

    private FabricaReporte() {  }

    private static String limpiaTexto(String texto) {
        String limpia = Normalizer.normalize(texto, Normalizer.Form.NFD);
        limpia = limpia.toLowerCase().replaceAll("[^a-z\\s]", "");

        return limpia;
    }

    private static Diccionario<String, Integer> cuentaPalabras(String texto) {
        Diccionario<String, Integer> conteo = new Diccionario<>();

        for (String palabra : limpiaTexto(texto).split("\\s+"))
            if (conteo.contiene(palabra))
                conteo.agrega(palabra, conteo.get(palabra) + 1);
            else
                conteo.agrega(palabra, 1);

        return conteo;
    }

    public static Reporte nuevoArchivo(String nombreArchivo) throws IOException {
        String texto = EntradaSalida.contenidosArchivo(nombreArchivo);
        Diccionario<String, Integer> contador = cuentaPalabras(texto);
        Diccionario<Palabra, Palabra> palabras = new Diccionario<>();

        Iterator<String> iterador = contador.iteradorLlaves();
        while (iterador.hasNext()) {
            String palabraTexto = iterador.next();
            Palabra palabra = new Palabra(palabraTexto, contador.get(palabraTexto));
            palabras.agrega(palabra, palabra);
        }

        return new Reporte(nombreArchivo, palabras);
    }
}
