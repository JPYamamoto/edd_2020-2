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

public class Reporte {

    private String ruta;
    private Lista<Palabra> palabras;

    public Reporte(String ruta, Lista<Palabra> palabras) {
        this.ruta = ruta;
        this.palabras = palabras;
    }

    public String getRuta() {
        return ruta;
    }

    public Diccionario<String, String> getDatos() {
        Lista<Palabra> listaPalabrasComunes = tomaPalabrasMasComunes(15);
        Diccionario<String, String> datos = new Diccionario<>();
        String marcadoPalabrasComunes = generaMarcadoPalabras(listaPalabrasComunes, totalPalabras());
        String marcadoPalabras = generaMarcadoPalabras(Lista.mergeSort(palabras).reversa(), totalPalabras());

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

    private Diccionario<String, Integer> calculaPalabrasGraficas() {
        Diccionario<String, Integer> palabrasGrafica = new Diccionario<>();
        int contador = 0;
        int total = totalPalabras();
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

    private String graficaRojinegro(Lista<Palabra> listaPalabras) {
        ArbolRojinegro<Palabra> arbol = new ArbolRojinegro<>(listaPalabras);
        GraficadorArbolRojinegro<Palabra> graficador = new GraficadorArbolRojinegro<>(arbol);
        return graficador.graficar();
    }

    private String graficaAVL(Lista<Palabra> listaPalabras) {
        ArbolAVL<Palabra> arbol = new ArbolAVL<>(listaPalabras);
        GraficadorArbolAVL<Palabra> graficador = new GraficadorArbolAVL<>(arbol);
        return graficador.graficar();
    }

    private GraficadorPastel<String, Integer> graficaPastel(Diccionario<String, Integer> palabrasGrafica) {
        return new GraficadorPastel<>(palabrasGrafica);
    }

    private GraficadorBarras<String, Integer> graficaBarras(Diccionario<String, Integer> palabrasGrafica) {
        return new GraficadorBarras<>(palabrasGrafica);
    }

    private String generaMarcadoPalabras(Lista<Palabra> listaPalabras, int total) {
        String result = "";
        for (Palabra palabra : listaPalabras)
            result += GeneradorHTML.marcadoPalabra(palabra, total);

        return result;
    }

    private int totalPalabras() {
        int total = 0;
        for (Palabra palabra : palabras)
            total += palabra.getOcurrencias();

        return total;
    }
}
