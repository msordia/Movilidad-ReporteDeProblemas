package itesm.mx.movilidad_reportedeproblemas.Services;

/**
 * Created by juanc on 11/2/2017.
 */

public class FileNameFinder {
    public static String getName(String path) {
        int lastIndex = path.lastIndexOf('/');
        return path.substring(lastIndex + 1);
    }
}
