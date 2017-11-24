package itesm.mx.movilidad_reportedeproblemas.Models;

import android.os.Parcel;
import android.os.Parcelable;

//////////////////////////////////////////////////////////
//Clase: Comment
// Descripción: Modelo para comentarios.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class Comment implements Parcelable {
    private long id;
    private long reportId;
    private String body;

    public Comment(String body) {
        this.body = body;
    }
    public Comment() {}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(reportId);
        parcel.writeString(body);
    }

    protected Comment(Parcel in) {
        id = in.readLong();
        reportId = in.readLong();
        body = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
