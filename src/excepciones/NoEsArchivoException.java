package excepciones;

public class NoEsArchivoException extends Exception {
    public NoEsArchivoException() {
        super("Se esperaba un archivo y se recibio un directorio");
    }
}
