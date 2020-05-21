package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorArbolRojinegro;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorArbolAVL;

import java.util.Iterator;

public class ReporteArchivo {

    String nombre;
    Diccionario<String, Integer> conteo;

    public ReporteArchivo(String nombre, Diccionario<String, Integer> conteo) {
        this.nombre = nombre;
        this.conteo = conteo;
    }

    public Lista<Palabra> palabrasMasComunes() {
        Lista<Palabra> lista = new Lista<>();
        Iterator<String> iterador = conteo.iteradorLlaves();

        while (iterador.hasNext()) {
            String palabra = iterador.next();
            lista.agrega(new Palabra(palabra, conteo.get(palabra)));
        }

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

    public String graficaRojinegro(Lista<Palabra> palabras) {
        ArbolRojinegro<Palabra> arbol = new ArbolRojinegro<>(palabras);
        GraficadorArbolRojinegro<Palabra> graficador = new GraficadorArbolRojinegro<>(arbol);
        return graficador.graficar();
    }

    public String graficaAVL(Lista<Palabra> palabras) {
        ArbolAVL<Palabra> arbol = new ArbolAVL<>(palabras);
        GraficadorArbolAVL<Palabra> graficador = new GraficadorArbolAVL<>(arbol);
        return graficador.graficar();
    }

    public void generaIndividual() {  }
}
