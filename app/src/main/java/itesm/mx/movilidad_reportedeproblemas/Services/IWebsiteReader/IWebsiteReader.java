package itesm.mx.movilidad_reportedeproblemas.Services.IWebsiteReader;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

/**
 * Created by juanc on 11/7/2017.
 */

public interface IWebsiteReader {
    void getWebsiteContent(String url, IWebsiteHandler handler);
    void executePost(HttpClient client, HttpPost post, IWebsiteHandler handler);
    void executeGet(HttpClient client, HttpGet get, IWebsiteHandler handler);

    public interface IWebsiteHandler {
        void handle(String content);
    }
}
