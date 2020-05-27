package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.proyecto3.graficadores.GraficadorGrafica;

import java.io.IOException;

/**
 * Clase para representar reportes de archivos.
 */
public class ReporteGlobal {

    // Lista de reportes.
    private Lista<ReporteArchivo> reportes;

    /**
     * Constructor de la clase. Inicializamos variables.
     * @param reportes la lista de reportes de archivos.
     */
    public ReporteGlobal(Lista<ReporteArchivo> reportes) {
        this.reportes = reportes;
    }

    /**
     * Genera información de los archivos a guardar que corresponden al
     * reporte.
     * @return un diccionario con las llaves como los nombres de los archivos y
     * el contenido en el valor.
     * @throws IOException si ocurre un error al generar el reporte, debido a
     * que necesitamos leer la plantilla HTML de los recursos.
     */
    public Diccionario<String, String> getArchivos() throws IOException {
        Diccionario<String, String> archivos = new Diccionario<>();

        archivos.agrega(Salida.nombreArchivo(null, "index", "html"),
                        GeneradorHTML.generaReporteGlobal(getDatos()));
        archivos.agrega(Salida.nombreArchivo(null, "grafica", "svg"),
                        generaGrafica());

        return archivos;
    }

    /**
     * Datos que usamos para generar el HTML del reporte.
     * @return diccionario con los datos del reporte.
     */
    private Diccionario<String, String> getDatos() {
        Diccionario<String, String> datos = new Diccionario<>();
        int conteoPalabrasTotal = cuentaTotalPalabras();
        int conteoPalabrasDistintas = cuentaPalabrasDistintas();
        Palabra palabraComun = palabraMasComun();

        datos.agrega("numero_archivos", String.valueOf(reportes.getLongitud()));
        datos.agrega("numero_palabras_leidas", String.valueOf(conteoPalabrasTotal));
        datos.agrega("numero_palabras_distintas", String.valueOf(conteoPalabrasDistintas));
        datos.agrega("palabra_comun", palabraComun.getPalabra());
        datos.agrega("palabra_comun_ocurrencias", String.valueOf(palabraComun.getOcurrencias()));
        datos.agrega("palabra_comun_ocurrencias", String.valueOf(palabraComun.getOcurrencias()));
        datos.agrega("grafica", Salida.nombreArchivo(null, "grafica", "svg"));

        return datos;
    }

    /**
     * Calcula el total de palabras que procesó el programa.
     * @return un entero como el número total del palabras.
     */
    private int cuentaTotalPalabras() {
        int total = 0;

        for (ReporteArchivo reporte : reportes)
            total += reporte.getTotal();

        return total;
    }

    /**
     * Calcula el número de palabras distintas que se leyó en los archivos.
     * @return el total de palabras distintas como entero.
     */
    private int cuentaPalabrasDistintas() {
        int total = 0;

        for (ReporteArchivo reporte : reportes)
            total += reporte.getPalabras().getLongitud();

        return total;
    }

    /**
     * Calcula cuál fue la palabra más común entre los archivos leídos.
     * @return la palabra más común.
     */
    private Palabra palabraMasComun() {
        Palabra palabraComun = null;

        for (ReporteArchivo reporte : reportes)
            for (Palabra palabra : reporte.getPalabras())
                if (palabraComun == null)
                    palabraComun = palabra;
                else if (palabra.getOcurrencias() > palabraComun.getOcurrencias())
                    palabraComun = palabra;

        if (palabraComun == null)
            return new Palabra("Ninguna", 0);

        return palabraComun;
    }

    /**
     * Genera el SVG de la gráfica que usamos en el reporte global, con los
     * archivos como vértices y aristas si comparten al menos una palabra de al
     * menos 7 caracteres de longitud.
     * @return el SVG de la gráfica.
     */
    private String generaGrafica() {
        Grafica<ReporteArchivo> grafica = new Grafica<>();

        for (ReporteArchivo reporte : reportes)
            grafica.agrega(reporte);

        for (ReporteArchivo archivo1 : reportes)
            for (ReporteArchivo archivo2 : reportes)
                if (hayArista(archivo1, archivo2))
                    if (!grafica.sonVecinos(archivo1, archivo2))
                        grafica.conecta(archivo1, archivo2);

        GraficadorGrafica<ReporteArchivo> graficador = new GraficadorGrafica<>(grafica);
        return graficador.graficar();
    }

    /**
     * Nos dice si hay alguna arista entre dos archivos, es decir, comparten
     * alguna palabra de al menos 7 caracteres de longitud, y son archivos
     * distintos.
     * @param archivo1 el primer archivo a comparar.
     * @param archivo2 el segundo archivo a comparar.
     * @return un boolean si hay una arista.
     */
    private boolean hayArista(ReporteArchivo archivo1, ReporteArchivo archivo2) {
        if (archivo1 == archivo2)
            return false;

        Lista<Palabra> lista = archivo2.getPalabras();
        for (Palabra elemento : archivo1.getPalabras())
            if (elemento.getPalabra().length() >= 7 && lista.contiene(elemento))
                return true;

        return false;
    }
}
