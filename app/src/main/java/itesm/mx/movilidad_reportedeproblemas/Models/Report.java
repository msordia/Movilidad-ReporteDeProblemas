package itesm.mx.movilidad_reportedeproblemas.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by juanc on 10/31/2017.
 */

public class Report {
    private long id;
    private long categoryId;
    private String userId;
    private Collection<Comment> comments = new ArrayList<>();
    private Collection<Image> images = new ArrayList<>();
    private Collection<Voicenote> voicenotes = new ArrayList<>();
    private Collection<UploadedFile> files = new ArrayList<>();
    private double longitude;
    private double latitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public Collection<Voicenote> getVoicenotes() {
        return voicenotes;
    }

    public Collection<UploadedFile> getFiles() {
        return files;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void log() {
        Log.i("LogReport", "Report " + getId() + ", User: " + getUserId());
        Log.i("LogReport", String.format("(%f, %f) %s", this.getLatitude(), this.getLongitude(), this.getCategoryId()));
        Log.i("LogReport", "Comments:");
        for (Comment comment : this.getComments()) {
            Log.i("LogReport", "\t" + comment.getBody());
        }

        Log.i("LogReport", "Bitmaps:");
        for (Image image : this.getImages()) {
            Log.i("LogReport", "\t" + image.getBytes().length + " bytes");
        }
        Log.i("LogReport", "Audios:");
        for (Voicenote voicenote : this.getVoicenotes()) {
            Log.i("LogReport", "\t" + voicenote.getBytes().length + " bytes");
        }

        Log.i("LogReport", "Files:");
        for (UploadedFile file : this.getFiles()) {
            Log.i("LogReport",  "\t" + file.getName() + ": " + file.getBytes().length + " bytes");
        }
    }
}
