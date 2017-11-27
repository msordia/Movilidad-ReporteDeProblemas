package itesm.mx.movilidad_reportedeproblemas.Services;

//////////////////////////////////////////////////////////
//Clase: FileNameFinder
// Descripción: Esta clase busca los archivos
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class FileNameFinder {
    public static String getName(String path) {
        int lastIndex = path.lastIndexOf('/');
        return path.substring(lastIndex + 1);
    }
}
