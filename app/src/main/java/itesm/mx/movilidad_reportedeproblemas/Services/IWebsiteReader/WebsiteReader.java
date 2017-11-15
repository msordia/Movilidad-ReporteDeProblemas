package itesm.mx.movilidad_reportedeproblemas.Services.IWebsiteReader;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by juanc on 11/7/2017.
 * Based on: http://www.vogella.com/tutorials/JavaNetworking/article.html
 */

public class WebsiteReader implements IWebsiteReader {
    @Override
    public void getWebsiteContent(String urlString, IWebsiteHandler handler) {
        new ReadWebsiteTask(handler).execute(urlString);
    }

    @Override
    public void executePost(HttpClient client, HttpPost post, IWebsiteHandler handler) {
        new ExecutePostTask(handler, client).execute(post);
    }

    class ReadWebsiteTask extends AsyncTask<String, Integer, String>{
        private IWebsiteHandler _handler;

        ReadWebsiteTask(IWebsiteHandler handler) {
            _handler = handler;
        }

        @Override
        protected String doInBackground(String... strings) {
            return getContent(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            _handler.handle(s);
            super.onPostExecute(s);
        }

        @Override
        protected void onCancelled() {
            Log.e("ReadWebsiteTask", "Task was cancelled");
        }

        protected String getContent(String urlString) {
            try  {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String nextLine = "";
                    while ((nextLine = reader.readLine()) != null) {
                        sb.append(nextLine + '\n');
                    }
                } catch (IOException e) {
                    Log.e("getWebSiteContent", e.toString());
                }
                return sb.toString();
            } catch (Exception e) {
                Log.e("getWebSiteContent", e.toString());
            }

            return "";
        }
    }

    class ExecutePostTask extends AsyncTask<HttpPost, Integer, String>{
        private IWebsiteHandler _handler;
        private HttpClient _client;

        ExecutePostTask(IWebsiteHandler handler, HttpClient client) {
            _handler = handler;
            _client = client;
        }

        @Override
        protected String doInBackground(HttpPost... post) {
            try {
                return EntityUtils.toString(_client.execute(post[0]).getEntity());
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            _handler.handle(s);
            super.onPostExecute(s);
        }

        @Override
        protected void onCancelled() {
            Log.e("ExecutePostTask", "Task was cancelled");
        }

        protected String getContent(String urlString) {
            try  {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String nextLine = "";
                    while ((nextLine = reader.readLine()) != null) {
                        sb.append(nextLine + '\n');
                    }
                } catch (IOException e) {
                    Log.e("ExecutePostTask", e.toString());
                }
                return sb.toString();
            } catch (Exception e) {
                Log.e("ExecutePostTask", e.toString());
            }

            return "";
        }
    }
}
