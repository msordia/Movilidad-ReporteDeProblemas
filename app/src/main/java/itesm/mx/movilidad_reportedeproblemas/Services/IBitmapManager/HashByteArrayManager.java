package itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager;

import java.util.Collection;
import java.util.HashSet;

//////////////////////////////////////////////////////////
//Clase: HashByteArrayManager
// Descripción: Administra los bytes.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class HashByteArrayManager implements IByteArrayManager {
    private HashSet<byte[]> _byteArrays = new HashSet<>();

    @Override
    public void addByteArray(byte[] bytes) {
        _byteArrays.add(bytes);
    }

    @Override
    public void removeByteArray(byte[] bytes) {
        _byteArrays.remove(bytes);
    }

    @Override
    public Collection<byte[]> getByteArrays() {
        return _byteArrays;
    }
}
