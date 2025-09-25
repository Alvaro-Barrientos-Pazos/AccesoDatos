package servicio;

import excepciones.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class OperacionesNIO {

    // Opcion 1 Stream
    public static void visualizarContenido(String ruta) throws DirectorioNoExisteException, NoEsDirectorioException, IOException {

        Path path = Paths.get(ruta);
        Utilidades.validarDirectorio(path.toFile());

        try (Stream<Path> paths = Files.walk(path)){
            paths.filter(Files::isRegularFile)  // comprobar si se tiene permisos de lectura    // catch AccessDeniedException
                    //.filter(Files::isHidden)    // para isHidden hay que implementarle un try & cath individual dentro de la lambda con IOException
            .forEach(Utilidades::mostrarInfo);
        }
    }


    // Opcion 2  DirectoryStream
    public static void visualizarContenido1(String ruta) throws DirectorioNoExisteException, NoEsDirectorioException, IOException {

        Path path = Paths.get(ruta);
        Utilidades.validarDirectorio(path.toFile());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path);){
            // Opcion A
            stream.forEach(p -> {
                String sangria = "---";
                Utilidades.mostrarInfo(p.toFile(),sangria.repeat(p.relativize(path).getNameCount()-1) );
            });

            // Opcion B
            String sangria = "---";

            for (Path p : stream){
                Utilidades.mostrarInfo(p.toFile(),sangria.repeat(p.relativize(path).getNameCount()-1) );
            }
        }
    }

    // Opcion 1 -> No recursivo
    // El filtro se puede aplicar directamente en newDirectoryStream
    public static void filtrarPorExtension(String ruta, String extension) throws IOException {
        Utilidades.verifyExtension(extension);
        Path path = Paths.get(ruta);

        // usamos un boolean para indicar si se ha encontrado algun archivo
        boolean archivosEncontrados = false;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path,"."+extension)){

            for (Path p : stream){
                Utilidades.mostrarInfo(p);
                archivosEncontrados = true;
            }

            if (!archivosEncontrados){
                System.out.println("No se encontro ningun archivo en: "+ruta);
            }

        }catch (AccessDeniedException e){
            System.out.println("No se tiene permisos de lectura en: "+ruta);
        }
    }

    // Opcion 2 -> no recursivo

    public static void filtrarPorExtension3(String ruta, String extension) throws IOException {
        Utilidades.verifyExtension(extension);
        Path path = Paths.get(ruta);

        // Usamos la clase AtomicInteger para indicar si se ha encontrado algun archivo
        // esta clase permite usarse dentro de una lambda saltandose la restriccion de que las variables externas sean final dentro de una lambda
        AtomicInteger contador = new AtomicInteger(0);

        try (Stream<Path> stream = Files.list(path)){
            stream.filter( (Path p) -> p.getFileName().toString().endsWith(extension) )
                    .forEach( (Path p ) -> {
                        Utilidades.mostrarInfo(p);
                        contador.incrementAndGet();
                    });

            if (contador.get() == 0){
                System.out.println("No se encontro ningun archivo con extensionen: "+ruta);
            }

        }
    }

    // Opcion 1 -> Recursivo
    // usar un filtro en el stream del walk
    public static void filtrarPorExtensionRecursivo(String ruta, String extension) throws IOException {
        Utilidades.verifyExtension(extension);
        Path path = Paths.get(ruta);

        try (Stream<Path> stream = Files.walk(path)){
            stream.filter(Files::isRegularFile)
                    .filter(Files::isReadable)
                    .filter((Path p) -> p.getFileName().toString().endsWith(extension))
                    .forEach(Utilidades::mostrarInfo);

        }catch (AccessDeniedException e){
            System.out.println("no se tiene permisos de lectura en: "+ruta);
        }
    }

    public static void recorrerRecursivo(String ruta) throws IOException {
        Path path = Paths.get(ruta);

        SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Utilidades.mostrarInfo(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("No se ha podido acceder al archivo " + file.toAbsolutePath());
                return FileVisitResult.CONTINUE;
            }
        };

        Files.walkFileTree(path, visitor);

        /*  Se puede definir en la misma linea con la siguiente syntax

        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {}
        };
        */
    }

    public static void filtrarPorExtensionYOrdenar(String ruta, String extension, boolean descendente) throws IOException {

        try(Stream<Path> stream = Files.list(Paths.get(ruta))){

            Comparator<Path> comp = Comparator.comparing(p-> p.getFileName().toString().toLowerCase());

            if (descendente){
                comp.reversed();
            }

            // Comparator con lambda
            // stream.sorted((Path p1, Path p2) -> p1.getFileName().toString().toLowerCase().compareTo(p2.getFileName().toString().toLowerCase()))

            stream.filter( (Path p) -> p.getFileName().toString().endsWith(extension) )
                .sorted(comp)
                .forEach(Utilidades::mostrarInfo);

        }catch (AccessDeniedException e){
            System.out.println("No se tiene permisos de lectura en: "+ruta);
        }
    }


    public void copiarArchivo(String rutaOrigen, String rutaDestino) throws IOException, ArchivoNoExisteException, NoEsArchivoException, FormatChangedNotSupportedException {
        File oriFile = new File(rutaOrigen);
        Utilidades.validarArchivo(oriFile);

        if (Utilidades.validatePathDrive(rutaDestino)){
            Path oriPath = Paths.get(rutaOrigen);

            Utilidades.createNestedDirectories(oriFile);
            Path resolvedPath = Paths.get(Utilidades.resolvePath(oriFile,rutaDestino));
            Files.copy(oriPath, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }


    public void moverArchivo(String rutaOrigen, String rutaDestino) throws ArchivoNoExisteException, IOException, NoEsArchivoException, FormatChangedNotSupportedException {
        File oriFile = new File(rutaOrigen);
        Utilidades.validarArchivo(oriFile);

        if (Utilidades.validatePathDrive(rutaDestino)){
            Path oriPath = Paths.get(rutaOrigen);
            Path destPath = Paths.get(rutaDestino);

            Utilidades.createNestedDirectories(oriFile);
            Path resolvedPath = Paths.get(Utilidades.resolvePath(oriFile,rutaDestino));
            Files.move(oriPath,destPath,StandardCopyOption.REPLACE_EXISTING);
        }

    }

}
