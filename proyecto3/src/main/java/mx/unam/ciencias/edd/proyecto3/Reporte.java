package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorArbolRojinegro;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorArbolAVL;
import mx.unam.ciencias.edd.proyecto3.html.GeneradorHTML;

public class Reporte {

    private String ruta;
    private Diccionario<Palabra, Palabra> palabras;

    public Reporte(String ruta, Diccionario<Palabra, Palabra> palabras) {
        this.ruta = ruta;
        this.palabras = palabras;
    }

    public Diccionario<String, String> getDatos() {
        Lista<Palabra> listaPalabras = palabrasMasComunes();
        Diccionario<String, String> datos = new Diccionario<>();
        String marcadoPalabras = generaPalabrasComunes(listaPalabras, totalPalabras());

        datos.agrega("archivo", ruta);
        datos.agrega("palabras_comunes", marcadoPalabras);
        datos.agrega("arbol_rojinegro", graficaRojinegro(listaPalabras));
        datos.agrega("arbol_avl", graficaAVL(listaPalabras));

        return datos;
    }

    public void escribeArchivos() {
        Salida.escribeArchivo(Salida.nombreArchivo(ruta) + ".html", generaHTML());
    }

    public Lista<Palabra> palabrasMasComunes() {
        Lista<Palabra> lista = new Lista<>();

        for (Palabra palabra : palabras)
            lista.agrega(palabra);

        // Ordena en reversa según cantidad de ocurrencias.
        lista = lista.mergeSort((a, b) -> b.getOcurrencias() - a.getOcurrencias());

        Lista<Palabra> listaFinal = new Lista<>();
        int limite = lista.getElementos() <= 15 ? lista.getElementos() : 15;
        int i = 0;

        for (Palabra palabra : lista) {
            if (i++ > limite)
                break;

            listaFinal.agrega(palabra);
        }

        return listaFinal;
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

    private String generaPalabrasComunes(Lista<Palabra> listaPalabras, int total) {
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
