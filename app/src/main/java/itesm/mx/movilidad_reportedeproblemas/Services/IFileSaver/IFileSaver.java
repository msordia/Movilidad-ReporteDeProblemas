package itesm.mx.movilidad_reportedeproblemas.Services.IFileSaver;

import android.content.Context;

//////////////////////////////////////////////////////////
//Clase: IFileSaver
// Descripción: Interfaz de IFileSaver
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public interface IFileSaver {
    int STATUS_SUCCESS = 1;
    int STATUS_FAILURE = 2;

    public void SaveFile(byte[] bytes, String name, IFileSavedHandler handler, Context context);

    public interface IFileSavedHandler {
        void handle(int statusCode);
    }
}
