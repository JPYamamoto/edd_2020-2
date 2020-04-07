package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para ordenar líneas de manera lexicográfica.
 */
public class OrdenadorLexicografico {

    /* El constructor es privado para evitar que se creen instancias de este. */
    private OrdenadorLexicografico() {}

    /**
     * Regresa una copia de la lista de líneas recibida, pero ordenada de manera
     * lexicográfica, utilizando la versión limpia de la cadena de texto.
     * @param lista la lista a ordenar.
     * @return una copia ordenada de la lista de líneas.
     */
    public static Lista<Linea> ordena(Lista<Linea> lista) {
        return lista.mergeSort((linea1, linea2) ->
                linea1.lineaLimpia().compareTo(linea2.lineaLimpia()));
    }

    /**
     * Regresa una copia de la lista de líneas recibida, pero ordenada de manera
     * lexicográfica en reversa.
     * @param lista la lista a ordenar.
     * @return una copia ordenada y en reversa de la lista de líneas.
     */
    public static Lista<Linea> ordenaReversa(Lista<Linea> lista) {
        return ordena(lista).reversa();
    }
}
