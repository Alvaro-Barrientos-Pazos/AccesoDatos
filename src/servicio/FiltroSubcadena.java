package servicio;

import java.io.File;
import java.io.FilenameFilter;

public class FiltroSubcadena implements FilenameFilter {

    private String substring;

    FiltroSubcadena(String substring) {
        this.substring = substring.toLowerCase();
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().contains(substring.toLowerCase());
    }
}