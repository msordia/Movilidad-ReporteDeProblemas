package itesm.mx.movilidad_reportedeproblemas.Services.IWebsiteReader;

/**
 * Created by juanc on 11/7/2017.
 */

public interface IWebsiteReader {
    void getWebsiteContent(String url, IWebsiteHandler handler);

    public interface IWebsiteHandler {
        void handle(String content);
    }
}
