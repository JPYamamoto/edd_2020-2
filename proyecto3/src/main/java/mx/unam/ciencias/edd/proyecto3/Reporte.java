package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorArbolRojinegro;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorArbolAVL;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorPastel;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorBarras;

import java.io.IOException;

/**
 * Clase para representar reportes de archivos.
 */
public class Reporte {

    // La ruta a la que corresponde cada archivo.
    private String ruta;
    // La lista de palabras que contiene el archivo.
    private Lista<Palabra> palabras;
    // El número de palabras que contiene el archivo, contando repeticiones.
    private int total;

    /**
     * El constructor de la clase.
     * @param ruta la ruta del archivo.
     * @param palabras las palabras del archivo.
     * @param total el número de palabras en el archivo.
     */
    public Reporte(String ruta, Lista<Palabra> palabras, int total) {
        this.ruta = ruta;
        this.palabras = palabras;
        this.total = total;
    }

    /**
     * Regresa la ruta del archivo.
     * @return ruta del archivo.
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Datos que usamos para generar el HTML del reporte.
     * @return diccionario con los datos del reporte.
     */
    public Diccionario<String, String> getDatos() {
        Lista<Palabra> listaPalabrasComunes = tomaPalabrasMasComunes(15);
        Diccionario<String, String> datos = new Diccionario<>();
        String marcadoPalabrasComunes = generaMarcadoPalabras(listaPalabrasComunes);
        String marcadoPalabras = generaMarcadoPalabras(Lista.mergeSort(palabras).reversa());

        datos.agrega("archivo", ruta);
        datos.agrega("palabras_comunes", marcadoPalabrasComunes);
        datos.agrega("arbol_rojinegro",
                     Salida.nombreArchivo(ruta, "arbol_rojinegro", "svg"));
        datos.agrega("arbol_avl",
                     Salida.nombreArchivo(ruta, "arbol_avl", "svg"));
        datos.agrega("grafica_pastel",
                     Salida.nombreArchivo(ruta, "grafica_pastel", "svg"));
        datos.agrega("etiquetas_pastel",
                     Salida.nombreArchivo(ruta, "etiquetas_pastel", "svg"));
        datos.agrega("grafica_barras",
                     Salida.nombreArchivo(ruta, "grafica_barras", "svg"));
        datos.agrega("etiquetas_barras",
                     Salida.nombreArchivo(ruta, "etiquetas_barras", "svg"));
        datos.agrega("conteo_palabras", marcadoPalabras);

        return datos;
    }

    /**
     * Genera información de los archivos a guardar que corresponden al
     * reporte.
     * @return un diccionario con las llaves como los nombres de los archivos y
     * el contenido en el valor.
     * @throws IOException si ocurre un error al generar el reporte, debido a
     * que necesitamos leer la plantilla HTML de los recursos.
     */
    public Diccionario<String, String> getArchivos() throws IOException {
        Diccionario<String, String> archivos = new Diccionario<>();
        Lista<Palabra> listaPalabras = tomaPalabrasMasComunes(15);

        Diccionario<String, Integer> palabrasGrafica = calculaPalabrasGraficas();

        GraficadorPastel<String, Integer> graficadorPastel = graficaPastel(palabrasGrafica);
        GraficadorBarras<String, Integer> graficadorBarras = graficaBarras(palabrasGrafica);

        archivos.agrega(Salida.nombreArchivo(ruta, "reporte", "html"),
                        GeneradorHTML.generaReporteIndividual(getDatos()));
        archivos.agrega(Salida.nombreArchivo(ruta, "arbol_rojinegro", "svg"),
                        graficaRojinegro(listaPalabras));
        archivos.agrega(Salida.nombreArchivo(ruta, "arbol_avl", "svg"),
                        graficaAVL(listaPalabras));
        archivos.agrega(Salida.nombreArchivo(ruta, "grafica_pastel", "svg"),
                        graficadorPastel.graficar());
        archivos.agrega(Salida.nombreArchivo(ruta, "etiquetas_pastel", "svg"),
                        graficadorPastel.graficarEtiquetas());
        archivos.agrega(Salida.nombreArchivo(ruta, "grafica_barras", "svg"),
                        graficadorBarras.graficar());
        archivos.agrega(Salida.nombreArchivo(ruta, "etiquetas_barras", "svg"),
                        graficadorBarras.graficarEtiquetas());

        return archivos;
    }

    /**
     * Calculamos cuáles son las palabras que aparecerán en las gráficas de
     * barras y de pastel.
     * Las palabras que no entren en este cálculo, van a juntarse en una sola
     * categoría.
     * A lo más van a haber 20 palabras en la gráfica (pues verificamos que
     * tengan al menos 5% de ocurrencias en el archivo) y mínimo habrán 15.
     * Lo anterior nos permite que tengamos una gráfica no muy llena, pero
     * tampoco una rebanada muy grande, excepto cuando los archivos son muy
     * poco densos, en cuyo caso evitamos tener muchas rebanaditas o barritas.
     * @return un diccionario con las llaves como las palabras que serán
     * graficadas y los valores son el número de veces que aparecen.
     */
    private Diccionario<String, Integer> calculaPalabrasGraficas() {
        Diccionario<String, Integer> palabrasGrafica = new Diccionario<>();
        int contador = 0;
        int limite = (int) Math.ceil(5.0 * total / 100);
        int i = 0;

        for (Palabra palabra : Lista.mergeSort(palabras).reversa()) {
            if (palabra.getOcurrencias() < limite && i++ >= 15)
                break;

            palabrasGrafica.agrega(palabra.getPalabra(), palabra.getOcurrencias());
            contador += palabra.getOcurrencias();
        }

        palabrasGrafica.agrega("Otras Palabras", total - contador);

        return palabrasGrafica;
    }

    /**
     * Ordena las palabras según la cantidad de veces que aparecen, y toma la
     * cantidad recibida.
     * @param cantidad la cantidad de palabras que queremos tomar en nuestra
     * lista.
     * @return lista con las palabras más comúnes.
     */
    public Lista<Palabra> tomaPalabrasMasComunes(int cantidad) {
        Lista<Palabra> lista = new Lista<>();
        int limite = palabras.getElementos() <= cantidad ? palabras.getElementos() : cantidad;
        int i = 0;

        for (Palabra palabra : Lista.mergeSort(palabras).reversa()) {
            if (i++ >= limite)
                break;

            lista.agrega(palabra);
        }

        return lista;
    }

    /**
     * Genera la gráfica del árbol rojinegro a partir de la lista de palabras
     * recibida.
     * @param listaPalabras la lista con las palabras que contendrá el árbol.
     * @return el SVG del árbol rojinegro.
     */
    private String graficaRojinegro(Lista<Palabra> listaPalabras) {
        ArbolRojinegro<Palabra> arbol = new ArbolRojinegro<>(listaPalabras);
        GraficadorArbolRojinegro<Palabra> graficador = new GraficadorArbolRojinegro<>(arbol);
        return graficador.graficar();
    }

    /**
     * Genera la gráfica del árbol AVL a partir de la lista de palabras
     * recibida.
     * @param listaPalabras la lista con las palabras que contendrá el árbol.
     * @return el SVG del árbol AVL.
     */
    private String graficaAVL(Lista<Palabra> listaPalabras) {
        ArbolAVL<Palabra> arbol = new ArbolAVL<>(listaPalabras);
        GraficadorArbolAVL<Palabra> graficador = new GraficadorArbolAVL<>(arbol);
        return graficador.graficar();
    }

    /**
     * Regresa el graficador que usamos para graficar la gráfica de pastel. No
     * regresamos el SVG porque no podemos regresar dos valores: el SVG de la
     * gráfica y el SVG de las etiquetas.
     * @param palabrasGrafica la lista de palabras que tendrá la gráfica.
     * @return el graficador de la gráfica de pastel.
     */
    private GraficadorPastel<String, Integer> graficadorPastel(Diccionario<String, Integer> palabrasGrafica) {
        return new GraficadorPastel<>(palabrasGrafica);
    }

    /**
     * Regresa el graficador que usamos para graficar la gráfica de barras. No
     * regresamos el SVG porque no podemos regresar dos valores: el SVG de la
     * gráfica y el SVG de las etiquetas.
     * @param palabrasGrafica la lista de palabras que tendrá la gráfica.
     * @return el graficador de la gráfica de barras.
     */
    private GraficadorBarras<String, Integer> graficaBarras(Diccionario<String, Integer> palabrasGrafica) {
        return new GraficadorBarras<>(palabrasGrafica);
    }

    /**
     * Genera el HTML del listado de las palabras recibidas.
     * @param listaPalabras la lista de palabras a generar.
     * @return el HTML del listado de palabras.
     */
    private String generaMarcadoPalabras(Lista<Palabra> listaPalabras) {
        String result = "";
        for (Palabra palabra : listaPalabras)
            result += GeneradorHTML.marcadoPalabra(palabra, total);

        return result;
    }
}
