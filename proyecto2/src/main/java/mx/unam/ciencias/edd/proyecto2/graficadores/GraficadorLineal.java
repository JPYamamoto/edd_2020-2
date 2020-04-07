package mx.unam.ciencias.edd.proyecto2.graficadores;

import java.util.Iterator;

public abstract class GraficadorLineal<T extends Comparable<T>> implements GraficadorSVG<T> {

    protected abstract Iterator<T> getIterador();

    public String graficar() {
        return null;
    }
}
