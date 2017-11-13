package itesm.mx.movilidad_reportedeproblemas.Services.IFileReader;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;

public interface IFileReader {
    byte[] readFile(Context context, Uri uri) throws IOException;
    byte[] readFile(Context context, String uri) throws IOException;
    void readFile(Context context, Uri uri, IFileHandler handler);

    public interface IFileHandler {
        void handle(byte[] bytes);
    }
}
