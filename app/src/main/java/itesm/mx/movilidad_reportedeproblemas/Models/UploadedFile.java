package itesm.mx.movilidad_reportedeproblemas.Models;

import android.os.Parcel;
import android.os.Parcelable;

//////////////////////////////////////////////////////////
//Clase: UploadedFile
// Descripción: Modelo para subir archivos.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public class UploadedFile implements Parcelable {
    private long id;
    private long reportId;
    private String name;
    private byte[] bytes;

    public UploadedFile(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public UploadedFile() {}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(reportId);
        parcel.writeString(name);
        parcel.writeByteArray(bytes);
    }

    protected UploadedFile(Parcel in) {
        id = in.readLong();
        reportId = in.readLong();
        name = in.readString();
        bytes = in.createByteArray();
    }

    public static final Creator<UploadedFile> CREATOR = new Creator<UploadedFile>() {
        @Override
        public UploadedFile createFromParcel(Parcel in) {
            return new UploadedFile(in);
        }

        @Override
        public UploadedFile[] newArray(int size) {
            return new UploadedFile[size];
        }
    };
}
