package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorArbolRojinegro;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorArbolAVL;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorPastel;

import java.io.IOException;

public class Reporte {

    private String ruta;
    private Lista<Palabra> palabras;
    private Diccionario<String, Integer> conteo;

    public Reporte(String ruta, Lista<Palabra> palabras, Diccionario<String, Integer> conteo) {
        this.ruta = ruta;
        this.palabras = palabras;
        this.conteo = conteo;
    }

    public String getRuta() {
        return ruta;
    }

    public Diccionario<String, String> getDatos() {
        Lista<Palabra> listaPalabrasComunes = palabrasMasComunes();
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
        datos.agrega("conteo_palabras", marcadoPalabras);

        return datos;
    }

    public Diccionario<String, String> getArchivos() throws IOException {
        Diccionario<String, String> archivos = new Diccionario<>();
        Lista<Palabra> listaPalabras = palabrasMasComunes();

        Diccionario<String, Integer> palabrasGrafica = new Diccionario<>();
        int contador = 0;
        for (Palabra palabra : listaPalabras) {
            palabrasGrafica.agrega(palabra.getPalabra(), palabra.getOcurrencias());
            contador += palabra.getOcurrencias();
        }
        palabrasGrafica.agrega("Otras", totalPalabras() - contador);

        archivos.agrega(Salida.nombreArchivo(ruta, "reporte", "html"),
                        GeneradorHTML.generaReporteIndividual(getDatos()));
        archivos.agrega(Salida.nombreArchivo(ruta, "arbol_rojinegro", "svg"),
                        graficaRojinegro(listaPalabras));
        archivos.agrega(Salida.nombreArchivo(ruta, "arbol_avl", "svg"),
                        graficaAVL(listaPalabras));
        archivos.agrega(Salida.nombreArchivo(ruta, "grafica_pastel", "svg"),
                        graficaPastel(palabrasGrafica));

        return archivos;
    }

    public Lista<Palabra> palabrasMasComunes() {
        Lista<Palabra> lista = new Lista<>();
        int limite = palabras.getElementos() <= 15 ? palabras.getElementos() : 15;
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

    private String graficaPastel(Diccionario<String, Integer> palabrasGrafica) {
        GraficadorPastel<String, Integer> graficador = new GraficadorPastel<>(palabrasGrafica);
        return graficador.graficar();
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
