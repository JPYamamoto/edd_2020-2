package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import java.text.Normalizer;

public class Contador {

    private Contador() {  }

    private static String limpiaTexto(String texto) {
        String limpia = Normalizer.normalize(texto, Normalizer.Form.NFD);
        limpia = limpia.toLowerCase().replaceAll("[^a-z\\s]", "");

        return limpia;
    }

    public static void cuentaPalabras(Diccionario<String, Integer> conteo, String texto) {
        for (String palabra : limpiaTexto(texto).split("\\s+"))
            if (conteo.contiene(palabra))
                conteo.agrega(palabra, conteo.get(palabra) + 1);
            else
                conteo.agrega(palabra, 1);
    }
}
