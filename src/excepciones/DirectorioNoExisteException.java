package excepciones;

public class DirectorioNoExisteException extends Exception {
    public DirectorioNoExisteException(String path) {
        super(String.format("El directorio %s no existe",path));
    }
}
