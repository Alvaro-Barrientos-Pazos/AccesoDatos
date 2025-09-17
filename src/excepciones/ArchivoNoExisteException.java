package excepciones;

public class ArchivoNoExisteException extends Exception {
    public ArchivoNoExisteException(String path) {
        super(String.format("El archivo %s no existe", path));
    }
}
