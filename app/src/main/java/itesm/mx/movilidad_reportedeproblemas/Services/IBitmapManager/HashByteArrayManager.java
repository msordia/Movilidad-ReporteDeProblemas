package itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager;

import java.util.Collection;
import java.util.HashSet;

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
