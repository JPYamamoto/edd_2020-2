package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Iterator;

/**
 * Clase que utilizamos para construir reportes. Para evitar realizar ciertas
 * operaciones en el constructor de {@link Reporte}, usamos este clase.
 */
public class GeneradorReporte {

    /**
     * Constructor privado para evitar instanciación y utilizar los métodos
     * públicos solo de manera estática.
     */
    private GeneradorReporte() {  }

    /**
     * Método que utilizamos para generar una instancia de {@link Reporte} a
     * partir del contenido de un archivo.
     * @param nombreArchivo el nombre del archivo a leer.
     * @return el reporte del archivo.
     * @throws IOException si hubo un error al leer el archivo.
     */
    public static Reporte nuevoReporte(String nombreArchivo) throws IOException {
        String texto = Entrada.contenidosArchivo(nombreArchivo);
        String[] textoLimpio = limpiaTexto(texto).split("\\s+");

        Diccionario<String, Integer> contador = cuentaPalabras(textoLimpio);
        Lista<Palabra> palabras = new Lista<>();

        Iterator<String> iterador = contador.iteradorLlaves();
        while (iterador.hasNext()) {
            String palabraTexto = iterador.next();
            palabras.agrega(new Palabra(palabraTexto, contador.get(palabraTexto)));
        }

        return new Reporte(nombreArchivo, palabras, textoLimpio.length);
    }

    /**
     * Método que elimina caracteres distintos de letras del texto recibido, y
     * normaliza el texto para eliminar acentos.
     * @param texto el texto a limpiar.
     * @return la cadena de texto limpia.
     */
    private static String limpiaTexto(String texto) {
        String limpia = Normalizer.normalize(texto, Normalizer.Form.NFD);
        limpia = limpia.toLowerCase().replaceAll("[^a-z\\s]", "");

        return limpia;
    }

    /**
     * Método para contar el número de veces que aparece cada palabra en un
     * archivo en tiempo O(n).
     * @param texto el texto cuyo contenido contamos.
     * @return un diccionario con cada palabra como llave y un entero indicando
     * el número de ocurrencias.
     */
    private static Diccionario<String, Integer> cuentaPalabras(String[] texto) {
        Diccionario<String, Integer> conteo = new Diccionario<>();

        for (String palabra : texto)
            if (conteo.contiene(palabra))
                conteo.agrega(palabra, conteo.get(palabra) + 1);
            else
                conteo.agrega(palabra, 1);

        return conteo;
    }
}
