package itesm.mx.movilidad_reportedeproblemas.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juanc on 11/2/2017.
 */

public class Image implements Parcelable {
    private long id;
    private long reportId;
    private byte[] bytes;

    public Image(byte[] bytes) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(reportId);
        parcel.writeByteArray(bytes);
    }

    protected Image(Parcel in) {
        id = in.readLong();
        reportId = in.readLong();
        bytes = in.createByteArray();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
