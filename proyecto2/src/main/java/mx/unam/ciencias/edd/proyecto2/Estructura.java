package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Pila;

public enum Estructura {
    ARBOL_AVL,
    ARBOL_BINARIO_COMPLETO,
    ARBOL_BINARIO_ORDENADO,
    ARBOL_ROJINEGRO,
    COLA,
    LISTA,
    PILA,
    INVALIDO;

    public static Estructura getEstructura(String estructura) {
        switch(estructura) {
            case "ArbolAVL":             return ARBOL_AVL;
            case "ArbolBinarioCompleto": return ARBOL_BINARIO_COMPLETO;
            case "ArbolBinarioOrdenado": return ARBOL_BINARIO_ORDENADO;
            case "ArbolRojinegro":       return ARBOL_ROJINEGRO;
            case "Cola":                 return COLA;
            case "Lista":                return LISTA;
            case "Pila":                 return PILA;
            default:                     return INVALIDO;
        }
    }

    /*
    public Coleccion<Integer> getInstanciaInteger() {
        switch(this) {
            case ARBOL_AVL:              return new ArbolAVL<>();
            case ARBOL_BINARIO_COMPLETO: return new ArbolBinarioCompleto<>();
            case ARBOL_BINARIO_ORDENADO: return new ArbolBinarioOrdenado<>();
            case ARBOL_ROJINEGRO:        return new ArbolRojinegro<>();
            case COLA:                   return new Cola<Integer>();
            case LISTA:                  return new Lista<>();
            case PILA:                   return new Pila<>();
            default:                     return null;
        }
    }
    */
}
