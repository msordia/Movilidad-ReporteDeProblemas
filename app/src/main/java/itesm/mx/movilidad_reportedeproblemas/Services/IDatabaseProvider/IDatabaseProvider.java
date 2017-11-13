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

/**
 * Created by juanc on 10/31/2017.
 */

public interface IDatabaseProvider {
    void getReport(long id, IDbHandler<Report> handler);
    void getReports(IDbHandler<ArrayList<Report>> handler);
    void deleteReport(long id, IDbHandler<Boolean> handler);
    void addReport(Report report, IDbHandler<Long> handler);
    void updateStatus(long id, int status, IDbHandler<Boolean> handler);

    void getCategory(long id, IDbHandler<Category> handler);
    void getCategories(IDbHandler<ArrayList<Category>> handler);
    void deleteCategory(long id, IDbHandler<Boolean> handler);
    void addCategory(Category cateogry, IDbHandler<Long> handler);

    void getReportsForUser(String userId, IDbHandler<ArrayList<Report>> handler);

    void isAdmin(String userId, IDbHandler<Boolean> handler);
    void makeAdmin(String userId, IDbHandler<Boolean> handler);
    void removeAdmin(String userId, IDbHandler<Boolean> handler);

    void getCommentsForReport(long reportId, IDbHandler<ArrayList<Comment>> handler);
    void getImagesForReport(long reportId, IDbHandler<ArrayList<Image>> handler);
    void getVoicenotesForReport(long reportId, IDbHandler<ArrayList<Voicenote>> handler);
    void getFilesForReport(long reportId, IDbHandler<ArrayList<UploadedFile>> handler);

    void getFile(long id, IDbHandler<UploadedFile> handler);

    void addComment(Comment comment, IDbHandler<Long> handler);
    void addImage(Image image, IDbHandler<Long> handler);
    void addVoicenote(Voicenote voicenote, IDbHandler<Long> handler);
    void addFile(UploadedFile file, IDbHandler<Long> handler);

    public interface IDbHandler<T> {
        void handle(T result);
    }
}
