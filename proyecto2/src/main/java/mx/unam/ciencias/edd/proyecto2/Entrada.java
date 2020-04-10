package mx.unam.ciencias.edd.proyecto2;

import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase que utilizamos para encapsular los métodos correspondientes al
 * procesamiento de la entrada del programa.
 */
public class Entrada {

    /**
     * Constructor privado para evitar instanciación y utilizar los métodos
     * públicos solo de manera estática.
     */
    private Entrada() {  }

    /* Método privado que se encarga de abrir el flujo de entrada. */
    public static BufferedReader abrirEntrada(String[] args) {
        if (args.length != 0)
            try {
                return new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
            } catch (IOException ioe) {
                System.out.printf("Error. El archivo %s no pudo ser leído.\n", args[0]);
                System.exit(1);
            }

        return new BufferedReader(new InputStreamReader(System.in));
    }

    /* Cierra el flujo de entrada. */
    public static void cierraEntrada(BufferedReader entrada) {
        try {
            entrada.close();
        } catch(IOException ioe) { /* Ya no hay nada que hacer. */ }
    }

    /**
     * Método privado para recuperar los elementos en la entrada que componen
     * la estructura de datos.
     */
    public static Lista<Integer> leerEntrada(BufferedReader entrada) {
        Lista<Integer> coleccion = new Lista<>();
        String numero = "";
        int letraInt;

        try {
            while ((letraInt = entrada.read()) != -1) {
                char letra = (char) letraInt;

                // Ignoramos hasta el final de la línea si encontramos un #.
                if (letra == '#') {
                    entrada.readLine();
                    continue;
                }

                // Ignoramos los caracteres no imprimibles y el espacio. Es
                // decir, aquellos cuyo código ASCII corresponde a un valor
                // igual o menor a 32.
                // Fuente: https://web.itu.edu.tr/sgunduz/courses/mikroisl/ascii.html
                if (letra <= 32) {
                    // Si encontramos un separador, y tenemos un número en
                    // string, lo convertimos a entero y se agrega a la lista.
                    if (!numero.isEmpty())
                        coleccion.agrega(Integer.parseInt(numero));

                    numero = "";
                } else if (Character.isDigit(letra)) {
                    // Agrega a la cadena cualquier dígito.
                    numero += String.valueOf(letra);
                } else {
                    // Si no es dígito ni un caracter no imprimibles, tenemos
                    // un error.
                    System.out.printf("El archivo contiene el siguiente caracter no permitido: %c\n", letra);
                    System.exit(1);
                }
            }
        } catch (IOException ioe) {
            cierraEntrada(entrada);
            System.out.println("Ocurrió un error al leer la entrada.");
            System.exit(1);
        }

        return coleccion;
    }

    /**
     * Método para identificar el nombre de la estructura de datos que será
     * representada.
     */
    public static Estructura identificaEstructura(BufferedReader entrada) {
        String estructuraString = "";
        char letra;

        try {
            while ((letra = (char) entrada.read()) != -1) {
                // Ignoramos hasta el final de la línea si encontramos un #.
                if (letra == '#') {
                    entrada.readLine();
                    continue;
                }

                // Ignoramos los caracteres no imprimibles y el espacio. Es
                // decir, aquellos cuyo código ASCII corresponde a un valor
                // igual o menor a 32.
                // Fuente: https://web.itu.edu.tr/sgunduz/courses/mikroisl/ascii.html
                // Esto sucede solo cuando no hemos identificado la estructura
                // de datos. Si ya tenemos el nombre, entra a otro caso.
                if (estructuraString.isEmpty() && letra <= 32)
                    continue;
                // Tomamos en cuenta solo las letras minúsculas y mayúsculas.
                else if ((65 <= letra && letra <= 90) || (97 <= letra && letra <= 122))
                    estructuraString += letra;
                // Cualquier otro caracter recibido al comienzo del archivo que
                // no entra en las condiciones anteriores nos dice que hemos
                // terminado de leer el nombre de la estructura. Regresamos el
                // valor de la enumeración Estructura que corresponde al nombre
                // recibido.
                else
                    return Estructura.getEstructura(estructuraString);
            }
        } catch (IOException ioe) {
            System.out.println("Hubo un error al leer de la entrada.");
            System.exit(1);
        }

        return null;
    }
}
