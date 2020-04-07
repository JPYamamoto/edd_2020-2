package mx.unam.ciencias.edd.proyecto2.graficadores;

public class GraficadorSVG {

    private GraficadorSVG() {}

    public static String declaracionXML() {
        return "<?xml version='1.0' encoding='UTF-8' ?>";
    }

    public static String comienzaSVG(int medidaX, int medidaY) {
        return String.format("<svg width='%d' height='%d'>", medidaX, medidaY);
    }

    public static String terminaSVG() {
        return "</svg>";
    }

    public static String graficaLinea(int origenX, int origenY, int cambioX,
                                      int cambioY, String color) {
        return String.format("<line x1='%d' y1='%d' x2='%d' y2='%d'" +
                "stroke='%s' stroke-width='3' />",
                origenX, origenY, origenX + cambioX, origenY + cambioY, color);
    }

    public static String graficaTriangulo(int origenX, int origenY, int medidaX,
                                          int medidaY, String color) {
        return String.format("<polygon points='%d,%d %d,%d %d,%d' fill='%s' />",
                origenX, (medidaY / 2) + origenY,
                origenX + medidaX, origenY,
                origenX + medidaX, origenY + medidaY,
                color);
    }

    public static String graficaRectanguloTexto(int origenX, int origenY,
                                                int medidaX, int medidaY,
                                                String colorBorde, String colorRelleno,
                                                int tamanoFuente, String colorFuente,
                                                String contenido) {
        return String.format("<g>" +
                "<rect x='%d' y='%d' width='%d' height='%d' stroke='%s' fill='%s' />" +
                "<text x='%d' y='%d' dominant-baseline='middle' text-anchor='middle'" +
                " font-family='sans-serif' font-size='%d'>%s</text>" +
                "</g>",
                origenX, origenY, medidaX, medidaY, colorBorde, colorRelleno,
                (medidaX / 2) + origenX, (medidaY /2) + origenY, tamanoFuente,
                contenido);
    }
}
