package itesm.mx.movilidad_reportedeproblemas.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juanc on 11/2/2017.
 */

public class Voicenote implements Parcelable {
    private long id;
    private long reportId;
    private byte[] bytes;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Voicenote(byte[] bytes) {
        this.bytes = bytes;
    }

    public Voicenote(){};

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

    protected Voicenote(Parcel in) {
        id = in.readLong();
        reportId = in.readLong();
        bytes = in.createByteArray();
    }

    public static final Creator<Voicenote> CREATOR = new Creator<Voicenote>() {
        @Override
        public Voicenote createFromParcel(Parcel in) {
            return new Voicenote(in);
        }

        @Override
        public Voicenote[] newArray(int size) {
            return new Voicenote[size];
        }
    };
}
