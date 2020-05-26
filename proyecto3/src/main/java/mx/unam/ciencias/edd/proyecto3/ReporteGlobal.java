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

    private Lista<ReporteArchivo> reportes;

    public ReporteGlobal(Lista<ReporteArchivo> reportes) {
        this.reportes = reportes;
    }

    public Diccionario<String, String> getArchivos() throws IOException {
        Diccionario<String, String> archivos = new Diccionario<>();

        archivos.agrega(Salida.nombreArchivo(null, "index", "html"),
                        GeneradorHTML.generaReporteGlobal(getDatos()));

        return archivos;
    }

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

        return datos;
    }

    private int cuentaTotalPalabras() {
        int total = 0;

        for (ReporteArchivo reporte : reportes)
            total += reporte.getTotal();

        return total;
    }

    private int cuentaPalabrasDistintas() {
        int total = 0;

        for (ReporteArchivo reporte : reportes)
            total += reporte.getPalabras().getLongitud();

        return total;
    }

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
}
