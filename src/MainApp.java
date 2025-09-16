import excepciones.DirectorioNoExisteExcepcion;
import excepciones.NoEsDirectorioException;
import servicio.OperacionesIO;

public class MainApp {
    public static void main(String[] args) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {

        //OperacionesIO.visualizarContenido("P:\\AccesoDatos\\Directorios");
        OperacionesIO.visualizarContenido("D:\\abarrpazo\\Github\\AccesoDatos\\Directorios");

        System.out.println("\nOperacionesIO.recorrerRecursivo");
        //OperacionesIO.recorrerRecursivo("P:\\AccesoDatos\\Directorios","-",1);
        OperacionesIO.recorrerRecursivo("D:\\abarrpazo\\Github\\AccesoDatos\\Directorios");
        //OperacionesIO.recorrerRecursivo("D:\\abarrpazo\\Github\\AccesoDatos\\Directorios","-",1);

        System.out.println("Final de programa");

    }
}