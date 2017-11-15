package itesm.mx.movilidad_reportedeproblemas.Services;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.concurrent.FutureCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by juanc on 11/15/2017.
 */

public class WebFileReader {
    public final static String BASE_URL = "http://www.reportesmovilidadtec.x10host.com/service.php?action=";
    public final static String DIR_IMAGE= "getImage&name=";
    public final static String DIR_FILE= "getFile&name=";
    public final static String DIR_VOICENOTE = "getVoicenote&name=";

    public static void readFile(String url, WebFileHandler handler) {
        new FileReaderTask(handler).execute(url);
    }

    public interface WebFileHandler {
        void handle(byte[] bytes);
    }

    private static class FileReaderTask extends AsyncTask<String, Integer, byte[]> {
        private WebFileHandler _handler;

        public FileReaderTask(WebFileHandler handler) {
            _handler = handler;
        }

        @Override
        protected byte[] doInBackground(String... strings) {
            return DownloadFiles(strings[0]);
        }

        public byte[] DownloadFiles(String url){
            try {
                URL u = new URL(url);
                InputStream is = u.openStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                return bos.toByteArray();
            } catch (MalformedURLException mue) {
                Log.e("FileReaderTask", "malformed url error", mue);
            } catch (IOException ioe) {
                Log.e("FileReaderTask", "io error", ioe);
            } catch (SecurityException se) {
                Log.e("FileReaderTask", "security error", se);
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
