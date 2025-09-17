package excepciones;

public class NoEsDirectorioException extends Exception {
    public NoEsDirectorioException() {
        super("Se esperaba un directorio y se recibio un archivo");
    }
}
