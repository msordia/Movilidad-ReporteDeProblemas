package itesm.mx.movilidad_reportedeproblemas.Models;

/**
 * Created by juanc on 11/2/2017.
 */

public class Comment {
    private long id;
    private long reportId;
    private String body;

    public Comment(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
