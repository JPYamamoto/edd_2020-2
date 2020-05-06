package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        int resultado = 0;

        int posicion = 0;
        while(posicion < llave.length)
            resultado ^= combina(sacaInt(llave, posicion++), sacaInt(llave, posicion++),
                                 sacaInt(llave, posicion++), sacaInt(llave, posicion++));

        return resultado;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        int a = 0x9E3779B9;
        int b = 0x9E3779B9;
        int c = 0xFFFFFFFF;

        int posicion = 0;
        boolean ejecucion = true;
        while (ejecucion) {
            if (llave.length - posicion >= 12) {
                a += combina(sacaInt(llave, posicion+3), sacaInt(llave, posicion+2),
                             sacaInt(llave, posicion+1), sacaInt(llave, posicion));
                posicion += 4;

                b += combina(sacaInt(llave, posicion+3), sacaInt(llave, posicion+2),
                             sacaInt(llave, posicion+1), sacaInt(llave, posicion));
                posicion += 4;

                c += combina(sacaInt(llave, posicion+3), sacaInt(llave, posicion+2),
                             sacaInt(llave, posicion+1), sacaInt(llave, posicion));
                posicion += 4;
            } else {
                ejecucion = false;
                c += llave.length;

                switch(llave.length - posicion) {
                    case 11: c += (sacaInt(llave, posicion+10) << 24);
                    case 10: c += (sacaInt(llave, posicion+9) << 16);
                    case 9:  c += (sacaInt(llave, posicion+8) << 8);

                    case 8:  b += (sacaInt(llave, posicion+7) << 24);
                    case 7:  b += (sacaInt(llave, posicion+6) << 16);
                    case 6:  b += (sacaInt(llave, posicion+5) << 8);
                    case 5:  b +=  sacaInt(llave, posicion+4);

                    case 4:  a += (sacaInt(llave, posicion+3) << 24);
                    case 3:  a += (sacaInt(llave, posicion+2) << 16);
                    case 2:  a += (sacaInt(llave, posicion+1) << 8);
                    case 1:  a +=  sacaInt(llave, posicion);
                }
            }

            a -= b;
            a -= c;
            a ^= (c >>> 13);
            b -= c;
            b -= a;
            b ^= (a << 8);
            c -= a;
            c -= b;
            c ^= (b >>> 13);

            a -= b;
            a -= c;
            a ^= (c >>> 12);
            b -= c;
            b -= a;
            b ^= (a << 16);
            c -= a;
            c -= b;
            c ^= (b >>> 5);

            a -= b;
            a -= c;
            a ^= (c >>> 3);
            b -= c;
            b -= a;
            b ^= (a << 10);
            c -= a;
            c -= b;
            c ^= (b >>> 15);
        }

        return c;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int h = 5381;

        for (int i = 0; i < llave.length; i++)
            h += (h << 5) + sacaInt(llave, i);

        return h;
    }

    private static int combina(int a, int b, int c, int d) {
        return (a << 24) | (b << 16) |
               (c << 8)  | d;
    }

    private static int sacaInt(byte[] llave, int posicion) {
        if (posicion < llave.length)
            return (0xFF & llave[posicion]);

        return 0;
    }
}
