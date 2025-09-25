package servicio;

import excepciones.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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


    public static void validarDirectorio(File dir) throws DirectorioNoExisteException, NoEsDirectorioException {
        if (!dir.exists()){
            throw new DirectorioNoExisteException(dir.getPath());
        }

        if (!dir.isDirectory()){
            throw new NoEsDirectorioException();
        }
    }


    public static void validarArchivo(File file) throws ArchivoNoExisteException, NoEsArchivoException {
        if (!file.exists()) {
            throw new ArchivoNoExisteException(file.getPath());
        }
        if (!file.isFile()) {
            throw new NoEsArchivoException();
        }
    }

    public static void mostrarInfo(Path path){
        mostrarInfo(path.toFile(),"-");
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


    public static String findPathExtension(String path){
        String ext = "";
        int extIndex = path.lastIndexOf('.');

        if ( extIndex > 0 && extIndex != path.length() - 1 ) {
            ext = path.substring(extIndex);
        }

         return ext;
    }


    // Si en la ruta destino se especifica una extension y esta es la misma extension que la del archivo origen se aplica
    // un nuevo nombre al archivo destino.
    // Si no hay extension se entiende que el usuario solo ha especificado la ruta del directorio destino por lo que se usa el nombre
    // original del archivo con su extension.
    public static String resolvePath(File sourceFile, String target) throws FormatChangedNotSupportedException {
        String ext = Utilidades.findPathExtension(target);
        String targetPath = target;

        if (ext.isEmpty()){
            targetPath = String.format("%s/%s",target,sourceFile.getName());
        }
        else if (!sourceFile.getPath().endsWith(ext)){
            // Actualmente cambiarle el formato al archivo produce una excepcion
            String sourceExt = Utilidades.findPathExtension(sourceFile.getName());
            throw new FormatChangedNotSupportedException(sourceExt,ext);
        }

        return targetPath;
    }

    public static void createNestedDirectories(File targetDir){
        if (!targetDir.exists()) {
            boolean creationResult = targetDir.mkdirs();

            if (!creationResult){
                System.out.printf("Error al crear directorios con ruta: %s",targetDir.getAbsolutePath());
            }
        }
    }

    //TODO: Maybe this can be reemplaced by Path.getRoot()
    public static boolean validatePathDrive(String path){
        String[] disk = path.split("(:/|:\\\\)", 2);

        if (!disk[0].equalsIgnoreCase("D") && !disk[0].equalsIgnoreCase("C")){
            System.out.println("Solo se aceptan rutas de la unidad D: y C:");
            return false;
        }
        return true;
    }

}