package itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager;

import java.util.Collection;

public interface IByteArrayManager {
    void addByteArray(byte[] bytes);
    void removeByteArray(byte[] bytes);
    Collection<byte[]> getByteArrays();
}
