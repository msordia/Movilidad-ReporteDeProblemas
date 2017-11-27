package itesm.mx.movilidad_reportedeproblemas.Services.IFileReader;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;

//////////////////////////////////////////////////////////
//Clase: IFileReader
// Descripción: Interfaz de FileReader
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public interface IFileReader {
    byte[] readFile(Context context, Uri uri) throws IOException;
    byte[] readFile(Context context, String uri) throws IOException;
    void readFile(Context context, Uri uri, IFileHandler handler);

    public interface IFileHandler {
        void handle(byte[] bytes);
    }
}
