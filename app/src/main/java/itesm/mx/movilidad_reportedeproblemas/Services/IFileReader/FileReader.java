package itesm.mx.movilidad_reportedeproblemas.Services.IFileReader;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

//////////////////////////////////////////////////////////
//Clase: FileReader
// Descripción: Lector de archivos.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class FileReader implements IFileReader {
    @Override
    public byte[] readFile(Context context, Uri uri) throws IOException {
        InputStream iStream = context.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while (-1 != (len = iStream.read(buffer))) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();

    }

    @Override
    public byte[] readFile(Context context, String uri) throws IOException {
        return readFile(context, Uri.parse(uri));
    }

    @Override
    public void readFile(Context context, Uri uri, IFileHandler handler) {
        new ReadFileTask(context, handler).execute(uri);
    }

    private class ReadFileTask extends AsyncTask<Uri, Integer, byte[]> {
        private IFileHandler _handler;
        private Context _context;

        ReadFileTask(Context context, IFileHandler handler){
            _handler = handler;
            _context = context;
        }

        @Override
        protected byte[] doInBackground(Uri... uris) {
            Uri uri = uris[0];

            try {
                return readFile(_context, uri);
            } catch (IOException e) {
                Log.e("ReadFileTask", e.toString());
            }
            return new byte[0];
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            _handler.handle(bytes);
        }
    }
}
