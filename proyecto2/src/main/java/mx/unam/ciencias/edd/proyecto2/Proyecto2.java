package mx.unam.ciencias.edd.proyecto2;

/**
 * Proyecto 2.
 */
public class Proyecto2 {

    public static void main(String[] args) {
        GeneradorEstructura<Integer> entrada = Entrada.procesaEntrada(args);

        System.out.println(entrada.getColeccion().toString());
        //Coleccion<Integer> estructura = Estructura.getInstanciaInteger()
    }
}
