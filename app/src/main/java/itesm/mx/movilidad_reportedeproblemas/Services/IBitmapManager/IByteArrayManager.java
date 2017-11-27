package itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager;

import java.util.Collection;

//////////////////////////////////////////////////////////
//Clase: IByteArrayManager
// Descripción: Interfaz de array manager
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public interface IByteArrayManager {
    void addByteArray(byte[] bytes);
    void removeByteArray(byte[] bytes);
    Collection<byte[]> getByteArrays();
}
