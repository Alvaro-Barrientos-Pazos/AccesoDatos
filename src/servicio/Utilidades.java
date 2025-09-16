package servicio;

import excepciones.DirectorioNoExisteExcepcion;
import excepciones.NoEsDirectorioException;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utilidades {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String humanizeFileSize(long size){

        if (size <= 0) {
            return "0 Bytes";
        }

        final float KILOBYTES = 1024;
        final float MEGABYTES = 1024*1024;
        final float GIGABYTES = MEGABYTES*1024;

        if (size >= GIGABYTES){
            return String.format("%.2f GiB", size / GIGABYTES);
        }
        else if (size >= MEGABYTES) {
            return String.format("%.2f MiB", size / MEGABYTES);
        }
        else if (size >= KILOBYTES){
            return String.format("%.2f KiB", size / KILOBYTES);
        }

        return String.format("%d Bytes", size);
    }


    public static String formatMiliDate(long miliDate){
        //return new Date(miliDate).toString();
        return dtf.format(Instant.ofEpochMilli(miliDate).atZone(ZoneId.systemDefault()) );
    }


    public static void validarDirectorio(File file) throws DirectorioNoExisteExcepcion, NoEsDirectorioException {
        if (!file.exists()){
            throw new DirectorioNoExisteExcepcion();
        }

        if (!file.isDirectory()){
            throw new NoEsDirectorioException();
        }
    }


    public static void mostrarInfo(File file, String indent) {
        String type = file.isDirectory() ? "<DIR>" : "<FICHERO>";
        String size = file.isDirectory() ? "-" : humanizeFileSize(file.length());

        System.out.printf("%s|%-22s %-10s %-10s %s%n",
                indent,
                file.getName(),
                type,
                size,
                formatMiliDate(file.lastModified())
        );
    }


    public static void mostrarInfo(File file, String indent, boolean displayAbsolutePath) {
        String type = file.isDirectory() ? "<DIR>" : "<FICHERO>";
        String size = file.isDirectory() ? "-" : humanizeFileSize(file.length());
        String file_name = displayAbsolutePath ? file.getAbsolutePath() : file.getName();

        System.out.printf("%s|%-22s %-10s %-10s %s%n",
                indent,
                file_name,
                type,
                size,
                formatMiliDate(file.lastModified())
        );
    }

    public static String verifyExtension(String ext){
        return ext.startsWith(".") ? ext.toLowerCase() : "."+ext.toLowerCase();
    }

}