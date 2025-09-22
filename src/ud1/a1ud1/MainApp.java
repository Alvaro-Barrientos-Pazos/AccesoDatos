package ud1.a1ud1;

import excepciones.*;
import servicio.Filtro;
import servicio.OperacionesIO;
import servicio.OperacionesNIO;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws NoEsDirectorioException, DirectorioNoExisteException, ArchivoNoExisteException, IOException, NoEsArchivoException, FormatChangedNotSupportedException {

        // Usar rutas relativas con isDirectory puede dar un falso positivo con archivos...
        //String rutaBase = System.getProperty("user.dir");

        //String rutaBase = "D:\\abarrpazo\\AccesoDatos\\Directorios";
        String rutaBase = "G:\\DAM\\DAM2\\AccesoDatos\\Directorios";

        //ejercicios_1_to_5(rutaBase);


        // Ejercicio 6
        final String NOMBRE_ARCHIVO = "aranna_casera_3.jpg";
        final String RUTA_ORIGEN = rutaBase+"\\"+NOMBRE_ARCHIVO;

        //String rutaDestino = rutaBase+"\\Fotos\\Arañas\\Caseras";             // Sin especificar nombre
        String rutaDestino = rutaBase+"\\Fotos\\aranna_3.jpg";                  // Cambiando nombre
        //String rutaDestino = rutaBase+"\\Fotos\\aranna_casera_3.jpag";       // Excepcion de extension
        //OperacionesIO.copiarArchivo(RUTA_ORIGEN, rutaDestino );      // DEBUG: Comentar cuando se vaya a usar con el copiar del ejercicio 7


        // Ejercicio 7
        rutaDestino = rutaBase+"\\Fotos\\Arañas\\Caseras\\Mover";
        //OperacionesIO.moverArchivo(RUTA_ORIGEN, rutaDestino );
        //OperacionesIO.copiarArchivo(rutaDestino+"\\"+NOMBRE_ARCHIVO, RUTA_ORIGEN );  // DEBUG: copiar el archivo recien creado en su posicion original tras borrarlo


        OperacionesIO.copiarDirectorio(rutaBase,"G:/");

    }


    static void ejercicios_1_to_5(String ruta) throws NoEsDirectorioException, DirectorioNoExisteException {

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