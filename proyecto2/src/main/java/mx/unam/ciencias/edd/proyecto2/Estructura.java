package mx.unam.ciencias.edd.proyecto2;

/**
 * Enumeración con las estructuras de datos que podemos representar.
 */
public enum Estructura {
    ARBOL_AVL,
    ARBOL_BINARIO_COMPLETO,
    ARBOL_BINARIO_ORDENADO,
    ARBOL_ROJINEGRO,
    COLA,
    LISTA,
    PILA,
    GRAFICA,
    MONTICULO_MINIMO,

    /**
     * Utilizaremos INVALIDO cuando se nos pide una estructura de datos que no
     * podemos representar. Por lo mismo, es indispensable que el programa no
     * intente continuar si se recibe este valor.
     */
    INVALIDO;

    /**
     * Regresa la enumeración correspondiente a la representación como cadena
     * de texto recibida. Regresa INVALIDO cuando el texto no corresponde a
     * ningún valor de la enumeración.
     * @param estructura la cadena a convertir a un valor de la enumeración.
     * @return el valor de la enumeración que corresponde o INVALIDO.
     */
    public static Estructura getEstructura(String estructura) {
        switch(estructura) {
            case "ArbolAVL":             return ARBOL_AVL;
            case "ArbolBinarioCompleto": return ARBOL_BINARIO_COMPLETO;
            case "ArbolBinarioOrdenado": return ARBOL_BINARIO_ORDENADO;
            case "ArbolRojinegro":       return ARBOL_ROJINEGRO;
            case "Cola":                 return COLA;
            case "Lista":                return LISTA;
            case "Pila":                 return PILA;
            case "Grafica":              return GRAFICA;
            case "MonticuloMinimo":      return MONTICULO_MINIMO;
            default:                     return INVALIDO;
        }
    }
}
