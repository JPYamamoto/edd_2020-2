package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto2.graficadores.*;

public class FabricaGraficador {

    private FabricaGraficador() {}

    public static <T extends Comparable<T>> GraficadorSVG<T>
    getGraficadorSVG(Coleccion<T> datos, Estructura estructura) {
        switch(estructura) {
            case ARBOL_AVL:              return new GraficadorArbolAVL<T>(new ArbolAVL<>(datos));
            case ARBOL_BINARIO_COMPLETO: return new GraficadorArbolBinarioCompleto<T>(new ArbolBinarioCompleto<>(datos));
            case ARBOL_BINARIO_ORDENADO: return new GraficadorArbolBinarioOrdenado<T>(new ArbolBinarioOrdenado<>(datos));
            case ARBOL_ROJINEGRO:        return new GraficadorArbolRojinegro<T>(new ArbolRojinegro<>(datos));
            case COLA:                   return new GraficadorCola<T>(toLista(datos));
            case PILA:                   return new GraficadorPila<T>(toLista(datos));
            case LISTA:                  return new GraficadorLista<T>(toLista(datos));
            default:                     return null;
        }
    }

    private static <T extends Comparable<T>> Lista<T> toLista(Coleccion<T> coleccion) {
        if (coleccion instanceof Lista)
            return (Lista<T>)coleccion;

        Lista<T> lista = new Lista<>();

        for (T elemento : coleccion)
            lista.agrega(elemento);

        return lista;
    }
}
