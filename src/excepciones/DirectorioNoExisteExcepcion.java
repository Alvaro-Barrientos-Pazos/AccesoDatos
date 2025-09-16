package excepciones;

public class DirectorioNoExisteExcepcion extends Exception {
    public DirectorioNoExisteExcepcion() {
        super("Directorio no existe");
    }
}
