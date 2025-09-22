package servicio;

import excepciones.DirectorioNoExisteException;
import excepciones.NoEsDirectorioException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class OperacionesNIO {

    // Opcion 1 Stream
    public static void visualizarContenido(String ruta) throws DirectorioNoExisteException, NoEsDirectorioException {

        Path path = Paths.get(ruta);
        Utilidades.validarDirectorio(path.toFile());

        try (Stream<Path> paths = Files.walk(path)){
            paths.forEach(Utilidades::mostrarInfo);

        } catch (Exception e) {
            throw new RuntimeException(e);
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


}
