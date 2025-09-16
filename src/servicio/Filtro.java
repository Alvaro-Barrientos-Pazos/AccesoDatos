package servicio;

import java.io.File;
import java.io.FilenameFilter;

public class Filtro implements FilenameFilter {

    private String ext;

    public Filtro(String ext) {
        this.ext = ext;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(ext);
    }
}
