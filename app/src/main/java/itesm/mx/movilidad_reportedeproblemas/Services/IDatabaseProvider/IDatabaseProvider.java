package itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider;

import android.speech.tts.Voice;

import java.util.ArrayList;
import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Comment;
import itesm.mx.movilidad_reportedeproblemas.Models.Image;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.Models.UploadedFile;
import itesm.mx.movilidad_reportedeproblemas.Models.User;
import itesm.mx.movilidad_reportedeproblemas.Models.Voicenote;

//////////////////////////////////////////////////////////
//Clase: IDatabaseProvider
// Descripción: Interfaz de la base de datos.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public interface IDatabaseProvider {
    void getReports(IDbHandler<ArrayList<Report>> handler);
    void addReport(Report report, IDbHandler<Long> handler);
    void updateStatus(long id, int status, IDbHandler<Boolean> handler);

    void getCategories(IDbHandler<ArrayList<Category>> handler);
    void addCategory(Category cateogry, IDbHandler<Long> handler);

    void getReportsForUser(String userId, IDbHandler<ArrayList<Report>> handler);

    void isAdmin(String userId, IDbHandler<Boolean> handler);
    void makeAdmin(String userId, IDbHandler<Boolean> handler);
    void removeAdmin(String userId, IDbHandler<Boolean> handler);

    void getCommentsForReport(long reportId, IDbHandler<ArrayList<Comment>> handler);
    void getImagesForReport(long reportId, IDbHandler<ArrayList<Image>> handler);
    void getVoicenotesForReport(long reportId, IDbHandler<ArrayList<Voicenote>> handler);
    void getFilesForReport(long reportId, IDbHandler<ArrayList<UploadedFile>> handler);

    interface IDbHandler<T> {
        void handle(T result);
    }
}
