package itesm.mx.movilidad_reportedeproblemas.Services.IFileSaver;

import android.content.Context;

/**
 * Created by juanc on 11/16/2017.
 */

public interface IFileSaver {
    int STATUS_SUCCESS = 1;
    int STATUS_FAILURE = 2;

    public void SaveFile(byte[] bytes, String name, IFileSavedHandler handler, Context context);

    public interface IFileSavedHandler {
        void handle(int statusCode);
    }
}
