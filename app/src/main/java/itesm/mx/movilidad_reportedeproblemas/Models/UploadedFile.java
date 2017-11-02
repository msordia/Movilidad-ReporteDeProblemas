package itesm.mx.movilidad_reportedeproblemas.Models;

/**
 * Created by juanc on 11/2/2017.
 */

public class UploadedFile {
    private long id;
    private long reportId;
    private String name;
    private byte[] bytes;

    public UploadedFile(String name, byte[] bytes) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
