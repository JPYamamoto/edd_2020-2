package mx.unam.ciencias.edd.proyecto3.graficadores;

/**
 * Interfaz que deben implementar las clases que se encargan de graficar las
 * estructuras de datos. Lo ideal es que cada estructura de datos tenga una
 * clase correspondiente que implemente esta interfaz para generar la
 * representación adecuada de la estructura.
 */
public abstract class GraficadorEstructura<T> {

    private int LADO_VACIO = 40;
    private int BORDE = 10;

    public String graficar() {
        if (esVacia())
            return graficarVacio();
        return graficarEstructura();
    }

    protected String graficarVacio() {
        return GraficadorSVG.declaracionXML() +
               GraficadorSVG.comienzaSVG(LADO_VACIO + (2 * BORDE),
                                         LADO_VACIO + (2 * BORDE)) +
               GraficadorSVG.graficaCirculo(BORDE + LADO_VACIO / 2,
                                            BORDE + LADO_VACIO / 2,
                                            LADO_VACIO / 2, "black", "white") +
               GraficadorSVG.graficaLinea(BORDE, BORDE + LADO_VACIO,
                                          LADO_VACIO, -LADO_VACIO, "black") +
               GraficadorSVG.terminaSVG();
    }

    /**
     * Método que regresa la gráfica SVG de una estructura de datos.
     * @return la cadena de texto con el SVG de la estructura.
     */
    protected abstract String graficarEstructura();

    /**
     * Método que nos indica si la estructura es vacía, para entonces regresar
     * un SVG que representa una estructura vacía.
     * @return boolean que indica si la estructura es vacía.
     */
    protected abstract boolean esVacia();
}
