package itesm.mx.movilidad_reportedeproblemas.Services.IFileReader;

import android.content.Context;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by juanc on 11/2/2017.
 */

public class FileReader implements IFileReader {
    @Override
    public byte[] readFile(Context context, Uri uri) throws IOException {
        InputStream iStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = iStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();

    }

    @Override
    public byte[] readFile(Context context, String uri) throws IOException {
        return readFile(context, Uri.parse(uri));
    }
}
