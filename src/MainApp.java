import excepciones.DirectorioNoExisteExcepcion;
import excepciones.NoEsDirectorioException;
import servicio.Filtro;
import servicio.OperacionesIO;

public class MainApp {
    public static void main(String[] args) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {

        //final String ruta = "D:\\abarrpazo\\Github\\AccesoDatos\\Directorios";
        final String ruta = "P:\\AccesoDatos\\Directorios";

        // Ejercicio 1
        OperacionesIO.visualizarContenido(ruta);

        // Ejercicio 2
        OperacionesIO.recorrerRecursivo(ruta);
        OperacionesIO.recorrerRecursivo(ruta,new Filtro("txt"));
        // Para este seria mas comodo hacer nuevos metodos
        // Una solucion es devolver una cadena con el metodo recursivo e ir concatenandola y comprobar si al terminar esta vacia
        OperacionesIO.recorrerRecursivo(ruta,new Filtro("txta"));

        // Ejercicio 3
        OperacionesIO.filtrarPorExtension(ruta,"txt");
        OperacionesIO.filtrarPorExtension(ruta,"txta");

        // Ejercicio 4
        OperacionesIO.filtrarPorExtensionYOrdenar(ruta, "txt", true);
        OperacionesIO.filtrarPorExtensionYOrdenar(ruta, "txta", true);

        // Ejercicio 5
        OperacionesIO.filtrarPorSubcadena(ruta, "arch");
        OperacionesIO.filtrarPorSubcadena(ruta, "zzz");
    }
}