package servicio;

import java.io.File;
import java.util.Date;

public class OperacionesIO {


    public static void visualizarContenido(String path){
        File file = new File(path);

        System.out.println("--- LISTANDO EL DIRECTORIO "+path.toUpperCase());

        if (!file.exists()){
            System.out.println("Error: La ruta especificada no existe");
            return;
        }

        if (!file.isDirectory()){
            System.out.println("Error: La ruta no es un directorio.");
            return;
        }

        System.out.println();

        String type = null;
        String size = null;

        for (File f : file.listFiles()){
            type = f.isDirectory() ? "<DIR>" : "<FICHERO>";
            size = f.isDirectory() ? "-" : humanizeFileSize(f.length());

            System.out.printf( "-|%-22s %-10s %-10s %s\n",
                f.getName(), type, size, new Date(f.lastModified())
            );
        }
    }

    private static String humanizeFileSize(long size){

        if (size <= 0) return "0 Bytes";

        String msg = null;

        final float KILOBYTES = 1024;
        final float MEGABYTES = 1024*1024;
        final float GIGABYTES = MEGABYTES*1024;

        if (size >= GIGABYTES){
            msg = String.format("%.2f GiB", size / GIGABYTES);
        }
        else if (size >= MEGABYTES) {
            msg = String.format("%.2f MiB", size / MEGABYTES);
        }
        else if (size >= KILOBYTES){
            msg = String.format("%.2f KiB", size / KILOBYTES);
        }
        else {
            msg = String.format("%d Bytes", size);
        }

        return msg;
    }


    public static void recorrerRecursivo(String path, String preffix, int depth){
        File file = new File(path);

        if (!file.exists()){
            System.out.println("Error: La ruta especificada no existe");
            return;
        }

        if (!file.isDirectory()){
            System.out.println("Error: La ruta no es un directorio.");
            return;
        }


        String type = null;
        String size = null;

        for (File f : file.listFiles()){
            type = f.isDirectory() ? "<DIR>" : "<FICHERO>";
            size = f.isDirectory() ? "" : humanizeFileSize(f.length());

            // Date añade un espacio al comienzo de la string por alguna razon
            System.out.printf( "%s|%s %s %s%s\n",
                    preffix.repeat(depth*2),f.getName(), type, size, new Date(f.lastModified())
            );

            if (f.isDirectory()){
                recorrerRecursivo(f.getPath(), preffix,depth+1);
            }
        }

    }
}
