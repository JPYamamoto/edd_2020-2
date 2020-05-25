package mx.unam.ciencias.edd.proyecto3;

/**
 * Clase que usamos para representar una palabra.
 */
public class Palabra implements Comparable<Palabra> {
    private String palabra;
    private int ocurrencias;

    /**
     * Constructor de la palabra que recibe el String de la palabra y el número
     * de veces que lo encontramos en el archivo.
     * @param palabra la palabra a representar.
     * @param ocurrencias el número de veces que la contamos en un archivo.
     */
    public Palabra(String palabra, int ocurrencias) {
        this.palabra = palabra;
        this.ocurrencias = ocurrencias;
    }

    /**
     * Método para obtener la palabra.
     * @return la palabra.
     */
    public String getPalabra() {
        return palabra;
    }

    /**
     * Método para obtener el número de veces que aparece la palabra.
     * @return el número de veces que aparece la palabra.
     */
    public int getOcurrencias() {
        return ocurrencias;
    }

    /**
     * La representación en cadena de la palabra.
     * @return la representación en cadena de la palabra.
     */
    public String toString() {
        return String.format("%s-%d", palabra, ocurrencias);
    }

    /**
     * Comparamos la palabra por número de ocurrencias.
     */
    public int compareTo(Palabra b) {
        return ocurrencias - b.ocurrencias;
    }
}
