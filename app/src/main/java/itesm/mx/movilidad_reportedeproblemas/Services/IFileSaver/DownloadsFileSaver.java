package itesm.mx.movilidad_reportedeproblemas.Services.IFileSaver;

import android.app.DownloadManager;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;

/**
 * Created by juanc on 11/16/2017.
 */

public class DownloadsFileSaver implements IFileSaver {
    @Override
    public void SaveFile(byte[] bytes, String name, IFileSavedHandler handler, Context context) {
        if (isExternalStorageWritable()) {
            File file = getAlbumStorageDir(name);

            if (file.exists())
                file.delete();

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                handler.handle(IFileSaver.STATUS_FAILURE);
            }

            try {
                WriteFileTask.Parameters params = new WriteFileTask.Parameters();
                params.path = file.getAbsolutePath();
                params.bytes = bytes;
                params.context = context;
                params.os = new FileOutputStream(file);
                params.handler = handler;
                params.name = name;

                new WriteFileTask().execute(params);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                handler.handle(IFileSaver.STATUS_FAILURE);
            }

        } else {
            Log.e("DownloadsFileSaver", "No external storage found.");
            handler.handle(IFileSaver.STATUS_FAILURE);
        }
    }

    public File getAlbumStorageDir(String name) {
        File parent = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS);
        return new File(parent, name);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private static class WriteFileTask extends AsyncTask<WriteFileTask.Parameters, Integer, Boolean> {
        private Parameters params;

        public static class Parameters {
            public byte[] bytes;
            public FileOutputStream os;
            public IFileSavedHandler handler;
            public String path;
            public String name;
            public Context context;
        }

        @Override
        protected Boolean doInBackground(Parameters... args) {
            params = args[0];

            try {
                FileOutputStream os = params.os;
                os.write(params.bytes);
                os.flush();
                os.close();

                DownloadManager manager = (DownloadManager) params.context.getSystemService(Context.DOWNLOAD_SERVICE);
                if (manager != null) {
                    String mimeType= URLConnection.guessContentTypeFromName(params.name);
                    manager.addCompletedDownload(params.name, "Reportes de movilidad Tec", true, mimeType, params.path, params.bytes.length, true);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            params.handler.handle(aBoolean ? IFileSaver.STATUS_SUCCESS : IFileSaver.STATUS_FAILURE);
        }
    }
}
