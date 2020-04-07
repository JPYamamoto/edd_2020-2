package mx.unam.ciencias.edd.proyecto1;

import java.text.Normalizer;

/**
 * Clase para representar una Línea de texto.
 * Sirven como contenedor para Strings, que a su vez contienen una versión de la
 * cadena sin ciertos caracteres.
 *
 * De esta manera no habrá que generar la versión normalizada de la cadena cada
 * vez que se necesite trabajar con ella, como al hacer una comparación.
 *
 * No implementa la interfaz {@link java.lang.Comparable}, pues depende de cada
 * ordenador decidir cómo se compara la línea.
 */
public class Linea {

    /* La cadena de texto original. */
    private String linea;
    /* La cadena de texto limpia. */
    private String lineaLimpia;

    /**
     * Construye una nueva línea que contiene a la cadena de texto original y
     * su versión limpia:
     *   - En minúsculas.
     *   - Sin espacios en blanco.
     *   - Sin acentos en vocales.
     *   - Sin diéresis en vocales.
     *   - Sin caracteres distintos a letras del alfabeto inglés.
     */
    public Linea(String linea) {
        this.linea = linea;

        String limpia = Normalizer.normalize(linea, Normalizer.Form.NFD);
        limpia = limpia.toLowerCase().replaceAll("[^a-z]", "");

        this.lineaLimpia = limpia;
    }

    /**
     * Regresa la representación limpia de la cadena de texto.
     * @return la representación limpia de la cadena de texto.
     */
    public String lineaLimpia() {
        return lineaLimpia;
    }

    /**
     * Regresa una representación en cadena de la linea.
     * @return una representación en cadena de la linea.
     */
    @Override public String toString() {
        return linea;
    }
}
