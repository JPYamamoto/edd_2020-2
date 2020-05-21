package mx.unam.ciencias.edd.proyecto3;

public class Palabra implements Comparable<Palabra> {
    private String palabra;
    private int ocurrencias;

    public Palabra(String palabra, int ocurrencias) {
        this.palabra = palabra;
        this.ocurrencias = ocurrencias;
    }

    public String getPalabra() {
        return palabra;
    }

    public int getOcurrencias() {
        return ocurrencias;
    }

    public String toString() {
        return palabra;
    }

    public int compareTo(Palabra b) {
        return ocurrencias - b.ocurrencias;
    }
}
