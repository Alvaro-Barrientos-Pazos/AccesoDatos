import servicio.OperacionesIO;

public class MainApp {
    public static void main(String[] args) {

        OperacionesIO.visualizarContenido("P:\\AccesoDatos\\Directorios");

        System.out.println("\nOperacionesIO.recorrerRecursivo");
        OperacionesIO.recorrerRecursivo("P:\\AccesoDatos\\Directorios","-",1);

    }
}