package servicio;

import excepciones.DirectorioNoExisteExcepcion;
import excepciones.NoEsDirectorioException;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class OperacionesIO {


    public static void filtrarPorExtension(String path, String ext) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {
        System.out.println("\n\n-- ARCHIVOS FILTRADOS POR EXTENSION EN DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);
        ext = Utilidades.verifyExtension(ext);

        Filtro filter = new Filtro(ext);
        File[] files = dir.listFiles(filter);

        if (files.length == 0){
            System.out.println("No se encontro ningún archivo con la extensión: "+ext);
            return;
        }

        for (File f : files){
            Utilidades.mostrarInfo(f,"-");
        }
    }


    public static void filtrarPorExtensionYOrdenar(String path, String ext, boolean descending) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {
        System.out.println("\n\n-- ARCHIVOS FILTRADOS POR EXTENSION Y ORDENADO EN DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);
        ext = Utilidades.verifyExtension(ext);

        Filtro filter = new Filtro(ext);
        ArrayList<File> filesList = getDirectoryTree(dir,filter);

        if (filesList.size() == 0){
            System.out.println("No se encontro ningún archivo con la extensión: "+ext);
        }
        else{
            Comparator<File> comparator = Comparator.comparing(File::getName);

            if (descending){
                comparator = comparator.reversed();
            }

            filesList.sort(comparator);

            for (File f : filesList){
                Utilidades.mostrarInfo(f,"-", true);
            }
        }

    }

    public static void filtrarPorSubcadena(String path, String substring) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {
        System.out.println("\n\n-- ARCHIVOS FILTRADOS POR SUBCADENA EN DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);

        FiltroSubcadena filter = new FiltroSubcadena(substring);
        ArrayList<File> filesList = getDirectoryTree(dir,filter);

        if (filesList.size() == 0){
            System.out.println("No se encontro ningún archivo que contenga la substring: "+substring);
        }
        else{
            for (File f : filesList){
                Utilidades.mostrarInfo(f,"-", true);
            }
        }
    }


    public static void visualizarContenido(String path) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {
        System.out.println("\n\n-- LISTANDO EL DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);

        for (File f : dir.listFiles()){
            Utilidades.mostrarInfo(f,"-");
        }
    }


    public static void visualizarContenido(String path, FilenameFilter filter) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {
        System.out.println("\n\n-- LISTANDO EL DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);

        for (File f : dir.listFiles(filter)){
            Utilidades.mostrarInfo(f,"-");
        }
    }


    public static void recorrerRecursivo(String path) throws DirectorioNoExisteExcepcion, NoEsDirectorioException {
        System.out.println("\n\n-- JERARQUÍA DEL DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);
        recorrer(dir, "-", 0, null);
    }


    public static void recorrerRecursivo(String path, FilenameFilter filter) throws DirectorioNoExisteExcepcion, NoEsDirectorioException {
        System.out.println("\n\n-- JERARQUÍA DEL DIRECTORIO FILTRADA "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);
        recorrer(dir, "-", 0, filter);
    }


    public static void recorrer(File dir, String preffix, int depth, FilenameFilter filter) throws DirectorioNoExisteExcepcion, NoEsDirectorioException {

        if (depth < 0){
            depth = 0;
        }

        File[] files = dir.listFiles();

        for (File f : files){

            if (f.isDirectory()){
                recorrer(f, preffix,depth+1, filter);
            }

            if (filter == null || filter.accept(dir, f.getName())) {
                Utilidades.mostrarInfo(f,preffix.repeat(depth*2));
            }
        }
    }

    public static ArrayList<File> getDirectoryTree(File dir, FilenameFilter filter) throws NoEsDirectorioException, DirectorioNoExisteExcepcion {

        File[] files = dir.listFiles();
        ArrayList<File> fileList = new ArrayList<>();

        for (File f : files){
            if (f.isDirectory()){
                fileList.addAll(getDirectoryTree(f,filter));
            }
            else if( filter.accept(dir,f.getName()) ){
                fileList.add(f);
            }

        }
        return fileList;
    }

}
