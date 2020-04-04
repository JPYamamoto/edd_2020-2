package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolRojinegro;

public class GeneradorEstructura<T extends Comparable<T>> {

    Estructura tipoEstructura;
    Coleccion<T> coleccion;

    GeneradorEstructura(Coleccion<T> datos, Estructura estructura) {
        tipoEstructura = estructura;

        switch(estructura) {
            case ARBOL_AVL:
                coleccion = new ArbolAVL<>(datos);
                break;
            case ARBOL_BINARIO_COMPLETO:
                coleccion = new ArbolBinarioCompleto<>(datos);
                break;
            case ARBOL_BINARIO_ORDENADO:
                coleccion = new ArbolBinarioOrdenado<>(datos);
                break;
            case ARBOL_ROJINEGRO:
                coleccion = new ArbolRojinegro<>(datos);
                break;
            case COLA:
            case PILA:
            case LISTA:
                coleccion = datos;
                break;
            default:
                coleccion = null;
        }
    }

    public Coleccion<T> getColeccion() {
        return coleccion;
    }
}
