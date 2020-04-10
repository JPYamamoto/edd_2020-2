package mx.unam.ciencias.edd.proyecto2.graficadores;

/**
 * Clase con varios métodos que nos permiten generar el SVG de varios
 * componentes que utilizamos para las gráficas en distintos puntos del
 * programa.
 */
public class GraficadorSVG {

    /**
     * Constructor privado para asegurar que la clase se utilice únicamente de
     * manera estática.
     */
    private GraficadorSVG() {}

    /**
     * Método que regresa la cadena para declarar que estamos utilizando XML.
     * @return la declaración XML.
     */
    public static String declaracionXML() {
        return "<?xml version='1.0' encoding='UTF-8' ?>";
    }

    /**
     * Genera la etiqueta de apertura de un SVG con sus respectivas medidas.
     * @param medidaX la medida del eje X del SVG.
     * @param medidaY la medida del eje Y del SVG.
     * @return la etiqueta de apertura de SVG.
     */
    public static String comienzaSVG(int medidaX, int medidaY) {
        return String.format("<svg width='%d' height='%d'>", medidaX, medidaY);
    }

    /**
     * Regresa la etiqueta de cerradura de SVG, para no tener que escribirla
     * directamente en otras partes del programa.
     * @return la etiqueta de cerradura de SVG.
     */
    public static String terminaSVG() {
        return "</svg>";
    }

    /**
     * Genera el SVG de una línea con las coordenadas y el color recibidos.
     * @param origenX la coordenada X donde comienza la línea.
     * @param origenY la coordenada Y donde comienza la línea.
     * @param cambioX el cambio en el eje X de la pendiente de la línea.
     * @param cambioY el cambio en el eje Y de la pendiente de la línea.
     * @param color el color de la línea.
     * @return el SVG de la línea que corresponde a los parámetros recibidos.
     */
    public static String graficaLinea(int origenX, int origenY, int cambioX,
                                      int cambioY, String color) {
        return String.format("<line x1='%d' y1='%d' x2='%d' y2='%d'" +
                " stroke='%s' stroke-width='3' />",
                origenX, origenY, origenX + cambioX, origenY + cambioY, color);
    }

    /**
     * Genera el SVG de un triángulo isósceles horizontal contenido dentro del
     * rectángulo que se genera con un punto de origen, y el cambio en los ejes
     * X y Y.
     * @param origenX la coordenada X donde comienza el rectángulo que contiene
     * al triángulo.
     * @param origenY la coordenada Y donde comienza el rectángulo que contiene
     * al triángulo.
     * @param medidaX la medida en el eje X del rectángulo que contiene al
     * triángulo.
     * @param medidaY la medida en el eje Y del rectángulo que contiene al
     * triángulo.
     * @param color el color del triángulo.
     * @return el SVG del triángulo que corresponde a los parámetros recibidos.
     */
    public static String graficaTriangulo(int origenX, int origenY, int medidaX,
                                          int medidaY, String color) {
        return String.format("<polygon points='%d,%d %d,%d %d,%d' fill='%s' />",
                origenX, (medidaY / 2) + origenY,
                origenX + medidaX, origenY,
                origenX + medidaX, origenY + medidaY,
                color);
    }

    /**
     * Genera el SVG de un rectángulo que contiene texto centrado en ambos
     * ejes.
     * @param origenX la coordenada X donde comienza el rectángulo.
     * @param origenY la coordenada Y donde comienza el rectángulo.
     * @param medidaX la medida en el eje X del rectángulo.
     * @param medidaY la medida en el eje Y del rectángulo.
     * @param colorBorde el color del borde del rectángulo.
     * @param colorRelleno el color del relleno del rectángulo.
     * @param tamanoFuente el tamaño de la fuente del texto.
     * @param colorFuente el color de la fuente del texto.
     * @param contenido el texto.
     * @return el SVG del rectángulo con las propiedades recibidas en los
     * argumentos, que contiene el texto recibido.
     */
    public static String graficaRectanguloTexto(int origenX, int origenY,
                                                int medidaX, int medidaY,
                                                String colorBorde, String colorRelleno,
                                                int tamanoFuente, String colorFuente,
                                                String contenido) {
        String svg = String.format("<g>" +
                "<rect x='%d' y='%d' width='%d' height='%d' stroke='%s' fill='%s' />",
                origenX, origenY, medidaX, medidaY, colorBorde, colorRelleno);
        svg += graficaTexto((medidaX / 2) + origenX, (medidaY /2) + origenY,
                tamanoFuente, colorFuente, contenido);
        svg += "</g>";
        return svg;
    }

    /**
     * Genera el SVG de un círculo que contiene texto centrado en ambos ejes.
     * @param centroX la coordenada X del centro del círculo.
     * @param centroY la coordenada Y del centro del círculo.
     * @param radio la medida del radio.
     * @param colorBorde el color del borde del círculo.
     * @param colorRelleno el color del relleno del círculo.
     * @param tamanoFuente el tamaño de la fuente del texto.
     * @param colorFuente el color de la fuente del texto.
     * @param contenido el texto.
     * @return el SVG del círculo con las propiedades recibidas en los
     * argumentos, que contiene el texto recibido.
     */
    public static String graficaCirculoTexto(int centroX, int centroY,
                                             int radio, String colorBorde,
                                             String colorRelleno,
                                             int tamanoFuente, String colorFuente,
                                             String contenido) {
        String svg = String.format("<g>" +
                "<circle cx='%d' cy='%d' r='%d' stroke='%s' stroke-width='3' fill='%s' />",
                centroX, centroY, radio, colorBorde, colorRelleno);
        svg += graficaTexto(centroX, centroY, tamanoFuente, colorFuente, contenido);
        svg += "</g>";
        return svg;
    }

    /**
     * Genera el SVG de un cuadro de texto.
     * @param centroX la coordenada X del centro del círculo.
     * @param centroY la coordenada Y del centro del círculo.
     * @param tamanoFuente el tamaño de la fuente del texto.
     * @param colorFuente el color de la fuente del texto.
     * @param contenido el texto.
     * @return el SVG del círculo con las propiedades recibidas en los
     * argumentos, que contiene el texto recibido.
     */
    public static String graficaTexto(int centroX, int centroY,
                                      int tamanoFuente, String colorFuente,
                                      String contenido) {
        // En firefox y en chrome, el atributo dominant-baseline='middle' nos
        // permite posicionar el texto con base en su línea media. Es decir,
        // las coordenadas x y y nos indican la posición del centro del texto.
        // De esta manera, podemos colocar centrar el texto simplemente
        // invocando este método con el centro del círculo o rectángulo donde
        // va a ir posicionado.
        // Este comportamiento no parece ser el mismo en el visor que trae
        // Gnome por defecto (Eye of Gnome), pues posiciona el texto levemente
        // más arriba. Pareciera que ubica en la coordenada y la parte inferior
        // del texto.
        return String.format("<text x='%d' y='%d' dominant-baseline='middle'" +
                " text-anchor='middle' font-family='sans-serif' font-size='%d'" +
                " fill='%s'>%s</text>",
                centroX, centroY, tamanoFuente, colorFuente, contenido);
    }
}
