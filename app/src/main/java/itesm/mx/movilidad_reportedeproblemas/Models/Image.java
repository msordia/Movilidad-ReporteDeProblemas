package itesm.mx.movilidad_reportedeproblemas.Models;

import android.os.Parcel;
import android.os.Parcelable;

//////////////////////////////////////////////////////////
//Clase: Image
// Descripción: Modelo para imagenes.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public class Image implements Parcelable {
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

    public Image() {}

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
