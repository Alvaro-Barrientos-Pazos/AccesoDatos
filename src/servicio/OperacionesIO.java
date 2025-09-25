package servicio;

import excepciones.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class OperacionesIO {

/*  • Lista solo los archivos que tengan la extensión en el directorio indicado. */
    public static void filtrarPorExtension(String path, String ext) throws NoEsDirectorioException, DirectorioNoExisteException {
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

/*  • Lista solo los archivos que tengan la extensión indicada en el directorio especificado y en todos sus subdirectorios(recursivo), ordenados alfabéticamente.
    ✓ Si descendente es true → orden de Z a A.
    ✓ Si descendente es false → orden de A a Z.
    ✓ La ordenación ignora mayúsculas y minúsculas.
*/
    public static void filtrarPorExtensionYOrdenar(String path, String ext, boolean descending) throws NoEsDirectorioException, DirectorioNoExisteException {
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
/*  • Lista todos los archivos cuyo nombre contenga la subcadena indicada, en el directorio especificado y en todos sus subdirectorios (recursivo).
    • La búsqueda ignora mayúsculas y minúsculas.       */
    public static void filtrarPorSubcadena(String path, String substring) throws NoEsDirectorioException, DirectorioNoExisteException {
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

/*  • Lista el contenido de un directorio (solo el nivel actual).
    • Muestra nombre, tipo (<DIR> o <FICHERO>), tamaño en KB (solo ficheros) y fecha de última modificación.
    • Los siguientes errores que se puedan producir se controlarán lanzado excepciones propias y son:
        - Si el parámetro introducido no existe, se visualizará un mensaje de error correspondiente.
        - Si el parámetro introducido no representa a un directorio, se visualizará un mensaje de error correspondiente.   */
    public static void visualizarContenido(String path) throws NoEsDirectorioException, DirectorioNoExisteException {
        System.out.println("\n\n-- LISTANDO EL DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);

        for (File f : dir.listFiles()){
            Utilidades.mostrarInfo(f,"-");
        }
    }


    public static void visualizarContenido(String path, FilenameFilter filter) throws NoEsDirectorioException, DirectorioNoExisteException {
        System.out.println("\n\n-- LISTANDO EL DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);

        for (File f : dir.listFiles(filter)){
            Utilidades.mostrarInfo(f,"-");
        }
    }

/*  · Lista el contenido de un directorio y todos sus subdirectorios con sangría.   */
    public static void recorrerRecursivo(String path) throws DirectorioNoExisteException, NoEsDirectorioException {
        System.out.println("\n\n-- JERARQUÍA DEL DIRECTORIO "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);
        recorrer(dir, "-", 0, null);
    }


    public static void recorrerRecursivo(String path, FilenameFilter filter) throws DirectorioNoExisteException, NoEsDirectorioException {
        System.out.println("\n\n-- JERARQUÍA DEL DIRECTORIO FILTRADA "+path.toUpperCase());

        File dir = new File(path);
        Utilidades.validarDirectorio(dir);
        recorrer(dir, "-", 0, filter);
    }


    public static void recorrer(File dir, String preffix, int depth, FilenameFilter filter) throws DirectorioNoExisteException, NoEsDirectorioException {

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


    public static ArrayList<File> getDirectoryTree(File dir, FilenameFilter filter) throws NoEsDirectorioException, DirectorioNoExisteException {

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


/*  • Copia un archivo desde una ruta de origen a una ruta de destino.
    • Si el directorio destino no existe, lo crea.
    • Si ya existe un archivo con el mismo nombre en destino, lo sobrescribe.       */
    public static File copiarArchivo(String origen, String destino) throws ArchivoNoExisteException, IOException, NoEsArchivoException, FormatChangedNotSupportedException {

        File sourceFile = new File(origen);
        Utilidades.validarArchivo(sourceFile);

        String targetPath = Utilidades.resolvePath(sourceFile,destino);
        File targetFile = new File(targetPath);

        Utilidades.createNestedDirectories( targetFile.getParentFile() );

        // Gravar por flujo de bytes en bloques de 8KB
        try(FileOutputStream output = new FileOutputStream(targetFile);
            FileInputStream input = new FileInputStream(sourceFile)){

            byte[] buffer = new byte[8192];
            int bytePointer;
            while ( (bytePointer = input.read(buffer) ) != -1) {
                output.write(buffer, 0, bytePointer);
            }
        }catch (Exception e){
            System.out.printf("Error al escribir el archivo con ruta: %s%n",targetFile.getAbsolutePath());
        }

        return sourceFile;
    }


/*  • Mueve un archivo desde una ruta de origen a una ruta de destino.
    • Si el directorio destino no existe, lo crea.
    • Si ya existe un archivo con el mismo nombre en destino, lo sobrescribe.       */
    public static void moverArchivo(String origen, String destino) throws ArchivoNoExisteException, NoEsArchivoException, FormatChangedNotSupportedException, IOException {

        File sourceFile = new File(origen);

        if (sourceFile.isDirectory()){
            System.out.println("No se puede mover directorios");
            return;
        }

        sourceFile = copiarArchivo(origen,destino);

        boolean deletionResult = sourceFile.delete();

        if (!deletionResult){
            System.out.println("Error al borrar el archivo original");
        }
    }


    public static void copiarDirectorio(String origen, String destino) throws DirectorioNoExisteException, NoEsDirectorioException, ArchivoNoExisteException, IOException, NoEsArchivoException, FormatChangedNotSupportedException {

        File sourceFile = new File(origen);
        Utilidades.validarDirectorio(sourceFile);
        File targetFile = new File(destino);

        if (Utilidades.validatePathDrive(destino)){

            Utilidades.createNestedDirectories(targetFile);

            File[] files = sourceFile.listFiles();

            if  (files == null) {
                System.out.println("Error al leer el directorio original "+origen);
                return;
            }

            String newDirPath;

            for (File f : files){
                if  (f.isDirectory()){
                    newDirPath = new File(destino,f.getName()).getPath();
                    copiarDirectorio(f.getAbsolutePath(),newDirPath);
                }
                else{
                    copiarArchivo(f.getAbsolutePath(),targetFile.getAbsolutePath());
                }
            }
        }

    }


    public static void borrar(String ruta) throws IOException {

        File sourceFile = new File(ruta);

        if (!sourceFile.exists()){
            throw new IOException("No se encontro ni archivo ni directorio con la ruta: "+ruta);
        }

        if (sourceFile.isDirectory()){
            borrarRecursivo(sourceFile);
        }
        else{
            borrarArchivo(sourceFile);
        }

    }

    public static void borrarRecursivo(File source){

        File[] files = source.listFiles();

        if (files == null){
            System.out.println("No se puede leer el contenido del directorio: "+source.getAbsolutePath());
            return;
        }

        for (File f : files){
            if (f.isDirectory()){
                borrarRecursivo(f);
            }
            else{
                borrarArchivo(f);
            }
        }
        borrarDirectorio(source);
    }

    public static void borrarArchivo(File file){
        if (!file.delete()){
            System.out.println("Error al borrar el archivo: "+file.getAbsolutePath());
        }
    }

    public static void borrarDirectorio(File dir){
        if (!dir.delete()){
            System.out.println("Error al borrar el directorio: "+dir.getAbsolutePath());
        }
    }

}
