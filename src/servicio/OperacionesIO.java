package servicio;

import excepciones.DirectorioNoExisteExcepcion;
import excepciones.NoEsDirectorioException;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OperacionesIO {


    public static void visualizarContenido(String path) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {
        File file = new File(path);

        System.out.println("--- LISTANDO EL DIRECTORIO "+path.toUpperCase());

        Utilidades.validarDirectorio(file);

        System.out.println();

        String type = null;
        String size = null;


        for (File f : file.listFiles()){
            type = f.isDirectory() ? "<DIR>" : "<FICHERO>";
            size = f.isDirectory() ? "-" : Utilidades.humanizeFileSize(f.length());

            System.out.printf( "-|%-22s %-10s %-10s %s\n",
                f.getName(), type, size, Utilidades.formatMiliDate(f.lastModified())
            );
        }
    }


    public static void visualizarContenidoFiltrado(String path, FilenameFilter filtro) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {
        File file = new File(path);

        System.out.println("--- LISTANDO EL DIRECTORIO "+path.toUpperCase());

        Utilidades.validarDirectorio(file);

        System.out.println();

        String type = null;
        String size = null;

        for (File f : file.listFiles(filtro)){
            type = f.isDirectory() ? "<DIR>" : "<FICHERO>";
            size = f.isDirectory() ? "-" : Utilidades.humanizeFileSize(f.length());

            System.out.printf( "-|%-22s %-10s %-10s %s\n",
                    f.getName(), type, size, Utilidades.formatMiliDate(f.lastModified())
            );
        }
    }


    public static void recorrerRecursivo(String path) throws DirectorioNoExisteExcepcion, NoEsDirectorioException {
        File dir = new File(path);
        Utilidades.validarDirectorio(dir);
        recorrerRecursivo(dir, "-", 0);
    }

    public static void recorrerRecursivo(String path, FilenameFilter filter) throws DirectorioNoExisteExcepcion, NoEsDirectorioException {
        File dir = new File(path);
        Utilidades.validarDirectorio(dir);
        recorrerRecursivo(dir, "-", 0, filter);
    }


    public static void recorrerRecursivo(File dir, String preffix, int depth) throws DirectorioNoExisteExcepcion, NoEsDirectorioException {
        String type = null;
        String size = null;

        for (File f : dir.listFiles()){
            type = f.isDirectory() ? "<DIR>" : "<FICHERO>";
            size = f.isDirectory() ? "" : Utilidades.humanizeFileSize(f.length());

            // Date añade un espacio al comienzo de la string por alguna razon
            System.out.printf( "%s|%s %s %s%s%n",
                    preffix.repeat(depth*2),f.getName(), type, size, Utilidades.formatMiliDate(f.lastModified())
            );

            if (f.isDirectory()){
                recorrerRecursivo(f, preffix,depth+1);
            }
        }
    }


    public static void recorrerRecursivo(File dir, String preffix, int depth, FilenameFilter filter) throws DirectorioNoExisteExcepcion, NoEsDirectorioException {
        String type = null;
        String size = null;

        for (File f : dir.listFiles(filter)){
            type = f.isDirectory() ? "<DIR>" : "<FICHERO>";
            size = f.isDirectory() ? "" : Utilidades.humanizeFileSize(f.length());

            // Date añade un espacio al comienzo de la string por alguna razon
            System.out.printf( "%s|%s %s %s%s%n",
                    preffix.repeat(depth*2),f.getName(), type, size, Utilidades.formatMiliDate(f.lastModified())
            );

            if (f.isDirectory()){
                recorrerRecursivo(f, preffix,depth+1);
            }
        }
    }
}
