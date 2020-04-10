package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, comparador, 0, arreglo.length-1);
    }

    private static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador, int a, int b) {
        if (b <= a)
            return;

        int izq = a + 1;
        int der = b;

        int result;
        while (izq < der)
            if ((result = comparador.compare(arreglo[izq], arreglo[a])) > 0 &&
                    comparador.compare(arreglo[der], arreglo[a]) <= 0)
                intercambia(arreglo, izq++, der--);
            else if (result <= 0)
                izq++;
            else
                der--;

        if (comparador.compare(arreglo[izq], arreglo[a]) > 0)
            izq--;

        intercambia(arreglo, a, izq);
        quickSort(arreglo, comparador, a, izq-1);
        quickSort(arreglo, comparador, izq+1, b);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arreglo.length; j++)
                if (comparador.compare(arreglo[j], arreglo[min]) < 0)
                    min = j;

            intercambia(arreglo, i, min);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        return busquedaBinaria(arreglo, elemento, comparador, 0, arreglo.length-1);
    }

    private static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comp, int a, int b) {
        if (b < a)
            return -1;

        int pivote = ((b - a) / 2) + a;
        int diferencia = comp.compare(elemento, arreglo[pivote]);

        if (diferencia == 0)
            return pivote;
        else if (diferencia < 0)
            return busquedaBinaria(arreglo, elemento, comp, a, pivote-1);
        else
            return busquedaBinaria(arreglo, elemento, comp, pivote+1, b);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    private static <T> void intercambia(T[] arr, int indice1, int indice2) {
        T temp = arr[indice1];
        arr[indice1] = arr[indice2];
        arr[indice2] = temp;
    }
}
