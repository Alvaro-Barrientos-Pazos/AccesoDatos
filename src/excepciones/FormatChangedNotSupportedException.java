package excepciones;

public class FormatChangedNotSupportedException extends Exception {
    public FormatChangedNotSupportedException(String ext1,String ext2) {
        super(String.format("El cambio de formato %s a %s no está soportado",ext1,ext2));
    }
}
