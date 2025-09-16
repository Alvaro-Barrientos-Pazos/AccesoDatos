package servicio;

import java.io.File;
import java.io.FilenameFilter;

public class Filtro implements FilenameFilter {

    private String ext;

    Filtro(String ext) {
        this.ext =  ext.startsWith(".") ? ext.toLowerCase() : "."+ext.toLowerCase();
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(ext);
    }
}
