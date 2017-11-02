package itesm.mx.movilidad_reportedeproblemas.Models;

/**
 * Created by juanc on 11/2/2017.
 */

public class Voicenote {
    private long id;
    private long reportId;
    private byte[] bytes;

    public Voicenote(byte[] bytes) {
        this.bytes = bytes;
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

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
