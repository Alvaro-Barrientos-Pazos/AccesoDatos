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

    public void mostrarInformacion(File file){
        return;
    }
}
